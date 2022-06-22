package h06;

import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.function.BiFunction;
import java.util.function.Function;

public class MyListsHashMap<K, V> implements MyMap<K, V> {
    private final LinkedList<KeyValuePair<K, V>>[] table;
    private final Fct2Int<K> hashFunction;

    /**
     * Creates a new list hash map.
     * @param hashFunction The used hash function.
     */
    @SuppressWarnings("unchecked")
    public MyListsHashMap(Fct2Int<K> hashFunction) {
        this.hashFunction = hashFunction;
        table = (LinkedList<KeyValuePair<K, V>>[]) new LinkedList<?>[hashFunction.getTableSize()];
        for (int i = 0; i < table.length; i++) {
            table[i] = new LinkedList<KeyValuePair<K, V>>();
        }
    }

    private <R> R execute(K key, BiFunction<LinkedList<KeyValuePair<K, V>>, KeyValuePair<K, V>, R> foundFunction,
                          Function<LinkedList<KeyValuePair<K, V>>, R> notFoundFunction) {
        LinkedList<KeyValuePair<K, V>> currentList = table[hashFunction.apply(key)];
        for (KeyValuePair<K, V> currentListElement : currentList) {
            if (currentListElement.getKey().equals(key)) {
                return foundFunction.apply(currentList, currentListElement);
            }
        }
        return notFoundFunction.apply(currentList);
    }

    @Override
    public boolean containsKey(K key) {
        return execute(key, (list, pair) -> true, list -> false);
    }

    @Override
    public @Nullable V getValue(K key) {
        return execute(key, (list, pair) -> pair.getValue(), list -> null);
    }

    @Override
    public @Nullable V put(K key, V value) {
        return execute(key,
            (list, pair) -> {
                V oldValue = pair.getValue();
                pair.setValue(value);
                return oldValue;
            },
            list -> {
                list.addFirst(new KeyValuePair<K, V>(key, value));
                return null;
            });
    }

    @Override
    public @Nullable V remove(K key) {
        return execute(key,
            (list, pair) -> {
                V value = pair.getValue();
                list.remove(pair);
                return value;
            },
            list -> null
        );
    }
}
