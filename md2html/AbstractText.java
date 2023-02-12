package md2html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AbstractText implements Mark, AddTag {
    public List<Mark> elements;

    public AbstractText(List<Mark> elements) {
        this.elements = new ArrayList<>(elements);
    }

    @Override
    public void toHtml(StringBuilder stringBuilder) {
        addHtmlTegLeft(stringBuilder);
        for (Mark mark : elements) {
            mark.toHtml(stringBuilder);
        }
        addHtmlTegRight(stringBuilder);
    }

    @Override
    public void addHtmlTegLeft(StringBuilder stringBuilder) {

    }

    @Override
    public void addHtmlTegRight(StringBuilder stringBuilder) {

    }

    @Override
    public void addMarkdown(StringBuilder stringBuilder) {

    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        addMarkdown(stringBuilder);
        for (Mark mark : elements) {
            mark.toMarkdown(stringBuilder);
        }
        addMarkdown(stringBuilder);
    }

    public void addElement(Mark elem) {
        elements.add(elem);
    }

}
