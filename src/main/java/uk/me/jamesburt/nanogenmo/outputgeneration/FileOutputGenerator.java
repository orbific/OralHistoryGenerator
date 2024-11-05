package uk.me.jamesburt.nanogenmo.outputgeneration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.me.jamesburt.nanogenmo.datastructures.BookMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterText;
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
    public void generate(BookMetadata metadata, List<ChapterText> outputChapters) {
        if(outputChapters.size() != metadata.chapterMetadata().length) {
            throw new RuntimeException("Metadata contains different number of chapters to the output text - stopping generation");
        }

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("The Secret History of Rave");
            writer.write("\n\n\n");

            for(int i=0; i<metadata.chapterMetadata().length;i++) {
                writer.write(metadata.chapterMetadata()[i].chapterTitle()+"\n\n\n");
                writer.write(outputChapters.get(i).editorialOverview()+"\n\n\n");
                writer.write(outputChapters.get(i).text()+"\n\n\n");
            }

        } catch (IOException ioe) {
            // Don't try to handle - exit and leave to developer to fix
            throw new RuntimeException(ioe);
        }

    }
}
