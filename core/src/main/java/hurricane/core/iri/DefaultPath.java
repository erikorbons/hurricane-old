package hurricane.core.iri;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DefaultPath implements Path {
	
	private final boolean isAbsolute;
	private final String[] segments;
	
	public DefaultPath(final boolean isAbsolute, final String ... segments) {
		if (!isAbsolute && segments.length == 0) {
			throw new IllegalArgumentException("Path may not be empty");
		}
		
		this.isAbsolute = isAbsolute;
		this.segments = Arrays.copyOf(segments, segments.length);
	}
	
	/* (non-Javadoc)
	 * @see hurricane.core.iri.Path#isAbsolute()
	 */
	@Override
	public boolean isAbsolute() {
		return isAbsolute;
	}
	
	/* (non-Javadoc)
	 * @see hurricane.core.iri.Path#getSegments()
	 */
	@Override
	public List<String> getSegments() {
		return Collections.unmodifiableList(Arrays.asList(segments));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isAbsolute ? 1231 : 1237);
		result = prime * result + Arrays.hashCode(segments);
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
		DefaultPath other = (DefaultPath) obj;
		if (isAbsolute != other.isAbsolute)
			return false;
		if (!Arrays.equals(segments, other.segments))
			return false;
		return true;
	}
	
}
