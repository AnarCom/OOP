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
    }


    @Test
    public void calcTimeForSingleThread() {
        new SequentialFinder(testData).checkThatAllIsPrime();
    }

    @Test
    public void calcTimeForMultipleThread() {
        new ParallelFinder(testData, 9).checkThatAllIsPrime();
    }

    @Test
    public void calcTimeForParallelStream() {
        Optional<Boolean> result = testData.stream()
                .parallel()
                .map(SinglePrimeChecker::isPrime)
                .reduce((x, y) -> x && y);
    }

}
