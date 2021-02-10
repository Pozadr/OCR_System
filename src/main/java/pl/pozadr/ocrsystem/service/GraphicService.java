package pl.pozadr.ocrsystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pozadr.ocrsystem.model.Graphic;
import pl.pozadr.ocrsystem.repository.GraphicRepo;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class GraphicService {
    Logger logger = LoggerFactory.getLogger(GraphicService.class);
    private final GraphicRepo graphicRepo;

    @Autowired
    public GraphicService(GraphicRepo graphicRepo) {
        this.graphicRepo = graphicRepo;
    }


    public Optional<Graphic> getLast() {
        return graphicRepo.findAll().stream().max(Comparator.comparing(Graphic::getId));
    }

    public List<Graphic> getHistory() {
        return graphicRepo.findAll();
    }

    public void saveGraphic(Graphic graphicToSave) {
        try {
            Graphic saved = graphicRepo.save(graphicToSave);

        } catch (IllegalArgumentException ex) {
            logger.error(ex.getMessage());
        }
    }

}
