package expression;

public class Set extends BinOperations {

    public Set(ItemExpression expr1, ItemExpression expr2) {
        super(expr1, expr2);
        commutativity = false;
    }


    @Override
    protected String signExpression() {
        return " set ";
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return set(expr1.evaluate(x, y, z), expr2.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "(" + expr1 + " set " + expr2 + ")";
    }

    @Override
    public double evaluate(double x) {
        return 0;
    }

    @Override
    public int evaluate(int x) {
        return set(expr1.evaluate(x), expr2.evaluate(x));
    }

    private int set(final int num1, final int num2) {
        return (num1 | (1 << num2));
    }

    @Override
    public int getPriority() {
        return 0;
    }
}