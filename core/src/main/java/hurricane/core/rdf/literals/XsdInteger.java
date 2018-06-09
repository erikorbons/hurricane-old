package hurricane.core.rdf.literals;

import hurricane.core.iri.IriReference;
import java.math.BigInteger;

public final class XsdInteger extends ObjectLiteral<BigInteger> {
  private final static IriReference iri = IriReference.ofXmlSchema("integer");

  public XsdInteger(final BigInteger value) {
    super(value);
  }

  @Override
  public String getLexicalForm() {
    return getValue().toString();
  }

  @Override
  public IriReference getDataTypeIri() {
    return iri;
  }
}
