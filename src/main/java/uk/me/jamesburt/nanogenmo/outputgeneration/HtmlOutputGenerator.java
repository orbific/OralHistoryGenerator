package uk.me.jamesburt.nanogenmo.outputgeneration;

import org.jsoup.nodes.Entities;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterOutput;

public class HtmlOutputGenerator implements OutputGenerator {

    private static final String fileName = "novel.html";
    @Override
    public void generate(List<ChapterOutput> outputChapters) {

        try (FileWriter writer = new FileWriter(fileName)) {

            writer.write(generateTextAsString(outputChapters));

        } catch (IOException ioe) {
            // Don't try to handle - exit and leave to developer to fix
            throw new RuntimeException(ioe);
        }
    }

    private void appendTextLinesAsHtml(Element element, String text) {
        String[] lines = text.split("\\r?\\n");

        // Wrap each line in <p> tags and append to a StringBuilder
        for (String line : lines) {
            if (!line.trim().isEmpty()) { // Ignore empty lines
                element.append("<p>").append(Entities.escape(line)).append("</p>\n");
            }
        }
    }

    /*
     * The file generation could be a default method on the interface.
     */
    protected String generateTextAsString(List<ChapterOutput> outputChapters) {
        Document doc = Document.createShell("");
        Element body = doc.body();
        body.appendElement("h1").text("The Secret History of Rave");

        for(ChapterOutput chapter: outputChapters) {
            body.appendElement("h2").text(chapter.getTitle());

            // TODO this embeds multiple paragraphs within an italic tag
            Element italicHeader = body.appendElement("i");
            appendTextLinesAsHtml(italicHeader, chapter.getOverview());

            appendTextLinesAsHtml(body, chapter.getOutputText());
        }
        return body.html();
    }
}
