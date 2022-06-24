package h06;

import h06.h1.DoubleHashingTests;
import h06.h1.Hash2IndexFctTests;
import h06.h1.LinearProbingTests;
import h06.h3.MyIndexHoppingHashMapTests;
import h06.transformers.MyIndexHoppingHashMapTransformer;
import org.sourcegrade.jagr.api.rubric.*;
import org.sourcegrade.jagr.api.testing.RubricConfiguration;

import java.lang.reflect.Method;
import java.util.Arrays;
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
                makeCriterionFromChildCriteria("H3 | Mehrfachsondierung",
                    makeCriterion("[[[containsKey(K)]]] in Klasse [[[MyIndexHoppingHashMap]]] funktioniert wie beschrieben, "
                            + "wenn Schlüssel und zugeordneter Wert in den internen Arrays vorhanden sind.",
                        () -> MyIndexHoppingHashMapTests.class
                            .getDeclaredMethod("testContainsKey", int.class, int.class, double.class, double.class)),
                    makeCriterion("[[[containsKey(K)]]] in Klasse [[[MyIndexHoppingHashMap]]] funktioniert wie beschrieben, "
                            + "wenn Schlüssel und zugeordneter Wert nicht in den internen Arrays vorhanden sind.",
                        () -> MyIndexHoppingHashMapTests.class
                            .getDeclaredMethod("testContainsKeyForeign", int.class, int.class, double.class, double.class)),
                    makeCriterion("[[[getValue(K)]]] in Klasse [[[MyIndexHoppingHashMap]]] funktioniert wie beschrieben, "
                            + "wenn Schlüssel und zugeordneter Wert in den internen Arrays vorhanden sind.",
                        () -> MyIndexHoppingHashMapTests.class
                            .getDeclaredMethod("testGetValue", int.class, int.class, double.class, double.class)),
                    makeCriterion("[[[getValue(K)]]] in Klasse [[[MyIndexHoppingHashMap]]] funktioniert wie beschrieben, "
                            + "wenn Schlüssel und zugeordneter Wert nicht in den internen Arrays vorhanden sind.",
                        () -> MyIndexHoppingHashMapTests.class
                            .getDeclaredMethod("testGetValueForeign", int.class, int.class, double.class, double.class)),
                    makeCriterion("[[[put(K, V)]]] in Klasse [[[MyIndexHoppingHashMap]]] funktioniert wie beschrieben, "
                            + "wenn Schlüssel und zugeordneter Wert nicht bereits in den internen Arrays vorhanden sind.",
                        () -> MyIndexHoppingHashMapTests.class
                            .getDeclaredMethod("testPut", int.class, int.class, double.class, double.class)),
                    makeCriterion("[[[put(K, V)]]] in Klasse [[[MyIndexHoppingHashMap]]] funktioniert wie beschrieben, "
                            + "wenn Schlüssel und zugeordneter Wert bereits in den internen Arrays vorhanden sind.",
                        () -> MyIndexHoppingHashMapTests.class
                            .getDeclaredMethod("testPutDuplicate", int.class, int.class, double.class, double.class)),
                    makeCriterion("[[[remove(K)]]] in Klasse [[[MyIndexHoppingHashMap]]] funktioniert wie beschrieben, "
                            + "wenn Schlüssel und zugeordneter Wert in den internen Arrays vorhanden sind.",
                        () -> MyIndexHoppingHashMapTests.class
                            .getDeclaredMethod("testRemove", int.class, int.class, double.class, double.class)),
                    makeCriterion("[[[remove(K)]]] in Klasse [[[MyIndexHoppingHashMap]]] funktioniert wie beschrieben, "
                            + "wenn Schlüssel und zugeordneter Wert nicht in den internen Arrays vorhanden sind.",
                        () -> MyIndexHoppingHashMapTests.class
                            .getDeclaredMethod("testRemoveForeign", int.class, int.class, double.class, double.class)),
                    makeCriterion("[[[rehash()]]] in Klasse [[[MyIndexHoppingHashMap]]] funktioniert wie beschrieben.",
                        () -> MyIndexHoppingHashMapTests.class
                            .getDeclaredMethod("testRehash", int.class, int.class, double.class, double.class)),
                    makeCriterion("In [[[put(K, V)]]] in Klasse [[[MyIndexHoppingHashMap]]] wird [[[rehash()]]] aufgerufen, "
                            + "wenn der Schwellwert überschritten wurde.",
                        () -> MyIndexHoppingHashMapTests.class
                            .getDeclaredMethod("testRehashInPut", int.class, int.class, double.class, double.class))
                )
            )
            .build();
    }

    @Override
    public void configure(RubricConfiguration configuration) {
        configuration.addTransformer(new MyIndexHoppingHashMapTransformer());
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
