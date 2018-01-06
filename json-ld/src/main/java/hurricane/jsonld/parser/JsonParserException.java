package hurricane.jsonld.parser;

public class JsonParserException extends Exception {
  private final long line;
  private final long lineOffset;

  public JsonParserException(final long line, final long lineOffset, final String message) {
    super (message);

    this.line = line;
    this.lineOffset = lineOffset;
  }

  public long getLine() {
    return line;
  }

  public long getLineOffset() {
    return lineOffset;
  }
}
