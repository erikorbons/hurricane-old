package hurricane.core.rdf.graphs;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ClassDescriptor {
  private final Class<?> cls;
  private Map<String, PropertyDescriptor> propertyDescriptors;

  public ClassDescriptor(final Class<?> cls) {
    this.cls = Objects.requireNonNull(cls, "cls cannot be null");

    this.propertyDescriptors = getPropertyDescriptors(cls).stream()
        .collect(Collectors.toMap(
            PropertyDescriptor::getName,
            Function.identity()
        ));
  }

  private static Collection<PropertyDescriptor> getPropertyDescriptors(final Class<?> cls) {
    final MethodHandles.Lookup lookup = MethodHandles.publicLookup();

    return Arrays.stream(cls.getMethods())
        .filter(method -> Modifier.isPublic(method.getModifiers()))
        .filter(method -> !Modifier.isStatic(method.getModifiers()))
        .filter(method -> method.getParameterCount() == 0)
        .filter(method -> !method.getReturnType().equals(Void.TYPE))
        .filter(ClassDescriptor::isProperty)
        .map(method -> {
          try {
            return new PropertyDescriptor(getPropertyName(method), lookup.unreflect(method));
          } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(
                "Unable to access method " + method.toGenericString(), e);
          }
        })
        .collect(Collectors.toList());
  }

  private static boolean isProperty(final Method method) {
    final String name = method.getName();
    return (name.startsWith("get") && name.length() > 3)
        || (name.startsWith("is") && name.length() > 2);
  }

  private static String getPropertyName(final Method method) {
    final String name = method.getName();
    final String baseName;

    if (name.startsWith("get")) {
      baseName = name.substring(3);
    } else if (name.startsWith("is")) {
      baseName = name.substring(2);
    } else {
      baseName = name;
    }

    return baseName.substring(0, 1).toLowerCase(Locale.US)
        + baseName.substring(1);
  }

  public static class PropertyDescriptor {
    private final String name;
    private final MethodHandle method;

    public PropertyDescriptor(final String name, final MethodHandle method) {
      this.name = Objects.requireNonNull(name, "name cannot be null");
      this.method = Objects.requireNonNull(method, "method cannot be null");
    }

    public String getName() {
      return name;
    }

    public MethodHandle getMethod() {
      return method;
    }
  }
}
