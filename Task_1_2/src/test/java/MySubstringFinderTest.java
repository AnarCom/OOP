import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MySubstringFinderTest {

    public Stream<Arguments> getTestSource() {
        return Stream.of(
                Arguments.of(
                        "nnn",
                        (TestDataProvider) (filename) -> {
                            FileWriter fw = new FileWriter(filename, StandardCharsets.UTF_8);
                            String str = "nnnnn n n nn nnnn nnnn nnn";
                            fw.write(str);
                            fw.close();
                            return new Long[]{0L, 1L, 2L, 13L, 14L, 18L, 19L, 23L};
                        }
                ),
                Arguments.of(
                        "arasdsdsd",
                        (TestDataProvider) (filename) -> {
                            FileWriter fw = new FileWriter(filename, StandardCharsets.UTF_8);
                            String str = "ararara arara araara";
                            fw.write(str);
                            fw.close();
                            return new Long[]{};
                        }
                ),
                Arguments.of(
                        "Email has",
                        (TestDataProvider) (filename) -> {
                            int a = 10000;
                            FileWriter fw = new FileWriter(filename, StandardCharsets.UTF_8);
                            List<Long> ans = new ArrayList<>();
                            ans.add(0L);
                            List<Long> ret = new ArrayList<>();
                            int sdv = 0;
                            String template = "Email has been sent. " +
                                    "This email travel.".repeat(100) +
                                    "Travel finished.";
                            for (int i = 0; i < a; i++) {
                                fw.write(template);
                                for (var j : ans) {
                                    ret.add(j + sdv);
                                }
                                sdv += template.length();
                            }

                            fw.close();
                            return ret.toArray(new Long[0]);
                        }
                ),
                Arguments.of(
                        "This email travel.",
                        (TestDataProvider) (filename) -> {
                            int a = 10000;
                            FileWriter fw = new FileWriter(filename, StandardCharsets.UTF_8);

                            List<Long> ret = new ArrayList<>();
                            int sdv = 0;
                            String template = "Email has been sent. " +
                                    "This email travel.".repeat(500) +
                                    "Travel finished.".repeat(100);
                            for (int i = 0; i < a; i++) {
                                fw.write(template);
                                for (int j = 0; j < 500; j++) {
                                    ret.add(21L + (18 * j) + sdv);
                                }
                                sdv += template.length();
                            }

                            fw.close();
                            return ret.toArray(new Long[0]);
                        }
                ),
                Arguments.of(
                        "n".repeat(10),
                        (TestDataProvider) (filename) -> {
                            FileWriter fw = new FileWriter(filename, StandardCharsets.UTF_8);
                            String str = "nnnn";
                            fw.write(str);
                            fw.close();
                            return new Long[]{};
                        }
                ),
                Arguments.of(
                        "n".repeat(10),
                        (TestDataProvider) (filename) -> {
                            FileWriter fw = new FileWriter(filename, StandardCharsets.UTF_8);
                            fw.write("");
                            fw.close();
                            return new Long[]{};
                        }
                ),
                Arguments.of(
                        "",
                        (TestDataProvider) (filename) -> {
                            FileWriter fw = new FileWriter(filename, StandardCharsets.UTF_8);
                            fw.write("b".repeat(120));
                            fw.close();
                            return new Long[]{};
                        }
                )
        );
    }

    @ParameterizedTest
    @MethodSource("getTestSource")
    @DisplayName("Test of normal work")
    public void mainTest(String substring, TestDataProvider provider) throws IOException {
        var exp = provider.provideFileAndAns("input.txt");
        MySubstringFinder finder = new MySubstringFinder();
        var data = finder.findSubPositions("input.txt", substring);

        assertArrayEquals(exp, data);

        File file = new File("input.txt");
        boolean wasDeleted = file.delete();
        assertTrue(wasDeleted);
    }

    public Stream<Arguments> getTestSourceForExceptions() {
        return Stream.of(
                Arguments.of(
                        null,
                        "subs",
                        "path to file is null",
                        IllegalArgumentException.class
                ),
                Arguments.of(
                        "null.txt",
                        null,
                        "substring is null",
                        IllegalArgumentException.class
                ),
                Arguments.of(
                        "null.txt",
                        "null",
                        "file not found",
                        IllegalArgumentException.class
                )
        );
    }


    @ParameterizedTest
    @MethodSource("getTestSourceForExceptions")
    @DisplayName("Test of not normal work")
    public void fileNotExistsExceptionTest(
            String fileName,
            String substring,
            String errMessage,
            Class<Exception> cl

    ) {
        Exception e = Assertions.assertThrows(
                cl,
                () -> new MySubstringFinder().findSubPositions(fileName, substring)
        );
        assertEquals(e.getMessage(), errMessage);
    }

    @FunctionalInterface
    private interface TestDataProvider {
        Long[] provideFileAndAns(String filename) throws IOException;
    }
}
