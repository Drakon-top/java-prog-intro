package expression.exceptions;

import expression.ItemExpression;
import expression.Multiply;
import expression.exceptions.CalculatingException;
import expression.exceptions.OverflowException;

public class CheckedMultiply extends Multiply {

    public CheckedMultiply(ItemExpression expr1, ItemExpression expr2) {
        super(expr1, expr2);
    }

    @Override
    public int evaluate(int x, int y, int z) throws CalculatingException {
        return multiplyExact(expr1.evaluate(x, y, z), expr2.evaluate(x, y, z));
    }

    private int multiplyExact(int x, int y) throws OverflowException {
        int r = x * y;
        if (x == -1 && y == Integer.MIN_VALUE || y == -1 && x == Integer.MIN_VALUE || y != 0 && r / y != x) {
            throw new OverflowException();
        }
        return r;
    }
}
