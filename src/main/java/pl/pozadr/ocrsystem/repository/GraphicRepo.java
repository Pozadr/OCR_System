package pl.pozadr.ocrsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pozadr.ocrsystem.model.Graphic;

@Repository
public interface GraphicRepo extends JpaRepository<Graphic, Long> {
}
