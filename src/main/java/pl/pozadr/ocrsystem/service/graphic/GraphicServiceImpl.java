package pl.pozadr.ocrsystem.service.graphic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pozadr.ocrsystem.model.Graphic;
import pl.pozadr.ocrsystem.repository.GraphicRepo;
import pl.pozadr.ocrsystem.validator.DbValidator;

import java.util.Comparator;
import java.util.List;

@Service
public class GraphicServiceImpl implements GraphicService {
    Logger logger = LoggerFactory.getLogger(GraphicServiceImpl.class);
    private final GraphicRepo graphicRepo;
    private final Graphic lastProceededGraphic;


    @Autowired
    public GraphicServiceImpl(GraphicRepo graphicRepo) {
        this.graphicRepo = graphicRepo;
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
     * Fetches data from DB. Returns historical OCR data as a List of Graphic(model).
     * @return - list of Graphic(model)
     */
    @Override
    public List<Graphic> getHistory() {
        return graphicRepo.findAll();
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

    /**
     * Prepares data from Controller to save and performs saving.
     * @param url - URL to image.
     * @param ocrResult - optical character recognition result.
     */
    @Override
    public void setDb(String url, String ocrResult) {
        Graphic graphicToSaveInDb = new Graphic();
        String urlToSave = DbValidator.validateUrl(url);
        String contentToSave = DbValidator.validateContent(ocrResult);

        graphicToSaveInDb.setUrl(urlToSave);
        graphicToSaveInDb.setContent(contentToSave);
        saveGraphic(graphicToSaveInDb);
    }

    /**
     * Saves data in DB and execute reduceNumberOfHistory method.
     * @param graphicToSave - object to save in the DB from Controller.
     */
    private void saveGraphic(Graphic graphicToSave) {
        try {
            graphicRepo.save(graphicToSave);
            reduceNumberOfHistory();

        } catch (IllegalArgumentException ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * Reduces maximum number of records in the DB to 10.
     * Recursion used.
     */
    private void reduceNumberOfHistory() {
        List<Graphic> allGraphic = getHistory();
        if (allGraphic.size() <= 10) {
            logger.debug("Number of records in db: {}", allGraphic.size());
        } else {
            try {
                Long minId = allGraphic.stream()
                        .min(Comparator.comparing(Graphic::getId)).get().getId();
                graphicRepo.deleteById(minId);
                logger.debug("Record with Id={} deleted.", minId);
                reduceNumberOfHistory();
            } catch (IllegalArgumentException ex) {
                logger.error(ex.getMessage());
            }
        }
    }

}
