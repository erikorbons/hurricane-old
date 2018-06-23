package hurricane.core.rdf.literals;

import hurricane.core.iri.IriReference;
import hurricane.core.rdf.Literal;
import java.io.StringWriter;
import java.util.Objects;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DocumentFragment;

public final class RdfXmlLiteral implements Literal {

  private static final TransformerFactory transformerFactory = TransformerFactory.newInstance();
  private static final IriReference iri = IriReference.ofRdfSyntax("XMLLiteral");

  private final DocumentFragment documentFragment;

  public RdfXmlLiteral(final DocumentFragment documentFragment) {
    this.documentFragment = (DocumentFragment) Objects
        .requireNonNull(documentFragment, "documentFragment cannot be null").cloneNode(true);
    this.documentFragment.normalize();
  }

  public DocumentFragment getValue() {
    return (DocumentFragment) documentFragment.cloneNode(true);
  }

  @Override
  public String getLexicalForm() {
    final DOMSource source = new DOMSource(documentFragment);
    final StringWriter writer = new StringWriter();
    final StreamResult result = new StreamResult(writer);

    try {
      transformerFactory.newTransformer().transform(source, result);
    } catch (TransformerException e) {
      throw new IllegalStateException(e);
    }

    return writer.toString();
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
    RdfXmlLiteral that = (RdfXmlLiteral) o;
    return Objects.equals(documentFragment, that.documentFragment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(documentFragment);
  }
}
