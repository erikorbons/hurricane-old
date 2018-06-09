package hurricane.core.rdf.literals;

import hurricane.core.iri.IriReference;
import hurricane.core.rdf.Literal;
import java.util.Objects;

public final class XsdBoolean implements Literal {
  private final static IriReference iri = IriReference.ofXmlSchema("boolean");

  private final boolean value;

  public XsdBoolean(final boolean value) {
    this.value = value;
  }

  @Override
  public String getLexicalForm() {
    return Boolean.toString(value);
  }

  @Override
  public IriReference getDataTypeIri() {
    return iri;
  }

  public boolean getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    XsdBoolean that = (XsdBoolean) o;
    return value == that.value;
  }

  @Override
  public int hashCode() {

    return Objects.hash(value);
  }
}
