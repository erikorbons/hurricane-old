package hurricane.core.iri;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntPredicate;

import hurricane.core.encoding.CharacterUtils;
import hurricane.core.encoding.PercentEncoding;

/**
 * Generic IRI, optionally containing all components.
 */
public interface IriReference {
	
	Optional<String> getScheme();
	Optional<Authority> getAuthority();
	Optional<Path> getPath();
	Optional<String> getQuery();
	Optional<String> getFragment();
	
	default IriReference applyReference(final IriReference reference) {
		Objects.requireNonNull(reference, "reference cannot be null");
		
		final Optional<String> targetScheme;
		final Optional<Authority> targetAuthority;
		final Optional<Path> targetPath;
		final Optional<String> targetQuery;
		final Optional<String> targetFragment;
		
		final Optional<String> referenceScheme = reference.getScheme();
		final Optional<Authority> referenceAuthority = reference.getAuthority();
		final Optional<Path> referencePath = reference.getPath();
		final Optional<String> referenceQuery = reference.getQuery();
		
		if (referenceScheme.isPresent()) {
			targetScheme = referenceScheme;
			targetAuthority = referenceAuthority;
			targetPath = reference.getPath();
			targetQuery = reference.getQuery();
		} else {
			if (referenceAuthority.isPresent()) {
				targetAuthority = referenceAuthority;
				targetPath = referencePath;
				targetQuery = referenceQuery;
			} else {
				if (!referencePath.isPresent()) {
					targetPath = getPath();
					if (referenceQuery.isPresent()) {
						targetQuery = referenceQuery;
					} else {
						targetQuery = getQuery();
					}
				} else {
					if (referencePath.get().isAbsolute()) {
						targetPath = Optional.of(referencePath.get().removeDotSegments());
					} else {
						targetPath = Optional.of(getPath()
								.orElse(new DefaultPath(true))
								.merge(referencePath.get())
								.removeDotSegments());
					}
					targetQuery = referenceQuery;
				}
				targetAuthority = getAuthority();
			}
			targetScheme = getScheme();
		}
		
		targetFragment = reference.getFragment();
		
		return of(targetScheme, targetAuthority, targetPath, targetQuery, targetFragment);
	}
	
	default IriReference applyFragment(final String fragment) {
		return applyReference(new DefaultIriReference(
				Optional.empty(), 
				Optional.empty(), 
				Optional.empty(), 
				Optional.empty(), 
				Optional.of(fragment)
			));
	}
	
	static IriReference of(
		final Optional<String> scheme, 
		final Optional<Authority> authority,
		final Optional<Path> path,
		final Optional<String> query,
		final Optional<String> fragment
	) {
		return new DefaultIriReference(scheme, authority, path, query, fragment);
	}
	
	static IriReference of(
		final Path path,
		final Optional<String> query,
		final Optional<String> fragment
	) {
		return new DefaultIriReference(Optional.empty(), Optional.empty(), Optional.of(path), query, fragment);
	}
	
	static IriReference of(final String iri) throws IriSyntaxException {
		return IriParser.parseIRI(iri);
	}
	
	static IriReference of(final String scheme, final String host, final String ... path) {
		return new DefaultIriReference(
				Optional.of(scheme), 
				Optional.of(new DefaultAuthority(
					Optional.<String>empty(), 
					host, 
					Optional.<Integer>empty()
				)), 
				Optional.of(new DefaultPath(true, path)), 
				Optional.empty(), 
				Optional.empty()
			);
	}

	default Optional<String> getEscapedAuthority() {
		return getAuthority().map(Authority::getEscapedString);
	}
	
	default Optional<String> getEscapedPath() {
		return getPath()
				.map(path -> {
					if (getScheme().isPresent()) {
						// First segment may contain a colon:
						return path.getEscapedString();
					} else {
						// First segment may not contain a colon:
						return path.getEscapedStringNoColon();
					}
				});
	}
	
	default Optional<String> getEscapedQuery() {
		return getQuery()
				.map(s -> PercentEncoding.encode(
						s, 
						((IntPredicate) CharacterUtils::isIPChar)
							.or(CharacterUtils::isIPrivate)
							.or(cp -> cp == '/' || cp == '?')
							.negate()
					));
	}
	
	default Optional<String> getEscapedFragment() {
		return getFragment()
				.map(s -> PercentEncoding.encode(
						s, 
						((IntPredicate) CharacterUtils::isIPChar)
							.or(cp -> cp == '/' || cp == '?')
							.negate()
					));
	}
	
	default String getEscapedString() {
		final StringBuilder builder = new StringBuilder();

		getScheme().ifPresent(scheme -> builder.append(scheme).append(":"));
		getEscapedAuthority().ifPresent(authority -> builder.append("//").append(authority));
		getEscapedPath().ifPresent(builder::append);
		getEscapedQuery().ifPresent(query -> builder.append("?").append(query));
		getEscapedFragment().ifPresent(fragment -> builder.append("#").append(fragment));
		
		return builder.toString();
	}
	
	default Optional<String> getASCIIAuthority() {
		return getAuthority().map(Authority::getASCIIString);
	}
	
	default Optional<String> getASCIIPath() {
		return getPath()
				.map(path -> {
					if (getScheme().isPresent()) {
						// First segment may contain a colon:
						return path.getASCIIString();
					} else {
						// First segment may not contain a colon:
						return path.getASCIIStringNoColon();
					}
				});
	}
	
	default Optional<String> getASCIIQuery() {
		return getQuery()
				.map(s -> PercentEncoding.encode(
						s, 
						((IntPredicate) CharacterUtils::isPChar)
							.or(cp -> cp == '/' || cp == '?')
							.negate()
					));
	}
	
	default Optional<String> getASCIIFragment() {
		return getFragment()
				.map(s -> PercentEncoding.encode(
						s, 
						((IntPredicate) CharacterUtils::isPChar)
							.or(cp -> cp == '/' || cp == '?')
							.negate()
					));
	}
	
	default String getASCIIString() {
		final StringBuilder builder = new StringBuilder();

		getScheme().ifPresent(scheme -> builder.append(scheme).append(":"));
		getASCIIAuthority().ifPresent(authority -> builder.append("//").append(authority));
		getASCIIPath().ifPresent(builder::append);
		getASCIIQuery().ifPresent(query -> builder.append("?").append(query));
		getASCIIFragment().ifPresent(fragment -> builder.append("#").append(fragment));
		
		return builder.toString();
	}
	
	default URI toURI() {
		try {
			return new URI(getASCIIString());
		} catch (URISyntaxException e) {
			// All IriReference's should be convertible to URI using their ASCII form. If not,
			// throw an unchecked exception:
			throw new IllegalStateException("IriReference could not be converted to URI", e);
		}
	}
}
