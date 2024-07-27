package bg.softuni.ninjamicroservice.ninja.repositories;

import bg.softuni.ninjamicroservice.ninja.entities.Joke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JokeRepository extends JpaRepository<Joke, Long> {
}
