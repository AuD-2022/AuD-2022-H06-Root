package h06;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

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

        private final DoubleHashing<Integer> doubleHashing = new DoubleHashing<>(getHash2IndexFct(), getHash2IndexFct());

        @Test
        void testApply() {
            assertEquals(49, doubleHashing.apply(1, 6));
            assertEquals(9, doubleHashing.apply(-666, 6));
        }
    }

    @Nested
    class MyDateTest {

        private MyDate getDate(boolean b) {
            var cal = Calendar.getInstance();
            cal.set(2002, Calendar.MARCH, 11, 3, 4, 5);
            return new MyDate(cal, b);
        }

        @Test
        void testConstructor() {
            var date = getDate(false);

            assertEquals(2002, date.getYear());
            assertEquals(Calendar.MARCH, date.getMonth());
            assertEquals(11, date.getDay());
            assertEquals(3, date.getHour());
            assertEquals(4, date.getMinute());
            assertFalse(date.getBool());
        }

        @Test
        void testHashWithFalse() {
            var date = getDate(false);
            assertEquals(200024328, date.hashCode());
        }

        @Test
        void testHashWithTrue() {
            var date = getDate(true);
            assertEquals(2058635491, date.hashCode());
        }

        @Test
        void testEquals() {
            assertEquals(getDate(true), getDate(true));
            assertEquals(getDate(false), getDate(false));
            assertNotEquals(getDate(true), getDate(false));
        }
    }
}
