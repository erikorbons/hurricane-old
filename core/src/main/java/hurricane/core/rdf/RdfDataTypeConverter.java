package hurricane.core.rdf;

import hurricane.core.iri.IriReference;
import java.lang.reflect.Type;

public interface RdfDataTypeConverter {

  IriReference getDataTypeIri();
  Type getValueType();


}
