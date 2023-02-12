package markup;

public interface Mark {

    void toMarkdown(StringBuilder stringBuilder);

    void toHtml(StringBuilder stringBuilder);
}
