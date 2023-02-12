package md2html;

import java.util.List;

public class Strong extends AbstractText implements ElementParagraph {

    public Strong(List<ElementParagraph> elements) {
        super(List.copyOf(elements));
    }

    @Override
    public void addMarkdown(StringBuilder stringBuilder) {
        stringBuilder.append("__");
    }

    @Override
    public void addHtmlTegLeft(StringBuilder stringBuilder) {
        stringBuilder.append("<strong>");
    }

    @Override
    public void addHtmlTegRight(StringBuilder stringBuilder) {
        stringBuilder.append("</strong>");
    }
}
