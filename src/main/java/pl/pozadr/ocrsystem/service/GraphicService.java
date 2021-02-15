package pl.pozadr.ocrsystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pozadr.ocrsystem.model.Graphic;
import pl.pozadr.ocrsystem.repository.GraphicRepo;

import java.util.Comparator;
import java.util.List;

@Service
public class GraphicService {
    Logger logger = LoggerFactory.getLogger(GraphicService.class);
    private final GraphicRepo graphicRepo;
    private final Graphic lastProceededGraphic;


    @Autowired
    public GraphicService(GraphicRepo graphicRepo) {
        this.graphicRepo = graphicRepo;
        this.lastProceededGraphic = new Graphic();

    }

    public Graphic getLastProceededGraphic() {
        return lastProceededGraphic;
    }

    public List<Graphic> getHistory() {
        return graphicRepo.findAll();
    }

    public void setLastProceededGraphic(String url, String ocrResult) {
        lastProceededGraphic.setUrl(url);
        lastProceededGraphic.setContent(ocrResult);
    }

    public void setDb(String url, String ocrResult) {
        Graphic saveInDbGraphic = new Graphic();
        saveInDbGraphic.setUrl(url);
        saveInDbGraphic.setContent(ocrResult);
        saveGraphic(saveInDbGraphic);
    }


    /**
     * Saves data ind DB and execute reduceNumberOfHistory method.
     *
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
