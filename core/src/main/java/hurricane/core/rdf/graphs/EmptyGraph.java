package hurricane.core.rdf.graphs;

import hurricane.core.rdf.Graph;
import java.util.Objects;

public enum EmptyGraph implements Graph {
  INSTANCE;

  @Override
  public Graph combineWith(Graph otherGraph) {
    return Objects.requireNonNull(otherGraph, "otherGraph cannot be null");
  }
}
