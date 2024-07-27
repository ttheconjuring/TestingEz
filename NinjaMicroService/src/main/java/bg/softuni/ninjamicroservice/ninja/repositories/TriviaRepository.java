package bg.softuni.ninjamicroservice.ninja.repositories;

import bg.softuni.ninjamicroservice.ninja.entities.Trivia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TriviaRepository extends JpaRepository<Trivia, Long> {
}
