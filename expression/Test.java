package expression;

import expression.exceptions.*;

public class Test {
    public static void main(String[] args) {
        ExpressionParser parser = new ExpressionParser();
        TripleExpression expression = parser.parse("pow10 -1");
        System.out.println(expression.toString());
    }

}
