package pl.pozadr.ocrsystem.config;

/**
 * Constants for the entire application.
 * Class is final to prevent it from being extended.
 * It can't be instantiated because of a private constructor.
 */
public final class AppConstants {
    private AppConstants() {
    }

    public static final int MAX_DB_URL_LENGTH = 1000;
    public static final int MAX_DB_CONTENT_LENGTH = 1000;
    public static final String TESSERACT_DATA_PATH = "./tessdata";
    public static final String UPLOAD_DIR = "./uploads/";
}
