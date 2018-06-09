package hurricane.core.rdf.literals;

import hurricane.core.iri.IriReference;

public final class XsdAnyUri extends ObjectLiteral<IriReference> {
  private final static IriReference iri = IriReference.ofXmlSchema("anyURI");

  public XsdAnyUri(final IriReference value) {
    super(value);
  }

  @Override
  public String getLexicalForm() {
    return getValue().getEscapedString();
  }

  @Override
  public IriReference getDataTypeIri() {
    return iri;
  }
}
