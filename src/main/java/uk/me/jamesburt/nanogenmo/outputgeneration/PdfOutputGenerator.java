package uk.me.jamesburt.nanogenmo.outputgeneration;

import com.itextpdf.html2pdf.HtmlConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import uk.me.jamesburt.nanogenmo.datastructures.BookMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterOutput;

import java.io.*;
import java.util.List;

public class PdfOutputGenerator implements OutputGenerator {

    private static final Logger logger = LoggerFactory.getLogger(PdfOutputGenerator.class);

    private static final String fileName = "novel.pdf";

    private final HtmlOutputGenerator htmlOutputGenerator;

    @Autowired
    public PdfOutputGenerator(HtmlOutputGenerator htmlOutputGenerator) {
        this.htmlOutputGenerator = htmlOutputGenerator;
    }

    @Override
    public void generate(String bookTitle, List<ChapterOutput> outputChapters) {

        String htmlContent = htmlOutputGenerator.generateTextAsString(bookTitle, outputChapters);
        try (OutputStream outputStream = new FileOutputStream(fileName)) {
            HtmlConverter.convertToPdf(htmlContent, outputStream);
            System.out.println("PDF created successfully at " + fileName);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    public void generate(String text) {
        try (OutputStream outputStream = new FileOutputStream(fileName)) {
            HtmlConverter.convertToPdf(text, outputStream);
            System.out.println("PDF created successfully at " + fileName);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
