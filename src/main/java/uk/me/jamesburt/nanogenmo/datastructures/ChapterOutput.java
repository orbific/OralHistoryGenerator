package uk.me.jamesburt.nanogenmo.datastructures;

import java.util.ArrayList;
import java.util.List;

public class ChapterOutput {
    private final String title;

    private final String overview;
    private final List<String> bodyText;

    public ChapterOutput(String title, String overview, String initial) {
        this.title = title;
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

    public int getChapterLength() {
        int wordCount = 0;
        for(String output: bodyText) {
            wordCount += output.split("\\s+").length;
        }
        return wordCount;
    }

    public String getOutputText() {
        StringBuilder sb = new StringBuilder();
        for(String output: bodyText) {
            sb.append(output);
            sb.append("\n");
        }
        return sb.toString();
    }

    public String getTitle() {
        return title;
    }
}
