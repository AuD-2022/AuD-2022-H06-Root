package h06;

import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestForSubmission("H06")
public class H01_Testsc {
    /////////////////////////////
    // DoubleHashingTableIndexFct
    /////////////////////////////

    @Test
    public void H01_DoubleHashingTableIndexFctBasic() {
        // Constructor
        Hash2IndexFct<Object> internalHashFunction0 = new Hash2IndexFct<Object>(41, 0);
        Hash2IndexFct<Object> internalHashFunction1 = new Hash2IndexFct<Object>(41, 29);
        new DoubleHashing<Object>(internalHashFunction0, internalHashFunction1);

        // Type parameter
        try {
            // new DoubleHashingTableIndexFct<OtherAndIntToIntFunction<Object>>(null, null);
            // fail("HashCodeTableIndexFcts type parameter is not bound to classes.");
        } catch (Exception e) {
        }

        // Interface
        Class<?>[] interfaces = DoubleHashing.class.getInterfaces();
        assertEquals(1, interfaces.length);
        assertEquals(BinaryFct2Int.class.getName(), interfaces[0].getName());
    }

    @Test
    public void H01_DoubleHashingTableIndexFctTableSize() {
        Hash2IndexFct<Object> internalHashFunction0 = new Hash2IndexFct<Object>(41, 0);
        Hash2IndexFct<Object> internalHashFunction1 = new Hash2IndexFct<Object>(41, 29);
        DoubleHashing<Object> hashFunction = new DoubleHashing<Object>(internalHashFunction0, internalHashFunction1);

        assertEquals(41, hashFunction.getTableSize());
        hashFunction.setTableSize(20);
        assertEquals(20, hashFunction.getTableSize());
    }

    @Test
    public void H01_DoubleHashingTableIndexFctApplyBasic() {
        Hash2IndexFct<Object> internalHashFunction0 = new Hash2IndexFct<Object>(41, 0);
        Hash2IndexFct<Object> internalHashFunction1 = new Hash2IndexFct<Object>(41, 29);
        DoubleHashing<Object> hashFunction = new DoubleHashing<Object>(internalHashFunction0, internalHashFunction1);

        assertEquals(35, hashFunction.apply("test", 0));
    }

    @Test
    public void H01_DoubleHashingTableIndexFctApplyAdvanced() {
        Hash2IndexFct<Object> internalHashFunction0 = new Hash2IndexFct<Object>(41, 0);
        Hash2IndexFct<Object> internalHashFunction1 = new Hash2IndexFct<Object>(41, 29);
        DoubleHashing<Object> hashFunction = new DoubleHashing<Object>(internalHashFunction0, internalHashFunction1);

        assertEquals(15, hashFunction.apply("t", 1));
        hashFunction.setTableSize(674);
        assertEquals(463, hashFunction.apply("testen", 134));
    }

}
