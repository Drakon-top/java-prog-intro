package expression.exceptions;

import expression.*;
import expression.common.Op;
import expression.exceptions.*;
import expression.parser.BaseParser;
import expression.parser.StringCharSource;
import expression.parser.TripleParser;

import java.util.ArrayList;
import java.util.List;

public class ExpressionParser extends BaseParser implements TripleParser {
    private List<ExpressionElement> listParseExpressionElement = new ArrayList<>();
    private int posExpressionElement = 0;

    private ExpressionElement lastSymbol = null;

    private ExpressionElement parseValueElement() throws IllegalArgumentException {
        if (take('-')) {
            if (between('0', '9') && (lastSymbol == null || lastElementIsOperation(lastSymbol))) {
                return new ExpressionConst(OperationType.CONST, parseInteger(true));
            } else if (lastSymbol == null || lastElementIsOperation(lastSymbol)) {
                return new ExpressionElement(OperationType.NEGATE);
            }
            return new ExpressionElement(OperationType.SUBTRACT);
        } else if (between('0', '9')) {
            return new ExpressionConst(OperationType.CONST, parseInteger(false));
        } else if (take('*')) {
            return new ExpressionElement(OperationType.MULTIPLY);
        } else if (take('/')) {
            return new ExpressionElement(OperationType.DIVIDE);
        } else if (take('+')) {
            return new ExpressionElement(OperationType.ADD);
        } else if (take('x')) {
            return new ExpressionVariable(OperationType.VARIABLE, "x");
        } else if (take('y')) {
            return new ExpressionVariable(OperationType.VARIABLE, "y");
        } else if (take('z')) {
            return new ExpressionVariable(OperationType.VARIABLE, "z");
        } else if (take('(')) {
            return new ExpressionElement(OperationType.BRACKETL);
        } else if (take(')')) {
            return new ExpressionElement(OperationType.BRACKETR);
        } else if (take('c')) {
            if (take('l')) {
                expect("ear");
                return new ExpressionElement(OperationType.CLEAR);
            } else if (take('o')) {
                expect("unt");
                if (checkedNextIsOperandSymbol()) {
                    throw error("Error operand after count without space");
                }
                return new ExpressionElement(OperationType.COUNT);
            }
            throw error("Unsupported input: c" + take());
        } else if (take('s')) {
            expect("et");
            return new ExpressionElement(OperationType.SET);
        } else if (take('l')) {
            expect("og");
            ExpressionUnary exp = new ExpressionUnary(OperationType.LOG, parseInteger(false));
            if (checkedNextIsOperandSymbol()) {
                throw error("Error operand after log without space");
            }
            return exp;
        } else if (take('p')) {
            expect("ow");
            ExpressionUnary exp = new ExpressionUnary(OperationType.POW, parseInteger(false));
            if (checkedNextIsOperandSymbol()) {
                throw error("Error operand after pow without space");
            }
            return exp;
        } else {
            throw error("Unsupported input: " + take());
        }
    }


    private boolean checkedNextIsOperandSymbol() {
        return between('0', '9') || take('x') || take('y') || take('z');
    }

    private boolean lastElementIsOperation(ExpressionElement element) {
//        return element.type != OperationType.VARIABLE && element.type != OperationType.CONST;
        return element.type == OperationType.NEGATE || element.type == OperationType.BRACKETL
                || element.type == OperationType.MULTIPLY || element.type == OperationType.ADD
                || element.type == OperationType.DIVIDE || element.type == OperationType.SUBTRACT
                || element.type == OperationType.COUNT || element.type == OperationType.SET
                || element.type == OperationType.CLEAR || element.type == OperationType.POW
                || element.type == OperationType.LOG;
    }

    private Integer parseInteger(boolean minus) throws IllegalArgumentException {
        final StringBuilder number = new StringBuilder();
        if (minus) {
            number.append('-');
        }
        if (take('0')) {
            return 0;
        }

        while (between('0', '9')) {
            number.append(take());
        }
        if (take('s')) {
            throw error("Invalid number in set: " + number);
        } else if (take('c')) {
            throw error("Invalid number in clear: " + number);
        }


        try {
            return Integer.parseInt(number.toString());
        } catch (NumberFormatException e) {
            throw error("Invalid number: " + number);
        }
    }

    private void skipWhiteSpace() {
        while (isWhiteSpace()) {

        }

    }

    private void parseElementOperation() throws IllegalArgumentException {
        take();
        listParseExpressionElement = new ArrayList<>();
        posExpressionElement = 0;
        lastSymbol = null;
        while (!eof()) {
            skipWhiteSpace();
            lastSymbol = parseValueElement();
            listParseExpressionElement.add(lastSymbol);
            skipWhiteSpace();
        }
    }

    @Override
    public TripleExpression parse(String expression) throws IllegalArgumentException {
//        System.out.println(expression + " ------exp");
        source = new StringCharSource(expression);
        parseElementOperation();
        checkEof();
        final TripleExpression result = getExpression();
        if (listParseExpressionElement.size() != posExpressionElement) {
            throw error("Unfinished expression or expression syntactically incorrect");
        }

        return result;
    }

    /// variable|const - priority 4
    /// -|count|log|pow priority 3
    /// *|/ - priority 2
    /// +|- priority 1
    /// set|clear - priority 0

    private TripleExpression getExpression() throws IllegalArgumentException {
        ExpressionElement now = getNext();
        return getSetClear(now);
    }

    private TripleExpression getSetClear(ExpressionElement element) throws IllegalArgumentException {
        TripleExpression expression = getAddSubtract(element);
        while (true) {
            if (endList()) {
                return expression;
            }

            ExpressionElement nowElement = getNext();
            switch (nowElement.type) {
                case SET -> expression = new Set((ItemExpression) expression, (ItemExpression) getAddSubtract(getNext()));
                case CLEAR -> expression = new Clear((ItemExpression) expression, (ItemExpression) getAddSubtract(getNext()));
                default -> {
                    back();
                    return expression;
                }
            }
        }
    }

    private TripleExpression getAddSubtract(ExpressionElement element) throws IllegalArgumentException {
        TripleExpression expression = getMultiplyDivide(element);
        while (true) {
            if (endList()) {
                return expression;
            }

            ExpressionElement nowElement = getNext();
            switch (nowElement.type) {
                case ADD -> expression = new CheckedAdd((ItemExpression) expression, (ItemExpression) getMultiplyDivide(getNext()));
                case SUBTRACT -> expression = new CheckedSubtract((ItemExpression) expression, (ItemExpression) getMultiplyDivide(getNext()));
                default -> {
                    back();
                    return expression;
                }
            }
        }
    }

    private TripleExpression getMultiplyDivide(ExpressionElement element) throws IllegalArgumentException {
        TripleExpression expression = getUnary(element);
        while (true) {
            if (endList()) {
                return expression;
            }

            ExpressionElement nowElement = getNext();
            switch (nowElement.type) {
                case MULTIPLY -> expression = new CheckedMultiply((ItemExpression) expression, (ItemExpression) getUnary(getNext()));
                case DIVIDE -> expression = new CheckedDivide((ItemExpression) expression, (ItemExpression) getUnary(getNext()));
                default -> {
                    back();
                    return expression;
                }
            }
        }
    }

    private TripleExpression getUnary(ExpressionElement element) throws IllegalArgumentException {
        return switch (element.type) {
            case CONST -> new Const(((ExpressionConst) element).getNumber());
            case VARIABLE -> new Variable(((ExpressionVariable) element).getVariable());
            case BRACKETL -> {
                TripleExpression expression = getExpression();
                ExpressionElement expressionElement = getNext();
                if (expressionElement.type != OperationType.BRACKETR) {
                    throw error("Error, incorrect expression. Expect ) actual " + expressionElement.type);
                }
                yield expression;
            }
            case COUNT -> new Count((ItemExpression) getUnary(getNext()));
            case NEGATE -> new CheckedNegate((ItemExpression) getUnary(getNext()));
            case LOG -> new CheckedLogarithm((ItemExpression) getUnary(getNext()), ((ExpressionUnary) element).getNumber());
            case POW -> new CheckedDegree((ItemExpression) getUnary(getNext()), ((ExpressionUnary) element).getNumber());
            default -> throw error("Error, incorrect expression " + element.type);
        };
    }

    private ExpressionElement getNext() {
        if (posExpressionElement >= listParseExpressionElement.size()) {
            throw error("Unfinished expression");
        }
        return listParseExpressionElement.get(posExpressionElement++);
    }

    private void back() {
        posExpressionElement--;
    }

    private boolean endList() {
        return posExpressionElement >= listParseExpressionElement.size() - 1;
    }


    private static class ExpressionElement {
        public final OperationType type;

        public ExpressionElement(OperationType type) {
            this.type = type;
        }
    }

    private static class ExpressionConst extends ExpressionElement {
        private final int number;

        private ExpressionConst(OperationType type, int number) {
            super(type);
            this.number = number;
        }

        public int getNumber() {
            return number;
        }
    }

    private static class ExpressionVariable extends ExpressionElement {
        private final String variable;

        private ExpressionVariable(OperationType type, String variable) {
            super(type);
            this.variable = variable;
        }

        public String getVariable() {
            return variable;
        }
    }

    private static class ExpressionUnary extends ExpressionElement {
        private final int number;

        private ExpressionUnary(OperationType type, int number) {
            super(type);
            this.number = number;
        }

        public int getNumber() {
            return number;
        }
    }

    private enum OperationType {
        BRACKETR, BRACKETL, ADD, SUBTRACT, MULTIPLY, DIVIDE, CONST, VARIABLE, NEGATE, COUNT, SET, CLEAR, LOG, POW
    }
}
