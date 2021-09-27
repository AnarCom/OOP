import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StackTest {
    @Test
    public void simpleTest() {
        Stack<Integer> stack = new Stack<>();
        stack.push(2);
        stack.push(7);
        assertEquals(stack.size(), 2);
        assertEquals(stack.pop(), 7);
        assertEquals(stack.size(), 1);
        assertEquals(stack.pop(), 2);
        assertEquals(stack.size(), 0);
    }

    @Test
    public void largeStackTest(){
        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i < 10000; i++){
            stack.push(i);
        }
        assertEquals(stack.size(), 10000);
        for(int i = 9999; i >= 0; i--){
            assertEquals(stack.pop(), i);
            assertEquals(stack.size(), i);
        }
    }
}
