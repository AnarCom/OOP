import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Iterator;

public class Stack<Type> implements Iterable<Type>, Iterator<Type> {

    private Type[] arr;
    private int end;

    /**
     * create a stack with 5 array buffer
     */
    public Stack() {
        arr = (Type[]) new Object[5];
        end = 0;
    }

    private void resizeArray() {
        if (end == arr.length - 1) {
            arr = Arrays.copyOf(arr, arr.length * 2);
        }
    }

    /**
     * pushes element to stack
     *
     * @param element element to push
     */
    public void push(Type element) {
        resizeArray();
        arr[end] = element;
        end++;
    }

    /**
     * pops element from stack
     *
     * @return poped element
     */
    public Type pop() {
        if (!hasNext()) {
            throw new EmptyStackException();
        }
        return arr[--end];
    }

    public int count() {
        return end;
    }

    @Override
    public Iterator<Type> iterator() {
        return null;
    }

    @Override
    public boolean hasNext() {
        return end > 0;
    }

    @Override
    public Type next() {
        return pop();
    }
}
