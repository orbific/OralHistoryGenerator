package uk.me.jamesburt.nanogenmo.outputgeneration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.me.jamesburt.nanogenmo.datastructures.BookMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterOutput;

import java.util.List;

/**
 * This needs to be an interface with implementations of different output formats
 */
public class CommandlineOutputGenerator implements OutputGenerator {

    private static final Logger logger = LoggerFactory.getLogger(CommandlineOutputGenerator.class);

    @Override
    public void generate(List<ChapterOutput> outputChapters) {

        System.out.println("\n*************\n");

        for(ChapterOutput chapter: outputChapters) {
            System.out.println(chapter.getTitle()+"\n");
            System.out.println(chapter.getOverview()+"\n");
            System.out.println(chapter.getOutputText()+"\n");
        }
    }
}
