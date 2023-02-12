package md2html;

import java.util.List;

public class Emphasis extends AbstractText implements ElementParagraph {

    public Emphasis(List<ElementParagraph> elements) {
        super(List.copyOf(elements));
    }

    @Override
    public void addMarkdown(StringBuilder stringBuilder) {
        stringBuilder.append("*");
    }

    @Override
    public void addHtmlTegLeft(StringBuilder stringBuilder) {
        stringBuilder.append("<em>");
    }

    @Override
    public void addHtmlTegRight(StringBuilder stringBuilder) {
        stringBuilder.append("</em>");
    }
}
