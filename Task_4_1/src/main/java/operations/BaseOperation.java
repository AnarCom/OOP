package operations;

import java.util.Stack;

public interface BaseOperation<T> {
    String getOperationRepresentation();
    void calc(Stack<T> stack);
    int getNumberOfArguments();
}
