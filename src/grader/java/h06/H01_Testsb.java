package h06;

import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestForSubmission("H06")
public class H01_Testsb {
    /////////////////////////////
    // LinearProbingTableIndexFct
    /////////////////////////////

    @Test
    public void H01_LinearProbingTableIndexFctBasic() {
        // Constructor
        HashCodeTableIndexFct<Object> internalHashFunction = new HashCodeTableIndexFct<Object>(10, 3);
        new LinearProbingTableIndexFct<Object>(internalHashFunction);

        // Type parameter
        try {
            //new LinearProbingTableIndexFct<OtherAndIntToIntFunction<Object>>(null);
            //fail("HashCodeTableIndexFcts type parameter is not bound to classes.");
        } catch (Exception e) {
        }

        // Interface
        Class<?>[] interfaces = LinearProbingTableIndexFct.class.getInterfaces();
        assertEquals(1, interfaces.length);
        assertEquals(BinaryFct2Int.class.getName(), interfaces[0].getName());
    }

    @Test
    public void H01_LinearProbingTableIndexFctTableSize() {
        HashCodeTableIndexFct<Object> internalHashFunction = new HashCodeTableIndexFct<Object>(10, 3);
        LinearProbingTableIndexFct<Object> hashFunction = new LinearProbingTableIndexFct<Object>(internalHashFunction);
        assertEquals(10, hashFunction.getTableSize());
        hashFunction.setTableSize(20);
        assertEquals(20, hashFunction.getTableSize());
    }

    @Test
    public void H01_LinearProbingTableIndexFctApplyBasic() {
        HashCodeTableIndexFct<Object> internalHashFunction = new HashCodeTableIndexFct<Object>(25, 0);
        LinearProbingTableIndexFct<Object> hashFunction = new LinearProbingTableIndexFct<Object>(internalHashFunction);
        assertEquals(23, hashFunction.apply("test", 0));
    }

    @Test
    public void H01_LinearProbingTableIndexFctApplyAdvanced() {
        HashCodeTableIndexFct<Object> internalHashFunction = new HashCodeTableIndexFct<Object>(20, 5);
        LinearProbingTableIndexFct<Object> hashFunction = new LinearProbingTableIndexFct<Object>(internalHashFunction);
        assertEquals(2, hashFunction.apply("t", 1));
        hashFunction.setTableSize(100);
        assertEquals(51, hashFunction.apply("testen", 23));
    }
}
