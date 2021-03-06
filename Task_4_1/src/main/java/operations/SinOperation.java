package operations;

import java.util.Stack;

public class SinOperation implements BaseOperation<Double>{
    @Override
    public String getOperationRepresentation() {
        return "sin";
    }

    @Override
    public void calc(Stack<Double> stack) {
        stack.push(Math.sin(stack.pop()));
    }

    @Override
    public int getNumberOfArguments() {
        return 1;
    }
}
