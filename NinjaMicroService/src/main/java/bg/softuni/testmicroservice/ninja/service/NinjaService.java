package bg.softuni.testmicroservice.ninja.service;


import bg.softuni.testmicroservice.ninja.dtos.*;

public interface NinjaService {

    FactDTO[] fetchFacts();

    JokeDTO[] fetchJokes();

    TriviaDTO[] fetchTrivia();

    QuoteDTO[] fetchQuotes();

    FactDTO[] getFacts();

    JokeDTO[] getJokes();

    TriviaDTO[] getTrivia();

    QuoteDTO[] getQuotes();

    ImprovementDTO[] getImprovements();
}
