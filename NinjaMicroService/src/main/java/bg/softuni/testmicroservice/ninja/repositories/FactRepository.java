package bg.softuni.testmicroservice.ninja.repositories;

import bg.softuni.testmicroservice.ninja.entities.Fact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactRepository extends JpaRepository<Fact, Long> {
}
