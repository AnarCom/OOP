import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Heap sort test")
public class HeapSortTests {

    private Stream<Arguments> getParametersForTest() {
        return Stream.of(
                Arguments.of(
                        new int[]{5, 4, 3, 2, 1},
                        new int[]{1, 2, 3, 4, 5},
                        5
                ),
                Arguments.of(
                        new int[]{-3, 1024, 753, 22, 1},
                        new int[]{-3, 1, 22, 753, 1024},
                        5
                ),
                Arguments.of(
                        new int[0],
                        new int[0],
                        1
                ),
                Arguments.of(
                        new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, -1, 1},
                        new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE, Integer.MAX_VALUE},
                        7
                ),
                Arguments.of(
                        new int[]{1, 0, 3, 15},
                        new int[]{0, 1, 3, 15},
                        2
                ),
                Arguments.of(
                        new int[]{3, 15, 27},
                        new int[]{3, 15, 27},
                        0
                )
        );
    }

    @DisplayName("Heap sort test")
    @ParameterizedTest
    @MethodSource("getParametersForTest")
    public void emptyArrayTest(int[] arr, int[] ans, int n) {
        HeapSort.heapSort(arr, n);
        assertArrayEquals(ans, arr);
    }

    private Stream<Arguments> getIllegalArgumentsForTest() {
        return Stream.of(
                Arguments.of(
                        new int[]{3, 15, 27},
                        new int[]{3, 15, 27},
                        12,
                        IllegalArgumentException.class,
                        "Size if bigger than arr.length"
                ),
                Arguments.of(
                        new int[]{3, 15, 27},
                        new int[]{3, 15, 27},
                        -1,
                        IllegalArgumentException.class,
                        "Size is less than zero"
                )
        );
    }

    @DisplayName("Heap (illegal argument) sort test")
    @ParameterizedTest
    @MethodSource("getIllegalArgumentsForTest")
    public void illegalArgumentTest(int[] arr, int[] et, int n, Class<Exception> cl, String message) {
        var ex = assertThrows(
                cl,
                () -> HeapSort.heapSort(arr, n)
        );
        assertEquals(message, ex.getMessage());
        assertArrayEquals(arr, et);
    }
}
