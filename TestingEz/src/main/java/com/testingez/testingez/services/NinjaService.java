package com.testingez.testingez.services;

import com.testingez.testingez.exceptions.custom.NinjaMicroServiceException;
import com.testingez.testingez.models.dtos.ninja.FactDTO;
import com.testingez.testingez.models.dtos.ninja.JokeDTO;
import com.testingez.testingez.models.dtos.ninja.QuoteDTO;
import com.testingez.testingez.models.dtos.ninja.TriviaDTO;

import java.util.List;

public interface NinjaService {

    List<TriviaDTO> fetchTrivia() throws NinjaMicroServiceException;

    List<FactDTO> fetchFacts() throws NinjaMicroServiceException;

    List<JokeDTO> fetchJokes() throws NinjaMicroServiceException;

    List<QuoteDTO> fetchQuotes() throws NinjaMicroServiceException;

}
