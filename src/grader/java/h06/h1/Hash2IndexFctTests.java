package h06.h1;

import h06.Hash2IndexFct;
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
public class Hash2IndexFctTests {

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testApply(int tableSize, int offset) {
        Hash2IndexFct<Object> instance = new Hash2IndexFct<>(tableSize, offset);
        Object object = new Object() {
            @Override
            public int hashCode() {
                return 42;
            }
        };

        TutorAssertions.assertEquals(
            Math.floorMod(object.hashCode() + offset, instance.getTableSize()), null,
            instance.apply(object), null,
            "Value returned by [[[apply(T)]]] in class [[[Hash2IndexFct]]] does not equal excepted value", () -> List.of(
                new Pair<>("[[[this]]]", "[[[new Hash2IndexFct<>(%d, %d)]]]".formatted(tableSize, offset)),
                new Pair<>("Argument #1 - [[[key]]]", "[[[new Object()]]] ([[[hashCode()]]] always returns 42)")
            ));
    }

    @ParameterizedTest
    @ArgumentsSource(Provider.class)
    public void testApplyOverflow(int tableSize, int offset) {
        Hash2IndexFct<Object> instance = new Hash2IndexFct<>(tableSize, offset);
        Object object = new Object() {
            @Override
            public int hashCode() {
                return Integer.MAX_VALUE;
            }
        };

        TutorAssertions.assertEquals(
            Math.floorMod((long) object.hashCode() + offset, instance.getTableSize()), null,
            instance.apply(object), null,
            "Value returned by [[[apply(T)]]] in class [[[Hash2IndexFct]]] does not equal expected value", () -> List.of(
                new Pair<>("[[[this]]]", "[[[new Hash2IndexFct<>(%d, %d)]]]".formatted(tableSize, offset)),
                new Pair<>("Argument #1 - [[[key]]]", "[[[new Object()]]] ([[[hashCode()]]] always returns Integer.MAX_VALUE)")
            ));
    }

    private static class Provider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            Random random = new Random(SEED);

            return Stream.generate(() -> new Integer[] {random.nextInt(10) + 1, random.nextInt(10) + 1})
                .limit(STREAM_SIZE)
                .map(Arguments::of);
        }
    }
}
