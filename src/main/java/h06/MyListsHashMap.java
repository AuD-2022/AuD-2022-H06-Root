package h06;

import java.lang.reflect.Array;
import java.util.LinkedList;

public class MyListsHashMap<K extends Object, V> implements MyMap<K, V> {
    private final LinkedList<KeyValuePair<K, V>>[] table;
    private final Fct2Int<K> hashFunction;

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
    public V getValue(K key) {
        LinkedList<KeyValuePair<K, V>> currentList = table[hashFunction.apply(key)];

        for (KeyValuePair<K, V> currentListElement : currentList) {
            if (currentListElement.getKey().equals(key)) return currentListElement.getValue();
        }

        return null;
    }

    @Override
    public V put(K key, V value) {
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
    public V remove(K key) {
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
