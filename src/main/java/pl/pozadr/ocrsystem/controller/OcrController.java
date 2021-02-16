package pl.pozadr.ocrsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.pozadr.ocrsystem.model.Graphic;
import pl.pozadr.ocrsystem.service.file.FileService;
import pl.pozadr.ocrsystem.service.graphic.GraphicService;
import pl.pozadr.ocrsystem.service.ocr.OcrService;
import pl.pozadr.ocrsystem.validator.ExtensionValidator;

import java.util.Optional;

/**
 * Takes user input and displays a response from the application API.
 */
@Controller
public class OcrController {
    private final OcrService ocrService;
    private final GraphicService graphicService;
    private final FileService fileService;
    private String fileOrUrlProceeded;
    private Long numberOfOcrSuccess;
    private Long numberOfSiteVisits;


    @Autowired
    public OcrController(OcrService ocrService, GraphicService graphicService, FileService fileService) {
        this.ocrService = ocrService;
        this.graphicService = graphicService;
        this.fileService = fileService;
        this.fileOrUrlProceeded = "";
        this.numberOfOcrSuccess = 0L;
        this.numberOfSiteVisits = 0L;
    }


    @GetMapping("/ocr-main")
    public String getMainPage(Model model) {
        numberOfSiteVisits++;
        model.addAttribute("ocrResult", "");
        model.addAttribute("imgUrl", "");
        model.addAttribute("ocrResultContent", false);

        return "ocr-main";
    }

    @GetMapping("/ocr-result")
    public String displayResult(Model model) {
        numberOfOcrSuccess++;
        Graphic lastProceededGraphic = graphicService.getLastProceededGraphic();
        String content = lastProceededGraphic.getContent();
        String url = lastProceededGraphic.getUrl();

        model.addAttribute("ocrResult", content);
        model.addAttribute("imgUrl", url);
        model.addAttribute("ocrResultContent", true);
        model.addAttribute("fileOrUrl", fileOrUrlProceeded);
        return "ocr-result";
    }

    @GetMapping("/ocr-error")
    public String getErrorPage(Model model) {
        model.addAttribute("ocrResult", "");
        model.addAttribute("imgUrl", "");
        model.addAttribute("ocrResultContent", false);
        model.addAttribute("fileOrUrl", fileOrUrlProceeded);
        return "ocr-error";
    }

    @PostMapping("/ocr-proceed-url")
    public String getOcrResultUrl(String url) {
        fileOrUrlProceeded = "url";
        boolean isUrlValid = ExtensionValidator.image(url);
        if (isUrlValid) {
            Optional<String> ocrResultOpt = ocrService.doOcrUrl(url);
            if (ocrResultOpt.isPresent()) {
                graphicService.setLastProceededGraphic(url, ocrResultOpt.get());
                return "redirect:/ocr-result";
            }
        }
        return "redirect:/ocr-error";
    }

    @PostMapping("/ocr-proceed-file")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        fileOrUrlProceeded = "file";
        String filename = file.getOriginalFilename();
        boolean isFileValid = ExtensionValidator.image(filename);
        if (isFileValid) {
            boolean isUploaded = fileService.uploadFileFromUser(file);
            if (isUploaded) {
                Optional<String> ocrResultOpt = ocrService.doOcrFile(fileService.getImageFile());
                if (ocrResultOpt.isPresent()) {
                    Optional<String> localImgBase64Opt = fileService.getImgBase64Format();
                    fileService.deleteImageFile();
                    if (localImgBase64Opt.isPresent()) {
                        graphicService.setLastProceededGraphic(localImgBase64Opt.get(), ocrResultOpt.get());
                        return "redirect:/ocr-result";
                    }
                }
            }
        }
        return "redirect:/ocr-error";
    }

    @GetMapping("/ocr/history")
    public String getHistory(Model model) {
        model.addAttribute("numberOfOcrSuccess", numberOfOcrSuccess);
        model.addAttribute("numberOfSiteVisits", numberOfSiteVisits);
        return "ocr-history";
    }

}
