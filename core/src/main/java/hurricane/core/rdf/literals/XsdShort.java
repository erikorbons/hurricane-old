package hurricane.core.rdf.literals;

import hurricane.core.iri.IriReference;
import hurricane.core.rdf.Literal;
import java.util.Objects;

public final class XsdShort implements Literal {
  private final static IriReference iri = IriReference.ofXmlSchema("short");

  private final short value;

  public XsdShort(final short value) {
    this.value = value;
  }

  @Override
  public String getLexicalForm() {
    return Short.toString(value);
  }

  @Override
  public IriReference getDataTypeIri() {
    return iri;
  }

  public short getValue() {
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
    XsdShort xsdShort = (XsdShort) o;
    return value == xsdShort.value;
  }

  @Override
  public int hashCode() {

    return Objects.hash(value);
  }
}
