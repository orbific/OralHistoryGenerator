package uk.me.jamesburt.nanogenmo.textbuilders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import uk.me.jamesburt.nanogenmo.Utilities;
import uk.me.jamesburt.nanogenmo.datastructures.BookMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.CastMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterOutput;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterResponseData;
import uk.me.jamesburt.nanogenmo.outputgeneration.OutputGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookBuilder {

    private static final Logger logger = LoggerFactory.getLogger(BookBuilder.class);

    private final OpenAiChatModel aiClient;

    @Autowired
    OutputGenerator outputGenerator;

    @Autowired
    ChapterBuilder chapterBuilder;

    @Value("classpath:/prompts/generate-synopsis.st")
    private Resource generateOverview;

    @Value("classpath:/prompts/generate-cast.st")
    private Resource generateCast;

    @Value("${bookgenerator.chapterCount}")
    private String chapterCount;

    @Value("${bookgenerator.wordsPerChapter}")
    private Integer wordsPerChapter;

    public BookBuilder(OpenAiChatModel aiClient) {
        this.aiClient = aiClient;
    }

    public void generateBook() {

        BookMetadata bookOverview = createBookMetadata();
        CastMetadata bookCast = createCast(bookOverview.summary());

        List<ChapterOutput> rawOutput = generateChapters(bookOverview, bookCast);

        outputGenerator.generate(rawOutput);

    }

    private List<ChapterOutput> generateChapters(BookMetadata bookOverview, CastMetadata castMetadata) {
        List<ChapterOutput> rawOutput = new ArrayList<>();
        if(bookOverview!=null) {
            for(ChapterMetadata chapterMetadata : bookOverview.chapterMetadata()) {
                rawOutput.add(createChapterText(chapterMetadata.chapterTitle(),chapterMetadata, castMetadata));
            }

        }
        return rawOutput;
    }

    private ChapterOutput createChapterText(String title, ChapterMetadata chapterMetadata, CastMetadata castMetadata) {
        logger.info("Generating opening text for chapter");
        ChapterResponseData chapterOpening = chapterBuilder.generate(chapterMetadata, castMetadata);
        ChapterOutput chapterOutput = new ChapterOutput(title, chapterOpening.editorialOverview(), chapterOpening.text());

        logger.info("Characters per chapter is "+ wordsPerChapter);
        while(chapterOutput.getChapterLength()<wordsPerChapter) {
            logger.info("Text size so far is "+chapterOutput.getChapterLength());
            logger.info("Making request for further chapter content");

            ChapterResponseData continuedText = chapterBuilder.generate(chapterMetadata, castMetadata, chapterOutput.getOutputText());
            chapterOutput.addText(continuedText.text());
        }

        return chapterOutput;
    }

    public BookMetadata createBookMetadata() {
        Map<String, Object> promptParameters = Map.of(
                "chapterCount", chapterCount
        );
        return (BookMetadata) Utilities.generateLlmJsonResponse(aiClient, promptParameters, generateCast, BookMetadata.class);
    }

    public CastMetadata createCast(String summary) {
        Map<String, Object> promptParameters = Map.of(
                "BookSummary", summary
        );
        return (CastMetadata) Utilities.generateLlmJsonResponse(aiClient, promptParameters, generateCast, CastMetadata.class);
    }

}
