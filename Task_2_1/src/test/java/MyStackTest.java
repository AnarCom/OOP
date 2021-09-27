import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyStackTest {
    @Test
    public void simpleTest() {
        MyStack<Integer> stack = new MyStack<>();
        stack.push(2);
        stack.push(7);
        assertEquals(2, stack.count());
        assertEquals(7, stack.pop());
        assertEquals(1, stack.count());
        assertEquals(2, stack.pop());
        assertEquals(0, stack.count());
    }

    @Test
    public void largeStackTest() {
        MyStack<Integer> stack = new MyStack<>();
        for (int i = 0; i < 10000; i++) {
            stack.push(i);
        }
        assertEquals(stack.count(), 10000);
        for (int i = 9999; i >= 0; i--) {
            assertEquals(i, stack.pop());
            assertEquals(i, stack.count());
        }
    }

    @Test
    public void stackPushTest() {
        MyStack<Integer> stack = new MyStack<>();
        stack.push(12);
        stack.push(13);

        MyStack<Integer> mainStack = new MyStack<>();
        mainStack.push(1);
        mainStack.pushStack(stack);

        assertEquals(13, mainStack.pop());
        assertEquals(12, mainStack.pop());
        assertEquals(1, mainStack.pop());
    }

    @Test
    public void stackPopTest() {
        MyStack<Integer> mainStack = new MyStack<>();
        for (int i = 0; i < 10000; i++) {
            mainStack.push(i);
        }
        assertEquals(mainStack.count(), 10000);
        for (int i = 9999; i >= 0; i--) {
            MyStack<Integer> l = mainStack.popStack(1);
            assertEquals(1, l.count());
            assertEquals(i, l.pop());
            assertEquals(0, l.count());
        }
    }
}
