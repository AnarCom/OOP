import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubstringFinderTest {
    public Stream<Arguments> testDataProvider() {
        return Stream.of(
                Arguments.of(
                        "nnnnn n n nn nnnn nnnn nnn",
                        "nnn",
                        new Long[]{0L, 1L, 2L, 13L, 14L, 18L, 19L, 23L}
                ),
                Arguments.of(
                        "121212321123",
                        "12",
                        new Long[]{0L, 2L, 4L, 9L}
                )
        );
    }

    @ParameterizedTest
    @MethodSource("testDataProvider")
    public void SubstringTest(String content, String sub, Long[] ans) throws IOException {
        FileWriter fw = new FileWriter("input.txt", StandardCharsets.UTF_8);
        fw.write(content);
        fw.close();
        ArrayList<Long> result = SubstringFinder.findSubstringInFile("input.txt", sub);
        assertArrayEquals(ans, result.toArray());
        Files.delete(Path.of("input.txt"));
    }
}
