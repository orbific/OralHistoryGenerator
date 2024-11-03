package uk.me.jamesburt.nanogenmo.config;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import uk.me.jamesburt.nanogenmo.outputgeneration.CommandlineOutputGenerator;
import uk.me.jamesburt.nanogenmo.outputgeneration.HtmlOutputGenerator;
import uk.me.jamesburt.nanogenmo.outputgeneration.OutputGenerator;
import uk.me.jamesburt.nanogenmo.outputgeneration.PdfOutputGenerator;
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
    public HtmlOutputGenerator createOutputGenerator() {
        return new HtmlOutputGenerator();
    }

    @Bean
    public CommandlineOutputGenerator createCommandLineGenerator() {
        return new CommandlineOutputGenerator();
    }

    @Bean
    @Primary
    public OutputGenerator createPdfOutputGenerator(HtmlOutputGenerator htmlOutputGenerator) {
        return new PdfOutputGenerator(htmlOutputGenerator);
    }


}
