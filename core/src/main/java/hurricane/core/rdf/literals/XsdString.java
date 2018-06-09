package hurricane.core.rdf.literals;

import hurricane.core.iri.IriReference;

public final class XsdString extends ObjectLiteral<String> {
  private final static IriReference iri = IriReference.ofXmlSchema("string");

  public XsdString(final String value) {
    super(value);
  }

  @Override
  public String getLexicalForm() {
    return getValue();
  }

  @Override
  public IriReference getDataTypeIri() {
    return iri;
  }
}
