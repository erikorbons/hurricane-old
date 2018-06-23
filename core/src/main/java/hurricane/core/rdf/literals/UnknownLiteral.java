package hurricane.core.rdf.literals;

import hurricane.core.iri.IriReference;
import hurricane.core.rdf.Literal;
import java.util.Objects;

public final class UnknownLiteral implements Literal {

  private final IriReference iri;
  private final String lexicalForm;

  public UnknownLiteral(final IriReference iri, final String lexicalForm) {
    this.iri = Objects.requireNonNull(iri, "iri cannot be null");
    this.lexicalForm = Objects.requireNonNull("lexicalForm cannot be null");
  }

  @Override
  public String getLexicalForm() {
    return lexicalForm;
  }

  @Override
  public IriReference getDataTypeIri() {
    return iri;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UnknownLiteral that = (UnknownLiteral) o;
    return Objects.equals(iri, that.iri) &&
        Objects.equals(lexicalForm, that.lexicalForm);
  }

  @Override
  public int hashCode() {

    return Objects.hash(iri, lexicalForm);
  }
}
