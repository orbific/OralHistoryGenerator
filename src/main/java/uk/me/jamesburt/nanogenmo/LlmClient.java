package uk.me.jamesburt.nanogenmo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;

public class LlmClient {

    private static final Logger logger = LoggerFactory.getLogger(LlmClient.class);

    private final ChatModel aiClient;

    public LlmClient(ChatModel aiClient) {
        this.aiClient = aiClient;
    }


    public <T extends Record> T generateLlmJsonResponse(Map<String, Object> userPromptParameters, Resource rawUserPromptText, Class<T> outputClass) {
        return generateLlmJsonResponse(userPromptParameters, rawUserPromptText, null, outputClass );
    }

    public <T extends Record> T generateLlmJsonResponse(Map<String, Object> userPromptParameters, Resource rawUserPromptText, Resource systemPromptText, Class<T> outputClass) {
        PromptTemplate promptTemplate = new PromptTemplate(rawUserPromptText);
        Message userMessage = promptTemplate.createMessage(userPromptParameters);
        var outputConverter = new BeanOutputConverter<>(outputClass);
        var jsonSchema = outputConverter.getJsonSchema();

        List<Message> messageList = List.of(userMessage);
        if(systemPromptText!=null) {
            SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemPromptText);
            Message systemMessage = systemPromptTemplate.createMessage();
            logger.info("SYSTEM PROMPT:\n>>>>>>>>>");
            logger.info(systemMessage.getContent());
            logger.info("<<<<<<<<<");
            messageList = List.of(userMessage, systemMessage);
        }
        logger.info("USER PROMPT:\n>>>>>>>>>");
        logger.info(userMessage.getContent());
        logger.info("<<<<<<<<<");

        ChatOptions co = OpenAiChatOptions.builder()
                .withResponseFormat(new ResponseFormat(ResponseFormat.Type.JSON_SCHEMA, jsonSchema))
                .build();
        Prompt prompt = new Prompt(messageList, co);
        ChatResponse response = aiClient.call(prompt);
        String content = response.getResult().getOutput().getContent();
        logger.info("AI RESPONSE:");
        Usage promptUsage = response.getMetadata().getUsage();
        logger.info("Prompt tokens     :"+promptUsage.getPromptTokens());
        logger.info("Completion tokens :"+promptUsage.getGenerationTokens());
        logger.info("Total tokens      :"+promptUsage.getTotalTokens());
        logger.info(">>>>>>>>>");
        logger.info(response.getResult().getOutput().getContent());
        logger.info("<<<<<<<<<");
        return outputClass.cast(outputConverter.convert(content));

    }

    public String generateLlmStringResponse(String fullPrompt) {
        ChatOptions co = OpenAiChatOptions.builder()
                .withTemperature(1.1)
                .build();
        Prompt prompt = new Prompt(fullPrompt, co);
        ChatResponse response = aiClient.call(prompt);
        return response.getResult().getOutput().getContent();
    }
}
