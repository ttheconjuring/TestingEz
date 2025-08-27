package bg.softuni.ninjaService.ninja.repository;

import bg.softuni.ninjaService.ninja.entities.Fact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactRepository extends JpaRepository<Fact, Long> {
}
