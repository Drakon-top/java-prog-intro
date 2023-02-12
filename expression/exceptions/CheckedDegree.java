package expression.exceptions;

import expression.ItemExpression;
import expression.exceptions.CalculatingException;
import expression.exceptions.OverflowException;
import expression.exceptions.PowerFunctionException;

public class CheckedDegree implements ItemExpression {

    private final ItemExpression exp;
    private final int pow;

    public CheckedDegree(ItemExpression object, int pow) {
        exp = object;
        this.pow = pow;
    }

    @Override
    public double evaluate(double x) {
        return exp.evaluate(x);
    }

    @Override
    public int evaluate(int x) {
        return degree(exp.evaluate(x));
    }

    @Override
    public String toMiniString() {
        if (exp.getPriority() >= getPriority()) {
            return "pow" + pow + " " + exp.toMiniString();
        }
        return "pow" + pow + "(" + exp.toMiniString() + ")";
    }

    @Override
    public int evaluate(int x, int y, int z) throws CalculatingException {
        return degree(exp.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "pow" + pow + "(" + exp + ")";
    }

    @Override
    public int getPriority() {
        return 3;
    }

    private int degree(int result) throws CalculatingException {
        if (result < 0) {
            throw new PowerFunctionException("raising to a negative degree");
        }

        if (pow < 0) {
            throw new PowerFunctionException("negative degree base");
        }

        if (pow == 0) {
            return 1;
        }
        int now = 1;
        for (int i = 0; i < result; i++) {
            now = multiplyExact(pow, now);
        }
        return now;
    }

    private int multiplyExact(int x, int y) throws OverflowException {
        int r = x * y;
        if (x == -1 && y == Integer.MIN_VALUE || y == -1 && x == Integer.MIN_VALUE || y != 0 && r / y != x) {
            throw new OverflowException();
        }
        return r;
    }
}
