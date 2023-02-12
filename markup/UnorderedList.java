package markup;

import java.util.List;

public class UnorderedList extends AbstractText implements ElementList {
    public UnorderedList(List<ListItem> elements) {
        super(List.copyOf(elements));
    }

    @Override
    void addMarkdown(StringBuilder stringBuilder) {

    }

    @Override
    void addHtmlTegLeft(StringBuilder stringBuilder) {
        stringBuilder.append("<ul>");
    }

    @Override
    void addHtmlTegRight(StringBuilder stringBuilder) {
        stringBuilder.append("</ul>");
    }
}
