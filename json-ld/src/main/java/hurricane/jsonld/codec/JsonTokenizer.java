package hurricane.jsonld.codec;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.async.ByteArrayFeeder;
import com.fasterxml.jackson.core.async.NonBlockingInputFeeder;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;
import org.springframework.core.codec.DecodingException;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import reactor.core.publisher.Flux;

public class JsonTokenizer implements Function<DataBuffer, Flux<TokenBuffer>>, Closeable {

  private final JsonParser jsonParser;
  private final int bufferSize;
  private ByteArrayFeeder inputFeeder;
  private TokenBuffer tokenBuffer;
  private int tokenCount;
  private int parsingDepth;

  public JsonTokenizer(final JsonParser jsonParser, final int bufferSize) {
    this.jsonParser = Objects.requireNonNull(jsonParser, "jsonParser cannot be null");
    if (bufferSize <= 0) {
      throw new IllegalArgumentException("bufferSize should be > 0");
    }
    this.bufferSize = bufferSize;

    this.inputFeeder = (ByteArrayFeeder) jsonParser.getNonBlockingInputFeeder();
    this.tokenBuffer = new TokenBuffer(jsonParser);
    this.tokenCount = 0;
    this.parsingDepth = 0;
  }

  @Override
  public Flux<TokenBuffer> apply(final DataBuffer dataBuffer) {
    try {
      final byte[] data = new byte[dataBuffer.readableByteCount()];
      dataBuffer.read(data);

      this.inputFeeder.feedInput(data, 0, data.length);

      // Consume JSON tokens until no more tokens are available:
      while (true) {
        final JsonToken token = jsonParser.nextToken();
        if (token == JsonToken.NOT_AVAILABLE) {
          break;
        }

        // Update the parsing depth:
        if (token == JsonToken.START_ARRAY || token == JsonToken.START_OBJECT) {
          ++ parsingDepth;
        } else if (token == JsonToken.END_ARRAY || token == JsonToken.END_OBJECT) {
          -- parsingDepth;
        }

        // Copy the token into the buffer:
        tokenBuffer.copyCurrentEvent(jsonParser);
        ++ tokenCount;
      }

      // Flush the token buffer if it is large enough or if the end of the stream was reached:
      final Flux<TokenBuffer> result;
      if (tokenCount >= bufferSize) {
        result = Flux.just(tokenBuffer);
        tokenBuffer = new TokenBuffer(jsonParser);
        tokenCount = 0;
      } else {
        result = Flux.empty();
      }

      return result;
    } catch (Throwable t) {
      return Flux.error(new DecodingException("JSON decoding failed: " + t.getMessage(), t));
    } finally {
      DataBufferUtils.release(dataBuffer);
    }
  }

  @Override
  public void close() {
    inputFeeder.endOfInput();
  }
}
