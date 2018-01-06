package hurricane.core.rdf.resources;

import hurricane.core.iri.IriReference;
import hurricane.core.rdf.Node;
import java.util.Optional;

public interface Resource {

  IriReference getId();

  Optional<Node> getProperty(IriReference predicate);

  Resource withProperty(IriReference predicate, Node value);

  static Resource of(final IriReference id, final Entry ... entries) {
    return null;
  }



}
