import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.EmptyStackException;

public class MyStack<T> {

    private T[] arr;
    private int end;

    /**
     * create a stack buffer
     */
    public MyStack() {
        arr = (T[]) new Object[5];
        end = 0;
    }

    private void resizeArray() {
        if (end == arr.length - 1) {
            arr = Arrays.copyOf(arr, arr.length * 2);
        }
    }

    private void resizeArray(int newSize) {
        if (end < newSize && arr.length < newSize) {
            arr = Arrays.copyOf(arr, newSize);
        }
    }

    /**
     * Pushed stack to main stack.
     *
     * @param myStack stack to push
     */
    public void pushStack(MyStack<T> myStack) {
        for (int i = 0; i < myStack.count(); i++) {
            this.push(myStack.arr[i]);
        }
    }

    /**
     * Popes stack.
     *
     * @param size size of stack to pop
     * @return New stack with data from main stack
     */
    public MyStack<T> popStack(int size) {
        if (end - size < 0) {
            throw new IllegalArgumentException("size is bigger that stack.size()");
        }
        MyStack<T> myStack = new MyStack<>();
        myStack.resizeArray(size);
        for (int i = end - size; i < end; i++) {
            myStack.push(this.arr[i]);
        }
        this.end -= size;
        return myStack;
    }

    /**
     * pushes element to stack
     *
     * @param element element to push
     */
    public void push(T element) {
        resizeArray();
        arr[end] = element;
        end++;
    }

    /**
     * pops element from stack
     *
     * @return pop'ed element
     */
    public T pop() {
        if (!hasNext()) {
            throw new EmptyStackException();
        }
        return arr[--end];
    }

    /**
     * @return number of elements that locates in stack
     */
    public int count() {
        return end;
    }

    private boolean hasNext() {
        return end > 0;
    }

}
