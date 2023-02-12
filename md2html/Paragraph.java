package md2html;

import java.util.List;

public class Paragraph extends AbstractText implements ElementList {

    public Paragraph(List<ElementParagraph> elements) {
        super(List.copyOf(elements));
    }

    @Override
    public void addMarkdown(StringBuilder stringBuilder) {

    }

    @Override
    public void addHtmlTegLeft(StringBuilder stringBuilder) {
        stringBuilder.append("<p>");
    }

    @Override
    public void addHtmlTegRight(StringBuilder stringBuilder) {
        stringBuilder.append("</p>");
    }
}
