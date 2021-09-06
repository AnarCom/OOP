import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@DisplayName("Heap sort test")
public class HeapSortTests {
    @Test
    public void simpleTests() {
        // first (example) test
        int[] arr = {5, 4, 3, 2, 1};
        int[] ans = {1, 2, 3, 4, 5};
        HeapSort.heapSort(arr, arr.length);
        assertArrayEquals(ans, arr);

        // second test
        int[] arr2 = {-3, 1024, 753, 22, 1};
        int[] ans2 = {-3, 1, 22, 753, 1024};
        HeapSort.heapSort(arr2, arr2.length);
        assertArrayEquals(ans2, arr2);
    }

    @Test
    public void emptyArrayTest() {
        int[] arr = {};
        int[] ans = {};
        HeapSort.heapSort(arr, arr.length);
        assertArrayEquals(ans, arr);
    }

    @Test
    public void maxIntNumbersTest() {
        int[] arr = {Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, -1, 1};
        int[] ans = {Integer.MIN_VALUE, Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE, Integer.MAX_VALUE};
        HeapSort.heapSort(arr, arr.length);
        assertArrayEquals(ans, ans);
    }
}
