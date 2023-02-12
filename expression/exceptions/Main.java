package expression.exceptions;

import expression.TripleExpression;

public class Main {
    public static void main(String[] args) {
        ExpressionParser parser = new ExpressionParser();
        try {
            TripleExpression expression = parser.parse("1000000*x*x*x*x*x/(x-1)");
            for (int i = 0; i < 11; i++) {
                try {
                    System.out.println(i + " " + expression.evaluate(i, 0, 0));
                } catch (DivisionByZero e) {
                    System.out.println("division by zero");
                } catch (OverflowException e) {
                    System.out.println("overflow");
                } catch (PowerFunctionException e) {
                    System.out.println("error in calculating the degree " + e.getMessage());
                } catch (CalculatingException e) {
                    System.out.println("error calculating the expression " + e.getMessage());
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}