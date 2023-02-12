public class TokenDefault implements Token {

    @Override
    public boolean symbolType(char sym) {
        return !Character.isWhitespace(sym);
    }
}