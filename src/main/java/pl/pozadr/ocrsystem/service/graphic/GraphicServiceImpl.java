package pl.pozadr.ocrsystem.service.graphic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pozadr.ocrsystem.model.Graphic;


@Service
public class GraphicServiceImpl implements GraphicService {
    Logger logger = LoggerFactory.getLogger(GraphicServiceImpl.class);
    private final Graphic lastProceededGraphic;


    @Autowired
    public GraphicServiceImpl() {
        this.lastProceededGraphic = new Graphic();
    }


    /**
     * @return - last proceeded optical character recognition result
     */
    @Override
    public Graphic getLastProceededGraphic() {
        return lastProceededGraphic;
    }

    /**
     * Sets last proceeded optical character recognition result.
     * @param url - URL to image.
     * @param ocrResult - optical character recognition result.
     */
    @Override
    public void setLastProceededGraphic(String url, String ocrResult) {
        lastProceededGraphic.setUrl(url);
        lastProceededGraphic.setContent(ocrResult);
    }

}
