package uk.me.jamesburt.nanogenmo.textbuilders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import uk.me.jamesburt.nanogenmo.datastructures.BookMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterText;
import uk.me.jamesburt.nanogenmo.outputgeneration.OutputGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO set up a common text-builder interface
 */
public class BookBuilder {

    private static final Logger logger = LoggerFactory.getLogger(BookBuilder.class);

    private final OpenAiChatModel aiClient;

    @Autowired
    OutputGenerator outputGenerator;

    @Autowired
    ChapterBuilder chapterBuilder;

    @Value("classpath:/prompts/generate-synopsis.st")
    private Resource generateOverview;

    public BookBuilder(OpenAiChatModel aiClient) {
        this.aiClient = aiClient;
    }

    public List<ChapterText> generateBook() {

        PromptTemplate promptTemplate = new PromptTemplate(generateOverview);
        Message m = promptTemplate.createMessage();

        var outputConverter = new BeanOutputConverter<>(BookMetadata.class);
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

        BookMetadata bookOverview = outputConverter.convert(content);

        List<ChapterText> rawOutput = new ArrayList<>();
        if(bookOverview!=null) {
            for(ChapterMetadata chapterMetadata : bookOverview.chapterMetadata()) {
                rawOutput.add(chapterBuilder.generate(chapterMetadata));
            }

        }
        // TODO need a better way to link the chapters and titles
        outputGenerator.generate(bookOverview, rawOutput);

        return rawOutput;
    }

}
