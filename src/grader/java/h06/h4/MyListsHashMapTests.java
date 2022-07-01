package h06.h4;

import h06.Fct2Int;
import h06.KeyValuePair;
import h06.MyListsHashMap;
import h06.utils.ReflectionUtils;
import h06.utils.TutorAssertions;
import kotlin.Pair;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static h06.Config.SEED;
import static h06.Config.STREAM_SIZE;

@TestForSubmission("h06")
@SuppressWarnings("unchecked")
public class MyListsHashMapTests {

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testContainsKey(MyListsHashMap<Object, Object> instance, int index) {
        Object key = new Object();
        Object value = new Object();
        ((LinkedList<KeyValuePair<Object, Object>>[]) ReflectionUtils.getFieldValue("table", instance))[index]
            .addFirst(new KeyValuePair<>(key, value));

        TutorAssertions.assertEquals(
            true, null,
            instance.containsKey(key), null,
            "[[[containsKey(K)]]] did not return [[[true]]], even though the key-value-pair is at the correct position in "
                + "[[[table]]]", () -> List.of(
                new Pair<>("[[[fct2Int]]]", "[[[Fct2Int]]] reference implementation; [[[apply(T)]]] always returns " + index),
                new Pair<>("[[[this]]]", "[[[new MyListsHashMap<>(fct2Int)]]]; [[[table]]] has been modified to simulate "
                    + "insertion operations")
            ));
    }

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testContainsKeyDisjoint(MyListsHashMap<Object, Object> instance, int index) {
        TutorAssertions.assertEquals(
            false, null,
            instance.containsKey(new Object()), null,
            "[[[containsKey(K)]]] did not return [[[false]]], even though the given key is not in [[[table]]]",
            () -> List.of(
                new Pair<>("[[[fct2Int]]]", "[[[Fct2Int]]] reference implementation; [[[apply(T)]]] always returns " + index),
                new Pair<>("[[[this]]]", "[[[new MyListsHashMap<>(fct2Int)]]]")
            ));
    }

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testGetValue(MyListsHashMap<Object, Object> instance, int index) {
        Object key = new Object();
        Object value = new Object();
        ((LinkedList<KeyValuePair<Object, Object>>[]) ReflectionUtils.getFieldValue("table", instance))[index]
            .addFirst(new KeyValuePair<>(key, value));

        TutorAssertions.assertEquals(
            value, null,
            instance.getValue(key), null,
            "[[[getValue(K)]]] did not return the expected value, even though the key-value-pair is at the correct position in "
                + "[[[table]]]", () -> List.of(
                new Pair<>("[[[fct2Int]]]", "[[[Fct2Int]]] reference implementation; [[[apply(T)]]] always returns " + index),
                new Pair<>("[[[this]]]", "[[[new MyListsHashMap<>(fct2Int)]]]; [[[table]]] has been modified to simulate "
                    + "insertion operations")
            ));
    }

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testGetValueDisjoint(MyListsHashMap<Object, Object> instance, int index) {
        TutorAssertions.assertEquals(
            null, null,
            instance.getValue(new Object()), null,
            "[[[getValue(K)]]] did not return [[[null]]], even though the given key is not in [[[table]]]",
            () -> List.of(
                new Pair<>("[[[fct2Int]]]", "[[[Fct2Int]]] reference implementation; [[[apply(T)]]] always returns " + index),
                new Pair<>("[[[this]]]", "[[[new MyListsHashMap<>(fct2Int)]]]")
            ));
    }

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testPut(MyListsHashMap<Object, Object> instance, int index) {
        LinkedList<KeyValuePair<Object, Object>>[] table = ReflectionUtils.getFieldValue("table", instance);
        Object key = new Object();
        Object value = new Object();

        TutorAssertions.assertEquals(
            null, null,
            instance.put(key, value), null,
            "[[[put(K, V)]]] did not return [[[null]]], even though the key-value-pair does not exist in [[[table]]] yet",
            () -> List.of(
                new Pair<>("[[[fct2Int]]]", "[[[Fct2Int]]] reference implementation; [[[apply(T)]]] always returns " + index),
                new Pair<>("[[[this]]]", "[[[new MyListsHashMap<>(fct2Int)]]]")
            ));
        for (int i = 0; i < table.length; i++) {
            if (i == index) {
                TutorAssertions.assertEquals(
                    1, null,
                    table[i].size(), null,
                    "[[[put(K, V)]]] did not create a new [[[KeyValuePair]]] object at the correct index (%d)".formatted(index),
                    () -> List.of(
                        new Pair<>("[[[fct2Int]]]", "[[[Fct2Int]]] reference implementation; [[[apply(T)]]] always returns " + index),
                        new Pair<>("[[[this]]]", "[[[new MyListsHashMap<>(fct2Int)]]]")
                    ));
            } else {
                TutorAssertions.assertEquals(
                    0, null,
                    table[i].size(), null,
                    "[[[put(K, V)]]] created a new [[[KeyValuePair]]] object at the wrong index (%d)".formatted(index),
                    () -> List.of(
                        new Pair<>("[[[fct2Int]]]", "[[[Fct2Int]]] reference implementation; [[[apply(T)]]] always returns " + index),
                        new Pair<>("[[[this]]]", "[[[new MyListsHashMap<>(fct2Int)]]]")
                    ));
            }
        }
        TutorAssertions.assertEquals(
            table[index].getFirst().getKey(), null,
            key, null,
            "[[[put(K, V)]]] did not create a new [[[KeyValuePair]]] object with the correct key", () -> List.of(
                new Pair<>("[[[fct2Int]]]", "[[[Fct2Int]]] reference implementation; [[[apply(T)]]] always returns " + index),
                new Pair<>("[[[this]]]", "[[[new MyListsHashMap<>(fct2Int)]]]")
            ));
        TutorAssertions.assertEquals(
            table[index].getFirst().getValue(), null,
            value, null,
            "[[[put(K, V)]]] did not create a new [[[KeyValuePair]]] object with the correct value", () -> List.of(
                new Pair<>("[[[fct2Int]]]", "[[[Fct2Int]]] reference implementation; [[[apply(T)]]] always returns " + index),
                new Pair<>("[[[this]]]", "[[[new MyListsHashMap<>(fct2Int)]]]")
            ));
    }

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testPutDuplicate(MyListsHashMap<Object, Object> instance, int index) {
        Object key = new Object();
        Object value = new Object();
        Object newValue = new Object();
        ((LinkedList<KeyValuePair<Object, Object>>[]) ReflectionUtils.getFieldValue("table", instance))[index]
            .addFirst(new KeyValuePair<>(key, value));

        TutorAssertions.assertEquals(
            value, null,
            instance.put(key, newValue), null,
            "[[[put(K, V)]]] did not return the old value that was associated with the given key", () -> List.of(
                new Pair<>("[[[fct2Int]]]", "[[[Fct2Int]]] reference implementation; [[[apply(T)]]] always returns " + index),
                new Pair<>("[[[this]]]", "[[[new MyListsHashMap<>(fct2Int)]]]; [[[table]]] has been modified to simulate "
                    + "insertion operations")
            ));
    }

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testRemove(MyListsHashMap<Object, Object> instance, int index) {
        LinkedList<KeyValuePair<Object, Object>>[] table = ReflectionUtils.getFieldValue("table", instance);
        Object key = new Object();
        Object value = new Object();
        ((LinkedList<KeyValuePair<Object, Object>>[]) ReflectionUtils.getFieldValue("table", instance))[index]
            .addFirst(new KeyValuePair<>(key, value));

        TutorAssertions.assertEquals(
            value, null,
            instance.remove(key), null,
            "[[[remove(K)]]] did not return the expected value, even though the key-value-pair is at the correct position in "
                + "[[[table]]]", () -> List.of(
                new Pair<>("[[[fct2Int]]]", "[[[Fct2Int]]] reference implementation; [[[apply(T)]]] always returns " + index),
                new Pair<>("[[[this]]]", "[[[new MyListsHashMap<>(fct2Int)]]]; [[[table]]] has been modified to simulate "
                    + "insertion operations")
            ));
        TutorAssertions.assertEquals(
            0, null,
            table[index].size(), null,
            "[[[remove(K)]]] did not remove the [[[KeyValuePair]]] object from [[[table]]] at index " + index, () -> List.of(
                new Pair<>("[[[fct2Int]]]", "[[[Fct2Int]]] reference implementation; [[[apply(T)]]] always returns " + index),
                new Pair<>("[[[this]]]", "[[[new MyListsHashMap<>(fct2Int)]]]; [[[table]]] has been modified to simulate "
                    + "insertion operations")
            ));
    }

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testRemoveDisjoint(MyListsHashMap<Object, Object> instance, int index) {
        TutorAssertions.assertEquals(
            null, null,
            instance.remove(new Object()), null,
            "[[[remove(K)]]] did not return [[[null]]] for a given key that has no value associated with it", () -> List.of(
                new Pair<>("[[[fct2Int]]]", "[[[Fct2Int]]] reference implementation; [[[apply(T)]]] always returns " + index),
                new Pair<>("[[[this]]]", "[[[new MyListsHashMap<>(fct2Int)]]]")
            ));
    }

    private static class Provider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            Random random = new Random(SEED);
            int tableSize = random.nextInt(5, 25);

            return Stream.generate(() -> {
                    int index = random.nextInt(tableSize);
                    return new Object[] {new MyListsHashMap<>(new Fct2IntImpl(tableSize, index)), index};
                })
                .limit(STREAM_SIZE)
                .map(Arguments::of);
        }
    }

    private static class Fct2IntImpl implements Fct2Int<Object> {
        private int tableSize;
        private final int applyReturnValue;

        private Fct2IntImpl(int tableSize, int applyReturnValue) {
            this.tableSize = tableSize;
            this.applyReturnValue = applyReturnValue;
        }

        @Override
        public int apply(Object x) {
            return applyReturnValue;
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
