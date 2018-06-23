package hurricane.core.rdf;

import hurricane.core.iri.IriReference;
import hurricane.core.rdf.formatters.NTriplesFormatter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RdfMapper {

  private final List<RdfDataTypeConverter> dataTypeConverters;
  private final Map<IriReference, RdfDataTypeConverter> iriToConverter;
  private final Map<Type, RdfDataTypeConverter> typeToConverter;
  private final RdfFormatter defaultFormatter;

  public RdfMapper(final List<RdfDataTypeConverter> dataTypeConverters) {
    this.dataTypeConverters = dataTypeConverters == null || dataTypeConverters.isEmpty()
        ? Collections.emptyList()
        : new ArrayList<>(dataTypeConverters);

    // Index the RDF data type converters:
    iriToConverter = this.dataTypeConverters.stream()
        .collect(Collectors.toMap(
            RdfDataTypeConverter::getDataTypeIri,
            Function.identity()
        ));
    typeToConverter = this.dataTypeConverters.stream()
        .collect(Collectors.toMap(
            RdfDataTypeConverter::getValueType,
            Function.identity()
        ));

    // Set the default formatter to NTriples:
    defaultFormatter = new NTriplesFormatter();
  }

  public Graph graph(final Object element) {
    return null;
  }

  public Graph graph(final Collection<?> elements) {
    return elements.stream()
        .map(this::graph)
        .reduce(Graph.empty(), Graph::combineWith);
  }

  public <T> Literal<T> literal(final T value) {
    return null;
  }

  public String format(final Triple<?> triple) {
    defaultFormatter.format(triple);
  }
}
