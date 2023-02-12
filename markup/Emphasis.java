package markup;

import java.util.List;

public class Emphasis extends AbstractText implements ElementParagraph {

    public Emphasis(List<ElementParagraph> elements) {
        super(List.copyOf(elements));
    }

    @Override
    void addMarkdown(StringBuilder stringBuilder) {
        stringBuilder.append("*");
    }

    @Override
    void addHtmlTegLeft(StringBuilder stringBuilder) {
        stringBuilder.append("<em>");
    }

    @Override
    void addHtmlTegRight(StringBuilder stringBuilder) {
        stringBuilder.append("</em>");
    }
}
