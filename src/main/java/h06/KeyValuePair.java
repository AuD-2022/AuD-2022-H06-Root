package h06;

public class KeyValuePair<K, V>
{
  private final K key;
  private V value;

  public KeyValuePair(K key, V value)
  {
    this.key = key;
    this.value = value;
  }

  public K getKey()
  {
    return key;
  }

  public V getValue()
  {
    return value;
  }

  public void setValue(V newValue)
  {
    value = newValue;
  }
}
