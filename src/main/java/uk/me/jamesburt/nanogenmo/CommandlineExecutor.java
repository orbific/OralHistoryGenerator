package uk.me.jamesburt.nanogenmo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uk.me.jamesburt.nanogenmo.textbuilders.BookBuilder;

@Component
public class CommandlineExecutor  implements CommandLineRunner {

    @Autowired
    BookBuilder bookBuilder;

    @Override
    public void run(String... args)  {
        bookBuilder.generateBook();
    }

}
