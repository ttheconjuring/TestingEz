package bg.softuni.ninjamicroservice.ninja.web;

import bg.softuni.ninjamicroservice.ninja.dtos.*;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface NinjaController {

    ResponseEntity<FactDTO[]> getFacts();

    ResponseEntity<JokeDTO[]> getJokes();

    ResponseEntity<TriviaDTO[]> getTrivia();

    ResponseEntity<QuoteDTO[]> getQuotes();

    ResponseEntity<FeedbackDTO[]> getFeedbacks();

    ResponseEntity<?> postFeedback(FeedbackDTO improvement);

    ResponseEntity<?> approveFeedback(UUID id);

    ResponseEntity<?> disapproveFeedback(UUID id);

}
