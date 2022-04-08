package h06;

import h06.*;
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
        HashCodeTableIndexFct<Object> internalHashFunction0 = new HashCodeTableIndexFct<Object>(41, 0);
        HashCodeTableIndexFct<Object> internalHashFunction1 = new HashCodeTableIndexFct<Object>(41, 29);
        new DoubleHashingTableIndexFct<Object>(internalHashFunction0, internalHashFunction1);

        // Type parameter
        try {
            // new DoubleHashingTableIndexFct<OtherAndIntToIntFunction<Object>>(null, null);
            // fail("HashCodeTableIndexFcts type parameter is not bound to classes.");
        } catch (Exception e) {
        }

        // Interface
        Class<?>[] interfaces = DoubleHashingTableIndexFct.class.getInterfaces();
        assertEquals(1, interfaces.length);
        assertEquals(BinaryFct2Int.class.getName(), interfaces[0].getName());
    }

    @Test
    public void H01_DoubleHashingTableIndexFctTableSize() {
        HashCodeTableIndexFct<Object> internalHashFunction0 = new HashCodeTableIndexFct<Object>(41, 0);
        HashCodeTableIndexFct<Object> internalHashFunction1 = new HashCodeTableIndexFct<Object>(41, 29);
        DoubleHashingTableIndexFct<Object> hashFunction = new DoubleHashingTableIndexFct<Object>(internalHashFunction0, internalHashFunction1);

        assertEquals(41, hashFunction.getTableSize());
        hashFunction.setTableSize(20);
        assertEquals(20, hashFunction.getTableSize());
    }

    @Test
    public void H01_DoubleHashingTableIndexFctApplyBasic() {
        HashCodeTableIndexFct<Object> internalHashFunction0 = new HashCodeTableIndexFct<Object>(41, 0);
        HashCodeTableIndexFct<Object> internalHashFunction1 = new HashCodeTableIndexFct<Object>(41, 29);
        DoubleHashingTableIndexFct<Object> hashFunction = new DoubleHashingTableIndexFct<Object>(internalHashFunction0, internalHashFunction1);

        assertEquals(35, hashFunction.apply("test", 0));
    }

    @Test
    public void H01_DoubleHashingTableIndexFctApplyAdvanced() {
        HashCodeTableIndexFct<Object> internalHashFunction0 = new HashCodeTableIndexFct<Object>(41, 0);
        HashCodeTableIndexFct<Object> internalHashFunction1 = new HashCodeTableIndexFct<Object>(41, 29);
        DoubleHashingTableIndexFct<Object> hashFunction = new DoubleHashingTableIndexFct<Object>(internalHashFunction0, internalHashFunction1);

        assertEquals(15, hashFunction.apply("t", 1));
        hashFunction.setTableSize(674);
        assertEquals(463, hashFunction.apply("testen", 134));
    }

}
