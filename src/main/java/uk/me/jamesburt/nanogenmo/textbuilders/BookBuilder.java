package uk.me.jamesburt.nanogenmo.textbuilders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import uk.me.jamesburt.nanogenmo.LlmClient;
import uk.me.jamesburt.nanogenmo.Utilities;
import uk.me.jamesburt.nanogenmo.datastructures.BookMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.CastMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterOutput;
import uk.me.jamesburt.nanogenmo.outputgeneration.OutputGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BookBuilder {

    private static final Logger logger = LoggerFactory.getLogger(BookBuilder.class);

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

        CastMetadata bookCast = createCast(bookOverview.summary());

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
     * @return
     */
    protected abstract String getBookTitle();

}
