package pl.pozadr.ocrsystem.validator;

import pl.pozadr.ocrsystem.config.AppConstants;

public class DbValidator {

    /**
     * Trims the data to the maximum specified database parameters before saving.
     * @param url - URL to trim.
     * @return - trimmed URL.
     */
    public static String validateUrl(String url) {
        if (url.length() > AppConstants.MAX_DB_URL_LENGTH) {
            return url.substring(0, AppConstants.MAX_DB_URL_LENGTH);
        }
        return url;
    }

    /**
     * Trims the data to the maximum specified database parameters before saving.
     * @param content - content to trim.
     * @return - trimmed content.
     */
    public static String validateContent(String content) {
        if (content.length() > AppConstants.MAX_DB_URL_LENGTH) {
            return content.substring(0, AppConstants.MAX_DB_URL_LENGTH);
        }
        return content;
    }
}
