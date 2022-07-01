package h06.utils;

import org.opentest4j.AssertionFailedError;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ReflectionUtils {

    private static final Map<Class<?>, Map<String, Field>> FIELD_CACHE = new HashMap<>();

    public static Field getField(Class<?> clazz, String fieldName) {
        return FIELD_CACHE.computeIfAbsent(clazz, c -> new HashMap<>())
            .computeIfAbsent(fieldName, s -> {
                try {
                    Field field = Stream.concat(Arrays.stream(clazz.getFields()), Arrays.stream(clazz.getDeclaredFields()))
                        .filter(f -> f.getName().equals(fieldName))
                        .findAny()
                        .orElseThrow(() ->
                            new AssertionFailedError("Unable to locate field %s in class %s".formatted(fieldName, clazz.getName())));
                    field.setAccessible(true);
                    return field;
                } catch (InaccessibleObjectException e) {
                    throw new AssertionFailedError("Unable to access field %s in class %s".formatted(fieldName, clazz.getName()), e);
                }
            });
    }

    public static <T, R> R getFieldValue(String fieldName, T instance) {
        return getFieldValue(getField(instance.getClass(), fieldName), instance);
    }

    @SuppressWarnings("unchecked")
    public static <T, R> R getFieldValue(Field field, T instance) {
        try {
            return (R) field.get(instance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T, V> void setFieldValue(String fieldName, T instance, V value) {
        setFieldValue(getField(instance.getClass(), fieldName), instance, value);
    }

    public static <T, V> void setFieldValue(Field field, T instance, V value) {
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
