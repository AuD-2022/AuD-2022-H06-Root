package h06;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PublicTests {

    @Nested
    class Hash2IndexFctTest {

        private final Hash2IndexFct<Integer> fct = new Hash2IndexFct<>(69, 420);

        @Test
        void testApply() {
            assertEquals(7, fct.apply(1));
            assertEquals(30, fct.apply(-666));
        }
    }

    @Nested
    class DoubleHashingTest {

    }
}
