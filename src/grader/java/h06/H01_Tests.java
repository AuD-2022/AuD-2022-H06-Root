package h06;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.TypeVariable;

import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

@TestForSubmission("H06")
public class H01_Tests {
    /////////////
    // Interfaces
    /////////////

    @SuppressWarnings("rawtypes")
    @Test
    public void H01_OtherToIntFunction() {
        Class klass = Util_Tests.checkType("OtherToIntFunction", "h06.hashFunctions", true, 3, 1);

        Method apply = Util_Tests.checkMethod(klass, "apply", int.class, Object.class);
        Method getTableSize = Util_Tests.checkMethod(klass, "setTableSize", void.class, int.class);
        Method setTableSize = Util_Tests.checkMethod(klass, "getTableSize", int.class);
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void H01_OtherAndIntToIntFunction() {
        Class klass = Util_Tests.checkType("OtherAndIntToIntFunction", "h06.hashFunctions", true, 3, 1);

        Method apply = Util_Tests.checkMethod(klass, "apply", int.class, Object.class, int.class);
        Method getTableSize = Util_Tests.checkMethod(klass, "setTableSize", void.class, int.class);
        Method setTableSize = Util_Tests.checkMethod(klass, "getTableSize", int.class);
    }
}
