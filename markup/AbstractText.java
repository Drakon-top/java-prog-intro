package markup;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractText implements Mark {
    protected List<Mark> elements;

    public AbstractText(List<Mark> elements) {
        this.elements = elements;
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
    public void toMarkdown(StringBuilder stringBuilder) {
        addMarkdown(stringBuilder);
        for (Mark mark : elements) {
            mark.toMarkdown(stringBuilder);
        }
        addMarkdown(stringBuilder);
    }

    abstract void addMarkdown(StringBuilder stringBuilder) ;

    abstract void addHtmlTegLeft(StringBuilder stringBuilder) ;

    abstract void addHtmlTegRight(StringBuilder stringBuilder) ;


}
