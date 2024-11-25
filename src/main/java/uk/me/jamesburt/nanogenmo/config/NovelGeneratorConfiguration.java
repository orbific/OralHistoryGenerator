package uk.me.jamesburt.nanogenmo.config;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import uk.me.jamesburt.nanogenmo.LlmClient;
import uk.me.jamesburt.nanogenmo.outputgeneration.CommandlineOutputGenerator;
import uk.me.jamesburt.nanogenmo.outputgeneration.HtmlOutputGenerator;
import uk.me.jamesburt.nanogenmo.outputgeneration.OutputGenerator;
import uk.me.jamesburt.nanogenmo.outputgeneration.PdfOutputGenerator;
import uk.me.jamesburt.nanogenmo.textbuilders.oralhistory.ChapterBuilder;
import uk.me.jamesburt.nanogenmo.textbuilders.oralhistory.BookBuilder;

@Configuration
public class NovelGeneratorConfiguration {

    @Bean
    public uk.me.jamesburt.nanogenmo.textbuilders.BookBuilder createSimpleNovelBuilder() {
        return new BookBuilder();
    }

    @Bean
    public ChapterBuilder createChapterBuilder() {
        return new ChapterBuilder();
    }


    @Bean
    @Primary
    public ChapterBuilder createOralHistoryChapterBuilder() {
        return new uk.me.jamesburt.nanogenmo.textbuilders.oralhistory.ChapterBuilder();
    }

    @Bean
    public LlmClient createLlmClient(OpenAiChatModel chatModel) {
        return new LlmClient(chatModel);
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
