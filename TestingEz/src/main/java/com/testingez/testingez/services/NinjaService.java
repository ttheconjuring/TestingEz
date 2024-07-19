package com.testingez.testingez.services;

import com.testingez.testingez.models.dtos.ninja.FactDTO;
import com.testingez.testingez.models.dtos.ninja.JokeDTO;
import com.testingez.testingez.models.dtos.ninja.QuoteDTO;
import com.testingez.testingez.models.dtos.ninja.TriviaDTO;

import java.util.List;

public interface NinjaService {

    List<TriviaDTO> fetchTrivia();

    List<FactDTO> fetchFacts();

    List<JokeDTO> fetchJokes();

    List<QuoteDTO> fetchQuotes();
}
