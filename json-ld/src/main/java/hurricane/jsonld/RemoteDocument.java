package hurricane.jsonld;

import hurricane.core.iri.IriReference;
import java.util.Optional;

public interface RemoteDocument {
  Optional<IriReference> getContextUrl();
  IriReference getDocumentUrl();

}
