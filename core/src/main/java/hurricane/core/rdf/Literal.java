package hurricane.core.rdf;

import hurricane.core.iri.IriReference;
import hurricane.core.rdf.literals.RdfLangString;
import hurricane.core.rdf.literals.XsdAnyUri;
import hurricane.core.rdf.literals.XsdBoolean;
import hurricane.core.rdf.literals.XsdByte;
import hurricane.core.rdf.literals.XsdDecimal;
import hurricane.core.rdf.literals.XsdDouble;
import hurricane.core.rdf.literals.XsdFloat;
import hurricane.core.rdf.literals.XsdGMonth;
import hurricane.core.rdf.literals.XsdGYear;
import hurricane.core.rdf.literals.XsdInt;
import hurricane.core.rdf.literals.XsdInteger;
import hurricane.core.rdf.literals.XsdLanguage;
import hurricane.core.rdf.literals.XsdLong;
import hurricane.core.rdf.literals.XsdShort;
import hurricane.core.rdf.literals.XsdString;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Month;
import java.time.Year;
import java.util.Locale;
import java.util.Optional;

/**
 * Plain literal.
 */
public interface Literal extends Node {
  String getLexicalForm();
  IriReference getDataTypeIri();

  default Optional<Locale> getLanguageTag() {
    return Optional.empty();
  }

  static XsdByte of(final byte value) {
    return new XsdByte(value);
  }

  static XsdShort of(final short value) {
    return new XsdShort(value);
  }

  static XsdInt of(final int value) {
    return new XsdInt(value);
  }

  static XsdLong of(final long value) {
    return new XsdLong(value);
  }

  static XsdBoolean of(final boolean value) {
    return new XsdBoolean(value);
  }

  static XsdDouble of(final double value) {
    return new XsdDouble(value);
  }

  static XsdFloat of(final float value) {
    return new XsdFloat(value);
  }

  static XsdString of(final String value) {
    return new XsdString(value);
  }

  static RdfLangString of(final String value, final Locale lang) {
    return new RdfLangString(value, lang);
  }

  static XsdDecimal of(final BigDecimal value) {
    return new XsdDecimal(value);
  }

  static XsdInteger of(final BigInteger value) {
    return new XsdInteger(value);
  }
}
