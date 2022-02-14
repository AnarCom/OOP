import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GettingDataForGraphTest {
    List<Integer> testData;

    @BeforeAll
    public void generateData() {
        testData = new SetGenerator(1000000).getAns();
    }

    Long started;

    @BeforeEach
    public void startTime() {
        started = System.nanoTime();
    }

    @AfterEach
    public void finishTime() {
        System.out.println((System.nanoTime() - started) / 1000000);
        started = 0L;
    }

    @Test
    public void aTest(){}

    @Test
    public void calcTimeForSingleThread() {
        new SequentialFinder(testData).checkThatAllIsPrime();
    }

    @Test
    public void calcTimeForMultipleThread() {
        new ParallelFinder(testData, 9).checkThatAllIsPrime();
    }

    @Test
    public void calcTimeForMultipleThread_1() {
        new ParallelFinder(testData, 5).checkThatAllIsPrime();
    }

    @Test
    public void calcTimeForMultipleThread_2() {
        new ParallelFinder(testData, 13).checkThatAllIsPrime();
    }

    @Test
    public void calcTimeForParallelStream() {
        Optional<Boolean> result = testData.stream()
                .parallel()
                .map(SinglePrimeChecker::isPrime)
                .reduce((x, y) -> x && y);
    }

}
