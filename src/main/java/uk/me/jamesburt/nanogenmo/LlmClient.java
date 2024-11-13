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
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import uk.me.jamesburt.nanogenmo.Utilities;
import uk.me.jamesburt.nanogenmo.textbuilders.BookBuilder;

import java.util.List;
import java.util.Map;

public class LlmClient {

    private static final Logger logger = LoggerFactory.getLogger(LlmClient.class);

    private final ChatModel aiClient;


    public LlmClient(ChatModel aiClient) {
        this.aiClient = aiClient;
    }


    public <T extends Record> T generateLlmJsonResponse(Map<String, Object> promptParameters, Resource rawPromptText, Class<T> outputClass) {
        PromptTemplate promptTemplate = new PromptTemplate(rawPromptText);
        Message m = promptTemplate.createMessage(promptParameters);
        var outputConverter = new BeanOutputConverter<>(outputClass);
        var jsonSchema = outputConverter.getJsonSchema();

        ChatOptions co = OpenAiChatOptions.builder()
                .withResponseFormat(new ResponseFormat(ResponseFormat.Type.JSON_SCHEMA, jsonSchema))
                .build();
        Prompt prompt = new Prompt(List.of(m), co);
        logger.info(prompt + "\n");
        ChatResponse response = aiClient.call(prompt);
        String content = response.getResult().getOutput().getContent();
        logger.info("AI responded.");
        logger.info(response.getMetadata().toString());
        logger.info(response.getResult().toString());
        return outputClass.cast(outputConverter.convert(content));
    }
}
