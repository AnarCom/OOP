import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class SetGeneratorTest {

    @Test
    public void GeneratorTest() {
        SetGenerator g = new SetGenerator(11);
        var ans = g.getAns();
        assertArrayEquals(new Integer[]{2, 3, 5, 7}, ans.toArray());
    }
}
