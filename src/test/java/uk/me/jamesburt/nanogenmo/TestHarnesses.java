package uk.me.jamesburt.nanogenmo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import uk.me.jamesburt.nanogenmo.datastructures.*;
import uk.me.jamesburt.nanogenmo.outputgeneration.CommandlineOutputGenerator;
import uk.me.jamesburt.nanogenmo.outputgeneration.HtmlOutputGenerator;
import uk.me.jamesburt.nanogenmo.textbuilders.StructuredBookBuilder;
import uk.me.jamesburt.nanogenmo.textbuilders.oralhistory.ChapterBuilder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * This class provides a series of simple test methods, allowing aspects of the code to be run in isolation
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHarnesses {

    @MockBean
    CommandlineExecutor commandlineExecutor;

    @Autowired
    LlmClient llmClient;

    @Autowired
    private StructuredBookBuilder structuredBookBuilder;

    @Autowired
    @Qualifier("oralHistoryChapterBuilder")
    private ChapterBuilder chapterBuilder;

    @Autowired
    private CommandlineOutputGenerator commandlineOutputGenerator;

    @Value("${bookgenerator.wordsPerChapter}")
    private Integer wordsPerChapter;


    @Test
    public void testGenerateSingleChapter() {
        fail("Needs to be updated");
//        ChapterMetadata testChapterDefinition = new ChapterMetadata(
//                "The Dawn of Rave: Rituals and Resonance",
//                "Exploring the emergence of rave culture in the late 1980s, this chapter delves into the mystical and ritualistic elements that intertwined with the music scene, highlighting the influence of the supernatural on early raves."
//        );
//        CharacterMetadata[] emptyCharactersArray = new CharacterMetadata[0];
//        ChapterResponseData result = chapterBuilder.generate(testChapterDefinition, new CastMetadata(emptyCharactersArray));
//        ChapterOutput output = new ChapterOutput(testChapterDefinition.chapterTitle(), result.editorialOverview(), result.text());
//        List<ChapterOutput> chapters = Collections.singletonList(output);
//        commandlineOutputGenerator.generate(chapters);
    }

    @Test
    public void generateNovel() {
        BookMetadata bookDescription = structuredBookBuilder.createMetadata();
//        for(ChapterMetadata chapter: bookDescription.chapterMetadata()) {
//            System.out.println(chapter.chapterTitle());
//            System.out.println(chapter.description());
//            System.out.println("\n");
//        }
//        System.out.println(bookDescription.summary());

        CastMetadata bookCast = structuredBookBuilder.createCast(bookDescription.summary());
//        System.out.println("-------------\n");
//        for(CharacterMetadata characterMetadata: bookCast.characterMetadata()) {
//            System.out.println(characterMetadata.name());
//            System.out.println(characterMetadata.profession());
//            System.out.println(characterMetadata.history());
//            System.out.println(characterMetadata.description());
//            System.out.println("-------------\n");
//        }

        List<ChapterOutput> rawOutput = new ArrayList<>();

        // Copied text from the main body of the code
        for(ChapterMetadata chapterMetadata : bookDescription.chapterMetadata()) {
        }

        new HtmlOutputGenerator().generate("Book title", rawOutput);


    }

    @Test
    public void generateOralhistorySynopsis() {
        // TODO need the right builder here
        BookMetadata bookDescription = structuredBookBuilder.createMetadata();
        for(ChapterMetadata chapter: bookDescription.chapterMetadata()) {
            System.out.println(chapter.chapterTitle());
            System.out.println(chapter.description());
            System.out.println("\n");
        }
        System.out.println(bookDescription.summary());

    }

}
