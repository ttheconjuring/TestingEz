package bg.softuni.testmicroservice.ninja.service;


import bg.softuni.testmicroservice.ninja.dtos.FactDTO;
import bg.softuni.testmicroservice.ninja.dtos.JokeDTO;
import bg.softuni.testmicroservice.ninja.dtos.QuoteDTO;
import bg.softuni.testmicroservice.ninja.dtos.TriviaDTO;

public interface NinjaService {

    FactDTO[] fetchFacts();

    JokeDTO[] fetchJokes();

    TriviaDTO[] fetchTrivia();

    QuoteDTO[] fetchQuotes();

    FactDTO[] getFacts();

    JokeDTO[] getJokes();

    TriviaDTO[] getTrivia();

    QuoteDTO[] getQuotes();

}
