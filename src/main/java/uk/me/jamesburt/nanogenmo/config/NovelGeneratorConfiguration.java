package uk.me.jamesburt.nanogenmo.config;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.me.jamesburt.nanogenmo.outputgeneration.CommandlineOutputGenerator;
import uk.me.jamesburt.nanogenmo.outputgeneration.FileOutputGenerator;
import uk.me.jamesburt.nanogenmo.outputgeneration.OutputGenerator;
import uk.me.jamesburt.nanogenmo.textbuilders.BookBuilder;
import uk.me.jamesburt.nanogenmo.textbuilders.ChapterBuilder;

@Configuration
public class NovelGeneratorConfiguration {

    @Bean
    public BookBuilder createBookBuilder(OpenAiChatModel chatModel) {
        return new BookBuilder(chatModel);
    }

    @Bean
    public ChapterBuilder createChapterBuilder(OpenAiChatModel chatModel) {
        return new ChapterBuilder(chatModel);
    }

    @Bean
    public OutputGenerator createOutputGenerator() {
        return new FileOutputGenerator();
    }

}