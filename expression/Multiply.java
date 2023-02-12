package expression;

public class Multiply extends BinOperations implements Expression, TripleExpression, DoubleExpression {

    public Multiply(ItemExpression expr1, ItemExpression expr2) {
        super(expr1, expr2);
    }

    public Multiply(Expression expr1, Expression expr2) {
        super((ItemExpression) expr1, (ItemExpression) expr2);
    }

    @Override
    public String toString() {
        return super.toString("*");
    }


    @Override
    public double evaluate(double value) {
        return expr1.evaluate(value) * expr2.evaluate(value);
    }

    @Override
    public int evaluate(int value) {
        return expr1.evaluate(value) * expr2.evaluate(value);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return expr1.evaluate(x, y ,z) * expr2.evaluate(x, y, z);
    }

    @Override
    protected String signExpression() {
        return " * ";
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
