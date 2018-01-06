package hurricane.jsonld.parser;

/**
 * Created by erik on 30-7-17.
 */
public enum TokenType {
  START_OBJECT,
  END_OBJECT,

  START_ARRAY,
  END_ARRAY,

  NAME,

  STRING_VALUE,
  NUMBER_VALUE,
  TRUE_VALUE,
  FALSE_VALUE,
  NULL_VALUE
}
