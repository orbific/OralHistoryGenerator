package uk.me.jamesburt.nanogenmo.outputgeneration;

import uk.me.jamesburt.nanogenmo.datastructures.BookMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterOutput;

import java.util.List;

public interface OutputGenerator {
    void generate(BookMetadata metadata, List<ChapterOutput> outputChapters);
}
