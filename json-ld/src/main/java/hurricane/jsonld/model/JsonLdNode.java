package hurricane.jsonld.model;

import com.fasterxml.jackson.databind.JsonSerializable;
import hurricane.core.iri.IriReference;
import java.util.Optional;

public interface JsonLdNode extends JsonSerializable {

  Optional<JsonLdNode> get(String term);
  Optional<JsonLdNode> get(IriReference term);
  Optional<JsonLdNode> get(int index);

  JsonLdNode path(String term);
  JsonLdNode path(IriReference term);
  JsonLdNode path(int index);

  boolean isDictionary();
  boolean isArray();
  boolean isValue();
}
