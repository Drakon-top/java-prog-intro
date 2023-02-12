package md2html;

import java.util.List;

public class Heading extends AbstractText implements Mark {

    private int numberHead;

    public Heading(List<Mark> elements, int numberH) {
        super(List.copyOf(elements));
        numberHead = numberH;
    }

    @Override
    public void addMarkdown(StringBuilder stringBuilder) {

    }

    @Override
    public void addHtmlTegLeft(StringBuilder stringBuilder) {
        stringBuilder.append("<h").append(numberHead).append(">");
    }

    @Override
    public void addHtmlTegRight(StringBuilder stringBuilder) {
        stringBuilder.append("</h").append(numberHead).append(">");
    }
}