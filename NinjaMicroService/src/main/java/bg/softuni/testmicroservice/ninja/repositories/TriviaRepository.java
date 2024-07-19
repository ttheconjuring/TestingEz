package bg.softuni.testmicroservice.ninja.repositories;

import bg.softuni.testmicroservice.ninja.entities.Trivia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TriviaRepository extends JpaRepository<Trivia, Long> {
}
