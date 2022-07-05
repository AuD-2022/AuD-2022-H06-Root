package h06.h6;

import h06.*;
import h06.transformers.MyDateTransformer;
import h06.utils.ReflectionUtils;
import h06.utils.TutorAssertions;
import kotlin.Pair;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.extension.JagrExecutionCondition;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.fail;

@TestForSubmission("h06")
public class RuntimeTestTests {

    private static final int TEST_SET_SIZE = ReflectionUtils.getFieldValue(
        ReflectionUtils.getField(RuntimeTest.class, "TEST_SET_SIZE"), null);

    private static MyDate[][] MY_DATES_TEST_SET;

    @BeforeAll
    public static void setup() {
        MyDateTransformer.CONSTRUCTOR_SURROGATE_ACTIVE = true;
        MyDateTransformer.HASH_CODE_SURROGATE_ACTIVE = true;
        MY_DATES_TEST_SET = RuntimeTest.generateTestdata();
    }

    @AfterAll
    public static void resetFlags() {
        MyDateTransformer.CONSTRUCTOR_SURROGATE_ACTIVE = false;
        MyDateTransformer.HASH_CODE_SURROGATE_ACTIVE = false;
    }

    @Test
    @ExtendWith(JagrExecutionCondition.class)
    public void testGenerateTestdataDimensions() {
        TutorAssertions.assertEquals(
            2, null,
            MY_DATES_TEST_SET.length, null,
            "The outer array returned by [[[generateTestdata()]]] did not have the expected size", List::of);
        TutorAssertions.assertEquals(
            TEST_SET_SIZE, null,
            MY_DATES_TEST_SET[0].length, null,
            "The inner array returned by [[[generateTestdata()]]] did not have the expected size at index 0", List::of);
        TutorAssertions.assertEquals(
            TEST_SET_SIZE, null,
            MY_DATES_TEST_SET[1].length, null,
            "The inner array returned by [[[generateTestdata()]]] did not have the expected size at index 1", List::of);
    }

    @Test
    @ExtendWith(JagrExecutionCondition.class)
    public void testCreateTestSetMyIndexHoppingHashMap() {
        int j = 1;

        for (int i = 1; i <= 2; i++) {
            for (int k = 1; k <= 2; k++) {
                for (int l = 1; l <= 2; l++) {
                    MyDate[][] testData = getTestData();
                    TestSet<MyDate> testSet = RuntimeTest.createTestSet(i, j, k, l, testData);

                    TutorAssertions.assertEquals(
                        i == 1, "[[[MyDate]]] array with [[[randomBoolean = %b]]]".formatted(i == 1),
                        testSet.getTestData()[0].getBool(), "[[[MyDate]]] array with [[[randomBoolean = %b]]]".formatted(i != 1),
                        "[[[createTestSet(int, int, int, int, MyDate[][])]]] did not pass the correct testData to the constructor "
                            + "of [[[TestSet]]]",
                        getCreateTestSetInputs(i, j, k, l));

                    MyMap<MyDate, MyDate> myMap = testSet.getHashTable();

                    TutorAssertions.assertEquals(
                        true, null,
                        myMap instanceof MyIndexHoppingHashMap, null,
                        "[[[createTestSet(int, int, int, int, MyDate[][])]]] did not use [[[MyIndexHoppingHashMap]]] as hash table",
                        getCreateTestSetInputs(i, j, k, l));
                    TutorAssertions.assertEquals(
                        (int) Math.pow(2, l == 1 ? 12 : 6), null,
                        ((Object[]) ReflectionUtils.getFieldValue("theKeys", myMap)).length, null,
                        "The hash map's size did not equal the expected value",
                        getCreateTestSetInputs(i, j, k, l));
                    TutorAssertions.assertEquals(
                        2.0, null,
                        ReflectionUtils.getFieldValue("resizeFactor", (MyIndexHoppingHashMap<MyDate, MyDate>) myMap), null,
                        "The [[[MyHoppingIndexHashMap]]] object was not initialized with a resize factor of 2.0",
                        getCreateTestSetInputs(i, j, k, l));
                    TutorAssertions.assertEquals(
                        0.75, null,
                        ReflectionUtils.getFieldValue("resizeThreshold", (MyIndexHoppingHashMap<MyDate, MyDate>) myMap), null,
                        "The [[[MyHoppingIndexHashMap]]] object was not initialized with a resize threshold of 0.75",
                        getCreateTestSetInputs(i, j, k, l));

                    BinaryFct2Int<MyDate> binaryFct2Int = ReflectionUtils.getFieldValue("hashFunction", myMap);

                    if (k == 1) {
                        TutorAssertions.assertEquals(
                            true, null,
                            binaryFct2Int instanceof LinearProbing, null,
                            "The test set's [[[MyHoppingIndexHashMap]]] object was not initialized with a "
                                + "[[[LinearProbing]]] object",
                            getCreateTestSetInputs(i, j, k, l));

                        Fct2Int<MyDate> internalHashFunction = ReflectionUtils.getFieldValue("hashFct", binaryFct2Int);

                        TutorAssertions.assertEquals(
                            true, null,
                            internalHashFunction instanceof Hash2IndexFct, null,
                            "The internal hash function of the [[[LinearProbing]]] object that was passed to the constructor "
                                + "of [[[MyHoppingIndexHashMap]]] as fourth argument is not an instance of [[[Hash2IndexFct]]]",
                            getCreateTestSetInputs(i, j, k, l));
                        TutorAssertions.assertEquals(
                            0, null,
                            ReflectionUtils.getFieldValue("offset", internalHashFunction), null,
                            "The internal hash function of the [[[LinearProbing]]] object that was passed to the constructor "
                                + "of [[[MyHoppingIndexHashMap]]] as fourth argument does not have an offset of 0",
                            getCreateTestSetInputs(i, j, k, l));
                    } else {
                        TutorAssertions.assertEquals(
                            true, null,
                            binaryFct2Int instanceof DoubleHashing, null,
                            "The test set's [[[MyHoppingIndexHashMap]]] object was not initialized with a "
                                + "[[[DoubleHashing]]] object",
                            getCreateTestSetInputs(i, j, k, l));

                        Fct2Int<MyDate> internalHashFunction1 = ReflectionUtils.getFieldValue("fct1", binaryFct2Int);
                        Fct2Int<MyDate> internalHashFunction2 = ReflectionUtils.getFieldValue("fct2", binaryFct2Int);

                        TutorAssertions.assertEquals(
                            true, null,
                            internalHashFunction1 instanceof Hash2IndexFct, null,
                            "The first internal hash function of the [[[LinearProbing]]] object that was passed to the "
                                + "constructor of [[[MyHoppingIndexHashMap]]] as fourth argument is not an instance of [[[Hash2IndexFct]]]",
                            getCreateTestSetInputs(i, j, k, l));
                        TutorAssertions.assertEquals(
                            true, null,
                            internalHashFunction1 instanceof Hash2IndexFct, null,
                            "The second internal hash function of the [[[LinearProbing]]] object that was passed to the "
                                + "constructor of [[[MyHoppingIndexHashMap]]] as fourth argument is not an instance of [[[Hash2IndexFct]]]",
                            getCreateTestSetInputs(i, j, k, l));
                        TutorAssertions.assertEquals(
                            0, null,
                            ReflectionUtils.getFieldValue("offset", internalHashFunction1), null,
                            "The first internal hash function of the [[[LinearProbing]]] object that was passed to the " +
                                "constructor of [[[MyHoppingIndexHashMap]]] as fourth argument does not have an offset of 0",
                            getCreateTestSetInputs(i, j, k, l));
                        TutorAssertions.assertEquals(
                            42, null,
                            ReflectionUtils.getFieldValue("offset", internalHashFunction2), null,
                            "The second internal hash function of the [[[LinearProbing]]] object that was passed to the "
                                + "constructor of [[[MyHoppingIndexHashMap]]] as fourth argument does not have an offset of 42",
                            getCreateTestSetInputs(i, j, k, l));
                    }
                }
            }
        }
    }

    @Test
    @ExtendWith(JagrExecutionCondition.class)
    public void testCreateTestSetMyListsHashMap() {
        int j = 2;

        for (int i = 1; i <= 2; i++) {
            for (int k = 1; k <= 2; k++) {
                for (int l = 1; l <= 2; l++) {
                    MyDate[][] testData = getTestData();
                    TestSet<MyDate> testSet = RuntimeTest.createTestSet(i, j, k, l, testData);

                    TutorAssertions.assertEquals(
                        i == 1, "[[[MyDate]]] array with [[[randomBoolean = %b]]]".formatted(i == 1),
                        testSet.getTestData()[0].getBool(), "[[[MyDate]]] array with [[[randomBoolean = %b]]]".formatted(i != 1),
                        "[[[createTestSet(int, int, int, int, MyDate[][])]]] did not pass the correct testData to the constructor "
                            + "of [[[TestSet]]]",
                        getCreateTestSetInputs(i, j, k, l));

                    MyMap<MyDate, MyDate> myMap = testSet.getHashTable();

                    TutorAssertions.assertEquals(
                        true, null,
                        myMap instanceof MyListsHashMap, null,
                        "[[[createTestSet(int, int, int, int, MyDate[][])]]] did not use [[[MyListsHashMap]]] as hash table",
                        getCreateTestSetInputs(i, j, k, l));

                    Fct2Int<MyDate> fct2Int = ReflectionUtils.getFieldValue("hashFunction", myMap);

                    TutorAssertions.assertEquals(
                        true, null,
                        fct2Int instanceof Hash2IndexFct, null,
                        "The hash function of the [[[MyListsHashMap]]] object is not an instance of [[[Hash2IndexFct]]]",
                        getCreateTestSetInputs(i, j, k, l));
                    TutorAssertions.assertEquals(
                        0, null,
                        ReflectionUtils.getFieldValue("offset", fct2Int), null,
                        "The offset of the [[[MyListsHashMap]]] object's hash function is not 0",
                        getCreateTestSetInputs(i, j, k, l));
                    TutorAssertions.assertEquals(
                        l == 1 ? 3 * TEST_SET_SIZE : TEST_SET_SIZE / 10, null,
                        ((Object[]) ReflectionUtils.getFieldValue("table", myMap)).length, null,
                        "The hash map's size did not equal the expected value",
                        getCreateTestSetInputs(i, j, k, l));
                }
            }
        }
    }

    @Test
    @ExtendWith(JagrExecutionCondition.class)
    public void testGenerateTestdataElementInit() {
        if (MY_DATES_TEST_SET.length != 2 && MY_DATES_TEST_SET[0].length != TEST_SET_SIZE && MY_DATES_TEST_SET[1].length != TEST_SET_SIZE) {
            fail("The array returned by [[[generateTestdata()]]] did not have the expected dimensions");
        }

        Field randomBooleanField = ReflectionUtils.getField(MyDate.class, "randomBoolean");

        for (int i = 0; i < TEST_SET_SIZE; i++) {
            TutorAssertions.assertEquals(
                true, null,
                ReflectionUtils.getFieldValue(randomBooleanField, MY_DATES_TEST_SET[0][i]), null,
                ("The [[[MyDate]]] object at index [0][%d] in the array returned by [[[generateTestdata()]]] was not initialized "
                    + " correctly").formatted(i), List::of);
            TutorAssertions.assertEquals(
                false, null,
                ReflectionUtils.getFieldValue(randomBooleanField, MY_DATES_TEST_SET[1][i]), null,
                ("The [[[MyDate]]] object at index [1][%d] in the array returned by [[[generateTestdata()]]] was not initialized "
                    + " correctly").formatted(i), List::of);
        }
    }

    @Test
    @ExtendWith(JagrExecutionCondition.class)
    public void testTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        MyMapImpl myMap = new MyMapImpl(calendar);
        MyDate[] testData = new MyDate[1000];
        MyDate testDatum = new MyDate(calendar, false);
        Arrays.fill(testData, testDatum);
        TestSet<MyDate> testSet = new TestSet<>(myMap, testData);
        Supplier<List<Pair<String, String>>> inputsSupplier = () -> List.of(
            new Pair<>("[[[calendar]]]", ("[[[Calendar]]] object with year = %d, month = %d, day_of_month = %d, "
                + "hour_of_day = %d, minute = %d").formatted(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))),
            new Pair<>("[[[myMap]]]", "[[[MyMap<MyDate, MyDate>]]] reference implementation"),
            new Pair<>("[[[testData]]]", "[[[MyDate]]] array with size 1000, filled with valid [[[MyDate]]] objects "
                + "([[[new MyDate(calendar, false)]]]; Constructor has been replaced to ensure correct initialization)"),
            new Pair<>("[[[testSet]]]", "[[[new TestSet<>(myMap, testData)]]]")
        );

        RuntimeTest.test(testSet);

        TutorAssertions.assertEquals(
            750, null,
            myMap.putInvocationCounter, null,
            "[[[put(K, V)]]] was not called 750 times by [[[RuntimeTest.test(testSet)]]]", inputsSupplier);
        TutorAssertions.assertEquals(
            testData.length, null,
            myMap.containsKeyInvocationCounter, null,
            "[[[containsKey(K)]]] was not called %d times by [[[RuntimeTest.test(testSet)]]]".formatted(testData.length),
            inputsSupplier);
        TutorAssertions.assertEquals(
            testData.length, null,
            myMap.getValueInvocationCounter, null,
            "[[[getValue(K)]]] was not called %d times by [[[RuntimeTest.test(testSet)]]]".formatted(testData.length),
            inputsSupplier);
        TutorAssertions.assertEquals(
            testData.length, null,
            myMap.removeInvocationCounter, null,
            "[[[remove(K)]]] was not called %d times by [[[RuntimeTest.test(testSet)]]]".formatted(testData.length),
            inputsSupplier);
    }

    private static Supplier<List<Pair<String, String>>> getCreateTestSetInputs(int i, int j, int k, int l) {
        return () -> List.of(
            new Pair<>("[[[i]]]", String.valueOf(i)),
            new Pair<>("[[[j]]]", String.valueOf(j)),
            new Pair<>("[[[k]]]", String.valueOf(k)),
            new Pair<>("[[[l]]]", String.valueOf(l)),
            new Pair<>("[[[testData]]]", "Two-dimensional [[[MyDate]]] array, each object initialized in the way the exercise "
                + "sheet describes")
        );
    }

    private static MyDate[][] getTestData() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        MyDate[][] testData = new MyDate[][] {new MyDate[1000], new MyDate[1000]};
        for (int i = 0; i < testData.length; i++) {
            Arrays.fill(testData[i], new MyDate(calendar, i == 0));
        }
        return testData;
    }

    private static class MyMapImpl implements MyMap<MyDate, MyDate> {
        private final MyDate myDateReturnValue;
        private int containsKeyInvocationCounter = 0;
        private int getValueInvocationCounter = 0;
        private int putInvocationCounter = 0;
        private int removeInvocationCounter = 0;

        private MyMapImpl(Calendar calendar) {
            this.myDateReturnValue = new MyDate(calendar, false);
        }

        @Override
        public boolean containsKey(MyDate key) {
            containsKeyInvocationCounter++;
            return false;
        }

        @Override
        public @Nullable MyDate getValue(MyDate key) {
            getValueInvocationCounter++;
            return myDateReturnValue;
        }

        @Override
        public @Nullable MyDate put(MyDate key, MyDate value) {
            putInvocationCounter++;
            return myDateReturnValue;
        }

        @Override
        public @Nullable MyDate remove(MyDate key) {
            removeInvocationCounter++;
            return myDateReturnValue;
        }
    }
}
