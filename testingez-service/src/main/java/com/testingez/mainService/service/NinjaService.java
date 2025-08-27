package com.testingez.mainService.service;

import com.testingez.mainService.exception.custom.NinjaMicroServiceException;
import com.testingez.mainService.model.dtos.ninja.*;

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
