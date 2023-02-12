package markup;

import java.util.List;

public class Strong extends AbstractText implements ElementParagraph {

    public Strong(List<ElementParagraph> elements) {
        super(List.copyOf(elements));
    }

    @Override
    void addMarkdown(StringBuilder stringBuilder) {
        stringBuilder.append("__");
    }

    @Override
    void addHtmlTegLeft(StringBuilder stringBuilder) {
        stringBuilder.append("<strong>");
    }

    @Override
    void addHtmlTegRight(StringBuilder stringBuilder) {
        stringBuilder.append("</strong>");
    }
}
