package hurricane.core.rdf.literals;

import hurricane.core.iri.IriReference;
import hurricane.core.rdf.Literal;
import java.util.Objects;

public final class XsdDouble implements Literal {
  private final static IriReference iri = IriReference.ofXmlSchema("double");

  private final double value;

  public XsdDouble(final double value) {
    this.value = value;
  }

  @Override
  public String getLexicalForm() {
    return Double.toString(value);
  }

  @Override
  public IriReference getDataTypeIri() {
    return iri;
  }

  public double getValue() {
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
    XsdDouble xsdDouble = (XsdDouble) o;
    return Double.compare(xsdDouble.value, value) == 0;
  }

  @Override
  public int hashCode() {

    return Objects.hash(value);
  }
}
