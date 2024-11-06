package uk.me.jamesburt.nanogenmo.outputgeneration;

import org.jsoup.nodes.Entities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.me.jamesburt.nanogenmo.datastructures.BookMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterData;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterOutput;

public class HtmlOutputGenerator implements OutputGenerator {

    private static final Logger logger = LoggerFactory.getLogger(HtmlOutputGenerator.class);

    private static final String fileName = "novel.html";
    @Override
    public void generate(BookMetadata metadata, List<ChapterOutput> outputChapters) {
        if(outputChapters.size() != metadata.chapterMetadata().length) {
            throw new RuntimeException("Metadata contains different number of chapters to the output text - stopping generation");
        }

        try (FileWriter writer = new FileWriter(fileName)) {

            writer.write(generateTextAsString(metadata,outputChapters));

        } catch (IOException ioe) {
            // Don't try to handle - exit and leave to developer to fix
            throw new RuntimeException(ioe);
        }
    }

    private void appendTextLinesAsHtml(Element element, String text) {
        String[] lines = text.split("\\r?\\n");

        // Wrap each line in <p> tags and append to a StringBuilder
        StringBuilder htmlBuilder = new StringBuilder();
        for (String line : lines) {
            if (!line.trim().isEmpty()) { // Ignore empty lines
                element.append("<p>").append(Entities.escape(line)).append("</p>\n");
            }
        }
    }

    /*
     * The file generation could be a default method on the interface.
     */
    protected String generateTextAsString(BookMetadata metadata, List<ChapterOutput> outputChapters) {
        Document doc = Document.createShell("");
        Element body = doc.body();
        body.appendElement("h1").text("The Secret History of Rave");
        body.appendElement("p").text("This is an HTML page generated using JSoup.");

        // TODO fix structures to avoid this issue of assuming both items are the same length
        for(int i=0; i<metadata.chapterMetadata().length;i++) {
            body.appendElement("h2").text(metadata.chapterMetadata()[i].chapterTitle());

            // TODO this appends paragraphs within an italic tag
            Element italicHeader = body.appendElement("i");
            appendTextLinesAsHtml(italicHeader, outputChapters.get(i).getOverview());

            appendTextLinesAsHtml(body, outputChapters.get(i).getOutputText());
        }
        return body.html();
    }
}
