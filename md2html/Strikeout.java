package md2html;

import java.util.List;

public class Strikeout extends AbstractText implements ElementParagraph {

    public Strikeout(List<ElementParagraph> elements) {
        super(List.copyOf(elements));
    }

    @Override
    public void addMarkdown(StringBuilder stringBuilder) {
        stringBuilder.append("~");
    }

    @Override
    public void addHtmlTegLeft(StringBuilder stringBuilder) {
        stringBuilder.append("<s>");
    }

    @Override
    public void addHtmlTegRight(StringBuilder stringBuilder) {
        stringBuilder.append("</s>");
    }
}
