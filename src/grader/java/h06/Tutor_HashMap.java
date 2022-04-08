package h06;

public class Tutor_HashMap<K, V> implements MyMap<K, V> {

    private int containsKeyCount;
    private int getValueCount;
    private int putCount;
    private int removeCount;

    @Override
    public boolean containsKey(K key) {
        containsKeyCount++;
        return false;
    }

    @Override
    public V getValue(K key) {
        getValueCount++;
        return null;
    }

    @Override
    public V put(K key, V value) {
        putCount++;
        return null;
    }

    @Override
    public V remove(K key) {
        removeCount++;
        return null;
    }

    public int getContainsKeyCount() {
        return containsKeyCount;
    }

    public int getGetValueCount() {
        return getValueCount;
    }

    public int getPutCount() {
        return putCount;
    }

    public int getRemoveCount() {
        return removeCount;
    }
}
