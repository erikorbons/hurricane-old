package hurricane.core.rdf;

public interface RdfFormatter {
  String format(RdfMapper mapper, Triple<?> triple);
  String format(RdfMapper mapper, Graph graph);

}
