package expression.exceptions;

import expression.Add;
import expression.ItemExpression;
import expression.exceptions.CalculatingException;
import expression.exceptions.OverflowException;

public class CheckedAdd extends Add {
    public CheckedAdd(ItemExpression expr1, ItemExpression expr2) {
        super(expr1, expr2);
    }

    @Override
    public int evaluate(int x, int y, int z) throws CalculatingException {
        return addExact(expr1.evaluate(x, y, z), expr2.evaluate(x, y, z));
    }

    private int addExact(int x, int y) throws OverflowException {
        int result = x + y;
        if (((x ^ result) & (y ^ result)) < 0) {
            throw new OverflowException();
        }
        return result;
    }
}
