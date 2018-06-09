package hurricane.core.rdf.literals;

import hurricane.core.iri.IriReference;
import hurricane.core.rdf.Literal;
import java.util.Objects;

public final class XsdByte implements Literal {
  private final static IriReference iri = IriReference.ofXmlSchema("byte");

  private final byte value;

  public XsdByte(final byte value) {
    this.value = value;
  }

  @Override
  public String getLexicalForm() {
    return Byte.toString(value);
  }

  @Override
  public IriReference getDataTypeIri() {
    return iri;
  }

  public byte getValue() {
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
    XsdByte xsdByte = (XsdByte) o;
    return value == xsdByte.value;
  }

  @Override
  public int hashCode() {

    return Objects.hash(value);
  }
}
