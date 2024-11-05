package uk.me.jamesburt.nanogenmo.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import uk.me.jamesburt.nanogenmo.CommandlineExecutor;
import uk.me.jamesburt.nanogenmo.datastructures.BookMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterMetadata;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterText;
import uk.me.jamesburt.nanogenmo.outputgeneration.CommandlineOutputGenerator;
import uk.me.jamesburt.nanogenmo.textbuilders.ChapterBuilder;

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
    private ChapterBuilder chapterBuilder;

    @Autowired
    private CommandlineOutputGenerator commandlineOutputGenerator;

    @Test
    public void testMyServiceMethod() {
        ChapterMetadata testChapterDefinition = new ChapterMetadata(
                "The Dawn of Rave: Rituals and Resonance",
                "Exploring the emergence of rave culture in the late 1980s, this chapter delves into the mystical and ritualistic elements that intertwined with the music scene, highlighting the influence of the supernatural on early raves."
        );
        ChapterMetadata[] allChapters = {testChapterDefinition};
        BookMetadata bookMetadata = new BookMetadata(allChapters);
        ChapterText result = chapterBuilder.generate(testChapterDefinition);
        List<ChapterText> chapters = Collections.singletonList(result);
        commandlineOutputGenerator.generate(bookMetadata, chapters);
    }
}
