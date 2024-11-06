package uk.me.jamesburt.nanogenmo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uk.me.jamesburt.nanogenmo.textbuilders.BookBuilder;

@Component
public class CommandlineExecutor  implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CommandlineExecutor.class);

    @Autowired
    BookBuilder bookBuilder;

    @Override
    public void run(String... args) throws Exception {
        logger.info("LAUNCHING BOOK GENERATION");
        bookBuilder.generateBook();
    }

}
