package com.testingez.testingez.services;

import com.testingez.testingez.exceptions.custom.NinjaMicroServiceException;
import com.testingez.testingez.models.dtos.ninja.*;

import java.util.List;
import java.util.UUID;

public interface NinjaService {

    List<TriviaDTO> fetchTrivia() throws NinjaMicroServiceException;

    List<FactDTO> fetchFacts() throws NinjaMicroServiceException;

    List<JokeDTO> fetchJokes() throws NinjaMicroServiceException;

    List<QuoteDTO> fetchQuotes() throws NinjaMicroServiceException;

    List<ImprovementDTO> fetchImprovements() throws NinjaMicroServiceException;

    void postImprovement(ImprovementDTO improvementData) throws NinjaMicroServiceException;

    void deleteImprovement(UUID id) throws NinjaMicroServiceException;
}
