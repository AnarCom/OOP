import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Heap sort test")
public class HeapSortTests {

    private Stream<Arguments> getParametersForTest() {
        return Stream.of(
                Arguments.of(
                        new int[]{5, 4, 3, 2, 1},
                        new int[]{1, 2, 3, 4, 5}
                ),
                Arguments.of(
                        new int[]{-3, 1024, 753, 22, 1},
                        new int[]{-3, 1, 22, 753, 1024}
                ),
                Arguments.of(
                        new int[0],
                        new int[0]
                ),
                Arguments.of(
                        new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, -1, 1},
                        new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE, Integer.MAX_VALUE}
                )
        );
    }

    @DisplayName("Heap sort test")
    @ParameterizedTest
    @MethodSource("getParametersForTest")
    public void emptyArrayTest(int[] arr, int[] ans) {
        HeapSort.heapSort(arr, arr.length);
        assertArrayEquals(ans, arr);
    }
}
