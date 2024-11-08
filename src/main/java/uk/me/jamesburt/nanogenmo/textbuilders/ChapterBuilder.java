package uk.me.jamesburt.nanogenmo.textbuilders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import uk.me.jamesburt.nanogenmo.Utilities;
import uk.me.jamesburt.nanogenmo.datastructures.CastMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterResponseData;
import uk.me.jamesburt.nanogenmo.datastructures.CharacterMetadata;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

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

        Message m;
        if(chapterTextSoFar!=null) {
            promptParameters.put("chapterText", chapterTextSoFar);
            m = Utilities.createMessage(promptParameters, generateChapterContinuation);
        } else {
            m = Utilities.createMessage(promptParameters, generateChapter);
        }


        var outputConverter = new BeanOutputConverter<>(ChapterResponseData.class);
        var jsonSchema = outputConverter.getJsonSchema();

        ChatOptions co = OpenAiChatOptions.builder()
                .withResponseFormat(new OpenAiApi.ChatCompletionRequest.ResponseFormat(OpenAiApi.ChatCompletionRequest.ResponseFormat.Type.JSON_SCHEMA, jsonSchema))
                .build();

        logger.info("Making call for chapter:" + chapterMetadata.chapterTitle()+ "\n");
        Prompt prompt = new Prompt(List.of(m),co);
        long startTime = System.currentTimeMillis();
        ChatResponse response = chatModel.call(prompt);
        logger.info("Request took "+(System.currentTimeMillis()-startTime)+"ms.");

        logger.info("AI responded with Chapter.\n");
        logger.info(response.getMetadata().toString());
        logger.info(response.getResult().toString());

        String content = response.getResult().getOutput().getContent();
        return outputConverter.convert(content);
    }

}
