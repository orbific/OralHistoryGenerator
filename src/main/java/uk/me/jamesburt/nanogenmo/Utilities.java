package uk.me.jamesburt.nanogenmo;

import com.ibm.icu.text.RuleBasedNumberFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Random;

public class Utilities {

    private static final Logger logger = LoggerFactory.getLogger(Utilities.class);


    public static String pickNumberAndConvertToWords(int n) {
        if (n < 1) throw new IllegalArgumentException("n must be greater than or equal to 1.");

        // Generate a random number between 1 and n
        Random random = new Random();
        int randomNumber = random.nextInt(n) + 1;

        // Convert the number to words
        RuleBasedNumberFormat numberFormat = new RuleBasedNumberFormat(Locale.UK, RuleBasedNumberFormat.SPELLOUT);
        return numberFormat.format(randomNumber);
    }

    /*
     * TODO this needs to move to a more generic method via an instantiated, configurable class
     */
    public static String getRandomTone() {
        String[] tones = {"irration", "world-weary anger", "joy", "cloying nostalgia"};
        Random random = new Random();
        int randomNumber = random.nextInt(tones.length);
        return tones[randomNumber];

    }

}
