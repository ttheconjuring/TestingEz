package bg.softuni.ninjaService.ninja.repository;

import bg.softuni.ninjaService.ninja.entities.Trivia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TriviaRepository extends JpaRepository<Trivia, Long> {
}
