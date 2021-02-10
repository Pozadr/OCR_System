package pl.pozadr.ocrsystem.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;

import net.sourceforge.tess4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;

@Service
public class OcrService {
    Logger logger = LoggerFactory.getLogger(OcrService.class);

    public Optional<String> doOcrUrl(String url) {
        // File imageFile = new File("eurotext.tif");
        try {
            URL imageUrl = new URL(url);
            BufferedImage imageFile = ImageIO.read(imageUrl);

            ITesseract instance = new Tesseract();
            instance.setDatapath("./tessdata");
            String result = instance.doOCR(imageFile);
            logger.info("OCR finished with result: \n" + result);
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
}