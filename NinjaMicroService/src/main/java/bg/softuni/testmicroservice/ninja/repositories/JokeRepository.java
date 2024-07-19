package bg.softuni.testmicroservice.ninja.repositories;

import bg.softuni.testmicroservice.ninja.entities.Joke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JokeRepository extends JpaRepository<Joke, Long> {
}
