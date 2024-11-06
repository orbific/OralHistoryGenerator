package uk.me.jamesburt.nanogenmo;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.io.Resource;

import java.util.Map;

public class Utilities {
    public static Message createMessage(Map<String, Object> promptParameters, Resource generateOverview) {
        PromptTemplate promptTemplate = new PromptTemplate(generateOverview);
        return promptTemplate.createMessage(promptParameters);
    }
}
