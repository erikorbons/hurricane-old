package hurricane.jsonld.parser;

public interface JsonToken {
  TokenType tokenType();
  String getValue();
}
