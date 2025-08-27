package bg.softuni.ninjaService.ninja.repository;

import bg.softuni.ninjaService.ninja.entities.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
}
