package markup;

import java.util.List;

public class Paragraph extends AbstractText implements ElementList {


    public Paragraph(List<ElementParagraph> elements) {
        super(List.copyOf(elements));
    }

    @Override
    void addMarkdown(StringBuilder stringBuilder) {

    }

    @Override
    void addHtmlTegLeft(StringBuilder stringBuilder) {

    }

    @Override
    void addHtmlTegRight(StringBuilder stringBuilder) {

    }
}
