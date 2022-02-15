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
    public void calcTimeForParallelStream() {
        Optional<Boolean> result = testData.stream()
                .parallel()
                .map(SinglePrimeChecker::isPrime)
                .reduce((x, y) -> x && y);
    }

    @Test
    public void calcTimeForMultipleThreads(){
        for(int i = 2; i < 20; i++){
            Long started = System.nanoTime();
            new ParallelFinder(testData, i).checkThatAllIsPrime();
            System.out.printf("%d: %d\n", i,  (System.nanoTime() - started) / 1000000);
        }
    }


}
