package h06.h6;

import h06.MyDate;
import h06.MyMap;
import h06.RuntimeTest;
import h06.TestSet;
import h06.transformers.MyDateTransformer;
import h06.utils.ReflectionUtils;
import h06.utils.TutorAssertions;
import kotlin.Pair;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.extension.JagrExecutionCondition;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.time.Instant;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static h06.Config.SEED;
import static h06.Config.STREAM_SIZE;
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

    private static class Provider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            Random random = new Random(SEED);

            return Stream.generate(() -> null)
                .limit(STREAM_SIZE)
                .map(Arguments::of);
        }
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
