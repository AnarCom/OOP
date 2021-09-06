public class HeapSort {
    /**
     * Returns left child id by parent id
     * @param parent_id id of parent in array
     * @return id of left child
     */
    private static int getLeftChildInd(int parent_id) {
        return 2 * parent_id + 1;
    }

    /**
     * Returns right child id by parent id
     * @param parent_id id of parent in array
     * @return id of right child
     */
    private static int getRightChildInd(int parent_id) {
        return 2 * parent_id + 2;
    }

    /**
     * checks that element by i is bigger than his parent and swaps (if not)
     * @param arr array for build a heap
     * @param i id of element to change
     * @param heap_size length of heap
     */
    private static void heapify(int[] arr, int i, int heap_size) {
        int l = getLeftChildInd(i);
        int r = getRightChildInd(i);
        int largest = i;
        if (l < heap_size && (arr[l] > arr[i])) largest = l;
        if (r < heap_size && (arr[r] > arr[largest])) largest = r;
        if (largest != i)
        {
            int t = arr[i];
            arr[i] = arr[largest];
            arr[largest] = t;
            heapify(arr, largest, heap_size);
        }
    }

    /**
     * builds a heap for all elements of array
     * @param arr array for building of heap
     * @param size size of array
     */
    private static void buildHeap(int[] arr, int size) {
        int i = (size - 1) / 2;
        while (i >= 0) {
            heapify(arr, i, size);
            i--;
        }
    }

    /**
     * heap sort main function 
     * @param arr array for sorting
     * @param size size of array (or number of elements to sort)
     */
    public static void heapSort(int[] arr, int size) {
        buildHeap(arr, size);
        int heapsize = size;
        for (int i = size - 1; i > 0; i--) {
            int t = arr[0];
            arr[0] = arr[i];
            arr[i] = t;
            heapsize--;
            heapify(arr, 0, heapsize);
        }
    }
}
