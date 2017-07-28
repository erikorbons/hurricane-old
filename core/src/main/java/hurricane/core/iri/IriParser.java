package hurricane.core.iri;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hurricane.core.encoding.PercentEncoding;

public final class IriParser {
	private final static Pattern iriPattern = Pattern.compile("^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?");
	private final static Pattern schemePattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9\\+\\-\\.]*$");
	private final static Pattern authorityPattern = Pattern.compile("^(([^@]+)@)?(([^\\:]+)|(\\[[0-9a-zA-Z\\:]+\\]))(\\:([0-9]+))?$");
	
	public static IriReference parseIRI(final String value) throws IriSyntaxException {
		final Matcher matcher = iriPattern.matcher(Objects.requireNonNull(value, "value cannot be null"));
		
		if (!matcher.matches()) {
			throw new IriSyntaxException("Invalid syntax");
		}
		
		
		return IriReference.of(
			parseScheme(matcher.group(2)),
			parseAuthority(matcher.group(4)),
			parsePath(matcher.group(5)),
			parseQuery(matcher.group(7)),
			parseFragment(matcher.group(9))
		);
	}

	private static Optional<String> parseScheme(final String scheme) throws IriSyntaxException {
		if (scheme == null) {
			return Optional.empty();
		}

		if (!schemePattern.matcher(scheme).matches()) {
			throw new IriSyntaxException(
				String.format("Invalid IriReference scheme. %s does not match %s.", scheme, schemePattern.toString())
			);
		}
		
		return Optional.of(scheme);
	}
	
	private static Optional<String> parseQuery(final String query) {
		if (query == null) {
			return Optional.empty();
		}
		
		return Optional.of(PercentEncoding.decode(query));
	}
	
	private static Optional<String> parseFragment(final String fragment) {
		if (fragment == null) {
			return Optional.empty();
		}
		
		return Optional.of(PercentEncoding.decode(fragment));
	}
	
	public static Optional<Path> parsePath(final String path) throws IriSyntaxException {
		if (path == null || path.isEmpty()) {
			return Optional.empty();
		}
		
		// Split the path:
		final List<String> parts = new ArrayList<>();
		
		int foundPosition;
		int startIndex = 0;
		while ((foundPosition = path.indexOf('/', startIndex)) > -1) {
			parts.add(PercentEncoding.decode(path.substring(startIndex, foundPosition)));
			startIndex = foundPosition + 1;
		}
		if (startIndex < path.length() - 1) {
			parts.add(path.substring(startIndex));
		}

		if (parts.size() >= 1 && parts.get(0).isEmpty()) {
			// Absolute path:
			return Optional.of(createPath(true, parts.subList(1, parts.size()).toArray(new String[parts.size() - 1])));
		}
		
		// Relative path:
		return Optional.of(createPath(false, parts.toArray(new String[parts.size()])));
	}
	
	private static Path createPath(final boolean isAbsolute, final String ... segments) {
		// Treat empty paths differently:
		if (isAbsolute && segments.length == 0) {
			return Paths.emptyAbsolute();
		}
		
		if (segments.length == 1 && isAbsolute) {
			return Paths.singleAbsolute(segments[0]);
		}
		
		if (segments.length == 1 && !isAbsolute) {
			return Paths.singleRelative(segments[0]);
		}
		
		return new DefaultPath(isAbsolute, segments);
	}
	
	public static Optional<Authority> parseAuthority(final String authority) throws IriSyntaxException {
		if (authority == null) {
			return Optional.empty();
		}
		
		final Matcher matcher = authorityPattern.matcher(authority);
		
		if (!matcher.matches()) {
			throw new IriSyntaxException(
				String.format("Authority section syntax error. %s does not match %s", authority, authorityPattern.toString())
			);
		}
		
		return Optional.of(new DefaultAuthority(
				Optional.ofNullable(matcher.group(2)).map(PercentEncoding::decode), 
				PercentEncoding.decode(matcher.group(3)), 
				Optional.ofNullable(matcher.group(7)).map(Integer::valueOf)
			));
	}
}
