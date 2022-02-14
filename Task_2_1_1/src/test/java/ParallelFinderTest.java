import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParallelFinderTest {
    @Test
    public void okTest() {
        SetGenerator generator = new SetGenerator(1000);
        var list = generator.getAns();
        assertTrue(new ParallelFinder(list, 9).checkThatAllIsPrime());
    }

    @Test
    public void notOkTest() {
        SetGenerator generator = new SetGenerator(1000);
        var list = generator.getAns();
        list.add(10);
        assertFalse(new ParallelFinder(list, 3).checkThatAllIsPrime());
    }

    @Test
    public void emptyTest() {
        var list = new ArrayList<Integer>();
        assertTrue(new ParallelFinder(list, 4).checkThatAllIsPrime());
    }
}
