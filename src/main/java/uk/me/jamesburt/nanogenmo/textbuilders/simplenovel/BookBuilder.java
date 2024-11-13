package uk.me.jamesburt.nanogenmo.textbuilders.simplenovel;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import uk.me.jamesburt.nanogenmo.datastructures.*;

import java.util.HashMap;
import java.util.Map;

public class BookBuilder extends uk.me.jamesburt.nanogenmo.textbuilders.BookBuilder {
    @Value("classpath:/prompts/generate-novel-synopsis.st")
    private Resource generateNovelOverview;

    @Value("${bookgenerator.wordsPerChapter}")
    private Integer wordsPerChapter;

    @Autowired
    private ChapterBuilder chapterBuilder;

    @Override
    protected ChapterOutput createChapterText(ChapterMetadata chapterMetadata, CastMetadata castMetadata) {
        ChapterResponseData chapterOpening = chapterBuilder.generateChapterText(chapterMetadata, castMetadata, "");
        ChapterOutput chapterOutput = new ChapterOutput("The Great Gatsby 2: Gatsby vs Kong", chapterOpening.editorialOverview(), chapterOpening.text());

        while(chapterOutput.getChapterLength()<wordsPerChapter) {

            // TODO something about this return type seems off - do we need more structure here rather than just wrapping a single body String?
            ChapterResponseData continuedText = chapterBuilder.generateChapterText(chapterMetadata, castMetadata, chapterOutput.getOutputText());
            chapterOutput.addText(continuedText.text());
        }

        return chapterOutput;
    }

    @Override
    public BookMetadata createMetadata() {
        return generateBookOverview(generateNovelOverview);
    }

}
