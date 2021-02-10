package pl.pozadr.ocrsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.pozadr.ocrsystem.model.Graphic;
import pl.pozadr.ocrsystem.service.OcrService;

import java.util.Optional;

@RestController
public class OcrController {
    private final OcrService ocrService;

    @Autowired
    public OcrController(OcrService ocrService) {
        this.ocrService = ocrService;
    }

    @PostMapping("/ocr")
    public ResponseEntity<String> getOcrResult(@RequestBody Graphic graphic) {
        Optional<String> ocrResultOpt =  ocrService.doOcrUrl(graphic.getUrl());
        return ocrResultOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
