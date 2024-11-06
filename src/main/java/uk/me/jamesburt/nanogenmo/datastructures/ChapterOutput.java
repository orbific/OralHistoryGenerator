package uk.me.jamesburt.nanogenmo.datastructures;

import java.util.ArrayList;
import java.util.List;

public class ChapterOutput {
    private final String overview;
    private final List<String> bodyText;

    public ChapterOutput(String overview, String initial) {
        this.overview = overview;
        bodyText = new ArrayList<>();
        bodyText.add(initial);
    }

    public void addText(String s) {
        bodyText.add(s);
    }

    public String getOverview() {
        return overview;
    }

    public List<String> getBodyText() {
        return bodyText;
    }

    public int getChapterLength() {
        int totalSize = 0;
        for(String output: bodyText) {
            totalSize+=bodyText.size();
        }
        return totalSize;
    }

    public String getOutputText() {
        StringBuilder sb = new StringBuilder();
        for(String output: bodyText) {
            sb.append(output);
            sb.append(":\n");
        }
        return sb.toString();
    }
}
