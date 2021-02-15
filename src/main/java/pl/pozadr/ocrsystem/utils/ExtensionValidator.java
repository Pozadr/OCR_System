package pl.pozadr.ocrsystem.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtensionValidator {

    /**
     * Regex:
     *     ( represents the starting of group 1.
     *     [^\\s]+ represents the string must contain at least one character.
     *     ( represents the starting of group 2.
     *     \\. Represents the string should follow by a dot(.).
     *     (?i) represents the string ignore the case-sensitive.
     *     ( represents the starting of group3.
     *     jpe?g|png|tif|bmp represents the string end with jpg or jpeg or png or gif or bmp extension.
     *     ) represents the ending of the group 3.
     *     ) represents the ending of the group 2.
     *     $ represents the end of the string.
     *     ) represents the ending of the group 1.
     * @param str - given file name with extension
     * @return - boolean - is file valid or not
     */
    public static boolean image(String str) {
        // Regex to check valid image file extension.
        String regex
                = "([^\\s]+(\\.(?i)(jpe?g|png|bmp))$)";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the string is empty
        // return false
        if (str == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given string
        // and regular expression.
        Matcher m = p.matcher(str);

        // Return if the string
        // matched the ReGex
        return m.matches();
    }
}
