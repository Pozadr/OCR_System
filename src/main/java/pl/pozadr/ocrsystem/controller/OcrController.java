package pl.pozadr.ocrsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.pozadr.ocrsystem.model.Graphic;
import pl.pozadr.ocrsystem.repository.GraphicRepo;
import pl.pozadr.ocrsystem.service.GraphicService;
import pl.pozadr.ocrsystem.service.OcrService;

import java.util.Optional;

@Controller
public class OcrController {
    private final OcrService ocrService;
    private final GraphicService graphicService;
    private boolean error;


    @Autowired
    public OcrController(OcrService ocrService, GraphicRepo graphicRepo, GraphicService graphicService) {
        this.ocrService = ocrService;
        this.graphicService = graphicService;
        this.error = false;
    }

    @GetMapping("ocr-main")
    public String getMainPage(Model model) {
        model.addAttribute("ocrResult", "");
        model.addAttribute("imgUrl", "");
        model.addAttribute("ocrResultContent", false);
        model.addAttribute("error", error);

        return "ocr-main";
    }

    @GetMapping("/ocr-result")
    public String getOcrResultUrl(Model model) {
        Optional<Graphic> lastResult = graphicService.getLast();
        if (lastResult.isPresent()) {
            Graphic lastGraphicObj = lastResult.get();
            String content = lastGraphicObj.getContent();
            String url = lastGraphicObj.getUrl();
            model.addAttribute("ocrResult", content);
            model.addAttribute("imgUrl", url);
            model.addAttribute("ocrResultContent", true);
            model.addAttribute("error", false);
        } else {
            model.addAttribute("ocrResult", "");
            model.addAttribute("ocrResult", "");
            model.addAttribute("ocrResultContent", false);
            model.addAttribute("error", true);
        }
        return "ocr-main";
    }

    @PostMapping("/ocr-proceed")
    public String getOcrResult(String url) {
        Graphic newGraphic = new Graphic();
        Optional<String> ocrResultOpt = ocrService.doOcrUrl(url);
        if (ocrResultOpt.isPresent()) {
            error = false;
            newGraphic.setUrl(url);
            String ocrResult = ocrResultOpt.get();
            newGraphic.setContent(ocrResult);
            graphicService.saveGraphic(newGraphic);
            return "redirect:/ocr-result";
        } else {
            error = true;
            return "redirect:/ocr-main";
        }
    }

    /*
    @GetMapping ("/ocr/all")
    public String getHistory(Model model) {
        List<Graphic> graphicList = graphicService.getHistory();
        model.addAttribute("history", graphicList);
        return "ocr-history";
    }

     */
}
