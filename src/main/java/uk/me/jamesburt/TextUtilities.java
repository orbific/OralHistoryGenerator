package uk.me.jamesburt;

import com.ibm.icu.text.RuleBasedNumberFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class TextUtilities {

    public static String convertIntToWords(int n) {
        RuleBasedNumberFormat numberFormat = new RuleBasedNumberFormat(Locale.UK, RuleBasedNumberFormat.SPELLOUT);
        return numberFormat.format(n);
    }

    public static int getWordCount(String output) {
        return output.split("\\s+").length;
    }
}
