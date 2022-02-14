import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SequentialFinderTest {

    @Test
    public void okTest(){
        SetGenerator generator = new SetGenerator(1000);
        var list = generator.getAns();
        assertTrue(new SequentialFinder(list).checkThatAllIsPrime());
    }

    @Test
    public void notOkTest(){
        SetGenerator generator = new SetGenerator(1000);
        var list = generator.getAns();
        list.add(10);
        assertFalse(new SequentialFinder(list).checkThatAllIsPrime());
    }

    @Test
    public void emptyTest(){
        var list = new ArrayList<Integer>();
        assertTrue(new SequentialFinder(list).checkThatAllIsPrime());
    }

}
