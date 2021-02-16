package pl.pozadr.ocrsystem.service.graphic;

import pl.pozadr.ocrsystem.model.Graphic;

import java.util.List;

public interface GraphicService {
    Graphic getLastProceededGraphic();
    void setLastProceededGraphic(String url, String ocrResult);
}
