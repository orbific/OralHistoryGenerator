package uk.me.jamesburt.nanogenmo.textbuilders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import uk.me.jamesburt.nanogenmo.LlmClient;
import uk.me.jamesburt.nanogenmo.datastructures.BookMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.CastMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterOutput;
import uk.me.jamesburt.nanogenmo.datastructures.CharacterMetadata;
import uk.me.jamesburt.nanogenmo.outputgeneration.OutputGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class StructuredBookBuilder {

    private static final Logger logger = LoggerFactory.getLogger(StructuredBookBuilder.class);

    @Autowired
    OutputGenerator outputGenerator;

    @Autowired
    LlmClient llmClient;

    @Value("classpath:/prompts/generate-cast.st")
    private Resource generateCast;

    @Value("${bookgenerator.chapterCount}")
    private String chapterCount;


    public void generateBook() {

        BookMetadata bookOverview = createMetadata();

        logger.info("CHAPTERS\n>>>>>>>>>>>>>>>>");
        for(ChapterMetadata chapterMetadata: bookOverview.chapterMetadata()) {
            logger.info(chapterMetadata.chapterTitle());
            logger.info(chapterMetadata.description()+"\n");
        }
        logger.info("<<<<<<<<<<<<<<<");

        CastMetadata bookCast = createCast(bookOverview.summary());

        logger.info("CAST\n>>>>>>>>>>>>>>>>");
        for(CharacterMetadata characterMetadata: bookCast.characterMetadata()) {
            logger.info(characterMetadata.name() + " ("+characterMetadata.profession()+")");
            logger.info(characterMetadata.history());
            logger.info(characterMetadata.description());
        }
        logger.info("<<<<<<<<<<<<<<<");


        List<ChapterOutput> rawOutput = generateChapters(bookOverview, bookCast);

        outputGenerator.generate(getBookTitle(), rawOutput);

    }


    private List<ChapterOutput> generateChapters(BookMetadata bookOverview, CastMetadata castMetadata) {
        List<ChapterOutput> rawOutput = new ArrayList<>();
        if(bookOverview!=null) {
            for(ChapterMetadata chapterMetadata : bookOverview.chapterMetadata()) {
                rawOutput.add(createChapterText(chapterMetadata, castMetadata));
            }
        }
        return rawOutput;
    }

    protected abstract ChapterOutput createChapterText(ChapterMetadata chapterMetadata, CastMetadata castMetadata);

    public abstract BookMetadata createMetadata();

    // TODO check this visibility is correct
    protected BookMetadata generateBookOverview(Resource bookSynopsis) {
        Map<String, Object> promptParameters = Map.of(
                "chapterCount", chapterCount
        );
        return llmClient.generateLlmJsonResponse(promptParameters, bookSynopsis, BookMetadata.class);

    }

    public CastMetadata createCast(String summary) {
        Map<String, Object> promptParameters = Map.of(
                "BookSummary", summary
        );
        return llmClient.generateLlmJsonResponse(promptParameters, generateCast, CastMetadata.class);
    }

    /**
     * The implementation of book builder needs to specify the title for the book being generated
     */
    protected abstract String getBookTitle();

}
