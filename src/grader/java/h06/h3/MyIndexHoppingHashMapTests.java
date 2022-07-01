package h06.h3;

import h06.MyIndexHoppingHashMap;
import h06.utils.ReflectionUtils;
import h06.utils.TutorAssertions;
import kotlin.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.extension.JagrExecutionCondition;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static h06.Config.SEED;
import static h06.Config.STREAM_SIZE;

@TestForSubmission("h06")
@SuppressWarnings("DuplicatedCode")
public class MyIndexHoppingHashMapTests {

    public static boolean REHASH_CALLED = false;

    @BeforeEach
    public void reset() {
        REHASH_CALLED = false;
    }

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testContainsKey(int tableSize, int index, double resizeFactor, double resizeThreshold) {
        MyIndexHoppingHashMap<Object, Object> instance = getHashFunction(tableSize, index, resizeFactor, resizeThreshold);
        Object key = new Object();
        Object value = new Object();
        ((Object[]) ReflectionUtils.getFieldValue("theKeys", instance))[index] = key;
        ((Object[]) ReflectionUtils.getFieldValue("theValues", instance))[index] = value;
        ((boolean[]) ReflectionUtils.getFieldValue("occupiedSinceLastRehash", instance))[index] = true;
        ReflectionUtils.setFieldValue("occupiedCount", instance, 1);

        TutorAssertions.assertEquals(
            true, null,
            instance.containsKey(key), null,
            "[[[containsKey(K)]]] did not return [[[true]]], even though the key is at the correct position in [[[theKeys]]]",
            () -> List.of(
                new Pair<>("[[[binaryFct2Int]]]",
                    "[[[BinaryFct2Int]]] reference implementation; [[[apply(T, int)]]] always returns " + index),
                new Pair<>("[[[this]]]", "[[[new MyIndexHoppingHashMap<>(%d, %f, %f, binaryFct2Int)]]] ".formatted(
                    tableSize, resizeFactor, resizeThreshold)
                    + "[[[theKeys]]], [[[theValues]]], [[[occupiedSinceLastRehash]]] and [[[occupiedCount]]] have been modified "
                    + "to simulate insertion operations")
            ));
    }

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testContainsKeyForeign(int tableSize, int index, double resizeFactor, double resizeThreshold) {
        MyIndexHoppingHashMap<Object, Object> instance = getHashFunction(tableSize, index, resizeFactor, resizeThreshold);

        TutorAssertions.assertEquals(
            false, null,
            instance.containsKey(new Object()), null,
            "[[[containsKey(K)]]] did not return [[[false]]], even though the key is not in [[[theKeys]]]", () -> List.of(
                new Pair<>("[[[binaryFct2Int]]]",
                    "[[[BinaryFct2Int]]] reference implementation; [[[apply(T, int)]]] always returns " + index),
                new Pair<>("[[[this]]]", "[[[new MyIndexHoppingHashMap<>(%d, %f, %f, binaryFct2Int)]]] ".formatted(
                    tableSize, resizeFactor, resizeThreshold)
                    + "[[[theKeys]]], [[[theValues]]], [[[occupiedSinceLastRehash]]] and [[[occupiedCount]]] have been modified "
                    + "to simulate insertion operations")
            ));
    }

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testGetValue(int tableSize, int index, double resizeFactor, double resizeThreshold) {
        MyIndexHoppingHashMap<Object, Object> instance = getHashFunction(tableSize, index, resizeFactor, resizeThreshold);
        Object key = new Object();
        Object value = new Object();
        ((Object[]) ReflectionUtils.getFieldValue("theKeys", instance))[index] = key;
        ((Object[]) ReflectionUtils.getFieldValue("theValues", instance))[index] = value;
        ((boolean[]) ReflectionUtils.getFieldValue("occupiedSinceLastRehash", instance))[index] = true;
        ReflectionUtils.setFieldValue("occupiedCount", instance, 1);

        TutorAssertions.assertEquals(
            value, null,
            instance.getValue(key), null,
            "[[[getValue(K)]]] did not return the expected object, even though key and value are both at the correct position",
            () -> List.of(
                new Pair<>("[[[binaryFct2Int]]]",
                    "[[[BinaryFct2Int]]] reference implementation; [[[apply(T, int)]]] always returns " + index),
                new Pair<>("[[[this]]]", "[[[new MyIndexHoppingHashMap<>(%d, %f, %f, binaryFct2Int)]]] ".formatted(
                    tableSize, resizeFactor, resizeThreshold)
                    + "[[[theKeys]]], [[[theValues]]], [[[occupiedSinceLastRehash]]] and [[[occupiedCount]]] have been modified "
                    + "to simulate insertion operations")
            ));
    }

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testGetValueForeign(int tableSize, int index, double resizeFactor, double resizeThreshold) {
        MyIndexHoppingHashMap<Object, Object> instance = getHashFunction(tableSize, index, resizeFactor, resizeThreshold);

        TutorAssertions.assertEquals(
            null, null,
            instance.getValue(new Object()), null,
            "[[[getValue(K)]]] did not return [[[null]]], even though key and value are not in [[[theKeys]]] and [[[theValues]]]",
            () -> List.of(
                new Pair<>("[[[binaryFct2Int]]]",
                    "[[[BinaryFct2Int]]] reference implementation; [[[apply(T, int)]]] always returns " + index),
                new Pair<>("[[[this]]]", "[[[new MyIndexHoppingHashMap<>(%d, %f, %f, binaryFct2Int)]]] ".formatted(
                    tableSize, resizeFactor, resizeThreshold)
                    + "[[[theKeys]]], [[[theValues]]], [[[occupiedSinceLastRehash]]] and [[[occupiedCount]]] have been modified "
                    + "to simulate insertion operations")
            ));
    }

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testPut(int tableSize, int index, double resizeFactor, double resizeThreshold) {
        Supplier<List<Pair<String, String>>> inputsListSupplier = () -> List.of(
            new Pair<>("[[[binaryFct2Int]]]",
                "[[[BinaryFct2Int]]] reference implementation; [[[apply(T, int)]]] always returns " + index),
            new Pair<>("[[[this]]]", "[[[new MyIndexHoppingHashMap<>(%d, %f, %f, binaryFct2Int)]]]".formatted(
                tableSize, resizeFactor, resizeThreshold))
        );
        MyIndexHoppingHashMap<Object, Object> instance = getHashFunction(tableSize, index, resizeFactor, resizeThreshold);
        Object key = new Object();
        Object value = new Object();

        TutorAssertions.assertEquals(
            null, null,
            instance.put(key, value), null,
            "[[[put(K, V)]]] did not return [[[null]]], even though key and value are not in [[[theKeys]]] and [[[theValues]]]",
            inputsListSupplier);
        TutorAssertions.assertEquals(
            key, null,
            ((Object[]) ReflectionUtils.getFieldValue("theKeys", instance))[index], null,
            "[[[put(K, V)]]] did not update [[[theKeys]]] at index " + index, inputsListSupplier);
        TutorAssertions.assertEquals(
            value, null,
            ((Object[]) ReflectionUtils.getFieldValue("theValues", instance))[index], null,
            "[[[put(K, V)]]] did not update [[[theValues]]] at index " + index, inputsListSupplier);
        TutorAssertions.assertEquals(
            true, null,
            ((boolean[]) ReflectionUtils.getFieldValue("occupiedSinceLastRehash", instance))[index], null,
            "[[[put(K, V)]]] did not update [[[occupiedSinceLastRehash]]] at index " + index, inputsListSupplier);
        TutorAssertions.assertEquals(
            1, null,
            ReflectionUtils.getFieldValue("occupiedCount", instance), null,
            "[[[put(K, V)]]] did not update [[[occupiedCount]]]", inputsListSupplier);
    }

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testPutDuplicate(int tableSize, int index, double resizeFactor, double resizeThreshold) {
        Supplier<List<Pair<String, String>>> inputsListSupplier = () -> List.of(
            new Pair<>("[[[binaryFct2Int]]]",
                "[[[BinaryFct2Int]]] reference implementation; [[[apply(T, int)]]] always returns " + index),
            new Pair<>("[[[this]]]", "[[[new MyIndexHoppingHashMap<>(%d, %f, %f, binaryFct2Int)]]] ".formatted(
                tableSize, resizeFactor, resizeThreshold)
                + "[[[theKeys]]], [[[theValues]]], [[[occupiedSinceLastRehash]]] and [[[occupiedCount]]] have been modified "
                + "to simulate insertion operations prior to calling [[[put(K, V)]]]")
        );
        MyIndexHoppingHashMap<Object, Object> instance = getHashFunction(tableSize, index, resizeFactor, resizeThreshold);
        Object key = new Object();
        Object value = new Object();
        Object newValue = new Object();
        ((Object[]) ReflectionUtils.getFieldValue("theKeys", instance))[index] = key;
        ((Object[]) ReflectionUtils.getFieldValue("theValues", instance))[index] = value;
        ((boolean[]) ReflectionUtils.getFieldValue("occupiedSinceLastRehash", instance))[index] = true;
        ReflectionUtils.setFieldValue("occupiedCount", instance, 1);

        TutorAssertions.assertEquals(
            value, null,
            instance.put(key, newValue), null,
            "[[[put(K, V)]]] did not return the old value associated with [[[key]]], "
                + "even though key and value are both at the correct position", inputsListSupplier);
        TutorAssertions.assertEquals(
            key, null,
            ((Object[]) ReflectionUtils.getFieldValue("theKeys", instance))[index], null,
            "[[[put(K, V)]]] updated [[[theKeys]]] at index %d, but it should not have".formatted(index), inputsListSupplier);
        TutorAssertions.assertEquals(
            newValue, null,
            ((Object[]) ReflectionUtils.getFieldValue("theValues", instance))[index], null,
            "[[[put(K, V)]]] did not update [[[theValues]]] at index " + index, inputsListSupplier);
        TutorAssertions.assertEquals(
            true, null,
            ((boolean[]) ReflectionUtils.getFieldValue("occupiedSinceLastRehash", instance))[index], null,
            "[[[put(K, V)]]] updated [[[occupiedSinceLastRehash]]] at index %d, but it should not have".formatted(index),
            inputsListSupplier);
        TutorAssertions.assertEquals(
            1, null,
            ReflectionUtils.getFieldValue("occupiedCount", instance), null,
            "[[[put(K, V)]]] updated [[[occupiedCount]]], but it should not have", inputsListSupplier);
    }

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testRemove(int tableSize, int index, double resizeFactor, double resizeThreshold) {
        Supplier<List<Pair<String, String>>> inputsListSupplier = () -> List.of(
            new Pair<>("[[[binaryFct2Int]]]",
                "[[[BinaryFct2Int]]] reference implementation; [[[apply(T, int)]]] always returns " + index),
            new Pair<>("[[[this]]]", "[[[new MyIndexHoppingHashMap<>(%d, %f, %f, binaryFct2Int)]]] ".formatted(
                tableSize, resizeFactor, resizeThreshold)
                + "[[[theKeys]]], [[[theValues]]], [[[occupiedSinceLastRehash]]] and [[[occupiedCount]]] have been modified "
                + "to simulate insertion operations prior to calling [[[remove(K)]]]")
        );
        MyIndexHoppingHashMap<Object, Object> instance = getHashFunction(tableSize, index, resizeFactor, resizeThreshold);
        Object key = new Object();
        Object value = new Object();
        ((Object[]) ReflectionUtils.getFieldValue("theKeys", instance))[index] = key;
        ((Object[]) ReflectionUtils.getFieldValue("theValues", instance))[index] = value;
        ((boolean[]) ReflectionUtils.getFieldValue("occupiedSinceLastRehash", instance))[index] = true;
        ReflectionUtils.setFieldValue("occupiedCount", instance, 1);

        TutorAssertions.assertEquals(
            value, null,
            instance.remove(key), null,
            "[[[remove(K)]]] did not return the value associated with [[[key]]], "
                + "even though key and value are both at the correct position", inputsListSupplier);
        TutorAssertions.assertEquals(
            null, null,
            ((Object[]) ReflectionUtils.getFieldValue("theKeys", instance))[index], null,
            "[[[remove(K)]]] did not remove key from [[[theKeys]]] at index " + index, inputsListSupplier);
        TutorAssertions.assertEquals(
            null, null,
            ((Object[]) ReflectionUtils.getFieldValue("theValues", instance))[index], null,
            "[[[remove(K)]]] did not remove value from [[[theValues]]] at index " + index, inputsListSupplier);
        TutorAssertions.assertEquals(
            true, null,
            ((boolean[]) ReflectionUtils.getFieldValue("occupiedSinceLastRehash", instance))[index], null,
            "[[[remove(K)]]] updated [[[occupiedSinceLastRehash]]] at index %d, but it should not have".formatted(index),
            inputsListSupplier);
        TutorAssertions.assertEquals(
            1, null,
            ReflectionUtils.getFieldValue("occupiedCount", instance), null,
            "[[[remove(K)]]] updated [[[occupiedCount]]], but it should not have", inputsListSupplier);
    }

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testRemoveForeign(int tableSize, int index, double resizeFactor, double resizeThreshold) {
        Supplier<List<Pair<String, String>>> inputsListSupplier = () -> List.of(
            new Pair<>("[[[binaryFct2Int]]]",
                "[[[BinaryFct2Int]]] reference implementation; [[[apply(T, int)]]] always returns " + index),
            new Pair<>("[[[this]]]", "[[[new MyIndexHoppingHashMap<>(%d, %f, %f, binaryFct2Int)]]]".formatted(
                tableSize, resizeFactor, resizeThreshold))
        );
        MyIndexHoppingHashMap<Object, Object> instance = getHashFunction(tableSize, index, resizeFactor, resizeThreshold);
        int newLength = (int) (tableSize * resizeFactor);

        try {
            Method rehash = MyIndexHoppingHashMap.class.getDeclaredMethod("rehash");
            rehash.setAccessible(true);
            rehash.invoke(instance);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        TutorAssertions.assertEquals(
            newLength, null,
            ((Object[]) ReflectionUtils.getFieldValue("theKeys", instance)).length, null,
            "[[[rehash()]]] did not resize array [[[theKeys]]]", inputsListSupplier);
        TutorAssertions.assertEquals(
            newLength, null,
            ((Object[]) ReflectionUtils.getFieldValue("theValues", instance)).length, null,
            "[[[rehash()]]] did not resize array [[[theValues]]]", inputsListSupplier);
        TutorAssertions.assertEquals(
            newLength, null,
            ((boolean[]) ReflectionUtils.getFieldValue("occupiedSinceLastRehash", instance)).length, null,
            "[[[rehash()]]] did not resize array [[[occupiedSinceLastRehash]]]", inputsListSupplier);
    }

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testRehash(int tableSize, int index, double resizeFactor, double resizeThreshold) {
        MyIndexHoppingHashMap<Object, Object> instance = getHashFunction(tableSize, index, resizeFactor, resizeThreshold);
        Object key = new Object();

        TutorAssertions.assertEquals(
            null, null,
            instance.remove(key), null,
            "[[[remove(K)]]] did not return [[[null]]], even though key and value are not in [[[theKeys]]] and [[[theValues]]]",
            () -> List.of(
                new Pair<>("[[[binaryFct2Int]]]",
                    "[[[BinaryFct2Int]]] reference implementation; [[[apply(T, int)]]] always returns " + index),
                new Pair<>("[[[this]]]", "[[[new MyIndexHoppingHashMap<>(%d, %f, %f, binaryFct2Int)]]]".formatted(
                    tableSize, resizeFactor, resizeThreshold)
                    + "[[[theKeys]]], [[[theValues]]], [[[occupiedSinceLastRehash]]] and [[[occupiedCount]]] have been modified "
                    + "to simulate insertion operations")
            ));
    }

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    @ExtendWith(JagrExecutionCondition.class)
    public void testRehashInPut(int tableSize, int index, double resizeFactor, double resizeThreshold) {
        Object[] theKeys = new Object[tableSize];
        Object[] theValues = new Object[tableSize];
        boolean[] occupiedSinceLastRehash = new boolean[tableSize];

        for (int i = 0; i < tableSize * resizeThreshold; i++) {
            theKeys[i] = new Object();
            theValues[i] = new Object();
            occupiedSinceLastRehash[i] = true;
        }

        MyIndexHoppingHashMap<Object, Object> instance = getHashFunction(tableSize, index, resizeFactor, resizeThreshold);
        ReflectionUtils.setFieldValue("theKeys", instance, theKeys);
        ReflectionUtils.setFieldValue("theValues", instance, theValues);
        ReflectionUtils.setFieldValue("occupiedSinceLastRehash", instance, occupiedSinceLastRehash);
        ReflectionUtils.setFieldValue("occupiedCount", instance, (int) (tableSize * resizeThreshold));

        instance.put(new Object(), new Object());

        TutorAssertions.assertEquals(
            true, null,
            REHASH_CALLED, null,
            "[[[put(K, V)]]] did not call [[[rehash()]]], even though [[[occupiedCount]]] exceeds threshold",
            () -> List.of(
                new Pair<>("[[[binaryFct2Int]]]",
                    "[[[BinaryFct2Int]]] reference implementation; [[[apply(T, int)]]] always returns " + index),
                new Pair<>("[[[this]]]", "[[[new MyIndexHoppingHashMap<>(%d, %f, %f, binaryFct2Int)]]]".formatted(
                    tableSize, resizeFactor, resizeThreshold)
                    + "[[[theKeys]]], [[[theValues]]], [[[occupiedSinceLastRehash]]] and [[[occupiedCount]]] have been modified "
                    + "to simulate insertion operations prior to calling [[[put(K, V)]]]")
            ));
    }

    private static MyIndexHoppingHashMap<Object, Object> getHashFunction(int tableSize, int index, double resizeFactor,
                                                                         double resizeThreshold) {
        return new MyIndexHoppingHashMap<>(tableSize, resizeFactor, resizeThreshold, new BinaryFct2IntImpl(tableSize, index));
    }

    private static class Provider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            Random random = new Random(SEED);
            int tableSize = random.nextInt(5, 25);

            return Stream.generate(() -> new Object[] {tableSize, random.nextInt(tableSize), 2d, 0.5})
                .limit(STREAM_SIZE)
                .map(Arguments::of);
        }
    }

    private static class BinaryFct2IntImpl implements h06.BinaryFct2Int<Object> {
        private int tableSize;
        private final int applyReturnValue;

        private BinaryFct2IntImpl(int tableSize, int applyReturnValue) {
            this.tableSize = tableSize;
            this.applyReturnValue = applyReturnValue;
        }

        @Override
        public int apply(Object x, int i) {
            return (this.applyReturnValue + i) % tableSize;
        }

        @Override
        public int getTableSize() {
            return this.tableSize;
        }

        @Override
        public void setTableSize(int tableSize) {
            this.tableSize = tableSize;
        }
    }
}
