package pl.pozadr.ocrsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.pozadr.ocrsystem.model.Graphic;
import pl.pozadr.ocrsystem.repository.GraphicRepo;
import pl.pozadr.ocrsystem.service.FileService;
import pl.pozadr.ocrsystem.service.GraphicService;
import pl.pozadr.ocrsystem.service.OcrService;

import java.util.List;
import java.util.Optional;

@Controller
public class OcrController {
    private final OcrService ocrService;
    private final GraphicService graphicService;
    private final FileService fileService;
    private String fileOrUrlProceeded;


    @Autowired
    public OcrController(OcrService ocrService, GraphicRepo graphicRepo, GraphicService graphicService, FileService fileService) {
        this.ocrService = ocrService;
        this.graphicService = graphicService;
        this.fileService = fileService;
        this.fileOrUrlProceeded = "";
    }


    @GetMapping("/ocr-main")
    public String getMainPage(Model model) {
        model.addAttribute("ocrResult", "");
        model.addAttribute("imgUrl", "");
        model.addAttribute("ocrResultContent", false);

        return "ocr-main";
    }

    @GetMapping("/ocr-result")
    public String displayResult(Model model) {
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
        Optional<String> ocrResultOpt = ocrService.doOcrUrl(url);
        if (ocrResultOpt.isPresent()) {
            graphicService.setLastProceededGraphic(url, ocrResultOpt.get());
            graphicService.setDb(url, ocrResultOpt.get());
            return "redirect:/ocr-result";
        } else {
            return "redirect:/ocr-error";
        }
    }

    @PostMapping("/ocr-proceed-file")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        fileOrUrlProceeded = "file";
        if (!file.isEmpty()) {
            boolean isUploaded = fileService.uploadFileFromUser(file);
            if (isUploaded) {
                Optional<String> ocrResultOpt = ocrService.doOcrFile(fileService.getImageFile());
                if (ocrResultOpt.isPresent()) {
                    Optional<String> localImgBase64Opt = fileService.getImgBase64Format();
                    fileService.deleteImageFile();
                    if (localImgBase64Opt.isPresent()) {
                        graphicService.setLastProceededGraphic(localImgBase64Opt.get(), ocrResultOpt.get());
                        graphicService.setDb("Unknown: local file", ocrResultOpt.get());
                        return "redirect:/ocr-result";
                    }
                }
            }
        }
        return "redirect:/ocr-error";
    }

    @GetMapping("/ocr/history")
    public String getHistory(Model model) {
        List<Graphic> graphicList = graphicService.getHistory();
        model.addAttribute("history", graphicList);
        return "ocr-history";
    }

    /*
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        // get error status
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        // TODO: log error details here

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            // display specific error page
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "500";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "403";
            }
        }

        // display generic error
        return "error";
    }

     */
}
