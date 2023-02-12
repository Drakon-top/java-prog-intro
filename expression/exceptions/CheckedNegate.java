package expression.exceptions;

import expression.ItemExpression;
import expression.Negate;
import expression.exceptions.CalculatingException;
import expression.exceptions.OverflowException;

public class CheckedNegate extends Negate {

    public CheckedNegate(ItemExpression object) {
        super(object);
    }

    @Override
    public int evaluate(int x, int y, int z) throws CalculatingException {
        return negateExact(exp.evaluate(x, y, z));
    }

    private int negateExact(int x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
        return -x;
    }
}
