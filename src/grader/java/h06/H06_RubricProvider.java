package h06;

import org.sourcegrade.jagr.api.rubric.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

@RubricForSubmission("h06")
public class H06_RubricProvider implements RubricProvider {

    @Override
    public Rubric getRubric() {
        return null;
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
