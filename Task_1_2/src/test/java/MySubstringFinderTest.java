import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MySubstringFinderTest {
    public Stream<Arguments> testDataProvider() {
        return Stream.of(
                Arguments.of(
                        "dannye",
                        new Integer[]{5, 4, 2, 2, 1, 6}
                ),
                Arguments.of(
                        "zorro",
                        new Integer[]{4, 3, 1, 1, 3}
                ),
                Arguments.of(
                        "",
                        new Integer[]{}
                )
        );
    }

    @ParameterizedTest
    @MethodSource("testDataProvider")
    @DisplayName("Check that offset work correctly")
    void testOfOffsetCalculator(String str, Integer[] arr) {
        MySubstringFinder finder = new MySubstringFinder();
        assertArrayEquals(arr, finder.buildOffset(str));
    }
}
