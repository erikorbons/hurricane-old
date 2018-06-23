package hurricane.core.rdf.formatters;

import hurricane.core.iri.IriReference;
import hurricane.core.rdf.Graph;
import hurricane.core.rdf.Literal;
import hurricane.core.rdf.RdfFormatter;
import hurricane.core.rdf.RdfMapper;
import hurricane.core.rdf.Triple;
import java.util.stream.IntStream;

public class NTriplesFormatter implements RdfFormatter {

  @Override
  public String format(final RdfMapper rdf, final Triple<?> triple) {
    final StringBuilder builder = new StringBuilder();
    return builder.toString();
  }

  private void format(final StringBuilder builder, final RdfMapper rdf, final Triple<?> triple) {
    builder.append('<');
    builder.append(triple.getSubject().getEscapedString());
    builder.append("> ");
    builder.append('<');
    builder.append(triple.getPredicate().getEscapedString());
    builder.append("> ");

    final Object object = triple.getObject();

    if (object instanceof IriReference) {
      builder.append('<');
      builder.append(((IriReference) object).getEscapedString());
    } else {
      final Literal<?> literal = rdf.literal(object);

      // Append the literal value:
      builder.append('"');
      escapeLexicalForm(builder, literal.getLexicalForm());
      builder.append('"');

      // Append the language tag or the type IRI:
      if (literal.getLanguageTag().isPresent()) {
        builder.append('@');
        builder.append(literal.getLanguageTag().get().toLanguageTag());
      } else {
        builder.append("^^<");
        builder.append(literal.getDataTypeIri().getEscapedString());
        builder.append("> ");
      }
    }

    builder.append('.');
  }

  @Override
  public String format(final RdfMapper rdf, final Graph graph) {
    final StringBuilder builder = new StringBuilder();



    return builder.toString();
  }

  private static void escapeLexicalForm(final StringBuilder builder, final String rawValue) {
    rawValue.chars().forEach((c) -> {
      final String echar;

      if (isPnChar(c)) {
        builder.append((char) c);
      } else if ((echar = toEchar(c)) != null) {
        builder.append(echar);
      } else {
        toUchar(builder, c);
      }
    });
  }

  private static void toUchar(final StringBuilder builder, final int ch) {
    final String hexValue = Integer.toHexString(ch);
    final int padding;

    if (ch > 0xFFFF) {
      padding = 8 - hexValue.length();
      builder.append("\\U");
    } else {
      padding = 4 - hexValue.length();
      builder.append("\\u");
    }

    IntStream.range(0, padding).forEach(i -> builder.append(' '));

    builder.append(hexValue);
  }

  private static String toEchar(final int ch) {
    switch (ch) {
      case '\t':
        return "\\t";
      case '\b':
        return "\\b";
      case '\n':
        return "\\n";
      case '\r':
        return "\\r";
      case '\f':
        return "\\f";
      case '\"':
        return "\\\"";
      case '\'':
        return "\\\'";
      case '\\':
        return "\\\\";
      default:
        return null;
    }
  }

  private static boolean isPnChar(final int ch) {
    return (ch >= 'A' && ch <= 'Z')
        || (ch >= 'a' && ch <= 'z')
        || (ch >= 0x00C0 && ch <= 0x00D6)
        || (ch >= 0x00D8 && ch <= 0x00F6)
        || (ch >= 0x00F8 && ch <= 0x02FF)
        || (ch >= 0x0370 && ch <= 0x037D)
        || (ch >= 0x037F && ch <= 0x1FFF)
        || (ch >= 0x200C && ch <= 0x200D)
        || (ch >= 0x2070 && ch <= 0x218F)
        || (ch >= 0x2C00 && ch <= 0x2FEF)
        || (ch >= 0x3001 && ch <= 0xD7FF)
        || (ch >= 0xF900 && ch <= 0xFDCF)
        || (ch >= 0xFDF0 && ch <= 0xFFFD)
        || (ch >= 0x1000 && ch <= 0xEFFFF);
  }
}
