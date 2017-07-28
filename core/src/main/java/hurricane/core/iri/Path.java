package hurricane.core.iri;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.IntPredicate;

import hurricane.core.encoding.CharacterUtils;
import hurricane.core.encoding.PercentEncoding;

public interface Path {

	boolean isAbsolute();
	List<String> getSegments();
	
	default Path merge(final Path other) {
		Objects.requireNonNull(other, "other cannot be null");
		
		if (other.isAbsolute()) {
			return other;
		}
		
		final List<String> segments = getSegments();
		final List<String> otherSegments = other.getSegments();
		
		// Special case: absolute empty path:
		if (segments.isEmpty()) {
			return new DefaultPath(true, otherSegments.toArray(new String[otherSegments.size()]));
		}
		
		// Append the paths:
		final String[] newSegments = new String[segments.size() - 1 + otherSegments.size()];
		
		for (int i = 0; i < segments.size() - 1; ++ i) {
			newSegments[i] = segments.get(i);
		}
		for (int i = 0, length = otherSegments.size(); i < length; ++ i) {
			newSegments[segments.size() - 1 + i] = otherSegments.get(i);
		}
		
		return new DefaultPath(isAbsolute(), newSegments);
	}
	
	default Path removeDotSegments() {
		final List<String> segments = getSegments();
		
		// Do nothing if there are no segments:
		if (segments.isEmpty()) {
			return this;
		}
		
		return this;
	}
	
	default String getEscapedString() {
		final StringJoiner joiner = new StringJoiner("/");
		
		for (final String s: getSegments()) {
			joiner.add(PercentEncoding.encode(
					s, 
					((IntPredicate) CharacterUtils::isIPChar)
						.negate()
				));
		}
		
		return isAbsolute()
				? "/" + joiner.toString()
				: joiner.toString();
	}
	
	default String getEscapedStringNoColon() {
		// No colon in first segment (isegment-nz-nc):
		final StringJoiner joiner = new StringJoiner("/");

		final List<String> segments = getSegments();
		final int length = segments.size();
		for (int i = 0; i < length; ++ i) {
			if (i == 0) {
				joiner.add(PercentEncoding.encode(
						segments.get(i), 
						((IntPredicate) CharacterUtils::isIUnreserved)
							.or(CharacterUtils::isSubDelimiter)
							.or(cp -> cp == '@')
							.negate()
					));
			} else {
				joiner.add(PercentEncoding.encode(
						segments.get(i), 
						((IntPredicate) CharacterUtils::isIPChar)
							.negate()
					));
			}
		}
		
		return isAbsolute()
				? "/" + joiner.toString()
				: joiner.toString();
	}
	
	default String getASCIIString() {
		final StringJoiner joiner = new StringJoiner("/");
		
		for (final String s: getSegments()) {
			joiner.add(PercentEncoding.encode(
					s, 
					((IntPredicate) CharacterUtils::isPChar)
						.negate()
				));
		}
		
		return isAbsolute()
				? "/" + joiner.toString()
				: joiner.toString();
	}
	
	default String getASCIIStringNoColon() {
		// No colon in first segment (segment-nz-nc):
		final StringJoiner joiner = new StringJoiner("/");

		final List<String> segments = getSegments();
		final int length = segments.size();
		for (int i = 0; i < length; ++ i) {
			if (i == 0) {
				joiner.add(PercentEncoding.encode(
						segments.get(i), 
						((IntPredicate) CharacterUtils::isUnreserved)
							.or(CharacterUtils::isSubDelimiter)
							.or(cp -> cp == '@')
							.negate()
					));
			} else {
				joiner.add(PercentEncoding.encode(
						segments.get(i), 
						((IntPredicate) CharacterUtils::isPChar)
							.negate()
					));
			}
		}
		
		return isAbsolute()
				? "/" + joiner.toString()
				: joiner.toString();
	}
}