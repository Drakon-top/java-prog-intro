package expression;

import java.util.Objects;

public class Const implements ItemExpression {
    private final double number;
    private final boolean doubleThis;

    public Const(int number) {
        this.number = number;
        doubleThis = false;
    }
    public Const(double number) {
        this.number = number;
        doubleThis = true;
    }

    @Override
    public int evaluate(int value) {
        return (int) number;
    }

    @Override
    public String toString() {
        if (doubleThis) {
            return String.valueOf(number);
        }
        return String.valueOf((int) number);
    }

    @Override
    public String toMiniString() {
        if (doubleThis) {
            return String.valueOf(number);
        }
        return String.valueOf((int) number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Const aConst = (Const) o;
        return number == aConst.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public double evaluate(double value) {
        return number;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return (int) number;
    }

    @Override
    public int getPriority() {
        return 4;
    }
}
