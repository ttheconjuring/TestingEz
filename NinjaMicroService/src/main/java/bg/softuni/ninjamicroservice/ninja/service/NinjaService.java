package bg.softuni.ninjamicroservice.ninja.service;


import bg.softuni.ninjamicroservice.ninja.dtos.*;

import java.util.UUID;

public interface NinjaService {

    FactDTO[] fetchFacts();

    JokeDTO[] fetchJokes();

    TriviaDTO[] fetchTrivia();

    QuoteDTO[] fetchQuotes();

    FactDTO[] getFacts();

    JokeDTO[] getJokes();

    TriviaDTO[] getTrivia();

    QuoteDTO[] getQuotes();

    FeedbackDTO[] getFeedbacks();

    FeedbackDTO postFeedback(FeedbackDTO feedbackDTO);

    FeedbackDTO disapproveFeedback(UUID id);
}
