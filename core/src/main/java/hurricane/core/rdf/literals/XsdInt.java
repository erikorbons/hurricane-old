package hurricane.core.rdf.literals;

import hurricane.core.iri.IriReference;
import hurricane.core.rdf.Literal;
import java.util.Objects;

public final class XsdInt implements Literal {
  private final static IriReference iri = IriReference.ofXmlSchema("int");

  private final int value;

  public XsdInt(final int value) {
    this.value = value;
  }

  @Override
  public String getLexicalForm() {
    return Integer.toString(value);
  }

  @Override
  public IriReference getDataTypeIri() {
    return iri;
  }

  public int getValue() {
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
    XsdInt xsdInt = (XsdInt) o;
    return value == xsdInt.value;
  }

  @Override
  public int hashCode() {

    return Objects.hash(value);
  }
}
