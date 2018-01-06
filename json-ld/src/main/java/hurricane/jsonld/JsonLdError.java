package hurricane.jsonld;

import java.util.Objects;

public class JsonLdError extends RuntimeException {
  private final JsonLdErrorCode code;

  public JsonLdError(final JsonLdErrorCode code) {
    this(code, code.getDescription(), null);
  }

  public JsonLdError(final JsonLdErrorCode code, final Throwable cause) {
    this(code, code.getDescription(), cause);
  }

  public JsonLdError(final JsonLdErrorCode code, final String message) {
    this(code, Objects.requireNonNull(message, "message cannot be null"), null);
  }

  public JsonLdError(final JsonLdErrorCode code, final String message, final Throwable cause) {
    super(message, cause);

    this.code = Objects.requireNonNull(code, "code cannot be null");
  }

  public JsonLdErrorCode getCode() {
    return code;
  }
}
