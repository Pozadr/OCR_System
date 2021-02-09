package pl.pozadr.ocrsystem;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import net.sourceforge.tess4j.*;

import javax.imageio.ImageIO;

public class TesseractService {

    public static void main(String[] args) throws IOException {
        // File imageFile = new File("eurotext.tif");
        // URL imageUrl = new URL("http://thewowstyle.com/wp-content/uploads/2015/03/hplyrikz-sad-quotes-ihdcqsh5.png");
        URL imageUrl = new URL("https://i0.wp.com/sekretypiekna.com.pl/wp-content/uploads/2018/09/dziwne.jpg?resize=572%2C600");
        BufferedImage imageFile = ImageIO.read(imageUrl);
        /**
         * JNA Interface Mapping
         **/
        ITesseract instance = new Tesseract();

        /**
         * You either set your own tessdata folder with your custom language pack or
         * use LoadLibs to load the default tessdata folder for you.
         **/
        instance.setDatapath("./tessdata");

        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
}