package h06;

import h06.h1.DoubleHashingTests;
import h06.h1.Hash2IndexFctTests;
import h06.h1.LinearProbingTests;
import h06.h3.MyIndexHoppingHashMapTests;
import h06.h4.MyListsHashMapTests;
import h06.h5.MyDateTests;
import h06.h6.RuntimeTestTests;
import h06.transformers.MyDateTransformer;
import h06.transformers.MyIndexHoppingHashMapTransformer;
import org.sourcegrade.jagr.api.rubric.*;
import org.sourcegrade.jagr.api.testing.RubricConfiguration;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.Callable;

@RubricForSubmission("h06")
public class H06_RubricProvider implements RubricProvider {

    @Override
    public Rubric getRubric() {
        return Rubric.builder()
            .title("H6 | Hashtabellen")
            .addChildCriteria(
                makeCriterionFromChildCriteria("H1 | Basisdatenstrukturen",
                    makeCriterion("Methode [[[apply(T)]]] von Klasse [[[Hash2IndexFct]]] funktioniert wie beschrieben.",
                        () -> Hash2IndexFctTests.class
                            .getDeclaredMethod("testApply", int.class, int.class)
                    ),
                    makeCriterion("Methode [[[apply(T)]]] von Klasse [[[LinearProbing]]] funktioniert wie beschrieben.",
                        () -> LinearProbingTests.class
                            .getDeclaredMethod("testApply", int.class, int.class)
                    ),
                    makeCriterion("Methode [[[apply(T)]]] von Klasse [[[DoubleHashing]]] funktioniert wie beschrieben.",
                        () -> DoubleHashingTests.class
                            .getDeclaredMethod("testApply", Hash2IndexFct.class, int.class)
                    ),
                    makeCriterion("Alle drei Methoden vermeiden arithmetischen Überlauf.", 0, 2,
                        () -> Hash2IndexFctTests.class
                            .getDeclaredMethod("testApplyOverflow", int.class, int.class),
                        () -> LinearProbingTests.class
                            .getDeclaredMethod("testApplyOverflow", int.class, int.class),
                        () -> DoubleHashingTests.class
                            .getDeclaredMethod("testApplyOverflow", Hash2IndexFct.class, int.class)
                    )
                ),
                makeCriterionFromChildCriteria("H3 | Mehrfachsondierung ([[[MyIndexHoppingHashMap]]])",
                    makeCriterion("[[[containsKey(K)]]] funktioniert wie beschrieben,  wenn Schlüssel und "
                            + "zugeordneter Wert in den internen Arrays vorhanden sind.",
                        () -> MyIndexHoppingHashMapTests.class
                            .getDeclaredMethod("testContainsKey", int.class, int.class, double.class, double.class)),
                    makeCriterion("[[[containsKey(K)]]] funktioniert wie beschrieben, wenn Schlüssel und "
                            + "zugeordneter Wert nicht in den internen Arrays vorhanden sind.",
                        () -> MyIndexHoppingHashMapTests.class
                            .getDeclaredMethod("testContainsKeyForeign", int.class, int.class, double.class, double.class)),
                    makeCriterion("[[[getValue(K)]]] funktioniert wie beschrieben, wenn Schlüssel und "
                            + "zugeordneter Wert in den internen Arrays vorhanden sind.",
                        () -> MyIndexHoppingHashMapTests.class
                            .getDeclaredMethod("testGetValue", int.class, int.class, double.class, double.class)),
                    makeCriterion("[[[getValue(K)]]] funktioniert wie beschrieben, wenn Schlüssel und "
                            + "zugeordneter Wert nicht in den internen Arrays vorhanden sind.",
                        () -> MyIndexHoppingHashMapTests.class
                            .getDeclaredMethod("testGetValueForeign", int.class, int.class, double.class, double.class)),
                    makeCriterion("[[[put(K, V)]]] funktioniert wie beschrieben, wenn Schlüssel und "
                            + "zugeordneter Wert nicht bereits in den internen Arrays vorhanden sind.",
                        () -> MyIndexHoppingHashMapTests.class
                            .getDeclaredMethod("testPut", int.class, int.class, double.class, double.class)),
                    makeCriterion("[[[put(K, V)]]] funktioniert wie beschrieben, wenn Schlüssel und "
                            + "zugeordneter Wert bereits in den internen Arrays vorhanden sind.",
                        () -> MyIndexHoppingHashMapTests.class
                            .getDeclaredMethod("testPutDuplicate", int.class, int.class, double.class, double.class)),
                    makeCriterion("[[[remove(K)]]] funktioniert wie beschrieben, wenn Schlüssel und "
                            + "zugeordneter Wert in den internen Arrays vorhanden sind.",
                        () -> MyIndexHoppingHashMapTests.class
                            .getDeclaredMethod("testRemove", int.class, int.class, double.class, double.class)),
                    makeCriterion("[[[remove(K)]]] funktioniert wie beschrieben, wenn Schlüssel und "
                            + "zugeordneter Wert nicht in den internen Arrays vorhanden sind.",
                        () -> MyIndexHoppingHashMapTests.class
                            .getDeclaredMethod("testRemoveForeign", int.class, int.class, double.class, double.class)),
                    makeCriterion("[[[rehash()]]] funktioniert wie beschrieben.",
                        () -> MyIndexHoppingHashMapTests.class
                            .getDeclaredMethod("testRehash", int.class, int.class, double.class, double.class)),
                    makeCriterion("In [[[put(K, V)]]] wird [[[rehash()]]] aufgerufen, wenn der Schwellwert überschritten wurde.",
                        () -> MyIndexHoppingHashMapTests.class
                            .getDeclaredMethod("testRehashInPut", int.class, int.class, double.class, double.class))
                ),
                makeCriterionFromChildCriteria("H4 | Hashtabelle von Listen ([[[MyListsHashMap]]])",
                    makeCriterion("[[[containsKey(K)]]] funktioniert wie beschrieben, wenn Schlüssel und "
                            + "zugeordneter Wert in [[[table]]] vorhanden sind.",
                        () -> MyListsHashMapTests.class
                            .getDeclaredMethod("testContainsKey", MyListsHashMap.class, int.class)),
                    makeCriterion("[[[containsKey(K)]]] funktioniert wie beschrieben, wenn Schlüssel und "
                            + "zugeordneter Wert nicht in [[[table]]] vorhanden sind.",
                        () -> MyListsHashMapTests.class
                            .getDeclaredMethod("testContainsKeyDisjoint", MyListsHashMap.class, int.class)),
                    makeCriterion("[[[getValue(K)]]] funktioniert wie beschrieben, wenn Schlüssel und "
                            + "zugeordneter Wert in [[[table]]] vorhanden sind.",
                        () -> MyListsHashMapTests.class
                            .getDeclaredMethod("testGetValue", MyListsHashMap.class, int.class)),
                    makeCriterion("[[[getValue(K)]]] funktioniert wie beschrieben, wenn Schlüssel und "
                            + "zugeordneter Wert nicht in [[[table]]] vorhanden sind.",
                        () -> MyListsHashMapTests.class
                            .getDeclaredMethod("testGetValueDisjoint", MyListsHashMap.class, int.class)),
                    makeCriterion("[[[put(K, V)]]] funktioniert wie beschrieben, wenn Schlüssel und "
                            + "zugeordneter Wert nicht bereits in [[[table]]] vorhanden sind.",
                        () -> MyListsHashMapTests.class
                            .getDeclaredMethod("testPut", MyListsHashMap.class, int.class)),
                    makeCriterion("[[[put(K, V)]]] funktioniert wie beschrieben, wenn Schlüssel und "
                            + "zugeordneter Wert bereits in [[[table]]] vorhanden sind.",
                        () -> MyListsHashMapTests.class
                            .getDeclaredMethod("testPutDuplicate", MyListsHashMap.class, int.class)),
                    makeCriterion("[[[remove(K)]]] funktioniert wie beschrieben, wenn Schlüssel und "
                            + "zugeordneter Wert in [[[table]]] vorhanden sind.",
                        () -> MyListsHashMapTests.class
                            .getDeclaredMethod("testRemove", MyListsHashMap.class, int.class)),
                    makeCriterion("[[[remove(K)]]] funktioniert wie beschrieben, wenn Schlüssel und "
                            + "zugeordneter Wert nicht in [[[table]]] vorhanden sind.",
                        () -> MyListsHashMapTests.class
                            .getDeclaredMethod("testRemoveDisjoint", MyListsHashMap.class, int.class))
                ),
                makeCriterionFromChildCriteria("H5 | Eigene hashCode-Implementation ([[[MyDate]]])",
                    makeCriterion("Der Konstruktor funktioniert wie beschrieben.",
                        () -> MyDateTests.class
                            .getDeclaredMethod("testConstructor", Calendar.class, boolean.class)),
                    makeCriterion("[[[hashCode()]]] funktioniert wie beschrieben.",
                        () -> MyDateTests.class
                            .getDeclaredMethod("testHashCode", Calendar.class, boolean.class)),
                    makeCriterion("[[[equals(Object)]]] funktioniert wie beschrieben.",
                        () -> MyDateTests.class
                            .getDeclaredMethod("testEquals"))
                ),
                makeCriterionFromChildCriteria("H6 | Tests ([[[RuntimeTests]]])",
                    Criterion.builder()
                        .shortDescription("Zufallszahlen werden in [[[generateTestdata()]]] wie beschrieben generiert. "
                            + "(kein Filtern, sondern Modulo-Bildung)")
                        .build(),
                    makeCriterion("Die Dimensionen des von [[[generateTestdata()]]] zurückgegebenen Arrays sind korrekt.",
                        () -> RuntimeTestTests.class
                            .getDeclaredMethod("testGenerateTestdataDimensions")),
                    makeCriterion("Die Elemente in dem von [[[generateTestdata()]]] zurückgegebenen Array wurden korrekt "
                            + "initialisiert.",
                        () -> RuntimeTestTests.class
                            .getDeclaredMethod("testGenerateTestdataElementInit")),
                    Criterion.builder()
                        .shortDescription("Methode [[[createTestSet(int, int, int, int, MyDate[][])]]] funktioniert wie "
                            + "beschrieben.")
                        .minPoints(0)
                        .maxPoints(2)
                        .build(),
                    makeCriterion("Methode [[[test(TestSet)]]] funktioniert wie beschrieben.",
                        () -> RuntimeTestTests.class
                            .getDeclaredMethod("testTest"))
                )
            )
            .build();
    }

    @Override
    public void configure(RubricConfiguration configuration) {
        configuration.addTransformer(new MyIndexHoppingHashMapTransformer());
        configuration.addTransformer(new MyDateTransformer());
    }

    @SafeVarargs
    private static Criterion makeCriterion(String shortDescription, Callable<Method>... callables) {
        return makeCriterion(shortDescription, 0, 1, callables);
    }

    @SafeVarargs
    private static Criterion makeCriterion(String shortDescription, int minPoints, int maxPoints, Callable<Method>... callables) {
        return Criterion.builder()
            .shortDescription(shortDescription)
            .minPoints(minPoints)
            .maxPoints(maxPoints)
            .grader(Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.and(Arrays.stream(callables).map(JUnitTestRef::ofMethod).toArray(JUnitTestRef[]::new)))
                .pointsFailedMin()
                .pointsPassedMax()
                .build())
            .build();
    }

    private static Criterion makeCriterionFromChildCriteria(String shortDescription, Criterion... criteria) {
        return Criterion.builder()
            .shortDescription(shortDescription)
            .addChildCriteria(criteria)
            .build();
    }
}
