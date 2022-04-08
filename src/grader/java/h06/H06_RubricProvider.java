package h06;

import org.sourcegrade.jagr.api.rubric.*;

@RubricForSubmission("h06")
public class H06_RubricProvider implements RubricProvider {
    ////////
    // H1 //
    ////////

    public static final Criterion H1_Interfaces = Criterion.builder()
        .shortDescription("Interfaces OtherToIntFunction and OtherAndIntToIntFunction")
        .maxPoints(1) // default maxPoints is 1
        .minPoints(0) // default minPoints is 0
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> H01_Tests.class.getMethod("H01_OtherToIntFunction")))
                .requirePass(JUnitTestRef.ofMethod(() -> H01_Tests.class.getMethod("H01_OtherAndIntToIntFunction")))
                .pointsPassedMax() // award maximum points if ALL tests passed
                .pointsFailedMin() // award minimum points if ANY test failed
                .build()
        ).build();

    public static final Criterion H1_HashCodeTableIndexFct = Criterion.builder()
        .shortDescription("Class HashCodeTableIndexFct")
        .maxPoints(1) // default maxPoints is 1
        .minPoints(0) // default minPoints is 0
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> H01_Testsa.class.getMethod("H01_HashCodeTableIndexFctBasic")))
                .requirePass(JUnitTestRef.ofMethod(() -> H01_Testsa.class.getMethod("H01_HashCodeTableIndexFctTableSize")))
                .requirePass(JUnitTestRef.ofMethod(() -> H01_Testsa.class.getMethod("H01_HashCodeTableIndexFctApplyBasic")))
                .requirePass(JUnitTestRef.ofMethod(() -> H01_Testsa.class.getMethod("H01_HashCodeTableIndexFctApplyAdvanced")))
                .pointsPassedMax() // award maximum points if ALL tests passed
                .pointsFailedMin() // award minimum points if ANY test failed
                .build()
        ).build();

    public static final Criterion H1_LinearProbingTableIndexFct = Criterion.builder()
        .shortDescription("Class LinearProbingTableIndexFct")
        .maxPoints(2) // default maxPoints is 1
        .minPoints(0) // default minPoints is 0
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> H01_Testsb.class.getMethod("H01_LinearProbingTableIndexFctBasic")))
                .requirePass(JUnitTestRef.ofMethod(() -> H01_Testsb.class.getMethod("H01_LinearProbingTableIndexFctTableSize")))
                .requirePass(JUnitTestRef.ofMethod(() -> H01_Testsb.class.getMethod("H01_LinearProbingTableIndexFctApplyBasic")))
                .requirePass(JUnitTestRef.ofMethod(() -> H01_Testsb.class.getMethod("H01_LinearProbingTableIndexFctApplyAdvanced")))
                .pointsPassedMax() // award maximum points if ALL tests passed
                .pointsFailedMin() // award minimum points if ANY test failed
                .build()
        ).build();

    public static final Criterion H1_DoubleHashingTableIndexFct = Criterion.builder()
        .shortDescription("Class DoubleHashingTableIndexFct")
        .maxPoints(2) // default maxPoints is 1
        .minPoints(0) // default minPoints is 0
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> H01_Testsc.class.getMethod("H01_DoubleHashingTableIndexFctBasic")))
                .requirePass(JUnitTestRef.ofMethod(() -> H01_Testsc.class.getMethod("H01_DoubleHashingTableIndexFctTableSize")))
                .requirePass(JUnitTestRef.ofMethod(() -> H01_Testsc.class.getMethod("H01_DoubleHashingTableIndexFctApplyBasic")))
                .requirePass(JUnitTestRef.ofMethod(() -> H01_Testsc.class.getMethod("H01_DoubleHashingTableIndexFctApplyAdvanced")))
                .pointsPassedMax() // award maximum points if ALL tests passed
                .pointsFailedMin() // award minimum points if ANY test failed
                .build()
        ).build();

    public static final Criterion H1 = Criterion.builder()
        .shortDescription("H1")
        .addChildCriteria(
            //H1_Interfaces,
            H1_HashCodeTableIndexFct,
            H1_LinearProbingTableIndexFct,
            H1_DoubleHashingTableIndexFct
        ) // maxPoints and minPoints and grading is inferred from child criteria
        .build();

    ////////
    // H2 //
    ////////

    public static final Criterion H2_MyMap = Criterion.builder()
        .shortDescription("Interface MyMap")
        .maxPoints(1) // default maxPoints is 1
        .minPoints(0) // default minPoints is 0
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> H02_Tests.class.getMethod("H02_MyMap")))
                .pointsPassedMax() // award maximum points if ALL tests passed
                .pointsFailedMin() // award minimum points if ANY test failed
                .build()
        ).build();

    public static final Criterion H2 = Criterion.builder()
        .shortDescription("H2")
        .addChildCriteria(
            H2_MyMap
        ) // maxPoints and minPoints and grading is inferred from child criteria
        .build();

    ////////
    // H3 //
    ////////

    public static final Criterion H3_MyIndexHoppingHashMap = Criterion.builder()
        .shortDescription("Class MyIndexHoppingHashMap")
        .maxPoints(1) // default maxPoints is 1
        .minPoints(0) // default minPoints is 0
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> H03_Tests.class.getMethod("H03_MyIndexHoppingHashMapBasic")))
                .pointsPassedMax() // award maximum points if ALL tests passed
                .pointsFailedMin() // award minimum points if ANY test failed
                .build()
        ).build();

    public static final Criterion H3_MyIndexHoppingHashMapPut = Criterion.builder()
        .shortDescription("Class MyIndexHoppingHashMap - Method Put")
        .maxPoints(3) // default maxPoints is 1
        .minPoints(0) // default minPoints is 0
        .grader(
            Grader.descendingPriority(
                Grader.testAwareBuilder()
                    .requirePass(JUnitTestRef.ofMethod(() -> H03_Tests.class.getMethod("H03_MyIndexHoppingHashMapPutSingle")))
                    .requirePass(JUnitTestRef.ofMethod(() -> H03_Tests.class.getMethod("H03_MyIndexHoppingHashMapPutMultiple")))
                    .pointsPassed((testCycle, criterion) -> GradeResult.ofCorrect(1))
                    .pointsFailed((testCycle, criterion) -> GradeResult.ofIncorrect(1))
                    .build(),
                Grader.testAwareBuilder()
                    .requirePass(JUnitTestRef.ofMethod(() -> H03_Tests.class.getMethod("H03_MyIndexHoppingHashMapPutReplace")))
                    .pointsPassed((testCycle, criterion) -> GradeResult.ofCorrect(1))
                    .pointsFailed((testCycle, criterion) -> GradeResult.ofIncorrect(1))
                    .build(),
                Grader.testAwareBuilder()
                    .requirePass(JUnitTestRef.ofMethod(() -> H03_Tests.class.getMethod("H03_MyIndexHoppingHashMapPutMultipleResize")))
                    .pointsPassed((testCycle, criterion) -> GradeResult.ofCorrect(1))
                    .pointsFailed((testCycle, criterion) -> GradeResult.ofIncorrect(1))
                    .build()
            )
        ).build();

    public static final Criterion H3_MyIndexHoppingHashMapRemove = Criterion.builder()
        .shortDescription("Class MyIndexHoppingHashMap - Method Remove")
        .maxPoints(2) // default maxPoints is 1
        .minPoints(0) // default minPoints is 0
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> H03_Tests.class.getMethod("H03_MyIndexHoppingHashMapRemoveEmpty")))
                .requirePass(JUnitTestRef.ofMethod(() -> H03_Tests.class.getMethod("H03_MyIndexHoppingHashMapRemoveSingle")))
                .requirePass(JUnitTestRef.ofMethod(() -> H03_Tests.class.getMethod("H03_MyIndexHoppingHashMapRemoveMultiple")))
                .pointsPassedMax() // award maximum points if ALL tests passed
                .pointsFailedMin() // award minimum points if ANY test failed
                .build()
        ).build();

    public static final Criterion H3_MyIndexHoppingHashMapContainsKey = Criterion.builder()
        .shortDescription("Class MyIndexHoppingHashMap - Method ContainsKey")
        .maxPoints(1) // default maxPoints is 1
        .minPoints(0) // default minPoints is 0
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> H03_Tests.class.getMethod("H03_MyIndexHoppingHashMapContainsKeyTrue")))
                .requirePass(JUnitTestRef.ofMethod(() -> H03_Tests.class.getMethod("H03_MyIndexHoppingHashMapContainsKeyFalse")))
                .pointsPassedMax() // award maximum points if ALL tests passed
                .pointsFailedMin() // award minimum points if ANY test failed
                .build()
        ).build();

    public static final Criterion H3_MyIndexHoppingHashMapGetValue = Criterion.builder()
        .shortDescription("Class MyIndexHoppingHashMap - Method GetValue")
        .maxPoints(1) // default maxPoints is 1
        .minPoints(0) // default minPoints is 0
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> H03_Tests.class.getMethod("H03_MyIndexHoppingHashMapGetValuePositive")))
                .requirePass(JUnitTestRef.ofMethod(() -> H03_Tests.class.getMethod("H03_MyIndexHoppingHashMapGetValueNegative")))
                .pointsPassedMax() // award maximum points if ALL tests passed
                .pointsFailedMin() // award minimum points if ANY test failed
                .build()
        ).build();

    public static final Criterion H3 = Criterion.builder()
        .shortDescription("H3")
        .addChildCriteria(
            H3_MyIndexHoppingHashMap,
            H3_MyIndexHoppingHashMapPut,
            H3_MyIndexHoppingHashMapRemove,
            H3_MyIndexHoppingHashMapContainsKey,
            H3_MyIndexHoppingHashMapGetValue
        ) // maxPoints and minPoints and grading is inferred from child criteria
        .build();

    ////////
    // H4 //
    ////////

    public static final Criterion H4_MyListsHashMap = Criterion.builder()
        .shortDescription("Class MyListsHashMap")
        .maxPoints(1) // default maxPoints is 1
        .minPoints(0) // default minPoints is 0
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> H04_Tests.class.getMethod("H04_MyListsHashMapBasic")))
                .pointsPassedMax() // award maximum points if ALL tests passed
                .pointsFailedMin() // award minimum points if ANY test failed
                .build()
        ).build();

    public static final Criterion H4_MyListsHashMapPut = Criterion.builder()
        .shortDescription("Class MyListsHashMap - Method Put")
        .maxPoints(2) // default maxPoints is 1
        .minPoints(0) // default minPoints is 0
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> H04_Tests.class.getMethod("H04_MyListsHashMapMapPutSingle")))
                .requirePass(JUnitTestRef.ofMethod(() -> H04_Tests.class.getMethod("H04_MyListsHashMapPutReplace")))
                .requirePass(JUnitTestRef.ofMethod(() -> H04_Tests.class.getMethod("H04_MyListsHashMapPutMultiple")))
                .pointsPassedMax() // award maximum points if ALL tests passed
                .pointsFailedMin() // award minimum points if ANY test failed
                .build()
        ).build();

    public static final Criterion H4_MyListsHashMapRemove = Criterion.builder()
        .shortDescription("Class MyListsHashMap - Method Remove")
        .maxPoints(2) // default maxPoints is 1
        .minPoints(0) // default minPoints is 0
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> H04_Tests.class.getMethod("H04_MyListsHashMapRemoveEmpty")))
                .requirePass(JUnitTestRef.ofMethod(() -> H04_Tests.class.getMethod("H04_MyListsHashMapRemoveSingle")))
                .requirePass(JUnitTestRef.ofMethod(() -> H04_Tests.class.getMethod("H04_MyListsHashMapRemoveMultiple")))
                .pointsPassedMax() // award maximum points if ALL tests passed
                .pointsFailedMin() // award minimum points if ANY test failed
                .build()
        ).build();

    public static final Criterion H4_MyListsHashMapContainsKey = Criterion.builder()
        .shortDescription("Class MyListsHashMap - Method ContainsKey")
        .maxPoints(1) // default maxPoints is 1
        .minPoints(0) // default minPoints is 0
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> H04_Tests.class.getMethod("H04_MyListsHashMapContainsKeyTrue")))
                .requirePass(JUnitTestRef.ofMethod(() -> H04_Tests.class.getMethod("H04_MyListsHashMapContainsKeyFalse")))
                .pointsPassedMax() // award maximum points if ALL tests passed
                .pointsFailedMin() // award minimum points if ANY test failed
                .build()
        ).build();

    public static final Criterion H4_MyListsHashMapGetValue = Criterion.builder()
        .shortDescription("Class MyListsHashMap - Method GetValue")
        .maxPoints(1) // default maxPoints is 1
        .minPoints(0) // default minPoints is 0
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> H04_Tests.class.getMethod("H04_MyIndexHoppingHashMapGetValuePositive")))
                .requirePass(JUnitTestRef.ofMethod(() -> H04_Tests.class.getMethod("H04_MyIndexHoppingHashMapGetValueNegative")))
                .pointsPassedMax() // award maximum points if ALL tests passed
                .pointsFailedMin() // award minimum points if ANY test failed
                .build()
        ).build();

    public static final Criterion H4 = Criterion.builder()
        .shortDescription("H4")
        .addChildCriteria(
            H4_MyListsHashMap,
            H4_MyListsHashMapPut,
            H4_MyListsHashMapRemove,
            H4_MyListsHashMapContainsKey,
            H4_MyListsHashMapGetValue
        ) // maxPoints and minPoints and grading is inferred from child criteria
        .build();

    ////////
    // H5 //
    ////////

    public static final Criterion H5_MyDate = Criterion.builder()
        .shortDescription("Class MyDate")
        .maxPoints(2) // default maxPoints is 1
        .minPoints(0) // default minPoints is 0
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> H05_Tests.class.getMethod("H05_MyDateBasic")))
                .requirePass(JUnitTestRef.ofMethod(() -> H05_Tests.class.getMethod("H05_MyDateHash")))
                .pointsPassedMax() // award maximum points if ALL tests passed
                .pointsFailedMin() // award minimum points if ANY test failed
                .build()
        ).build();

    public static final Criterion H5 = Criterion.builder()
        .shortDescription("H5")
        .addChildCriteria(
            H5_MyDate
        ) // maxPoints and minPoints and grading is inferred from child criteria
        .build();

    ////////
    // H6 //
    ////////

    public static final Criterion H06_RuntimeTestGenerateTestData = Criterion.builder()
        .shortDescription("Class RuntimeTest - Method GenerateTestData")
        .maxPoints(2) // default maxPoints is 1
        .minPoints(0) // default minPoints is 0
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> H06_Tests.class.getMethod("H06_RuntimeTestGenerateTestData")))
                .pointsPassedMax() // award maximum points if ALL tests passed
                .pointsFailedMin() // award minimum points if ANY test failed
                .build()
        ).build();

    public static final Criterion H06_RuntimeTestCreateTestSet = Criterion.builder()
        .shortDescription("Class RuntimeTest - Method CreateTestSet")
        .maxPoints(2) // default maxPoints is 1
        .minPoints(0) // default minPoints is 0
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> H06_Tests.class.getMethod("H06_RuntimeTestGenerateTestData")))
                .pointsPassedMax() // award maximum points if ALL tests passed
                .pointsFailedMin() // award minimum points if ANY test failed
                .build()
        ).build();


    public static final Criterion H06_RuntimeTestExecuteTest = Criterion.builder()
        .shortDescription("Class RuntimeTest - Method Test")
        .maxPoints(1) // default maxPoints is 1
        .minPoints(0) // default minPoints is 0
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> H06_Tests.class.getMethod("H06_RuntimeTestExecuteTest")))
                .pointsPassedMax() // award maximum points if ALL tests passed
                .pointsFailedMin() // award minimum points if ANY test failed
                .build()
        ).build();

    public static final Criterion H6 = Criterion.builder()
        .shortDescription("H6")
        .addChildCriteria(
            H06_RuntimeTestGenerateTestData,
            H06_RuntimeTestCreateTestSet,
            H06_RuntimeTestExecuteTest
        ) // maxPoints and minPoints and grading is inferred from child criteria
        .build();

    public static final Rubric RUBRIC = Rubric.builder()
        .title("Ü6 Tests")
        .addChildCriteria(H1)
        //.addChildCriteria(H2)
        .addChildCriteria(H3)
        .addChildCriteria(H4)
        .addChildCriteria(H5)
        .addChildCriteria(H6)
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}
