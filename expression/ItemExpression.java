package expression;

public interface ItemExpression extends Expression, DoubleExpression, TripleExpression {
    int getPriority();

}
