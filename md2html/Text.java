package md2html;

public class Text implements ElementParagraph {
    private StringBuilder text = new StringBuilder();

    Text(String tex) {
        text.append(tex);
    }

    Text(StringBuilder tex) {
        text.append(tex);
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        stringBuilder.append(text);
    }

    @Override
    public void toHtml(StringBuilder stringBuilder) {
        stringBuilder.append(text.toString());
    }
}
