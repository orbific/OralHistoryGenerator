package uk.me.jamesburt.nanogenmo.textbuilders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import uk.me.jamesburt.nanogenmo.datastructures.BookMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterText;

import java.util.Map;
import java.util.List;

/**
 * TODO add a JSON structure for the chapter to get the formatting right
 */
public class ChapterBuilder {

    private static final Logger logger = LoggerFactory.getLogger(ChapterBuilder.class);

    @Value("classpath:/prompts/generate-chapter.st")
    private Resource generateChapter;

    private final OpenAiChatModel chatModel;

    public ChapterBuilder(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public ChapterText generate(ChapterMetadata chapterMetadata) {
        Map<String, Object> promptParameters = Map.of("chapterTitle", chapterMetadata.chapterTitle(), "description", chapterMetadata.description());
        PromptTemplate promptTemplate = new PromptTemplate(generateChapter,promptParameters);
        Message m = promptTemplate.createMessage();

        var outputConverter = new BeanOutputConverter<>(ChapterText.class);
        var jsonSchema = outputConverter.getJsonSchema();

        ChatOptions co = OpenAiChatOptions.builder()
                .withResponseFormat(new OpenAiApi.ChatCompletionRequest.ResponseFormat(OpenAiApi.ChatCompletionRequest.ResponseFormat.Type.JSON_SCHEMA, jsonSchema))
                .build();

        // TODO - need to keep calling the model until the chapter hits the word count
        logger.info("Making call for chapter:" + chapterMetadata.chapterTitle()+ "\n");
        Prompt prompt = new Prompt(List.of(m),co);
        ChatResponse response = chatModel.call(prompt);

        logger.info("AI responded with Chapter.\n");
        logger.info(response.getMetadata().toString());
        logger.info(response.getResult().toString());

        String content = response.getResult().getOutput().getContent();
        return outputConverter.convert(content);
    }

}
