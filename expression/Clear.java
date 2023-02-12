package expression;

public class Clear extends BinOperations {

    public Clear(ItemExpression expr1, ItemExpression expr2) {
        super(expr1, expr2);
        commutativity = false;
    }

    @Override
    protected String signExpression() {
        return " clear ";
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return clear(expr1.evaluate(x, y, z), expr2.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "(" + expr1.toString() + " clear " + expr2.toString() + ")";
    }

    @Override
    public double evaluate(double x) {
        return 0;
    }

    @Override
    public int evaluate(int x) {
        return clear(expr1.evaluate(x), expr2.evaluate(x));
    }

    private int clear(final int num1, final int num2) {
        return (num1 & ~(1 << num2));
    }

    @Override
    public int getPriority() {
        return 0;
    }
}