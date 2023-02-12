public class TokenInput implements Token {

    @Override
    public boolean symbolType(char sym) {
        return Character.getType(sym) == Character.DASH_PUNCTUATION || sym == '\'' || Character.isLetter(sym);
    }
}