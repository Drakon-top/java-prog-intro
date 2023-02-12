package expression;

public class Count implements ItemExpression {

    private final ItemExpression exp;

    public Count(ItemExpression object) {
        exp = object;
    }

    @Override
    public String toMiniString() {
        if (exp.getPriority() >= getPriority()) {
            return "count " + exp.toMiniString();
        }
        return "count(" + exp.toMiniString() + ")";

    }

    @Override
    public int evaluate(int x, int y, int z) {
        return count(exp.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "count(" + exp + ")";
    }

    private int count(final int number) {
        String nums = String.format("%32s", Integer.toBinaryString(number)).replaceAll(" ", "0");
        int count = 0;
        for (int i = 0; i < nums.length(); i++){
            if (nums.charAt(i) == '1') {
                count++;
            }
        }
        return count;
    }

    @Override
    public double evaluate(double x) {
        return 0;
    }

    @Override
    public int evaluate(int x) {
        return 0;
    }

    @Override
    public int getPriority() {
        return 3;
    }
}
