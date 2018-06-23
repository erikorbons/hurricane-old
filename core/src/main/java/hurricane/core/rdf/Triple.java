package hurricane.core.rdf;

import hurricane.core.iri.IriReference;

public interface Triple<T> {
  IriReference getSubject();
  IriReference getPredicate();
  T getObject();
}
