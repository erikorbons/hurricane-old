package hurricane.core.iri;

import java.util.Optional;
import java.util.function.IntPredicate;

import hurricane.core.encoding.CharacterUtils;
import hurricane.core.encoding.PercentEncoding;

public interface Authority {
	
	Optional<String> getUserinfo();
	String getHost();
	Optional<Integer> getPort();
	
	default Optional<String> getEscapedUserinfo() {
		return getUserinfo()
			.map(s -> PercentEncoding.encode(
					s,
					((IntPredicate) CharacterUtils::isIUnreserved)
						.or(CharacterUtils::isSubDelimiter)
						.or(cp -> cp == ':')
						.negate()
				));
	}
	
	default String getEscapedHost() {
		final String host = getHost();
		
		if (host.startsWith("[")) {
			// IP6 addresses are returned as-is:
			return host;
		}

		return PercentEncoding.encode(
				host, 
				((IntPredicate) CharacterUtils::isIUnreserved)
					.or(CharacterUtils::isSubDelimiter)
					.negate()
			);
	}
	
	default Optional<String> getASCIIUserinfo() {
		return getUserinfo()
				.map(s -> PercentEncoding.encode(
						s,
						((IntPredicate) CharacterUtils::isUnreserved)
							.or(CharacterUtils::isSubDelimiter)
							.or(cp -> cp == ':')
							.negate()
					));
	}
	
	default String getASCIIHost() {
		final String host = getHost();
		
		if (host.startsWith("[")) {
			// IP6 addresses are returned as-is:
			return host;
		}

		return PercentEncoding.encode(
				host, 
				((IntPredicate) CharacterUtils::isUnreserved)
					.or(CharacterUtils::isSubDelimiter)
					.negate()
			);
	}
	
	default String getEscapedString() {
		final StringBuilder builder = new StringBuilder();

		getEscapedUserinfo().ifPresent(userInfo -> builder.append(userInfo).append("@"));
		builder.append(getEscapedHost());
		getPort().ifPresent(port -> builder.append(":").append(port.toString()));
		
		return builder.toString();
	}
	
	default String getASCIIString() {
		final StringBuilder builder = new StringBuilder();

		getASCIIUserinfo().ifPresent(userInfo -> builder.append(userInfo).append("@"));
		builder.append(getASCIIHost());
		getPort().ifPresent(port -> builder.append(":").append(port.toString()));
		
		return builder.toString();
	}
}
