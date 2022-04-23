package h06;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.LinkedList;

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
        table = (LinkedList<KeyValuePair<K, V>>[]) Array.newInstance(LinkedList.class, hashFunction.getTableSize());
        for (int i = 0; i < table.length; i++) {
            table[i] = new LinkedList<KeyValuePair<K, V>>();
        }
    }

    @Override
    public boolean containsKey(K key) {
        LinkedList<KeyValuePair<K, V>> currentList = table[hashFunction.apply(key)];

        for (KeyValuePair<K, V> currentListElement : currentList) {
            if (currentListElement.getKey().equals(key)) return true;
        }

        return false;
    }

    @Override
    public @Nullable V getValue(K key) {
        LinkedList<KeyValuePair<K, V>> currentList = table[hashFunction.apply(key)];

        for (KeyValuePair<K, V> currentListElement : currentList) {
            if (currentListElement.getKey().equals(key)) return currentListElement.getValue();
        }

        return null;
    }

    @Override
    public @Nullable V put(K key, V value) {
        LinkedList<KeyValuePair<K, V>> currentList = table[hashFunction.apply(key)];

        for (KeyValuePair<K, V> currentListElement : currentList) {
            if (currentListElement.getKey().equals(key)) {
                V oldValue = currentListElement.getValue();
                currentListElement.setValue(value);
                return oldValue;
            }
        }

        currentList.addFirst(new KeyValuePair<K, V>(key, value));
        return null;
    }

    @Override
    public @Nullable V remove(K key) {
        LinkedList<KeyValuePair<K, V>> currentList = table[hashFunction.apply(key)];

        for (KeyValuePair<K, V> currentListElement : currentList) {
            if (currentListElement.getKey().equals(key)) {
                V value = currentListElement.getValue();
                currentList.remove(currentListElement);
                return value;
            }
        }

        return null;
    }
}
