package h06;

import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestForSubmission("H06")
public class H01_Testsa {
    ////////////////////////
    // HashCodeTableIndexFct
    ////////////////////////

    @Test
    public void H01_HashCodeTableIndexFctBasic() {
        // Constructor
        //Class klass = Util_Tests.checkType("HashCodeTableIndexFct", "h06.hashFunctions", false, -1, 1);
        //Object Util_Test.invokeConstructor(new Class<?>[] {int.class, int.class}, 10, 2);
        new HashCodeTableIndexFct<Object>(10, 2);

        // Type parameter
        try {
            // new HashCodeTableIndexFct<OtherAndIntToIntFunction<Object>>(0, 0);
            // fail("HashCodeTableIndexFcts type parameter is not bound to classes.");
        } catch (Exception e) {
        }

        // Interface
        Class<?>[] interfaces = HashCodeTableIndexFct.class.getInterfaces();
        assertEquals(1, interfaces.length);
        assertEquals(Fct2Int.class.getName(), interfaces[0].getName());
    }

    @Test
    public void H01_HashCodeTableIndexFctTableSize() {
        HashCodeTableIndexFct<Object> hashFunction = new HashCodeTableIndexFct<Object>(10, 0);
        assertEquals(10, hashFunction.getTableSize());
        hashFunction.setTableSize(20);
        assertEquals(20, hashFunction.getTableSize());
    }

    @Test
    public void H01_HashCodeTableIndexFctApplyBasic() {
        HashCodeTableIndexFct<Object> hashFunction = new HashCodeTableIndexFct<Object>(10, 0);
        assertEquals(8, hashFunction.apply("test"));
    }

    @Test
    public void H01_HashCodeTableIndexFctApplyAdvanced() {
        HashCodeTableIndexFct<Object> hashFunction = new HashCodeTableIndexFct<Object>(10, 15);
        assertEquals(1, hashFunction.apply("t"));
        hashFunction.setTableSize(100);
        assertEquals(38, hashFunction.apply("testen"));
    }
}
