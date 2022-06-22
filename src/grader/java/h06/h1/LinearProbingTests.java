package h06.h1;

import h06.Fct2Int;
import h06.LinearProbing;
import h06.utils.TutorAssertions;
import kotlin.Pair;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static h06.Config.SEED;
import static h06.Config.STREAM_SIZE;

@TestForSubmission("h06")
public class LinearProbingTests {

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testApply(int i, int tableSize) {
        Fct2Int<Object> fct = new Fct2IntImpl(tableSize, 0);
        LinearProbing<Object> instance = new LinearProbing<>(fct);
        Object object = new Object();

        TutorAssertions.assertEquals(
            Math.floorMod(fct.apply(object) + i, fct.getTableSize()), null,
            instance.apply(object, i), null,
            "Value returned by [[[apply(T, int)]]] in class [[[LinearProbing]]] did not equal expected value", () -> List.of(
                new Pair<>("[[[this]]]",
                    "[[[new LinearProbing<>(fct)]]]; [[[fct.getTableSize()]]] == %d, [[[fct.apply(T)]]] always returns %d"
                        .formatted(fct.getTableSize(), fct.apply(null))),
                new Pair<>("Argument #1 - [[[key]]]", "[[[new Object()]]]"),
                new Pair<>("Argument #2 - [[[offset]]]", String.valueOf(i))
            ));
    }

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testApplyOverflow(int i, int tableSize) {
        Fct2Int<Object> fct = new Fct2IntImpl(tableSize, Integer.MAX_VALUE);
        LinearProbing<Object> instance = new LinearProbing<>(fct);
        Object object = new Object();

        TutorAssertions.assertEquals(
            Math.floorMod((long) fct.apply(object) + i, fct.getTableSize()), null,
            instance.apply(object, i), null,
            "Value returned by [[[apply(T, int)]]] in class [[[LinearProbing]]] did not equal expected value", () -> List.of(
                new Pair<>("[[[this]]]", ("[[[new LinearProbing<>(fct)]]]; [[[fct.getTableSize()]]] == %d, "
                    + "[[[fct.apply(T)]]] always returns Integer.MAX_VALUE").formatted(fct.getTableSize())),
                new Pair<>("Argument #1 - [[[key]]]", "[[[new Object()]]]"),
                new Pair<>("Argument #2 - [[[offset]]]", String.valueOf(i))
            ));
    }

    private static class Provider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            Random random = new Random(SEED);

            return Stream.generate(() -> new Object[] {random.nextInt(10) + 1, random.nextInt(10) + 1})
                .limit(STREAM_SIZE)
                .map(Arguments::of);
        }
    }

    private static class Fct2IntImpl implements Fct2Int<Object> {

        private int tableSize;
        private final int applyReturnValue;

        private Fct2IntImpl(int tableSize, int applyReturnValue) {
            this.tableSize = tableSize;
            this.applyReturnValue = applyReturnValue;
        }

        @Override
        public int apply(Object x) {
            return this.applyReturnValue;
        }

        @Override
        public int getTableSize() {
            return this.tableSize;
        }

        @Override
        public void setTableSize(int tableSize) {
            this.tableSize = tableSize;
        }
    }
}
