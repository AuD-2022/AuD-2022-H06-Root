package h06;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.lang.reflect.Field;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

@TestForSubmission("H06")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class H06_Tests {
    private static MyDate[][] testData;

    final int TestDataSize = 1_000;

    @Test
    @Order(1)
    public void H06_RuntimeTestGenerateTestData() {
        testData = RuntimeTest.generateTestdata();
        assertEquals(testData.length, 2, "Das Haupt-Array der Testdaten sollte die Länge 2 haben.");
        assertEquals(testData[0].length, 1000, "Das Unter-Array 0 der Testdaten sollte die Länge 1000 haben.");
        assertEquals(testData[1].length, 1000, "Das Unter-Array 1 der Testdaten sollte die Länge 1000 haben.");

        for (int i = 0; i < 1000; i++) {
            checkDate(true, testData[0][i]);
            checkDate(false, testData[1][i]);
        }
    }

    private void checkDate(boolean expectedTrue, MyDate date) {
        assertTrue(date.getYear() <= 2022 && date.getYear() >= 1970,
            "Das Jahr der generierten Testdaten sollte zwischen 1970 und 2022 liegen. Es wurde ein Datum mit folgendem Jahr gefunden: " + date.getYear());
        assertEquals(expectedTrue, date.getBool(),
            "Es wurde erwartet, dass alle Daten im Unter-Array" + (expectedTrue ? " 0 " : "1 ") + "den Wert " + expectedTrue + " haben. Es wurde ein Datum mit Wert " + date.getBool() + " gefunden.");
    }

    @Test
    @Order(2)
    public void H06_RuntimeTestCreateTestSet() {
        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <= 2; j++) {
                for (int k = 1; k <= 2; k++) {
                    for (int l = 1; l <= 2; l++) {
                        try {
                            TestSet<MyDate> testSet = RuntimeTest.createTestSet(i, j, k, l, testData);
                            TestSet<MyDate> solutionTestSet = createTestSet(i, j, k, l, testData);
                            assertArrayEquals(testSet.getTestData(), solutionTestSet.getTestData());
                            MyMap<MyDate,MyDate> testSetHashTable = testSet.getHashTable();
                            MyMap<MyDate,MyDate> solutionTestSetHashTable = testSet.getHashTable();
                            assertEquals(testSetHashTable.getClass(), solutionTestSetHashTable.getClass());

                            if (solutionTestSetHashTable.getClass().equals(MyIndexHoppingHashMap.class)){
                                Field tableField = testSetHashTable.getClass().getDeclaredField("theKeys");
                                Field solutionTableField = solutionTestSetHashTable.getClass().getDeclaredField("theKeys");
                                tableField.setAccessible(true);
                                solutionTableField.setAccessible(true);

                                assertEquals(((Object[])tableField.get(testSetHashTable)).length,
                                    ((Object[])solutionTableField.get(solutionTestSetHashTable)).length);
                            }
                            else {
                                Field tableField = testSetHashTable.getClass().getDeclaredField("table");
                                Field solutionTableField = solutionTestSetHashTable.getClass().getDeclaredField("table");
                                tableField.setAccessible(true);
                                solutionTableField.setAccessible(true);

                                assertEquals(((LinkedList<KeyValuePair<Object, Object>>[])tableField.get(testSetHashTable)).length,
                                    ((LinkedList<KeyValuePair<Object, Object>>[])solutionTableField.get(solutionTestSetHashTable)).length);
                            }
                        } catch (Exception e) {
                            fail(e.getMessage());
                        }
                    }
                }
            }
        }
    }

    private final static int TEST_SET_SIZE = 1_000;

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

    @Test
    @Order(3)
    public void H06_RuntimeTestExecuteTest() {
        Tutor_HashMap<MyDate, MyDate> hashMap = new Tutor_HashMap<>();
        TestSet<MyDate> testSet = new TestSet<>(hashMap, testData[0]);
        RuntimeTest.test(testSet);

        assertEquals(TestDataSize, hashMap.getContainsKeyCount(), "Test run() die Anzahl der Aufrufe für containsKey ist falsch.");
        assertEquals(TestDataSize, hashMap.getGetValueCount(), "Test run() die Anzahl der Aufrufe für getValue ist falsch.");
        assertEquals(TestDataSize * (3f/4f), hashMap.getPutCount(), "Test run() die Anzahl der Aufrufe für putCount ist falsch.");
        assertEquals(TestDataSize, hashMap.getRemoveCount(), "Test run() die Anzahl der Aufrufe für removeCount ist falsch.");
    }
}
