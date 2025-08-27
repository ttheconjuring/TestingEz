package bg.softuni.ninjaService.ninja.repository;

import bg.softuni.ninjaService.ninja.entities.Joke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JokeRepository extends JpaRepository<Joke, Long> {
}
