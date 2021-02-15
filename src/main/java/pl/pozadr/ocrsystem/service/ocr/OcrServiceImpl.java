package pl.pozadr.ocrsystem.service.ocr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;

import net.sourceforge.tess4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.pozadr.ocrsystem.config.AppConstants;

import javax.imageio.ImageIO;

@Service
public class OcrServiceImpl implements OcrService {
    Logger logger = LoggerFactory.getLogger(OcrServiceImpl.class);


    @Override
    public Optional<String> doOcrUrl(String url) {
        try {
            URL imageUrl = new URL(url);
            BufferedImage imageFile = ImageIO.read(imageUrl);

            ITesseract instance = new Tesseract();
            instance.setDatapath(AppConstants.TESSERACT_DATA_PATH);
            String result = instance.doOCR(imageFile);
            logger.debug("OCR finished with result: \n" + result);
            return Optional.of(result);
        } catch (TesseractException e) {
            logger.error("Error: Tesseract OCR library: {}", e.getMessage());
        } catch (MalformedURLException e) {
            logger.error("Error: URL: {}", e.getMessage());
        } catch (IOException e) {
            logger.error("Error: ImageIO: {}", Arrays.toString(e.getStackTrace()));

        }
        return Optional.empty();
    }

    @Override
    public Optional<String> doOcrFile(File imageFile) {
        try {
            ITesseract instance = new Tesseract();
            instance.setDatapath(AppConstants.TESSERACT_DATA_PATH);
            String result = instance.doOCR(imageFile);
            logger.debug("OCR finished with result: \n" + result);
            return Optional.of(result);

        } catch (TesseractException e) {
            logger.error("Error: Tesseract OCR library: {}", e.getMessage());
        }
        return Optional.empty();
    }
}