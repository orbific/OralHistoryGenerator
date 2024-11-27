package uk.me.jamesburt.nanogenmo.textbuilders.oralhistory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import uk.me.jamesburt.nanogenmo.datastructures.*;
import uk.me.jamesburt.nanogenmo.textbuilders.StructuredBookBuilder;

public class BookBuilder extends StructuredBookBuilder {

    private static final Logger logger = LoggerFactory.getLogger(uk.me.jamesburt.nanogenmo.textbuilders.oralhistory.BookBuilder.class);

    @Value("classpath:/prompts/oralhistory/generate-oralhistory-synopsis.st")
    private Resource generateOralHistoryOverview;

    @Autowired
    ChapterBuilder chapterBuilder;

    @Override
    protected ChapterOutput createChapterText(ChapterMetadata chapterMetadata, CastMetadata castMetadata) {
        logger.info("Generating opening text for chapter");
        return chapterBuilder.generateChapterText(chapterMetadata, castMetadata, "");
    }

    @Override
    public BookMetadata createMetadata() {
        return generateBookOverview(generateOralHistoryOverview);
    }

    /**
     * TODO Ideally this should not be hardcoded in the class - should be some parameters as part of the config
     * @return
     */
    @Override
    protected String getBookTitle() {
        return "The Secret History of Rave";
    }
}
