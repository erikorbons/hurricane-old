package hurricane.core.rdf.literals;

import hurricane.core.rdf.Literal;
import java.util.Objects;

public abstract class ObjectLiteral<T> implements Literal {

  final T value;

  public ObjectLiteral(final T value) {
    this.value = Objects.requireNonNull(value, "value cannot be null");
  }

  public T getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ObjectLiteral<?> that = (ObjectLiteral<?>) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
