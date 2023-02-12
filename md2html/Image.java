package md2html;

import java.util.List;

public class Image implements Mark {

    private final String alt;
    private final String src;

    public Image(String alt, String src) {
        this.alt = alt;
        this.src = src;
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {

    }

    @Override
    public void toHtml(StringBuilder stringBuilder) {
        stringBuilder.append("<img alt='").append(alt).append("' src='").append(src).append("'>");
    }
}
