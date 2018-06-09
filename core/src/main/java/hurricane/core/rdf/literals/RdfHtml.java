package hurricane.core.rdf.literals;

import hurricane.core.iri.IriReference;
import hurricane.core.rdf.Literal;
import java.util.Objects;

public final class RdfHtml implements Literal {

  private final String value;

  public RdfHtml(final String value) {
    this.value = Objects.requireNonNull(value);
  }

  @Override
  public String getLexicalForm() {
    return value;
  }

  @Override
  public IriReference getDataTypeIri() {
    return null;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RdfHtml rdfHtml = (RdfHtml) o;
    return Objects.equals(value, rdfHtml.value);
  }

  @Override
  public int hashCode() {

    return Objects.hash(value);
  }
}
