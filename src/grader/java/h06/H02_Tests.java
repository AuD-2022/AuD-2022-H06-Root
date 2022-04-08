package h06;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.TypeVariable;

import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

@TestForSubmission("H06")
public class H02_Tests {
    /////////////
    // Interface
    /////////////

    @SuppressWarnings("rawtypes")
    @Test
    public void H02_MyMap() {
        Class<?> klass = Util_Tests.checkType("MyMap", "h06.hashTables", true, 4, 2);

        Method containsKey = Util_Tests.checkMethod(klass, "containsKey", boolean.class, Object.class);
        Method getValue = Util_Tests.checkMethod(klass, "getValue", Object.class, Object.class);
        Method put = Util_Tests.checkMethod(klass, "put", Object.class, Object.class, Object.class);
        Method remove = Util_Tests.checkMethod(klass, "remove", Object.class, Object.class);
    }
}
