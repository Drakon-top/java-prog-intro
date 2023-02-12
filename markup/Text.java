package markup;

public class Text implements ElementParagraph {
    public String text;

    Text(String text) {
        this.text = text;
    }
    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        stringBuilder.append(text);
    }

    @Override
    public void toHtml(StringBuilder stringBuilder) {
        stringBuilder.append(text);
    }
}
