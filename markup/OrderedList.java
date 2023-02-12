package markup;

import java.util.List;

public class OrderedList extends AbstractText implements ElementList {

    public OrderedList(List<ListItem> elements) {
        super(List.copyOf(elements));
    }

    @Override
    void addMarkdown(StringBuilder stringBuilder) {

    }

    @Override
    void addHtmlTegLeft(StringBuilder stringBuilder) {
        stringBuilder.append("<ol>");
    }

    @Override
    void addHtmlTegRight(StringBuilder stringBuilder) {
        stringBuilder.append("</ol>");
    }
}
