package hurricane.core.rdf;

import hurricane.core.rdf.graphs.EmptyGraph;

public interface Graph {

  Graph combineWith(Graph otherGraph);

  static Graph empty() {
    return EmptyGraph.INSTANCE;
  }
}
