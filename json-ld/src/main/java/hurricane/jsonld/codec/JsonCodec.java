package hurricane.jsonld.codec;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.util.Objects;
import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;

public class JsonCodec {

  private final ObjectMapper objectMapper;

  public JsonCodec(final ObjectMapper objectMapper) {
    this.objectMapper = Objects.requireNonNull(objectMapper, "objectMapper cannot ben null");
  }

  public ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  public Flux<TokenBuffer> decode(final Flux<DataBuffer> in, final Charset charset) {
    try {
      final JsonFactory jsonFactory = getObjectMapper().getFactory();
      final JsonParser jsonParser = jsonFactory.createNonBlockingByteArrayParser();
      final JsonTokenizer tokenizer = new JsonTokenizer(jsonParser, 1000);

      return in.flatMap(tokenizer).doFinally(signal -> tokenizer.close());
    } catch (IOException e) {
      return Flux.error(new UncheckedIOException(e));
    }
  }
}
