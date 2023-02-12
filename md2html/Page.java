package md2html;

import java.util.List;

public class Page extends AbstractText implements ElementList {

    public Page(List<ElementParagraph> elements) {
        super(List.copyOf(elements));
    }

    @Override
    public void addMarkdown(StringBuilder stringBuilder) {

    }

    @Override
    public void addHtmlTegLeft(StringBuilder stringBuilder) {

    }

    @Override
    public void addHtmlTegRight(StringBuilder stringBuilder) {

    }

    @Override
    public void toHtml(StringBuilder stringBuilder) {
        for (Mark mark : elements) {
            mark.toHtml(stringBuilder);
            stringBuilder.append("\n");
        }
        if (!stringBuilder.isEmpty()) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
    }
}


