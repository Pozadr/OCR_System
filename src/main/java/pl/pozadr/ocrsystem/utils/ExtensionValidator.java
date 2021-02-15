package pl.pozadr.ocrsystem.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtensionValidator {

    /**
     * Validator for URL or File image input.
     *
     * Regex:
     * ( represents the starting of group 1.
     * [^\\s]+ represents the string must contain at least one character.
     * ( represents the starting of group 2.
     * \\. Represents the string should follow by a dot(.).
     * (?i) represents the string ignore the case-sensitive.
     * ( represents the starting of group3.
     * jpe?g|png|tif|bmp represents the string end with jpg or jpeg or png or gif or bmp extension.
     * ) represents the ending of the group 3.
     * ) represents the ending of the group 2.
     * $ represents the end of the string.
     * ) represents the ending of the group 1.
     *
     * @param str - given file name with extension
     * @return - boolean - is file valid or not
     */
    public static boolean image(String str) {
        Logger logger = LoggerFactory.getLogger(ExtensionValidator.class);
        String regex
                = "([^\\s]+(\\.(?i)(jpe?g|png|bmp))$)";
        Pattern pattern = Pattern.compile(regex);

        if (str == null) {
            logger.debug("Given String is null.");
            return false;
        }

        /*
        Pattern class contains matcher() method
        to find matching between given string
        and regular expression.
         */
        Matcher matcher = pattern.matcher(str);

        if (matcher.matches()) {
            return true;
        } else {
            logger.debug("Given String doesn't match.");
            return false;
        }
    }
}
