package h06.h1;

import h06.DoubleHashing;
import h06.Hash2IndexFct;
import h06.utils.ReflectionUtils;
import h06.utils.TutorAssertions;
import kotlin.Pair;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static h06.Config.SEED;
import static h06.Config.STREAM_SIZE;

@TestForSubmission("h06")
public class DoubleHashingTests {

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testApply(Hash2IndexFct<Object> fct, int factor) {
        DoubleHashing<Object> instance = new DoubleHashing<>(fct, fct);
        Object object = new Object() {
            @Override
            public int hashCode() {
                return 42;
            }
        };

        int a = fct.apply(object);
        int b = fct.apply(object);
        b = (b % 2 == 0 ? b + 1 : b);

        TutorAssertions.assertEquals(
            Math.floorMod(a + factor * b, fct.getTableSize()), null,
            instance.apply(object, factor), null,
            "Value returned by [[[apply(T, int)]]] in class [[[DoubleHashing]]]] did not equal expected value", () -> List.of(
                new Pair<>("[[[fct]]]", "[[[Hash2IndexFct]]] reference implementation; [[[tableSize]]] == %d, [[[offset]]] == 0"
                    .formatted(fct.getTableSize())),
                new Pair<>("[[[this]]]", "[[[new DoubleHashing<>(fct, fct)]]]"),
                new Pair<>("Argument #1 - [[[key]]]",
                    "[[[new Object()]]] ([[[hashCode()]]] always returns %d)".formatted(object.hashCode())),
                new Pair<>("Argument #2 - [[[factor]]]", String.valueOf(factor))
            ));
    }

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testApplyOverflow(Hash2IndexFct<Object> fct, int factor) {
        DoubleHashing<Object> instance = new DoubleHashing<>(fct, fct);
        Object object = new Object();

        int a = fct.apply(object);
        int b = fct.apply(object);
        b = (b % 2 == 0 ? b + 1 : b);

        TutorAssertions.assertEquals(
            Math.floorMod(a + (long) Integer.MAX_VALUE * b, fct.getTableSize()), null,
            instance.apply(object, Integer.MAX_VALUE), null,
            "Value returned by [[[apply(T, int)]]] in class [[[DoubleHashing]]]] did not equal expected value", () -> List.of(
                new Pair<>("[[[fct]]]", "[[[Hash2IndexFct]]] reference implementation; [[[tableSize]]] == %d, [[[offset]]] == 0"
                    .formatted(fct.getTableSize())),
                new Pair<>("[[[this]]]", "[[[new DoubleHashing<>(fct, fct)]]]"),
                new Pair<>("Argument #1 - [[[key]]]",
                    "[[[new Object()]]] ([[[hashCode()]]] always returns %d)".formatted(object.hashCode())),
                new Pair<>("Argument #2 - [[[factor]]]", "[[[Integer.MAX_VALUE]]]")
            ));
    }

    private static class Provider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            Random random = new Random(SEED);

            return Stream.generate(() -> {
                    int tableSize = random.nextInt(10) + 1;
                    int offset = 0;
                    Answer<?> answer = invocation -> {
                        Method calledMethod = invocation.getMethod();
                        if (calledMethod.getName().equals("apply")
                            && calledMethod.getParameterCount() == 1
                            && calledMethod.getParameterTypes()[0].equals(Object.class)) {
                            return Math.floorMod(Math.abs((long) invocation.getArgument(0).hashCode()) + offset, tableSize);
                        } else {
                            return invocation.callRealMethod();
                        }
                    };
                    Hash2IndexFct<Object> instance = Mockito.mock(Hash2IndexFct.class, answer);
                    Field tableSizeField = ReflectionUtils.getField(Hash2IndexFct.class, "tableSize");
                    ReflectionUtils.setFieldValue(tableSizeField, instance, tableSize);
                    Field offsetField = ReflectionUtils.getField(Hash2IndexFct.class, "offset");
                    ReflectionUtils.setFieldValue(offsetField, instance, offset);

                    return new Object[] {instance, random.nextInt(10) + 1};
                })
                .limit(STREAM_SIZE)
                .map(Arguments::of);
        }
    }
}
