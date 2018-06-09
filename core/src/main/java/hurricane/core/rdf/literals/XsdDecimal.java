package hurricane.core.rdf.literals;

import hurricane.core.iri.IriReference;
import java.math.BigDecimal;

public class XsdDecimal extends ObjectLiteral<BigDecimal> {
  private final static IriReference iri = IriReference.ofXmlSchema("decimal");

  public XsdDecimal(final BigDecimal value) {
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
