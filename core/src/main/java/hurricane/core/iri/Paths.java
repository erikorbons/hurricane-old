package hurricane.core.iri;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Paths {

	public static Path emptyAbsolute() {
		return Singletons.emptyAbsolutePath;
	}
	
	public static Path singleAbsolute(final String segment) {
		return new SingleSegmentAbsolutePath(segment);
	}
	
	public static Path singleRelative(final String segment) {
		return new SingleSegmentRelativePath(segment);
	}
	
	public static final class Singletons {
		public static EmptyAbsolutePath emptyAbsolutePath = new EmptyAbsolutePath();
	}
	
	public static final class EmptyAbsolutePath implements Path {

		@Override
		public boolean isAbsolute() {
			return true;
		}

		@Override
		public List<String> getSegments() {
			return Collections.emptyList();
		}
	}
	
	public static final class SingleSegmentAbsolutePath implements Path {

		private final String segment;
		
		public SingleSegmentAbsolutePath(final String segment) {
			this.segment = Objects.requireNonNull(segment, "segment cannot be null");
		}
		
		@Override
		public boolean isAbsolute() {
			return true;
		}

		@Override
		public List<String> getSegments() {
			return Collections.singletonList(segment);
		}
	}
	
	public static final class SingleSegmentRelativePath implements Path {

		private final String segment;
		
		public SingleSegmentRelativePath(final String segment) {
			this.segment = Objects.requireNonNull(segment, "segment cannot be null");
		}
		
		@Override
		public boolean isAbsolute() {
			return false;
		}

		@Override
		public List<String> getSegments() {
			return Collections.singletonList(segment);
		}
	}
}
