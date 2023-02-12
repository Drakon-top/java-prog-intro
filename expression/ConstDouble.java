package expression;

import java.util.Objects;

public class ConstDouble implements DoubleExpression {
    private final double number;

    public ConstDouble(double number) {
        this.number = number;
    }

    @Override
    public double evaluate(double value) {
        return number;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }

    @Override
    public String toMiniString() {
        return String.valueOf(number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConstDouble aConst = (ConstDouble) o;
        return number == aConst.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
