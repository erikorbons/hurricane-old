package hurricane.core.rdf.literals;

import hurricane.core.iri.IriReference;
import hurricane.core.rdf.Literal;
import java.util.Objects;

public final class XsdLong implements Literal {
  private final static IriReference iri = IriReference.ofXmlSchema("long");

  private final long value;

  public XsdLong(final long value) {
    this.value = value;
  }

  @Override
  public String getLexicalForm() {
    return Long.toString(value);
  }

  @Override
  public IriReference getDataTypeIri() {
    return iri;
  }

  public long getValue() {
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
    XsdLong xsdLong = (XsdLong) o;
    return value == xsdLong.value;
  }

  @Override
  public int hashCode() {

    return Objects.hash(value);
  }
}
