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
import java.util.Random;

public class ChapterBuilder {

    private static final Logger logger = LoggerFactory.getLogger(ChapterBuilder.class);

    @Value("classpath:/prompts/generate-chapter-opening.st")
    private Resource generateChapter;

    @Value("classpath:/prompts/generate-chapter-continuation.st")
    private Resource generateChapterContinuation;

    @Value("classpath:/prompts/generate-single-account.st")
    private Resource generateSingleAccount;

    @Value("classpath:/prompts/generate-novel-text.st")
    private Resource generateNovelText;

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

        return Utilities.generateLlmJsonResponse(chatModel, promptParameters, promptToUse, ChapterResponseData.class);

    }

    // TODO there is a lot of hard-coding here that needs to be dealt with
    public ChapterResponseData generateViaSingleAccount(ChapterMetadata chapterMetadata, CastMetadata castMetadata, String chapterTextSoFar) {
        Map<String, Object> promptParameters = new HashMap<>();
        promptParameters.put("chapterTitle", chapterMetadata.chapterTitle());
        promptParameters.put("description", chapterMetadata.description());
        promptParameters.put("bookTitle", "The Secret History of Rave");
        promptParameters.put("chapterDescription", "Exploring the birth of the UK rave scene in the late 1980s, this chapter delves into the Acid House movement's psychedelic influences and the mystical experiences reported by ravers, revealing how early gatherings were often inspired by notions of transcendence and altered states.");
        promptParameters.put("bookSummary", "This oral history traces the evolution of rave music in the UK, starting from its inception in the late 1980s, where the Acid House scene combined pulsating beats with a quest for spiritual and psychedelic experiences. As the movement grew, significant events like the Castlemorton Common Festival illustrated how these gatherings became a breeding ground for communal spirituality and a unique relationship with the supernatural. Moving into the present day, the book explores the modern resurgence of rave culture, where new subgenres and technology continue to inspire ravers to seek out mystical experiences, revealing a lasting connection between music, community, and the ethereal.");

        // TODO not sure this is producing random output
        // TODO how do I ensure not to repeat the previous speaker?
        Random r = new Random();
        CharacterMetadata speaker = castMetadata.characterMetadata()[r.nextInt(castMetadata.characterMetadata().length)];

        promptParameters.put("name", speaker.name());
        promptParameters.put("role", speaker.profession());
        promptParameters.put("castDescription",speaker.description());
        promptParameters.put("castHistory",speaker.history());

        promptParameters.put("lengthInParagraphs", Utilities.pickNumberAndConvertToWords(4));
        String tone = Utilities.getRandomTone();
        promptParameters.put("tone", tone);

        Resource promptToUse = generateNovelText;
        if(chapterTextSoFar!=null) {
            String earlierSection = "The new account follows the earlier section of the chapter" +
                    "\n\n---\n\n" +
                    chapterTextSoFar;
            promptParameters.put("earlierSection", earlierSection);
        } else {
            promptParameters.put("earlierSection", "");
            promptToUse = generateChapter;
        }

        return Utilities.generateLlmJsonResponse(chatModel, promptParameters, promptToUse, ChapterResponseData.class);

    }


    public ChapterResponseData generateNarrativeNovelAccount(ChapterMetadata chapterMetadata, CastMetadata castMetadata, String chapterTextSoFar) {
        Map<String, Object> promptParameters = new HashMap<>();
        promptParameters.put("chapterTitle", chapterMetadata.chapterTitle());
        promptParameters.put("description", chapterMetadata.description());
        promptParameters.put("bookTitle", "The Great Gatsby 2: ");
        promptParameters.put("chapterDescription", chapterMetadata.description());
        promptParameters.put("chapterTextSoFar", chapterTextSoFar);

        // TODO fix these params
        StringBuilder sb = new StringBuilder();
        for(CharacterMetadata character: castMetadata.characterMetadata()) {
            sb.append(character.name()).append(" (").append(character.profession()).append(")\n");
            sb.append(character.description());
            sb.append(character.history());

        }
        promptParameters.put("bookCast",sb.toString());
        // TODO promptParameters.put("booksoFarSummary", "A summary of earlier chapters (if they exist)");


        Resource promptToUse = generateNovelText;
        return Utilities.generateLlmJsonResponse(chatModel, promptParameters, promptToUse, ChapterResponseData.class);

    }

}
