package pl.pozadr.ocrsystem.service.ocr;

import java.io.File;
import java.util.Optional;

public interface OcrService {
    Optional<String> doOcrUrl(String url);
    Optional<String> doOcrFile(File imageFile);
}
