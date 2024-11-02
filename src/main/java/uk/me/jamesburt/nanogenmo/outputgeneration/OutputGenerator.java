package uk.me.jamesburt.nanogenmo.outputgeneration;

import uk.me.jamesburt.nanogenmo.datastructures.BookMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterText;

import java.util.List;

public interface OutputGenerator {
    void generate(BookMetadata metadata, List<ChapterText> outputChapters);
}
