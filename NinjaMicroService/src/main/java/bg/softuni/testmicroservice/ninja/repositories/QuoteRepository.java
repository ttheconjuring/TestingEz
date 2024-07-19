package bg.softuni.testmicroservice.ninja.repositories;

import bg.softuni.testmicroservice.ninja.entities.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
}
