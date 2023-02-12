package expression.parser;

import expression.TripleExpression;

public class BaseParser implements TripleParser {
    public static final char END = 0;
    protected CharSource source;
    private char ch;

    public char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    protected boolean isWhiteSpace() {
        if (Character.isWhitespace(ch)) {
            take();
            return true;
        } else {
            return false;
        }
    }
    protected boolean take(char expect) {
        if (ch == expect) {
            take();
            return true;
        } else {
            return false;
        }
    }

    protected void expect(String chars) {
        for (char ch : chars.toCharArray()) {
            expect(ch);
        }
    }

    protected void expect(char expected) {
        if (!take(expected)) {
            throw source.error("Expected '" + expected + "', found '" + ch + "'");
        }
    }

    @Override
    public TripleExpression parse(String expression) {
        source = new StringCharSource(expression);
        take();
        return null;
    }

    protected void checkEof() {
        if (!eof()) {
            throw error("Expected EOF, found " + take());
        }
    }

    protected boolean eof() {
        return ch == END;
    }

    protected boolean between(char min, char max) {
        return min <= ch && ch <= max;
    }

    protected IllegalArgumentException error(String message) {
        return source.error(message);
    }
}
