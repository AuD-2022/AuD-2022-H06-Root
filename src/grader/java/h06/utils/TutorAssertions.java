package h06.utils;

import kotlin.Pair;
import org.jetbrains.annotations.Nullable;
import org.opentest4j.AssertionFailedError;

import java.util.*;
import java.util.function.Supplier;

public class TutorAssertions {

    public static void assertEquals(Object expected, @Nullable String expectedDesc,
                                    Object actual, @Nullable String actualDesc,
                                    String message,
                                    Supplier<List<Pair<String, String>>> inputsSupplier) {
        if (!Objects.equals(expected, actual)) {
            throw new AssertionFailedError(
                getExceptionMessage(expected, expectedDesc, actual, actualDesc, message, inputsSupplier)
            );
        }
    }

    private static String getExceptionMessage(Object expected, @Nullable String expectedDesc,
                                              Object actual, @Nullable String actualDesc,
                                              String messageHead,
                                              Supplier<List<Pair<String, String>>> inputsSupplier) {
        String exceptionMessageHead = "%s ==> expected: <%s> but was: <%s>".formatted(
            messageHead,
            expectedDesc == null ? Objects.toString(expected) : expectedDesc,
            actualDesc == null ? Objects.toString(actual) : actualDesc
        );
        StringBuilder builder = new StringBuilder(exceptionMessageHead).append("\n");
        List<Pair<String, String>> inputsList = inputsSupplier.get();

        if (inputsList.size() > 0) {
            builder.append("Inputs:\n");
            inputsList.forEach(pair -> builder.append("%s: %s".formatted(pair.getFirst(), pair.getSecond())).append("\n"));
        }

        return builder.toString();
    }
}
