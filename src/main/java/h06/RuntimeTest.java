package h06;

import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

public class RuntimeTest
{
	private final static int TEST_SET_SIZE = 1_000;

    /**
     * Generates two test data sets with 1,000 dates each.
     * The first test data set is in component 0 of the returned array and is initialized with true.
     * The second test data set is in component 1 of the returned array and is initialized with false.
     * The dates are between 1970 and 2022.
     * @return Two test data sets of 1,000 dates each.
     */
    public static MyDate[][] generateTestdata()
    {
        Calendar maxTimeCalendar = Calendar.getInstance();
        maxTimeCalendar.set(2023, Calendar.JANUARY, 1);
        long maxTime = maxTimeCalendar.getTimeInMillis();

        MyDate[][] testSet = new MyDate[2][TEST_SET_SIZE];

        for (int i = 0; i < TEST_SET_SIZE; i++)
        {
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTimeInMillis(ThreadLocalRandom.current().nextLong(1, maxTime));
            testSet[0][i] = new MyDate(currentCalendar, true);
            testSet[1][i] = new MyDate(currentCalendar, false);
        }

        return testSet;
    }

    /**
     * Generates a test set.
     * @param i See exercise sheet.
     * @param j See exercise sheet.
     * @param k See exercise sheet.
     * @param l See exercise sheet.
     * @param testData The testdata used.
     * @return A test set.
     */
    public static TestSet<MyDate> createTestSet(int i, int j, int k, int l, MyDate[][] testData) {
        MyDate[] usedTestData = testData[i - 1];

        int initialTableSize = (l == 1) ? ((j == 1) ? 4096 : TEST_SET_SIZE * 3)
            : ((j == 1) ? 64 : TEST_SET_SIZE / 10);

        Hash2IndexFct<MyDate> basicHashFunction = new Hash2IndexFct<MyDate>(initialTableSize, 0);

        MyMap<MyDate, MyDate> hashMap;

        if (j == 1) {
            BinaryFct2Int<MyDate> hashFunction = (k == 1) ? new LinearProbing<MyDate>(basicHashFunction) :
                new DoubleHashing<MyDate>(basicHashFunction, new Hash2IndexFct<MyDate>(initialTableSize, 42));
            hashMap = new MyIndexHoppingHashMap<MyDate, MyDate>(initialTableSize, 2.0, 0.75, hashFunction);
        }
        else {
            hashMap = new MyListsHashMap<MyDate, MyDate>(basicHashFunction);
        }

        return new TestSet<MyDate>(hashMap, usedTestData);
    }

    /**
     * Tests the given test set.
     * @param testSet The test set to test.
     */
	public static void test(TestSet<MyDate> testSet) {
        MyMap<MyDate, MyDate> hashMap = testSet.getHashTable();
        MyDate[] testData = testSet.getTestData();

        for (int n = 0; n < TEST_SET_SIZE * 3/4; n++) {
			hashMap.put(testData[n], testData[n]);
		}

		for (int n = 0; n < TEST_SET_SIZE; n++) {
			hashMap.containsKey(testData[n]);
		}

		for (int n = 0; n < TEST_SET_SIZE; n++) {
			hashMap.getValue(testData[n]);
		}

		for (int n = 0; n < TEST_SET_SIZE; n++) {
			hashMap.remove(testData[n]);
		}
	}
}
