package h06;

import org.jetbrains.annotations.Nullable;

public class MyIndexHoppingHashMap<K, V> implements MyMap<K, V> {
    private K[] theKeys;
    private V[] theValues;
    private final BinaryFct2Int<K> hashFunction;

    private int occupiedCount = 0;
    private boolean[] occupiedSinceLastRehash;
    private final double resizeThreshold;
    private final double resizeFactor;

    /**
     * Create a new index hopping hash map.
     * @param initialSize The initial size of the hashmap.
     * @param resizeFactor The resize factor which determines the new size after the resize threshold is reached.
     * @param resizeThreshold The threshold after which the hash table is resized.
     * @param hashFunction The used hash function.
     */
    @SuppressWarnings("unchecked")
    public MyIndexHoppingHashMap(int initialSize, double resizeFactor, double resizeThreshold, BinaryFct2Int<K> hashFunction) {
        theKeys = (K[]) new Object[initialSize];
        theValues = (V[]) new Object[initialSize];
        occupiedSinceLastRehash = new boolean[initialSize];

        this.resizeFactor = resizeFactor;
        this.resizeThreshold = resizeThreshold;
        this.hashFunction = hashFunction;
        this.hashFunction.setTableSize(initialSize);
    }

    /**
     * Find the key or an empty place to put the key.
     * @param key The key to search.
     * @return The index of the key or the index of an empty place.
     */
    private int search(K key) {
        boolean emptyPlaceFound = false;
        int emptyPlaceIndex = -1;

        for (int i = 0; i < hashFunction.getTableSize(); i++) {
            int hashValue = hashFunction.apply(key, i);
            // Position would have been occupied if element was existing / element was found
            if (!occupiedSinceLastRehash[hashValue] || key.equals(theKeys[hashValue])) {
                return hashValue;
            }
            // Memorise the position of the first empty place (needed for put)
            if (!emptyPlaceFound && theKeys[hashValue] == null) {
                emptyPlaceFound = true;
                emptyPlaceIndex = hashValue;
            }
        }
        return emptyPlaceIndex;
    }

    @Override
    public @Nullable V getValue(K key) {
        int index = search(key);
        return key.equals(theKeys[index]) ? theValues[index] : null;
    }

    @Override
    public boolean containsKey(K key) {
        int index = search(key);
        return key.equals(theKeys[index]);
    }

    @Override
    public @Nullable V put(K key, V value) {
        int index = search(key);
        if (!occupiedSinceLastRehash[index]) {
            if (occupiedCount + 1 > hashFunction.getTableSize() * resizeThreshold) {
                rehash();
                return put(key, value);
            } else {
                occupiedSinceLastRehash[index] = true;
                occupiedCount++;
            }
        }
        // get old value / null if no old value is present
        V oldValue = theValues[index];
        theKeys[index] = key;
        theValues[index] = value;
        return oldValue;
    }

    @Override
    public @Nullable V remove(K key) {
        int index = search(key);
        // get old value / null if key is not present
        V oldValue = theValues[index];
        theKeys[index] = null;
        theValues[index] = null;
        return oldValue;
    }

    /***
     * Creates a new bigger hashtable (current size multiplied by resizeFactor)
     * and inserts all elements of the old hashtable into the new one.
     */
    @SuppressWarnings("unchecked")
    private void rehash() {
        int newSize = (int) (hashFunction.getTableSize() * resizeFactor);
        hashFunction.setTableSize(newSize);

        K[] oldKeys = theKeys;
        V[] oldValues = theValues;

        theKeys = (K[]) new Object[newSize];
        theValues = (V[]) new Object[newSize];
        occupiedSinceLastRehash = new boolean[newSize];
        occupiedCount = 0;

        for (int i = 0; i < oldKeys.length; i++) {
            if (oldKeys[i] != null) {
                put(oldKeys[i], oldValues[i]);
            }
        }
    }
}
