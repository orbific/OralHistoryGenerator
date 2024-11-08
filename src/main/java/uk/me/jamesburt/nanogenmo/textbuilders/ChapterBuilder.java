package uk.me.jamesburt.nanogenmo.textbuilders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import uk.me.jamesburt.nanogenmo.Utilities;
import uk.me.jamesburt.nanogenmo.datastructures.*;

import java.util.HashMap;
import java.util.Map;

public class ChapterBuilder {

    private static final Logger logger = LoggerFactory.getLogger(ChapterBuilder.class);

    @Value("classpath:/prompts/generate-chapter-opening.st")
    private Resource generateChapter;

    @Value("classpath:/prompts/generate-chapter-continuation.st")
    private Resource generateChapterContinuation;

    private final OpenAiChatModel chatModel;

    public ChapterBuilder(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public ChapterResponseData generate(ChapterMetadata chapterMetadata, CastMetadata castMetadata) {
        return generate(chapterMetadata, castMetadata, null);
    }

    public ChapterResponseData generate(ChapterMetadata chapterMetadata, CastMetadata castMetadata, String chapterTextSoFar) {
        Map<String, Object> promptParameters = new HashMap<>();
        promptParameters.put("chapterTitle", chapterMetadata.chapterTitle());
        promptParameters.put("description", chapterMetadata.description());
        promptParameters.put("characters", CastMetadata.convertCastToString(castMetadata));

        Resource promptToUse;
        if(chapterTextSoFar!=null) {
            promptParameters.put("chapterText", chapterTextSoFar);
            promptToUse = generateChapterContinuation;
        } else {
            promptToUse = generateChapter;
        }

        return (ChapterResponseData) Utilities.generateLlmJsonResponse(chatModel, promptParameters, promptToUse, ChapterResponseData.class);

    }

}
