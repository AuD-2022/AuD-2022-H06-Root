package h06.mocks;

import h06.DoubleHashing;
import h06.Hash2IndexFct;
import h06.LinearProbing;
import h06.utils.ReflectionUtils;
import org.mockito.Answers;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;

@SuppressWarnings("rawtypes")
public class Mock {

    public static final BiConsumer<Hash2IndexFct, Object[]> HASH_2_INDEX_FCT = (instance, args) -> {
        Field tableSizeField = ReflectionUtils.getField(Hash2IndexFct.class, "tableSize");
        int tableSize = (int) args[0];
        ReflectionUtils.setFieldValue(tableSizeField, instance, tableSize);

        Field offsetField = ReflectionUtils.getField(Hash2IndexFct.class, "offset");
        int offset = (int) args[1];
        ReflectionUtils.setFieldValue(offsetField, instance, offset);

        Mockito.doAnswer(invocation -> {
            Method calledMethod = invocation.getMethod();
            if (calledMethod.getName().equals("apply")
                && calledMethod.getParameterCount() == 1
                && calledMethod.getParameterTypes()[0].equals(Object.class)) {
                return Math.floorMod(Math.abs((long) invocation.getArgument(0).hashCode()) + offset, tableSize);
            } else {
                return invocation.callRealMethod();
            }
        });
    };
    public static final BiConsumer<LinearProbing, Object[]> LINEAR_PROBING = (instance, args) -> {

    };
    public static final BiConsumer<DoubleHashing, Object[]> DOUBLE_HASHING = (instance, args) -> {

    };

    public static <T> T getMock(Class<T> clazz, BiConsumer<T, Object[]> constructorSurrogate, Object... constructorArgs) {
        T t = Mockito.mock(clazz, Answers.CALLS_REAL_METHODS);
        constructorSurrogate.accept(t, constructorArgs);
        return t;
    }
}
