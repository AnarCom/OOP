package operations;

import java.util.Stack;

public class LnOperation implements BaseOperation<Double>{
    @Override
    public String getOperationRepresentation() {
        return "ln";
    }

    @Override
    public void calc(Stack<Double> stack) {
        stack.push(Math.log(stack.pop()));
    }

    @Override
    public int getNumberOfArguments() {
        return 1;
    }
}
