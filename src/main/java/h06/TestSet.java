package h06;

public class TestSet<W> {

    private final MyMap<W, W> hashTable;

    private final W[] testData;

    public TestSet(MyMap<W, W> hashTable, W[] testData) {
        this.hashTable = hashTable;
        this.testData = testData;
    }

    public MyMap<W, W> getHashTable() {
        return hashTable;
    }

    public W[] getTestData() {
        return testData;
    }
}
