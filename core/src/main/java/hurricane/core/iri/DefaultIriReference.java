package hurricane.core.iri;

import java.util.Objects;
import java.util.Optional;

/**
 * https://tools.ietf.org/html/rfc3986
 */
public class DefaultIriReference implements IriReference {
	private final String scheme;
	private final Authority authority;
	private final Path path;
	private final String query;
	private final String fragment;
	
	public DefaultIriReference(
			final Optional<String> scheme, 
			final Optional<Authority> authority,
			final Optional<Path> path,
			final Optional<String> query,
			final Optional<String> fragment
	) {
		this.scheme = Objects.requireNonNull(scheme, "scheme cannot be null")
				.orElse(null);
		this.authority = Objects.requireNonNull(authority, "authority cannot be null")
				.orElse(null);
		this.path = Objects.requireNonNull(path, "path cannot be null")
				.orElse(null);
		this.query = Objects.requireNonNull(query, "query cannot be null")
				.orElse(null);
		this.fragment = Objects.requireNonNull(fragment, "fragment cannot be null")
				.orElse(null);
	}

	@Override
	public Optional<String> getScheme() {
		return Optional.ofNullable(scheme);
	}
	
	@Override
	public Optional<Authority> getAuthority() {
		return Optional.ofNullable(authority);
	}
	
	@Override
	public Optional<Path> getPath() {
		return Optional.ofNullable(path);
	}

	@Override
	public Optional<String> getQuery() {
		return Optional.ofNullable(query);
	}
	
	@Override
	public Optional<String> getFragment() {
		return Optional.ofNullable(fragment);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authority == null) ? 0 : authority.hashCode());
		result = prime * result + ((fragment == null) ? 0 : fragment.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((query == null) ? 0 : query.hashCode());
		result = prime * result + ((scheme == null) ? 0 : scheme.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultIriReference other = (DefaultIriReference) obj;
		if (authority == null) {
			if (other.authority != null)
				return false;
		} else if (!authority.equals(other.authority))
			return false;
		if (fragment == null) {
			if (other.fragment != null)
				return false;
		} else if (!fragment.equals(other.fragment))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (query == null) {
			if (other.query != null)
				return false;
		} else if (!query.equals(other.query))
			return false;
		if (scheme == null) {
			if (other.scheme != null)
				return false;
		} else if (!scheme.equals(other.scheme))
			return false;
		return true;
	}
}
