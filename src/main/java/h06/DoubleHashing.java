package h06;

public class DoubleHashing<T> implements BinaryFct2Int<T>
{
	/**
	 * Hash function 1 used in object internal operations. Set in the constructor.
	 */
	private final Hash2IndexFct<T> fct1;

	/**
	 * Hash function 2 used in object internal operations. Set in the constructor.
	 */
	private final Hash2IndexFct<T> fct2;

	/**
	 * Creates an object of the DoubleHashing class.
	 * The object uses the hash functions specified in the "fct1" and "fct2" parameters.
	 * Therefore, the hash functions are stored in the object attributes of the same name for further use.
	 * @param fct1 The first hash function to be used in upcoming tasks.
	 * @param fct2 The second hash function to be used in upcoming tasks.
	 */
	public DoubleHashing(Hash2IndexFct<T> fct1, Hash2IndexFct<T> fct2) {
		this.fct1 = fct1;
		this.fct2 = fct2;
	}

    /**
     * Returns the current table size.
     * @return Current table size.
     */
	@Override
	public int getTableSize()
	{
		return fct1.getTableSize();
	}

    /**
     * Sets the current table size for both internal hash functions.
     * @param tableSize New table size.
     */
	@Override
	public void setTableSize(int tableSize) {
		fct1.setTableSize(tableSize);
		fct2.setTableSize(tableSize);
	}

    /**
     * Calculates the hash value of parameter "key" using "fct1"
     * and "fct2" as well as parameter "factor".
     * @param key The key from which to calculate the hash value.
     * @param factor The factor that is multiplied by the second hash function result.
     * @return ModuloUtil.addModulo(hash0(key), factor * hash1(key), max(tableSize, factor * hash1(key)) % tableSize
     */
	@Override
	public int apply(T key, int factor) {
		int a = fct1.apply(key);
		long b = fct2.apply(key);
		int tableSize = getTableSize();
		long i = factor;

        if (Math.floorMod(b, 2) == 0) {
            b++;
        }

		return ModuloUtil.addModulo(a, Math.toIntExact(Math.floorMod(i * b, tableSize)), tableSize);
	}
}
