package h06;

import h06.*;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Util_Tests
{
  @Test
  public static Method checkMethod(Class klass, String methodName, Type returnType, Class... parameterTypes)
  {
    Method method = null;
    // Search for method with correct name and parameters
    try { method = klass.getDeclaredMethod(methodName, parameterTypes); }
    catch (Exception e) {
      // Search for method with correct parameters
      Optional<Method> methodParameterSearch = Arrays.stream(klass.getDeclaredMethods()).filter(m -> m.getName().equals(methodName)).findFirst();
      if (methodParameterSearch.isPresent()) {
        method = methodParameterSearch.get();
      } else {
        // Search for method with correct name
        Optional<Method> methodNameSearch = Arrays.stream(klass.getDeclaredMethods()).filter(m ->
          (m.getParameterCount() == parameterTypes.length) &&
            (Arrays.deepEquals(m.getParameterTypes(), parameterTypes))
        ).findFirst();
        if (methodNameSearch.isPresent()) {
          method = methodNameSearch.get();
        } else {
          fail(klass.getName() + " enthält anders als erwartet nicht die Methode " + methodName);
        }
      }
    }

    // Parameters
    Parameter[] parameters = method.getParameters();
    assertEquals(parameterTypes.length, parameters.length, "Methode " + methodName + " hat anders als erwartet nicht 1 Parameter, sondern: " + parameterTypes.length);
    for (int i = 0; i < parameters.length; i++)
      assertEquals(parameterTypes[i], parameters[i].getType(), "Parameter " + (i + 1) + " der Methode " + methodName + " ist anders als erwartet vom Typ: " + parameters[0].getType());
    // Name check would be possible too (here) method signatur needs to change then though

    // Return Parameter
    assertEquals(returnType, method.getReturnType(), "Der Rückgabetyp der Methode setTableSize ist anders als erwartet vom Typ: " + method.getReturnType());

    return method;
  }

  public static Class<?> checkType(String typeName, String packageName, boolean isInterface, int numberOfMethods, int numberOfTypVariables)
  {
    Class<?> klass = getClass(typeName, packageName);

    // Interface Check
    assertEquals(isInterface, klass.isInterface(),  typeName + " ist anders als erwartet " + (isInterface ? "kein" : "ein") + " Interface.");

    // Type Parameter Check
    TypeVariable<?>[] typeVariables = klass.getTypeParameters();
    assertEquals(numberOfTypVariables, typeVariables.length, (isInterface ? "Interface " : "Klasse ") + typeName + " hat anders als erwartet nicht " + numberOfTypVariables + " generische(n) Typparameter, sondern: " + typeVariables.length);

    // Method Check
    if (numberOfMethods != -1)
    {
      Method[] methods = klass.getDeclaredMethods();
      assertEquals(numberOfMethods, methods.length, (isInterface ? "Interface " : "Klasse ") + typeName + " hat anders als erwartet nicht 3 Methoden, sondern: " + methods.length);
    }

    return klass;
  }

//  private static Set<Class> getClasses(String packageName)
//  {
//    InputStream stream = ClassLoader.getSystemClassLoader()
//      .getResourceAsStream(packageName.replaceAll("[.]", "/"));
//    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
//    return reader.lines()
//      .filter(line -> line.endsWith(".class") || line.endsWith(".java"))
//      .map(line -> getClass(line, packageName))
//      .collect(Collectors.toSet());
//  }

  public static Class<?> getClass(String className, String packageName) {
    try {
      return Class.forName(packageName + "." + className);
    } catch (ClassNotFoundException e) {
      // handle the exception
    }
    return null;
  }

//  public static Constructor getConstructor(Class klass, Class... types)
//  {
//    Constructor constructor = null;
//    try { constructor = klass.getConstructor(types);}
//    catch (Exception e) { fail("Die Signatur des Konstruktur von Klasse " + klass.getName() + " entspricht nicht den Erwartung."); }
//    return constructor;
//  }
//
//  public static void invokeConstructor(Class klass, Constructor constructor, Object... parameters)
//  {
//    try {
//      constructor.setAccessible(true); // override access modifiers
//      constructor.newInstance(parameters);
//    }
//    catch (Exception e)
//    {
//      fail("Der Konstruktor von Klasse " + klass.getName() + " hat eine unerwartete Exception zurückgeliefert: " + e.getMessage());
//    }
//  }
}
