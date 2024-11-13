package uk.me.jamesburt.nanogenmo;

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
