package h06.h5;

import h06.MyDate;
import h06.transformers.MyDateTransformer;
import h06.utils.ReflectionUtils;
import h06.utils.TutorAssertions;
import kotlin.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.extension.JagrExecutionCondition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static h06.Config.SEED;
import static h06.Config.STREAM_SIZE;

@TestForSubmission("h06")
public class MyDateTests {

    @BeforeEach
    public void resetSurrogateFlag() {
        MyDateTransformer.HASH_CODE_SURROGATE_ACTIVE = false;
    }

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testConstructor(Calendar calendar, boolean bool) {
        Supplier<List<Pair<String, String>>> inputsSupplier = () -> List.of(
            new Pair<>("Argument #1 - [[[date]]]", ("[[[Calendar]]] object with year = %d, month = %d, day_of_month = %d, "
                + "hour_of_day = %d, minute = %d").formatted(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))),
            new Pair<>("Argument #2 - [[[randomBoolean]]]", String.valueOf(bool))
        );
        MyDate instance = new MyDate((Calendar) calendar.clone(), bool);

        TutorAssertions.assertEquals(
            calendar.get(Calendar.YEAR), null,
            ReflectionUtils.getFieldValue("year", instance), null,
            "Field [[[year]]] does not have expected value", inputsSupplier);
        TutorAssertions.assertEquals(
            calendar.get(Calendar.MONTH), null,
            ReflectionUtils.getFieldValue("month", instance), null,
            "Field [[[month]]] does not have expected value", inputsSupplier);
        TutorAssertions.assertEquals(
            calendar.get(Calendar.DAY_OF_MONTH), null,
            ReflectionUtils.getFieldValue("day", instance), null,
            "Field [[[day]]] does not have expected value", inputsSupplier);
        TutorAssertions.assertEquals(
            calendar.get(Calendar.HOUR_OF_DAY), null,
            ReflectionUtils.getFieldValue("hour", instance), null,
            "Field [[[hour]]] does not have expected value", inputsSupplier);
        TutorAssertions.assertEquals(
            calendar.get(Calendar.MINUTE), null,
            ReflectionUtils.getFieldValue("minute", instance), null,
            "Field [[[minute]]] does not have expected value", inputsSupplier);
        TutorAssertions.assertEquals(
            bool, null,
            ReflectionUtils.getFieldValue("randomBoolean", instance), null,
            "Field [[[randomBoolean]]] does not have expected value", inputsSupplier);
    }

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    @ExtendWith(JagrExecutionCondition.class)
    public void testHashCode(Calendar calendar, boolean bool) throws NoSuchMethodException, InvocationTargetException,
        InstantiationException, IllegalAccessException {
        MyDate instance = MyDate.class
            .getDeclaredConstructor(Calendar.class, boolean.class, int.class)
            .newInstance(calendar.clone(), bool, 0);

        TutorAssertions.assertEquals(
            hashCodeRefImpl(calendar, bool), null,
            instance.hashCode(), null,
            "[[[hashCode()]]] did not return the expected value", () -> List.of(
                new Pair<>("Argument #1 - [[[date]]]", ("[[[Calendar]]] object with year = %d, month = %d, day_of_month = %d, "
                    + "hour_of_day = %d, minute = %d").formatted(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))),
                new Pair<>("Argument #2 - [[[randomBoolean]]]", String.valueOf(bool))
            ));
    }

    @Test
    @ExtendWith(JagrExecutionCondition.class)
    public void testEquals() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        MyDateTransformer.HASH_CODE_SURROGATE_ACTIVE = true;
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, Calendar.JANUARY, 1, 0, 0);
        Supplier<List<Pair<String, String>>> inputsSupplier = () -> List.of(
            new Pair<>("Argument #1 - [[[date]]]", ("[[[Calendar]]] object with year = %d, month = %d, day_of_month = %d, "
                + "hour_of_day = %d, minute = %d").formatted(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))),
            new Pair<>("Argument #2 - [[[randomBoolean]]]", String.valueOf(true))
        );

        Constructor<MyDate> injectedConstructor = MyDate.class.getDeclaredConstructor(Calendar.class, boolean.class, int.class);
        MyDate instance1 = injectedConstructor.newInstance(calendar, true, 0);
        MyDate instance2 = injectedConstructor.newInstance(calendar, true, 0);
        MyDate instance3 = injectedConstructor.newInstance(calendar, true, 1);

        TutorAssertions.assertEquals(
            true, null,
            instance1.equals(instance2), null,
            "[[[equals(Object)]]] did not return [[[true]]], even though both objects have the same hashCode", inputsSupplier);
        TutorAssertions.assertEquals(
            false, null,
            instance1.equals(instance3), null,
            "[[[equals(Object)]]] did not return [[[true]]], even though both objects have the same hashCode", inputsSupplier);
    }

    private static class Provider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            Random random = new Random(SEED);

            return Stream.generate(() -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(
                    random.nextInt(1970, 2021 + 1),
                    random.nextInt(0, 11 + 1),
                    random.nextInt(1, 28 + 1),
                    random.nextInt(0, 24 + 1),
                    random.nextInt(0, 60 + 1)
                );
                return new Object[] {calendar, random.nextBoolean()};
            })
                .limit(STREAM_SIZE)
                .map(Arguments::of);
        }
    }

    private static int hashCodeRefImpl(Calendar date, boolean randomBoolean) {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);

        if (randomBoolean) {
            long coefficientYear = 4563766470487201L;
            long coefficientMonth = 73232L;
            long coefficientDay = 4L;
            long coefficientHour = 1234L;
            long coefficientMinute = 99998L;

//            return Math.floorMod(
//                Math.floorMod(coefficientYear * year, (long) Integer.MAX_VALUE)
//                    + Math.floorMod(coefficientMonth * month, Integer.MAX_VALUE)
//                    + Math.floorMod(coefficientDay * day, Integer.MAX_VALUE)
//                    + Math.floorMod(coefficientHour * hour, Integer.MAX_VALUE)
//                    + Math.floorMod(coefficientMinute * minute, Integer.MAX_VALUE),
//                Integer.MAX_VALUE);
            return (int) Math.floorMod(
                Math.floorMod((coefficientYear * year), (long)Integer.MAX_VALUE)
                        + Math.floorMod((coefficientMonth * month), Integer.MAX_VALUE)
                        + Math.floorMod((coefficientDay * day), Integer.MAX_VALUE)
                        + Math.floorMod((coefficientHour * hour), Integer.MAX_VALUE)
                        + Math.floorMod((coefficientMinute * minute), Integer.MAX_VALUE)
                    , Integer.MAX_VALUE);
        } else {
            long coefficientSum = 98924L;

//            return Math.floorMod(((year + month + day + hour + minute) * coefficientSum), Integer.MAX_VALUE);
            return (int)Math.floorMod(((year + month + day + hour + minute) * coefficientSum), Integer.MAX_VALUE);
        }
    }
}
