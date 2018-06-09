package hurricane.core.rdf;

import hurricane.core.iri.IriReference;

public interface IriNode extends Node {
  IriReference getIri();
}
