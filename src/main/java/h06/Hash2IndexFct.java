package h06;

public class Hash2IndexFct<T extends Object> implements Fct2Int<T>
{
	/**
	 * Table size used in calculation.
	 */
	private int tableSize;

	/**
	 * Offset used in calculation.
	 */
	private final int offset;

	/**
	 * Creates a new hash function h(x) = (x + offset) mod tableSize.
	 * @param initTableSize Initial table size used in calculation.
	 * @param offset Offset used in calculation.
	 */
	public Hash2IndexFct(int initTableSize, int offset) {
		this.offset = offset;
		tableSize = initTableSize;
	}

	@Override
	/**
	 * Calculates the hash value of parameter "key".
	 * @param key The key from which to calculate the hash value.
	 * @return key.hashCode() modulo tableSize
	 */
	public int apply(T key)
	{
	  return Math.floorMod(key.hashCode() + offset, tableSize);
	}

	@Override
	/**
	 * Returns the current table size.
	 * @return Current table size.
	 */
	public int getTableSize()
	{
		return tableSize;
	}

	@Override
	/**
	 * Sets the current table size.
	 * @param tableSize New table size.
	 */
	public void setTableSize(int tableSize)
	{
		this.tableSize = tableSize;
	}
}
