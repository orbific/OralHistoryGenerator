package uk.me.jamesburt.nanogenmo.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import uk.me.jamesburt.nanogenmo.CommandlineExecutor;
import uk.me.jamesburt.nanogenmo.Utilities;
import uk.me.jamesburt.nanogenmo.datastructures.*;
import uk.me.jamesburt.nanogenmo.outputgeneration.CommandlineOutputGenerator;
import uk.me.jamesburt.nanogenmo.textbuilders.BookBuilder;
import uk.me.jamesburt.nanogenmo.textbuilders.ChapterBuilder;

import java.util.*;

/**
 * This class provides a series of simple test methods, allowing aspects of the code to be run in isolation
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHarnesses {

    @MockBean
    CommandlineExecutor commandlineExecutor;

    @Autowired
    OpenAiChatModel aiClient;

    @Autowired
    private BookBuilder bookBuilder;

    @Autowired
    private ChapterBuilder chapterBuilder;

    @Autowired
    private CommandlineOutputGenerator commandlineOutputGenerator;

    @Value("classpath:/prompts/generate-single-account.st")
    private Resource generateCast;


    @Test
    public void testGenerateSingleChapter() {
        ChapterMetadata testChapterDefinition = new ChapterMetadata(
                "The Dawn of Rave: Rituals and Resonance",
                "Exploring the emergence of rave culture in the late 1980s, this chapter delves into the mystical and ritualistic elements that intertwined with the music scene, highlighting the influence of the supernatural on early raves."
        );
        CharacterMetadata[] emptyCharactersArray = new CharacterMetadata[0];
        ChapterResponseData result = chapterBuilder.generate(testChapterDefinition, new CastMetadata(emptyCharactersArray));
        ChapterOutput output = new ChapterOutput(testChapterDefinition.chapterTitle(), result.editorialOverview(), result.text());
        List<ChapterOutput> chapters = Collections.singletonList(output);
        commandlineOutputGenerator.generate(chapters);
    }

    @Test
    public void generateSynopsis() {
        BookMetadata bookDescription = bookBuilder.createBookMetadata();
        for(ChapterMetadata chapter: bookDescription.chapterMetadata()) {
            System.out.println(chapter.chapterTitle());
            System.out.println(chapter.description());
            System.out.println("\n");
        }
        System.out.println(bookDescription.summary());

    }

    @Test
    public void generateCast() {
        String summary = "This oral history delves into the vibrant evolution of rave music in the UK, tracing its roots from the Acid House movement of the late 1980s through significant historical events like the Criminal Justice Act and the rise of festival culture. The narrative intertwines the enchanting and sometimes eerie aspects of rave culture, exploring its connections to spirituality, the supernatural, and the collective consciousness of its participants. Each chapter uncovers a unique facet of rave history, from the rituals and altered states fostered on the dance floor to the dark undercurrents of addiction and the occult. As the story unfolds, it reflects on the resilience and evolution of the rave community, culminating in a vision for its future amidst the challenges of modernity.";
        CastMetadata castMetadata = bookBuilder.createCast(summary);
        System.out.println("----------------------");
        for(CharacterMetadata character: castMetadata.characterMetadata()) {
            System.out.println(character.name()+": "+character.profession());
            System.out.println(character.description());
            System.out.println(character.history());
        }
    }

    @Test
    public void generateSingleAccount() {
        Map<String, Object> promptParameters = new HashMap<>();
        promptParameters.put("bookTitle", "The Secret History of Rave");
        promptParameters.put("chapterTitle", "The Dawn of Rave: Acid House and the Supernatural");
        promptParameters.put("chapterDescription", "Exploring the birth of the UK rave scene in the late 1980s, this chapter delves into the Acid House movement's psychedelic influences and the mystical experiences reported by ravers, revealing how early gatherings were often inspired by notions of transcendence and altered states.");
        promptParameters.put("bookSummary", "This oral history traces the evolution of rave music in the UK, starting from its inception in the late 1980s, where the Acid House scene combined pulsating beats with a quest for spiritual and psychedelic experiences. As the movement grew, significant events like the Castlemorton Common Festival illustrated how these gatherings became a breeding ground for communal spirituality and a unique relationship with the supernatural. Moving into the present day, the book explores the modern resurgence of rave culture, where new subgenres and technology continue to inspire ravers to seek out mystical experiences, revealing a lasting connection between music, community, and the ethereal.");
        promptParameters.put("lengthInParagraphs", Utilities.pickNumberAndConvertToWords(4));
        String tone = Utilities.getRandomTone();
        promptParameters.put("tone", tone);

        promptParameters.put("name", "Tom Williams");
        promptParameters.put("role", "Policeman turned activist");
        promptParameters.put("castDescription","A former police officer turned activist who witnessed the clash between law enforcement and rave culture firsthand, advocating for the rights of ravers.");
        promptParameters.put("castHistory","Tom spent years enforcing the law during the height of the rave scene, often finding himself at odds with the very culture he was trying to control. His turning point came during the protests against the Criminal Justice Act, where he saw the passion and resilience of the rave community. Disillusioned with the system, Tom left the police force and became an activist, fighting for the rights of ravers and promoting harm reduction policies. His journey highlights the transformation of perception surrounding rave culture—from a criminalized activity to a vibrant community deserving of respect and recognition.");
        promptParameters.put("earlierSection","");

        SingleAccount response = Utilities.generateLlmJsonResponse(aiClient, promptParameters, generateCast, SingleAccount.class);
        System.out.println("********");
        System.out.println("Tone here is " + tone);
        System.out.println("********");
        System.out.println(response.text());
    }

}
