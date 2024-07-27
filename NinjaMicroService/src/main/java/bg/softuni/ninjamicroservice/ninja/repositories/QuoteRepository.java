package bg.softuni.ninjamicroservice.ninja.repositories;

import bg.softuni.ninjamicroservice.ninja.entities.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
}
