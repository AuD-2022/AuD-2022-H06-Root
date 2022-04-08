package h06;

public class MyIndexHoppingHashMap<K extends Object, V> implements MyMap<K, V> {
    private final double resizeThreshold;
    private final double resizeFactor;
    private K[] theKeys;
    private V[] theValues;
    private boolean[] occupiedSinceLastRehash;
    private int occupiedCount = 0;
    private final BinaryFct2Int<K> hashFunction;

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

    @Override
    public boolean containsKey(K key) {
        for (int i = 0; i < hashFunction.getTableSize(); i++) {
            int hashValue = hashFunction.apply(key, i);
            // Position would have been occupied if element was existing
            if (!occupiedSinceLastRehash[hashValue]) {
                return false;
            }
            if (key.equals(theKeys[hashValue])) {
                return true;
            }
        }

        return false;
    }

    @Override
    public V getValue(K key) {
        for (int i = 0; i < hashFunction.getTableSize(); i++) {
            int hashValue = hashFunction.apply(key, i);
            // Position would have been occupied if element was existing
            if (!occupiedSinceLastRehash[hashValue]) {
                return null;
            }
            if (key.equals(theKeys[hashValue])) {
                return theValues[hashValue];
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        for (int i = 0; i < hashFunction.getTableSize(); i++) {
            int hashValue = hashFunction.apply(key, i);

            if (!occupiedSinceLastRehash[hashValue]) {
                if (occupiedCount + 1 > hashFunction.getTableSize() * resizeThreshold) {
                    rehash();
                    return put(key, value);
                } else {
                    occupiedSinceLastRehash[hashValue] = true;
                    theKeys[hashValue] = key;
                    theValues[hashValue] = value;
                    occupiedCount++;
                    return null;
                }
            } else if (theKeys[hashValue].equals(key)) {
                V oldValue = theValues[hashValue];
                theValues[hashValue] = value;
                return oldValue;
            }
        }

        // Only previously occupied places available, insert element into the first free one
        for (int i = 0; i < hashFunction.getTableSize(); i++) {
            int hashValue = hashFunction.apply(key, i);

            if (theKeys[hashValue] == null) {
                theKeys[hashValue] = key;
                theValues[hashValue] = value;
                return null;
            }
        }

        // Should never happen (hashtable is full)
        return null;
    }

    @Override
    public V remove(K key) {
        for (int i = 0; i < hashFunction.getTableSize(); i++) {
            int hashValue = hashFunction.apply(key, i);
            // Position would have been occupied if element was existing
            if (!occupiedSinceLastRehash[hashValue]) {
                return null;
            }
            if (key.equals(theKeys[hashValue])) {
                V oldValue = theValues[hashValue];
                theKeys[hashValue] = null;
                theValues[hashValue] = null;
                return oldValue;
            }
        }

        return null;
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
