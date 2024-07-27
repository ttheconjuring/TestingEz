package bg.softuni.ninjamicroservice.ninja.web;

import bg.softuni.ninjamicroservice.ninja.dtos.*;
import org.springframework.http.ResponseEntity;

public interface NinjaController {

    ResponseEntity<FactDTO[]> getFacts();

    ResponseEntity<JokeDTO[]> getJokes();

    ResponseEntity<TriviaDTO[]> getTrivia();

    ResponseEntity<QuoteDTO[]> getQuotes();

    ResponseEntity<ImprovementDTO[]> getImprovements();

    ResponseEntity<String> postImprovement(ImprovementDTO improvement);

}
