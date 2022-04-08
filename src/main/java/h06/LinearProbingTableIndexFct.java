package h06;

public class LinearProbingTableIndexFct<T extends Object> implements BinaryFct2Int<T>
{
	/**
	 * Hash function used in object internal operations. Set in the constructor.
	 */
	private final Fct2Int<T> hashFct;

	/**
	 * Creates an object of the LinearProbingTableIndexFct class.
	 * The object uses the hash function specified in the "internalHashFunction" parameter.
	 * Therefore the hash function is stored in the object attribute of the same name for further use.
	 * @param hashFct The hash function to be used in upcoming tasks.
	 */
	public LinearProbingTableIndexFct(Fct2Int<T> hashFct)
	{
		this.hashFct = hashFct;
	}

	@Override
	/**
	 * Returns the current table size.
	 * @return Current table size.
	 */
	public int getTableSize()
	{
		return hashFct.getTableSize();
	}

	@Override
	/**
	 * Sets the current table size.
	 * @param tableSize New table size.
	 */
	public void setTableSize(int tableSize)
	{
		hashFct.setTableSize(tableSize);
	}

	@Override
	/**
	 * Calculates the hash value of parameter "key" by using the hash function passed
	 * when the object was created and adds the offset specified in the parameter "offset".
	 * @param key The key from which to calculate the hash value.
	 * @param offset The offset to add.
	 * @return ModuloUtil.addModulo(hash(key), offset, max(tableSize, offset)) % tableSize
	 */
	public int apply(T key, int offset)
	{
		int tableSize = getTableSize();
		int a = hashFct.apply(key);
		int i = offset;

		return ModuloUtil.addModulo(a, i, tableSize);
	}
}
