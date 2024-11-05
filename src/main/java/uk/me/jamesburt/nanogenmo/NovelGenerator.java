package uk.me.jamesburt.nanogenmo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.me.jamesburt.nanogenmo.datastructures.ChapterText;
import uk.me.jamesburt.nanogenmo.outputgeneration.CommandlineOutputGenerator;
import uk.me.jamesburt.nanogenmo.outputgeneration.OutputGenerator;
import uk.me.jamesburt.nanogenmo.textbuilders.BookBuilder;

import java.util.List;

@SpringBootApplication
public class NovelGenerator {

    public static void main(String[] args) {
        SpringApplication.run(NovelGenerator.class, args);
    }

}
