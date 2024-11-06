package uk.me.jamesburt.nanogenmo.outputgeneration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.me.jamesburt.nanogenmo.datastructures.BookMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterData;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterOutput;

import java.util.List;

/**
 * This needs to be an interface with implementations of different output formats
 */
public class CommandlineOutputGenerator implements OutputGenerator {

    private static final Logger logger = LoggerFactory.getLogger(CommandlineOutputGenerator.class);


    @Override
    public void generate(BookMetadata metadata, List<ChapterOutput> outputChapters) {

        System.out.println("\n*************\n");

        // TODO fix structures to avoid this issue of assuming both items are the same length
        for(int i=0; i<metadata.chapterMetadata().length;i++) {
            System.out.println(metadata.chapterMetadata()[i].chapterTitle()+"\n");
            System.out.println(outputChapters.get(i).getOverview()+"\n");
            System.out.println(outputChapters.get(i).getOutputText()+"\n");
        }
    }
}
