package com.testingez.mainService.services;

import com.testingez.mainService.exceptions.custom.NinjaMicroServiceException;
import com.testingez.mainService.models.dtos.ninja.*;

import java.util.List;
import java.util.UUID;

public interface NinjaService {

    List<TriviaDTO> fetchTrivia() throws NinjaMicroServiceException;

    List<FactDTO> fetchFacts() throws NinjaMicroServiceException;

    List<JokeDTO> fetchJokes() throws NinjaMicroServiceException;

    List<QuoteDTO> fetchQuotes() throws NinjaMicroServiceException;

    List<FeedbackDTO> fetchFeedback() throws NinjaMicroServiceException;

    void postFeedback(FeedbackDTO feedbackDTO) throws NinjaMicroServiceException;

    void approveFeedback(UUID id) throws NinjaMicroServiceException;

    void disapproveFeedback(UUID id) throws NinjaMicroServiceException;

}
