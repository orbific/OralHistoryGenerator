package uk.me.jamesburt.nanogenmo.textbuilders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import uk.me.jamesburt.nanogenmo.Utilities;
import uk.me.jamesburt.nanogenmo.datastructures.*;
import uk.me.jamesburt.nanogenmo.outputgeneration.OutputGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Value("classpath:/prompts/generate-cast.st")
    private Resource generateCast;

    @Value("${bookgenerator.chapterCount}")
    private String chapterCount;

    // TODO need to adjust this to be in words
    @Value("${bookgenerator.charactersPerChapter}")
    private String charactersPerChapter;

    public BookBuilder(OpenAiChatModel aiClient) {
        this.aiClient = aiClient;
    }

    // TODO this method is too long and needs refactoring
    public void generateBook() {

        BookMetadata bookOverview = createBookMetadata();
        CastMetadata bookCast = createCast(bookOverview.summary());

        List<ChapterOutput> rawOutput = generateChapters(bookOverview, bookCast);

        outputGenerator.generate(bookOverview, rawOutput);

    }

    private List<ChapterOutput> generateChapters(BookMetadata bookOverview, CastMetadata castMetadata) {
        List<ChapterOutput> rawOutput = new ArrayList<>();
        if(bookOverview!=null) {
            for(ChapterMetadata chapterMetadata : bookOverview.chapterMetadata()) {
                rawOutput.add(createChapterText(chapterMetadata, castMetadata));
            }

        }
        return rawOutput;
    }

    private ChapterOutput createChapterText(ChapterMetadata chapterMetadata, CastMetadata castMetadata) {
        logger.info("Generating opening text for chapter");
        ChapterData chapterOpening = chapterBuilder.generate(chapterMetadata, castMetadata);
        ChapterOutput chapterOutput = new ChapterOutput(chapterOpening.editorialOverview(), chapterOpening.text());

        // TODO should be able to create the parameter as integer?
        logger.info("Characters per chapter is "+charactersPerChapter);
        int characterCount = Integer.parseInt(charactersPerChapter);
        while(chapterOutput.getChapterLength()<characterCount) {
            logger.info("Making request for further chapter content");
            // TODO we need another prompt here to build on the previous info
            ChapterData continuedText = chapterBuilder.generate(chapterMetadata, castMetadata, chapterOutput.getOutputText());
            chapterOutput.addText(continuedText.text());
        }

        return chapterOutput;
    }

    public BookMetadata createBookMetadata() {
        Map<String, Object> promptParameters = Map.of(
                "chapterCount", chapterCount
        );

        Message m = Utilities.createMessage(promptParameters, generateOverview);

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
        return outputConverter.convert(content);
    }


    public CastMetadata createCast(String summary) {
        Map<String, Object> promptParameters = Map.of(
                "BookSummary", summary
        );

        Message m = Utilities.createMessage(promptParameters, generateCast);

        var outputConverter = new BeanOutputConverter<>(CastMetadata.class);
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
