package hurricane.jsonld;

public enum JsonLdErrorCode {
  COLLIDING_KEYWORDS("colliding keywords"),
  COMPACTION_TO_LIST_OF_LISTS("compaction to list of lists"),
  CONFLICTING_INDEXES("conflicting indexes"),
  CYCLIC_IRI_MAPPING("cyclic IRI mapping"),
  INVALID_ID_VALUE("invalid @id value"),
  INVALID_INDEX_VALUE("invalid @index value"),
  INVALID_NEST_VALUE("invalid @nest value"),
  INVALID_PREFIX_VALUE("invalid @prefix value"),
  INVALID_REVERSE_VALUE("invalid @reverse value"),
  INVALID_VERSION_VALUE("invalid @version value"),
  INVALID_BASE_IRI("invalid base IRI"),
  INVALID_CONTAINER_MAPPING("invalid container mapping"),
  INVALID_DEFAULT_LANGUAGE("invalid default language"),
  INVALID_IRI_MAPPING("invalid IRI mapping"),
  INVALID_KEYWORD_ALIAS("invalid keyword alias"),
  INVALID_LANGUAGE_MAP_VALUE("invalid language map value"),
  INVALID_LANGUAGE_MAPPING("invalid language mapping"),
  INVALID_LANGUAGE_TAGGED_STRING("invalid language-tagged string"),
  INVALID_LANGUAGE_TAGGED_VALUE("invalid language-tagged value"),
  INVALID_LOCAL_CONTEXT("invalid local context"),
  INVALID_REMOTE_CONTEXT("invalid remote context"),
  INVALID_REVERSE_PROPERTY("invalid reverse property"),
  INVALID_REVERSE_PROPERTY_MAP("invalid reverse property map"),
  INVALID_REVERSE_PROPERTY_VALUE("invalid reverse property value"),
  INVALID_SCOPED_CONTEXT("invalid scoped context"),
  INVALID_SET_OR_LIST_OBJECT("invalid set or list object"),
  INVALID_TERM_DEFINITION("invalid term definition"),
  INVALID_TYPE_MAPPING("invalid type mapping"),
  INVALID_TYPE_VALUE("invalid type value"),
  INVALID_TYPED_VALUE("invalid typed value"),
  INVALID_VALUE_OBJECT("invalid value object"),
  INVALID_VALUE_OBJECT_VALUE("invalid value object value"),
  INVALID_VOCAB_MAPPING("invalid vocab mapping"),
  KEYWORD_REDEFINITION("keyword redefinition"),
  LIST_OF_LISTS("list of lists"),
  LOADING_DOCUMENT_FAILED("loading document failed"),
  LOADING_REMOTE_CONTEXT_FAILED("loading remote context failed"),
  MULTIPLE_CONTEXT_LINK_HEADERS("multiple context link headers"),
  PROCESSING_MODE_CONFLICT("processing mode conflict"),
  RECURSIVE_CONTEXT_INCLUSION("recursive context inclusion");

  private final String description;

  JsonLdErrorCode(final String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
