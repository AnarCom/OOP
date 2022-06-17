/**
 * Class that contains HeapSort realization.
 */
public class HeapSort {
  /**
   * Returns left child id by parent id.
   *
   * @param parentId id of parent in array
   * @return id of left child
   */
  private static int getLeftChildInd(int parentId) {
    return 2 * parentId + 1;
  }

  /**
   * Returns right child id by parent id.
   *
   * @param parentId id of parent in array
   * @return id of right child
   */
  private static int getRightChildInd(int parentId) {
    return 2 * parentId + 2;
  }

  /**
   * Checks that element by i is bigger than his parent and swaps (if not).
   *
   * @param arr      array for build a heap
   * @param i        id of element to change
   * @param heapSize length of heap
   */
  private static void heapify(int[] arr, int i, int heapSize) {
    int l = getLeftChildInd(i);
    int r = getRightChildInd(i);
    int largest = i;
    if (l < heapSize && (arr[l] > arr[i])) {
      largest = l;
    }
    if (r < heapSize && (arr[r] > arr[largest])) {
      largest = r;
    }
    if (largest != i) {
      int t = arr[i];
      arr[i] = arr[largest];
      arr[largest] = t;
      heapify(arr, largest, heapSize);
    }
  }

  /**
   * Builds a heap for all elements of array.
   *
   * @param arr  array for building of heap
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
   * Heap sort main function.
   *
   * @param arr  array for sorting
   * @param size size of array (or number of elements to sort)
   */
  public static void heapSort(int[] arr, int size) {
    if (size > arr.length) {
      throw new IllegalArgumentException("Size if bigger than arr.length");
    }
    if (size < 0) {
      throw new IllegalArgumentException("Size is less than zero");
    }
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
