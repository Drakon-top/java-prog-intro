package expression.exceptions;

import expression.ItemExpression;
import expression.Subtract;

public class CheckedSubtract extends Subtract {

    public CheckedSubtract(ItemExpression expr1, ItemExpression expr2) {
        super(expr1, expr2);
    }

    @Override
    public int evaluate(int x, int y, int z) throws CalculatingException {
        return subtractExact(expr1.evaluate(x, y, z), expr2.evaluate(x, y, z));
    }

    private int subtractExact(int x, int y) throws OverflowException {
        int result = x - y;
        if (((x ^ y) & (x ^ result)) < 0) {
            throw new OverflowException();
        }
        return result;
    }
}
