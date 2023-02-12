package expression;


import java.util.Objects;

public abstract class BinOperations implements ItemExpression {
    protected ItemExpression expr1;
    protected ItemExpression expr2;
    public boolean commutativity = true;

    public BinOperations(ItemExpression expr1, ItemExpression expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }


    public String toString(String token) {
        return "(" + expr1.toString() + ' ' + token + ' ' + expr2.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinOperations that = (BinOperations) o;
        return Objects.equals(expr1, that.expr1) && Objects.equals(expr2, that.expr2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expr1, expr2, getClass());
    }

    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        if (expr1.getPriority() < getPriority()) {
            sb.append("(").append(expr1.toMiniString()).append(")");
        } else {
            sb.append(expr1.toMiniString());
        }
        sb.append(signExpression());
        if (expr2.getPriority() < getPriority() || (expr2.getPriority() <= getPriority() && !commutativity)
                || (expr2 instanceof Divide && this instanceof Multiply)) {
            sb.append("(").append(expr2.toMiniString()).append(")");
        } else {
            sb.append(expr2.toMiniString());
        }

        return sb.toString();
    }

    protected abstract String signExpression();
}
