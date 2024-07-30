package bg.softuni.ninjamicroservice.ninja.repositories;

import bg.softuni.ninjamicroservice.ninja.entities.Improvement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImprovementRepository extends JpaRepository<Improvement, UUID> {
}
