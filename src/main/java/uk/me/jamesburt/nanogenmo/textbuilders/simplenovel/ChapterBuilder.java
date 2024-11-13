package uk.me.jamesburt.nanogenmo.textbuilders.simplenovel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import uk.me.jamesburt.nanogenmo.LlmClient;
import uk.me.jamesburt.nanogenmo.datastructures.CastMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterResponseData;
import uk.me.jamesburt.nanogenmo.datastructures.CharacterMetadata;

import java.util.HashMap;
import java.util.Map;

public class ChapterBuilder {

    @Autowired
    LlmClient llmClient;

    @Value("classpath:/prompts/generate-novel-text.st")
    private Resource generateNovelText;

    public ChapterResponseData generateChapterText(ChapterMetadata chapterMetadata, CastMetadata castMetadata, String chapterTextSoFar) {
        Map<String, Object> promptParameters = new HashMap<>();
        promptParameters.put("chapterTitle", chapterMetadata.chapterTitle());
        promptParameters.put("description", chapterMetadata.description());
        promptParameters.put("bookTitle", "The Great Gatsby 2: Gatsby vs Kong");
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
        return llmClient.generateLlmJsonResponse(promptParameters, promptToUse, ChapterResponseData.class);

    }
}
