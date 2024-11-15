package uk.me.jamesburt.nanogenmo.outputgeneration;

import uk.me.jamesburt.nanogenmo.datastructures.ChapterOutput;

import java.util.List;

public interface OutputGenerator {
    void generate(String bookTitle, List<ChapterOutput> outputChapters);
}
