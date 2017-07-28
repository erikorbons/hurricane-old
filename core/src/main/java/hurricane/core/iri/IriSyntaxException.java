package hurricane.core.iri;

public class IriSyntaxException extends Exception {
	private static final long serialVersionUID = -8700039687813282038L;
	
	public IriSyntaxException(final String message) {
		super(message);
	}
	
	public IriSyntaxException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	public IriSyntaxException(final Throwable cause) {
		super(cause);
	}
}
