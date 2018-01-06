package hurricane.jsonld.parser;

import com.fasterxml.jackson.core.JsonParseException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Stack;

public class JsonParser {

  private final int maxDepth;

  public JsonParser(final int maxDepth) {
    if (maxDepth < 1) {
      throw new IllegalArgumentException("maxDepth should be a positive integer");
    }

    this.maxDepth = maxDepth;
  }

  public Session parse() {
    return new Session();
  }

  private enum SessionState {
    GLOBAL,
    OBJECT_MEMBER_OR_END,
    OBJECT_MEMBER,
    KEY,
    EXPECT_COLON,
    VALUE,
    COMMA_OR_END_OBJECT,
    ARRAY_MEMBER_OR_END,
    ARRAY_MEMBER,
    COMMA_OR_END_ARRAY,
    EXPECT_EOF,

    STRING,
    NUMBER,
    CONSTANT,

    NEED_INPUT
  }

  private enum ScopeType {
    OBJECT,
    ARRAY
  }

  private static class Scope {
    private final ScopeType type;
    private final long line;
    private final long lineOffset;

    public Scope(final ScopeType type, final long line, final long lineOffset) {
      if (type != ScopeType.OBJECT && type != ScopeType.ARRAY) {
        throw new IllegalArgumentException("Scope type must be object or array");
      }

      this.type = type;
      this.line = line;
      this.lineOffset = lineOffset;
    }

    public ScopeType getType() {
      return type;
    }

    public long getLine() {
      return line;
    }

    public long getLineOffset() {
      return lineOffset;
    }
  }

  public class Session {
    private long globalOffset = 0;
    private long line = 1;
    private final LinkedList<String> inputStrings;
    private final Stack<Scope> scopes;
    private long lineOffset = 0;
    private int currentInputOffset = 0;
    private String currentInput;
    private SessionState state;
    private final StringBuilder token = new StringBuilder();

    public Session() {
      this.inputStrings = new LinkedList<>();
      this.scopes = new Stack<>();
    }

    public void addInput(final String input) throws JsonParserException {
      inputStrings.addLast(Objects.requireNonNull(input, "Input cannot be null"));

      // Parse tokens until all current input is exhausted:
      parse();
    }

    /**
     * Scans for tokens in any
     */
    private void parse() throws JsonParserException {
      while (true) {
        final SessionState newState;

        switch (state) {
          case GLOBAL:
            newState = parseGlobal();
            break;
          case OBJECT_MEMBER_OR_END:
            newState = parseObjectMember(true);
            break;
          case OBJECT_MEMBER:
            newState = parseObjectMember(false);
            break;
          case ARRAY_MEMBER_OR_END:
            newState = parseArrayMember(true);
            break;
          case ARRAY_MEMBER:
            newState = parseArrayMember(false);
            break;
          case COMMA_OR_END_ARRAY:
            newState = parseCommaOrEnd(']');
            break;
          case COMMA_OR_END_OBJECT:
            newState = parseCommaOrEnd('}');
            break;
          case EXPECT_COLON:
            newState = parseColon();
            break;
          case VALUE:
            newState = parseValue();
            break;
          case EXPECT_EOF:
            newState = parseEof();
            break;
          case STRING:
            newState = parseString();
            break;
          case NUMBER:
            newState = parseNumber();
            break;
          case CONSTANT:
            newState = parseConstant();
            break;
          default:
            throw new IllegalStateException("Invalid parser session state");
        }

        if (newState == SessionState.NEED_INPUT) {
          return;
        }
      }
    }

    private char skipWhitespace() {
      char c;
      while ((c = nextChar()) <= ' ') {
        if (c == 0) {
          return 0;
        }
      }
      return c;
    }

    private SessionState parseGlobal() throws JsonParserException {
      state = SessionState.GLOBAL;

      // Skip whitespace:
      final char c = skipWhitespace();
      if (c == 0) {
        return SessionState.NEED_INPUT;
      }

      switch (c) {
        case '{':
          scopes.push(new Scope(ScopeType.OBJECT, line, lineOffset));
          return SessionState.OBJECT_MEMBER_OR_END;
        case '[':
          scopes.push(new Scope(ScopeType.ARRAY, line, lineOffset));
          return SessionState.ARRAY_MEMBER_OR_END;
        default:
          throw new JsonParserException(
              line,
              lineOffset,
              String.format("Unexpected character %X, expected start of object or array", c)
          );
      }
    }

    private SessionState parseObjectMember(final boolean allowEnd) throws JsonParserException {
      final char c = skipWhitespace();
      if (c == 0) {
        return SessionState.NEED_INPUT;
      }

      switch (c) {
        case '}':
          if (allowEnd) {
            return finishScope();
          }
          break;
        case '"':
          token.setLength(0);
          return SessionState.KEY;
      }

      throw new JsonParserException(
          line,
          lineOffset,
          String.format("Unexpected character %X, expected object key", c)
      );
    }

    private SessionState parseArrayMember(final boolean allowEnd) throws JsonParserException {
      final char c = skipWhitespace();
      if (c == 0) {
        return SessionState.NEED_INPUT;
      }

      if (allowEnd && c == ']') {
        return finishScope();
      }

      return parseValue(c);
    }

    private SessionState parseValue() throws JsonParserException {
      final char c = skipWhitespace();
      if (c == 0) {
        return SessionState.NEED_INPUT;
      }

      return parseValue(c);
    }

    private SessionState parseValue(final char c) throws JsonParserException {
      switch (c) {
        case '{':
          scopes.push(new Scope(ScopeType.OBJECT, line, lineOffset));
          return SessionState.OBJECT_MEMBER_OR_END;
        case '[':
          scopes.push(new Scope(ScopeType.ARRAY, line, lineOffset));
          return SessionState.ARRAY_MEMBER_OR_END;
      }

      token.setLength(0);

      if (c == '"') {
        return SessionState.STRING;
      }

      if (c == '-' || (c >= '0' && c <= '9')) {
        token.append(c);
        return SessionState.NUMBER;
      }

      if (c >= 'a' && c <= 'z') {
        token.append(c);
        return SessionState.CONSTANT;
      }

      throw new JsonParserException(
          line,
          lineOffset,
          String.format("Unexpected character %X, expected array member", c)
      );
    }

    private SessionState parseString() throws JsonParserException {
      return null;
    }

    private SessionState parseNumber() throws JsonParserException {
      return null;
    }

    private SessionState parseConstant() throws JsonParserException {
      return null;
    }

    private SessionState parseCommaOrEnd(final char endCharacter) throws JsonParserException {
      final char c = skipWhitespace();
      if (c == 0) {
        return SessionState.NEED_INPUT;
      }

      if (c == endCharacter) {
        return finishScope();
      }

      if (c != ',') {
        throw new JsonParserException(
            line,
            lineOffset,
            String.format("Unexpected character %X, expected comma or end of object", c)
        );
      }

      if (scopes.peek().getType() == ScopeType.OBJECT) {
        return SessionState.OBJECT_MEMBER;
      } else {
        return SessionState.ARRAY_MEMBER;
      }
    }

    private SessionState parseColon() throws JsonParserException {
      final char c = skipWhitespace();
      if (c == 0) {
        return SessionState.NEED_INPUT;
      }

      if (c == ':') {
        return SessionState.VALUE;
      }

      throw new JsonParserException(
          line,
          lineOffset,
          String.format("Unexpected character %X, expected ':'", c)
      );
    }

    private SessionState parseEof() throws JsonParserException {
      final char c = skipWhitespace();
      if (c == 0) {
        return SessionState.NEED_INPUT;
      }

      throw new JsonParserException(
          line,
          lineOffset,
          String.format("Unexpected character %X, expected EOF", c)
      );
    }

    private SessionState finishScope() throws JsonParserException {
      final Scope scope = scopes.pop();

      if (scopes.isEmpty()) {
        // The top level element has been parsed, expect end of file:
        return SessionState.EXPECT_EOF;
      }

      switch (scopes.peek().getType()) {
        case ARRAY:
          return SessionState.COMMA_OR_END_ARRAY;
        case OBJECT:
          return SessionState.COMMA_OR_END_OBJECT;
        default:
          throw new IllegalStateException("Invalid scope type");
      }
    }

    private char nextChar() {
      // Get the next input if needed, skip empty strings:
      while (currentInput == null || currentInputOffset >= currentInput.length()) {
        if (inputStrings.isEmpty()) {
          return 0;
        }

        currentInput = inputStrings.pollFirst();
        currentInputOffset = 0;
      }

      // Take the next character from the current input string:
      final char result = currentInput.charAt(currentInputOffset);
      ++ currentInputOffset;
      ++ globalOffset;
      ++ lineOffset;

      if (result == '\n') {
        ++ line;
        lineOffset = 0;
      }

      return result;
    }
  }
}
