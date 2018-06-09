package hurricane.core.rdf.literals;

import hurricane.core.iri.IriReference;
import hurricane.core.rdf.Literal;
import java.util.Objects;

public final class XsdFloat implements Literal {
  private final static IriReference iri = IriReference.ofXmlSchema("float");

  private final float value;

  public XsdFloat(final float value) {
    this.value = value;
  }

  @Override
  public String getLexicalForm() {
    return Float.toString(value);
  }

  @Override
  public IriReference getDataTypeIri() {
    return iri;
  }

  public float getValue() {
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
    XsdFloat xsdFloat = (XsdFloat) o;
    return Float.compare(xsdFloat.value, value) == 0;
  }

  @Override
  public int hashCode() {

    return Objects.hash(value);
  }
}
