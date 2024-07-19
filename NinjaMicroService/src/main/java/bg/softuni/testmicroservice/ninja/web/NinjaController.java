package bg.softuni.testmicroservice.ninja.web;

import bg.softuni.testmicroservice.ninja.dtos.FactDTO;
import bg.softuni.testmicroservice.ninja.dtos.JokeDTO;
import bg.softuni.testmicroservice.ninja.dtos.QuoteDTO;
import bg.softuni.testmicroservice.ninja.dtos.TriviaDTO;
import org.springframework.http.ResponseEntity;

public interface NinjaController {

    ResponseEntity<FactDTO[]> getFacts();

    ResponseEntity<JokeDTO[]> getJokes();

    ResponseEntity<TriviaDTO[]> getTrivia();

    ResponseEntity<QuoteDTO[]> getQuotes();

}
