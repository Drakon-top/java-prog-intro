package expression;

public class Subtract extends BinOperations {

    public Subtract(ItemExpression expr1, ItemExpression expr2) {
        super(expr1, expr2);
        commutativity = false;
    }

    public Subtract(Expression expr1, Expression expr2) {
        super((ItemExpression) expr1, (ItemExpression) expr2);
    }

    @Override
    public String toString() {
        return super.toString("-");
    }

    @Override
    protected String signExpression() {
        return " - ";
    }

    @Override
    public double evaluate(double value) {
        return expr1.evaluate(value) - expr2.evaluate(value);
    }

    @Override
    public int evaluate(int value) {
        return expr1.evaluate(value) - expr2.evaluate(value);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return expr1.evaluate(x, y ,z) - expr2.evaluate(x, y, z);
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
