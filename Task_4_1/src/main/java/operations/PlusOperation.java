package operations;

import java.util.Stack;

public class PlusOperation implements BaseOperation<Double>{
    @Override
    public String getOperationRepresentation() {
        return "+";
    }

    @Override
    public void calc(Stack<Double> stack) {
        stack.push(stack.pop() + stack.pop());
    }

    @Override
    public int getNumberOfArguments() {
        return 2;
    }
}
