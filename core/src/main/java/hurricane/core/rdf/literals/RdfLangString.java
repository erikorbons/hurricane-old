package hurricane.core.rdf.literals;

import hurricane.core.iri.IriReference;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public final class RdfLangString extends ObjectLiteral<String> {
  private final static IriReference iri = IriReference.ofXmlSchema("string");

  private final Locale lang;

  public RdfLangString(final String value, final Locale lang) {
    super(value);

    this.lang = Objects.requireNonNull(lang, "lang cannot be null");
  }

  @Override
  public String getLexicalForm() {
    return getValue();
  }

  @Override
  public IriReference getDataTypeIri() {
    return iri;
  }

  @Override
  public Optional<Locale> getLanguageTag() {
    return Optional.of(lang);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    RdfLangString that = (RdfLangString) o;
    return Objects.equals(lang, that.lang) && Objects.equals(getValue(), that.getValue());
  }

  @Override
  public int hashCode() {

    return Objects.hash(super.hashCode(), lang);
  }
}
