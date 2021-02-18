package pl.pozadr.ocrsystem.config;

/**
 * Constants for the entire application.
 */
public final class AppConstants {
    public static final int MAX_DB_URL_LENGTH = 1000;
    public static final int MAX_DB_CONTENT_LENGTH = 1000;
    public static final String TESSERACT_DATA_PATH = "./.apt/usr/share/tesseract-ocr/4.00/tessdata";
    //"/app.apt/usr/bin/tesseract"; //"./tessdata";
    public static final String UPLOAD_DIR = "./uploads/";

    private AppConstants() {

    }
}
