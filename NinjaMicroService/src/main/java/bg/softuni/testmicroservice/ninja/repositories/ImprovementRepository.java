package bg.softuni.testmicroservice.ninja.repositories;

import bg.softuni.testmicroservice.ninja.entities.Improvement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImprovementRepository extends JpaRepository<Improvement, String> {
}
