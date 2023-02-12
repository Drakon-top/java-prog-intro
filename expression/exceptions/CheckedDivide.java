package expression.exceptions;

import expression.Divide;
import expression.ItemExpression;
import expression.exceptions.CalculatingException;
import expression.exceptions.DivisionByZero;
import expression.exceptions.OverflowException;

public class CheckedDivide extends Divide {

    public CheckedDivide(ItemExpression expr1, ItemExpression expr2) {
        super(expr1, expr2);
    }

    @Override
    public int evaluate(int x, int y, int z) throws CalculatingException {
        return divideChecked(expr1.evaluate(x, y, z), expr2.evaluate(x, y, z));
    }

    private int divideChecked(int x, int y) {
        if (y == 0) {
            throw new DivisionByZero();
        }
        if (y == -1 && x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
        return x / y;
    }
}
