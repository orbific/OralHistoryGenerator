package uk.me.jamesburt.nanogenmo.outputgeneration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import uk.me.jamesburt.nanogenmo.datastructures.BookMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterOutput;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * This needs to be an interface with implementations of different output formats
 */
public class FileOutputGenerator implements OutputGenerator {

    private static final Logger logger = LoggerFactory.getLogger(FileOutputGenerator.class);

    private static final String fileName = "novel.txt";
    @Override
    public void generate(String bookTitle, List<ChapterOutput> outputChapters) {

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(bookTitle);
            writer.write("\n\n\n");

            for(ChapterOutput chapter: outputChapters) {
                writer.write(chapter.getTitle()+"\n\n\n");
                writer.write(chapter.getOverview()+"\n\n\n");
                writer.write(chapter.getOutputText()+"\n\n\n");
            }

        } catch (IOException ioe) {
            // Don't try to handle - exit and leave to developer to fix
            throw new RuntimeException(ioe);
        }

    }
}
