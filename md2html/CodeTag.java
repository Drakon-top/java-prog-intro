package md2html;

import java.util.List;

public class CodeTag extends AbstractText implements ElementParagraph {

    public CodeTag(List<ElementParagraph> elements) {
        super(List.copyOf(elements));
    }

    @Override
    public void addMarkdown(StringBuilder stringBuilder) {

    }

    @Override
    public void addHtmlTegLeft(StringBuilder stringBuilder) {
        stringBuilder.append("<code>");
    }

    @Override
    public void addHtmlTegRight(StringBuilder stringBuilder) {
        stringBuilder.append("</code>");
    }
}
