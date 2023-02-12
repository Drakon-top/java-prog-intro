package markup;

import java.util.List;

public class ListItem extends AbstractText {

    public ListItem(List<ElementList> elements) {
        super(List.copyOf(elements));
    }

    @Override
    void addMarkdown(StringBuilder stringBuilder) {

    }

    @Override
    void addHtmlTegLeft(StringBuilder stringBuilder) {
        stringBuilder.append("<li>");
    }

    @Override
    void addHtmlTegRight(StringBuilder stringBuilder) {
        stringBuilder.append("</li>");
    }
}
