package hurricane.core.iri;

import java.util.Objects;
import java.util.Optional;

public class DefaultAuthority implements Authority {

	private final String userInfo;
	private final String host;
	private final int port;
	
	public DefaultAuthority(final Optional<String> userInfo, final String host, final Optional<Integer> port) {
		this.userInfo = Objects.requireNonNull(userInfo, "userInfo cannot be null").orElse(null);
		this.host = Objects.requireNonNull(host, "host cannot be null");
		this.port = Objects.requireNonNull(port).orElse(-1);
	}
	
	public Optional<String> getUserinfo() {
		return Optional.ofNullable(userInfo);
	}
	
	public String getHost() {
		return host;
	}
	
	public Optional<Integer> getPort() {
		return port < 0 ? Optional.empty() : Optional.of(port);
	}
}
