import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubstringFinderTest {
    @Test
    public void simpleTest1() throws IOException {
        FileWriter fw = new FileWriter("input.txt", StandardCharsets.UTF_8);
        String str = "nnnnn n n nn nnnn nnnn nnn";
        fw.write(str);
        fw.close();
        Long[] expectedResult = new Long[]{0L, 1L, 2L, 13L, 14L, 18L, 19L, 23L};
        ArrayList<Long> result = SubstringFinder.findSubstringInFile("input.txt", "nnn");
        assertArrayEquals(expectedResult, result.toArray());
        File file = new File("input.txt");
        assertTrue(file.delete());
    }

    @Test
    public void simpleTest2() throws IOException {
        FileWriter fw = new FileWriter("input.txt", StandardCharsets.UTF_8);
        String str = "121212321123";
        fw.write(str);
        fw.close();
        Long[] expectedResult = new Long[]{0L,2L,4L,9L};
        ArrayList<Long> result = SubstringFinder.findSubstringInFile("input.txt", "12");
        assertArrayEquals(expectedResult, result.toArray());

        File file = new File("input.txt");
        assertTrue(file.delete());
    }
}
