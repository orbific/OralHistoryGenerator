package uk.me.jamesburt.nanogenmo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uk.me.jamesburt.nanogenmo.textbuilders.SimpleBookBuilder;
import uk.me.jamesburt.nanogenmo.textbuilders.StructuredBookBuilder;

import java.io.IOException;

@Component
public class CommandlineExecutor  implements CommandLineRunner {

    @Autowired
    StructuredBookBuilder structuredBookBuilder;

    @Autowired
    SimpleBookBuilder basicBuilder;

    @Override
    public void run(String... args)  {
        try {
            basicBuilder.generateBook();

        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }

    }

}
