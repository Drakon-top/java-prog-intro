package expression.exceptions;

import expression.ItemExpression;
import expression.exceptions.CalculatingException;
import expression.exceptions.PowerFunctionException;

public class CheckedLogarithm implements ItemExpression {

    private final ItemExpression exp;
    private final int log;

    public CheckedLogarithm(ItemExpression object, int log) {
        exp = object;
        this.log = log;
    }

    @Override
    public double evaluate(double x) {
        return exp.evaluate(x);
    }

    @Override
    public int evaluate(int x) {
        return logarithm(exp.evaluate(x));
    }

    private int logarithm(int result) throws CalculatingException {
        if (result <= 0) {
            throw new PowerFunctionException("Negative number in logarithm");
        }

        if (log <= 0) {
            throw new PowerFunctionException("Negative base of the logarithm");
        }
        if (result == 1) {
            return 0;
        }
        int now = log;
        int i = 1;
        while (now < result) {
            i++;
            if (checkedMultiplyExact(now, log)) {
                break;
            }
            now *= log;
        }
        if (now == result) {
            return i;
        }
        return i - 1;
    }

    @Override
    public String toMiniString() {
        if (exp.getPriority() >= getPriority()) {
            return "log" + log + " " + exp.toMiniString();
        }
        return "log" + log + "(" + exp.toMiniString() + ")";
    }

    @Override
    public int evaluate(int x, int y, int z) throws CalculatingException {
        return logarithm(exp.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "log" + log + "(" + exp + ")";
    }

    @Override
    public int getPriority() {
        return 3;
    }

    private boolean checkedMultiplyExact(int x, int y) {
        int r = x * y;
        if (x == -1 && y == Integer.MIN_VALUE || y == -1 && x == Integer.MIN_VALUE || y != 0 && r / y != x) {
            return true;
        }
        return false;
    }
}