package h06;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PublicTests {

    private static final int TABLE_SIZE = 69;

    @Nested
    class Hash2IndexFctTest {

        private final Hash2IndexFct<Integer> fct = getHash2IndexFct();

        @Test
        void testApply() {
            assertEquals(7, fct.apply(1));
            assertEquals(30, fct.apply(-666));
        }
    }

    private Hash2IndexFct<Integer> getHash2IndexFct() {
        return new Hash2IndexFct<>(TABLE_SIZE, 420);
    }

    @Nested
    class LinearProbingTest {

        private final LinearProbing<Integer> probing = new LinearProbing<>(getHash2IndexFct());

        @Test
        void testApply() {
            assertEquals(13, probing.apply(1, 6));
            assertEquals(36, probing.apply(-666, 6));
        }
    }

    @Nested
    class DoubleHashingTest {

    }
}
