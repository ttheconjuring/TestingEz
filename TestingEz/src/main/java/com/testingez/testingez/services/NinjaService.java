package com.testingez.testingez.services;

import com.testingez.testingez.exceptions.custom.NinjaMicroServiceException;
import com.testingez.testingez.models.dtos.ninja.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NinjaService {

    List<TriviaDTO> fetchTrivia() throws NinjaMicroServiceException;

    List<FactDTO> fetchFacts() throws NinjaMicroServiceException;

    List<JokeDTO> fetchJokes() throws NinjaMicroServiceException;

    List<QuoteDTO> fetchQuotes() throws NinjaMicroServiceException;

    List<ImprovementDTO> fetchImprovements() throws NinjaMicroServiceException;

    void postImprovement(ImprovementDTO improvementData) throws NinjaMicroServiceException;
}
