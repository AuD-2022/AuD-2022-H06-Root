package h06;

import h06.h1.DoubleHashingTests;
import h06.h1.Hash2IndexFctTests;
import h06.h1.LinearProbingTests;
import h06.transformers.Hash2IndexFctTransformer;
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
                )
            )
            .build();
    }

    @SafeVarargs
    private static Criterion makeCriterion(String shortDescription, Callable<Method>... callables) {
        return makeCriterion(shortDescription, 0, 1, callables);
    }

    @Override
    public void configure(RubricConfiguration configuration) {
        configuration.addTransformer(new Hash2IndexFctTransformer());
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
