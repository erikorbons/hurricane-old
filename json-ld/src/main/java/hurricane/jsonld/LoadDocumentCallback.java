package hurricane.jsonld;

import hurricane.core.iri.IriReference;
import java.util.concurrent.CompletionStage;

@FunctionalInterface
public interface LoadDocumentCallback {
  CompletionStage<RemoteDocument> loadDocument(final IriReference url);
}
