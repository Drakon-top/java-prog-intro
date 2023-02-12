package markup;

import java.util.List;

public class Strikeout extends AbstractText implements ElementParagraph {

    public Strikeout(List<ElementParagraph> elements) {
        super(List.copyOf(elements));
    }

    @Override
    void addMarkdown(StringBuilder stringBuilder) {
        stringBuilder.append("~");
    }

    @Override
    void addHtmlTegLeft(StringBuilder stringBuilder) {
        stringBuilder.append("<s>");
    }

    @Override
    void addHtmlTegRight(StringBuilder stringBuilder) {
        stringBuilder.append("</s>");
    }
}
