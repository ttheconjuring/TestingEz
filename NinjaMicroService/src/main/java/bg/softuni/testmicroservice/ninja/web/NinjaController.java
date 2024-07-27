package bg.softuni.testmicroservice.ninja.web;

import bg.softuni.testmicroservice.ninja.dtos.*;
import org.springframework.http.ResponseEntity;

public interface NinjaController {

    ResponseEntity<FactDTO[]> getFacts();

    ResponseEntity<JokeDTO[]> getJokes();

    ResponseEntity<TriviaDTO[]> getTrivia();

    ResponseEntity<QuoteDTO[]> getQuotes();

    ResponseEntity<ImprovementDTO[]> getImprovements();

}
