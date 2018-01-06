package hurricane.core.rdf.resources;

import hurricane.core.iri.IriReference;
import hurricane.core.rdf.Triple;
import java.util.Optional;
import java.util.stream.Stream;

public interface ResourceCollection {

  Optional<Resource> getResource(IriReference id);

  static ResourceCollection ofResources(final Resource ... resources) {
    return ofResources(Stream.of(resources));
  }

  static ResourceCollection ofResources(final Stream<Resource> resources) {
    return null;
  }

  static ResourceCollection ofTriples(final Triple ... triples) {
    return ofTriples(Stream.of(triples));
  }

  static ResourceCollection ofTriples(final Stream<Triple> triples) {
    return null;
  }
}
