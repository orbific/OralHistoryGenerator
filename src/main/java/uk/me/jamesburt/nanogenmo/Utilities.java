package uk.me.jamesburt.nanogenmo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.core.io.Resource;
import uk.me.jamesburt.nanogenmo.textbuilders.BookBuilder;

import java.util.List;
import java.util.Map;

public class Utilities {

    private static final Logger logger = LoggerFactory.getLogger(Utilities.class);

    public static Message createMessage(Map<String, Object> promptParameters, Resource generateOverview) {
        PromptTemplate promptTemplate = new PromptTemplate(generateOverview);
        return promptTemplate.createMessage(promptParameters);
    }

    public static Record generateLlmJsonResponse(ChatModel aiClient, Map<String, Object> promptParameters, Resource resource, Class<? extends Record> outputClass) {
        Message m = createMessage(promptParameters, resource);
        var outputConverter = new BeanOutputConverter<>(outputClass);
        var jsonSchema = outputConverter.getJsonSchema();

        ChatOptions co = OpenAiChatOptions.builder()
                .withResponseFormat(new OpenAiApi.ChatCompletionRequest.ResponseFormat(OpenAiApi.ChatCompletionRequest.ResponseFormat.Type.JSON_SCHEMA, jsonSchema))
                .build();
        Prompt prompt = new Prompt(List.of(m),co);
        logger.info(prompt + "\n");
        ChatResponse response = aiClient.call(prompt);
        String content = response.getResult().getOutput().getContent();
        logger.info("AI responded.");
        logger.info(response.getMetadata().toString());
        logger.info(response.getResult().toString());
        return outputConverter.convert(content);
    }
}
