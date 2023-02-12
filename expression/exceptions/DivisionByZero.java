package expression.exceptions;

public class DivisionByZero extends CalculatingException {
    public DivisionByZero(String message) {
        super(message);
    }

    public DivisionByZero() {}
}
