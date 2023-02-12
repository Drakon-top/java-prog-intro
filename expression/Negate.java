package expression;


public class Negate implements ItemExpression {

    protected final ItemExpression exp;

    public Negate(ItemExpression object) {
        exp = object;
    }


    @Override
    public double evaluate(double x) {
        return -exp.evaluate(x);
    }

    @Override
    public int evaluate(int x) {
        return -exp.evaluate(x);
    }

    @Override
    public String toMiniString() {
        if (exp.getPriority() >= getPriority()) {
            return "- " + exp.toMiniString();
        }
        return "-(" + exp.toMiniString() + ")";
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return -exp.evaluate(x, y, z);
    }

    @Override
    public String toString() {
        return "-(" + exp + ")";
    }

    @Override
    public int getPriority() {
        return 3;
    }
}
