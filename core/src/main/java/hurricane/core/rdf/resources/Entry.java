package hurricane.core.rdf.resources;

import hurricane.core.iri.IriReference;
import hurricane.core.rdf.Node;
import java.util.Objects;

public interface Entry {
  IriReference getPredicate();
  Node getValue();

  static Entry of(final IriReference predicate, final Node value) {
    return new DefaultEntry(predicate, value);
  }

  class DefaultEntry implements Entry {

    private final IriReference predicate;
    private final Node value;

    public DefaultEntry(final IriReference predicate, final Node value) {
      this.predicate = Objects.requireNonNull(predicate, "predicate cannot be null");
      this.value = Objects.requireNonNull(value, "value cannot be null");
    }

    @Override
    public IriReference getPredicate() {
      return predicate;
    }

    @Override
    public Node getValue() {
      return value;
    }
  }
}
