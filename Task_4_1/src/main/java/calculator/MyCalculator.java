package calculator;

import enums.CalcModes;
import operations.*;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

public class MyCalculator {
    private final HashMap<String, BaseOperation<Double>> operators = new HashMap<>();

    public MyCalculator() {
        this(CalcModes.DEFAULT_OPERATIONS);
    }

    public MyCalculator(CalcModes mode) {
        if (mode != CalcModes.EMPTY_OPERATIONS) {
            registerOperation(new PlusOperation());
            registerOperation(new MinusOperation());
            registerOperation(new SinOperation());
            registerOperation(new CosOperation());
            registerOperation(new LnOperation());
            registerOperation(new PowOperation());
            registerOperation(new SqrtOperation());
            registerOperation(new MltOperation());
            registerOperation(new DivOperation());
        }
    }

    public Double calc(String formula) {
        String[] tokens = formula.split(" ");
        Stack<Double> stack = new Stack<>();
        ArrayUtils.reverse(tokens);

        for (var token : tokens) {
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else if (operators.containsKey(token)) {
                computeByStack(token, stack);
            } else {
                throw new IllegalArgumentException(
                        String.format(
                                "Cannot parse operation = '%s' (Check that the operation is registered)",
                                token
                        )
                );
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Number of arguments are different to number " +
                    "of sum(operation.getNumberOfArguments");
        }
        return stack.pop();
    }

    public void registerOperation(BaseOperation<Double> operator) {
        registerOperation(operator, false);
    }

    public void registerOperation(BaseOperation<Double> operator, boolean rewriteOper) {
        if (operator == null) {
            throw new IllegalArgumentException("operator is null");
        }
        if (operators.containsKey(operator.getOperationRepresentation()) && !rewriteOper) {
            throw new IllegalArgumentException("this key is exists in HashMap");
        }
        operators.put(operator.getOperationRepresentation(), operator);

    }

    private void computeByStack(String operation, Stack<Double> stack) {
        var operator = operators.get(operation);
        if (operator.getNumberOfArguments() > stack.size()) {
            throw new IllegalArgumentException("Cannot calc formula due to number of input arguments");
        }
        operator.calc(stack);
    }

    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
