package h06;

public class ModuloUtil {

	/**
	 * Calculates (a + b) % n without generating intermediate result > n.
	 * @param a
	 * @param b
	 * @param n
	 * @return (a + b) % n
	 */
	public static int addModulo(int a, int b, int n) {
		if (a > b)
		{
			int temp = a;
			a = b;
			b = temp;
		}

		// (a + b) % n == ((b % n) + (a % n) % n == ((b % n) + ((-n + (a % n)) % n)) % n == ((b % n) - ((n - (a % n)) % n)) % n
		return Math.floorMod(Math.floorMod(b, n) - Math.floorMod(n - Math.floorMod(a, n), n), n);
	}
}
