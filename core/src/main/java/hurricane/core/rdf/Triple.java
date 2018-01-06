package hurricane.core.rdf;

import hurricane.core.iri.IriReference;

public interface Triple {
  IriReference getSubject();
  IriReference getPredicate();
  Node getObject();
}
