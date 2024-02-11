package pl.chmielewski.rfid.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.chmielewski.rfid.entities.CardTouches;

@Repository
public interface CardTouchesRepo extends JpaRepository<CardTouches, Long> {
}
