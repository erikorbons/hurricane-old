package hurricane.core.rdf;

import hurricane.core.iri.IriReference;
import java.util.Optional;

public interface Node {

  default Optional<IriReference> asIri() {
    return (this instanceof IriNode)
        ? Optional.of(((IriNode) this).getIri())
        : Optional.empty();
  }

  default Optional<Literal> asLiteral() {
    return (this instanceof Literal)
        ? Optional.of((Literal) this)
        : Optional.empty();
  }
}
