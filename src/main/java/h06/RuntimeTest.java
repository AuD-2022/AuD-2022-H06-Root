package h06;

import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

public class RuntimeTest
{
	private final static int TEST_SET_SIZE = 1_000;

    public static MyDate[][] generateTestdata()
    {
        Calendar maxTimeCalendar = Calendar.getInstance();
        maxTimeCalendar.set(2022, Calendar.DECEMBER, 31, 23, 59);
        long maxTime = maxTimeCalendar.getTimeInMillis() + 1;

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

    public static TestSet<MyDate> createTestSet(int i, int j, int k, int l, MyDate[][] testData) {
        MyDate[] usedTestData = testData[i - 1];

        int initialTableSize = (l == 1) ? ((j == 1) ? 4096 : TEST_SET_SIZE * 3)
            : ((j == 1) ? 64 : TEST_SET_SIZE / 10);

        HashCodeTableIndexFct<MyDate> basicHashFunction = new HashCodeTableIndexFct<MyDate>(initialTableSize, 0);

        MyMap<MyDate, MyDate> hashMap;

        if (j == 1) {
            BinaryFct2Int<MyDate> hashFunction = (k == 1) ? new LinearProbingTableIndexFct<MyDate>(basicHashFunction) :
                new DoubleHashingTableIndexFct<MyDate>(basicHashFunction, new HashCodeTableIndexFct<MyDate>(initialTableSize, 42));
            hashMap = new MyIndexHoppingHashMap<MyDate, MyDate>(initialTableSize, 2.0, 0.75, hashFunction);
        }
        else {
            hashMap = new MyListsHashMap<MyDate, MyDate>(basicHashFunction);
        }

        return new TestSet<MyDate>(hashMap, usedTestData);
    }

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
