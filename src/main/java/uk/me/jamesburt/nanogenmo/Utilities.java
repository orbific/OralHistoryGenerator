package uk.me.jamesburt.nanogenmo;

import com.ibm.icu.text.RuleBasedNumberFormat;
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
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class Utilities {

    private static final Logger logger = LoggerFactory.getLogger(Utilities.class);

    public static Message createMessage(Map<String, Object> promptParameters, Resource generateOverview) {
        PromptTemplate promptTemplate = new PromptTemplate(generateOverview);
        return promptTemplate.createMessage(promptParameters);
    }

    public static <T extends Record> T generateLlmJsonResponse(ChatModel aiClient, Map<String, Object> promptParameters, Resource resource, Class<T> outputClass) {
        Message m = createMessage(promptParameters, resource);
        var outputConverter = new BeanOutputConverter<>(outputClass);
        var jsonSchema = outputConverter.getJsonSchema();

        ChatOptions co = OpenAiChatOptions.builder()
                .withResponseFormat(new ResponseFormat(ResponseFormat.Type.JSON_SCHEMA, jsonSchema))
                .build();
        Prompt prompt = new Prompt(List.of(m),co);
        logger.info(prompt + "\n");
        ChatResponse response = aiClient.call(prompt);
        String content = response.getResult().getOutput().getContent();
        logger.info("AI responded.");
        logger.info(response.getMetadata().toString());
        logger.info(response.getResult().toString());
        return outputClass.cast(outputConverter.convert(content));
    }


    public static String pickNumberAndConvertToWords(int n) {
        if (n < 1) throw new IllegalArgumentException("n must be greater than or equal to 1.");

        // Generate a random number between 1 and n
        Random random = new Random();
        int randomNumber = random.nextInt(n) + 1;

        // Convert the number to words
        RuleBasedNumberFormat numberFormat = new RuleBasedNumberFormat(Locale.UK, RuleBasedNumberFormat.SPELLOUT);
        return numberFormat.format(randomNumber);
    }

    /*
     * TODO this needs to move to a more generic method via an instantiated, configurable class
     */
    public static String getRandomTone() {
        String[] tones = {"irration", "world-weary anger", "joy", "cloying nostalgia"};
        Random random = new Random();
        int randomNumber = random.nextInt(tones.length);
        return tones[randomNumber];

    }

}
