package uk.me.jamesburt.nanogenmo.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import uk.me.jamesburt.nanogenmo.CommandlineExecutor;
import uk.me.jamesburt.nanogenmo.datastructures.*;
import uk.me.jamesburt.nanogenmo.outputgeneration.CommandlineOutputGenerator;
import uk.me.jamesburt.nanogenmo.textbuilders.BookBuilder;
import uk.me.jamesburt.nanogenmo.textbuilders.ChapterBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class provides a series of simple test methods, allowing aspects of the code to be run in isolation
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHarnesses {

    @MockBean
    CommandlineExecutor commandlineExecutor;

    @Autowired
    private BookBuilder bookBuilder;

    @Autowired
    private ChapterBuilder chapterBuilder;

    @Autowired
    private CommandlineOutputGenerator commandlineOutputGenerator;

    @Test
    public void testGenerateSingleChapter() {
        ChapterMetadata testChapterDefinition = new ChapterMetadata(
                "The Dawn of Rave: Rituals and Resonance",
                "Exploring the emergence of rave culture in the late 1980s, this chapter delves into the mystical and ritualistic elements that intertwined with the music scene, highlighting the influence of the supernatural on early raves."
        );
        ChapterMetadata[] allChapters = {testChapterDefinition};
        BookMetadata bookMetadata = new BookMetadata("",allChapters);
        CharacterMetadata[] emptyCharactersArray = new CharacterMetadata[0];
        ChapterData result = chapterBuilder.generate(testChapterDefinition, new CastMetadata(emptyCharactersArray));
        ChapterOutput output = new ChapterOutput(result.editorialOverview(), result.text());
        List<ChapterOutput> chapters = Collections.singletonList(output);
        commandlineOutputGenerator.generate(bookMetadata, chapters);
    }

    @Test
    public void generateSynopsis() {
        BookMetadata bookDescription = bookBuilder.createBookMetadata();
//        for(ChapterMetadata chapter: bookDescription.chapterMetadata()) {
//            System.out.println(chapter.chapterTitle());
//            System.out.println(chapter.description());
//            System.out.println("\n");
//        }
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
}
