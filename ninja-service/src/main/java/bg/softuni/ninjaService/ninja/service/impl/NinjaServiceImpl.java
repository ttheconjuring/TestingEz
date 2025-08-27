package bg.softuni.ninjaService.ninja.service.impl;

import bg.softuni.ninjaService.ninja.config.NinjaConfig;
import bg.softuni.ninjaService.ninja.dtos.*;
import bg.softuni.ninjaService.ninja.entities.Feedback;
import bg.softuni.ninjaService.ninja.repository.*;
import bg.softuni.ninjaService.ninja.service.NinjaService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@Service
public class NinjaServiceImpl implements NinjaService {

    private final RestClient restClient;
    private final NinjaConfig ninjaConfig;
    private final FactRepository factRepository;
    private final JokeRepository jokeRepository;
    private final TriviaRepository triviaRepository;
    private final QuoteRepository quoteRepository;
    private final FeedbackRepository feedbackRepository;
    private final ModelMapper modelMapper;

    @Override
    public FactDTO[] fetchFacts() {
        String BASE_URL = this.ninjaConfig.getFacts().getUrl();
        String API_KEY = this.ninjaConfig.getApiKey();
        FactDTO[] facts = new FactDTO[5];
        for (int i = 0; i < 5; i++) {
            facts[i] = Objects.requireNonNull(this.restClient
                    .get()
                    .uri(BASE_URL)
                    .header("X-Api-Key", API_KEY)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(FactDTO[].class))[0];
        }
        return facts;
    }

    @Override
    public JokeDTO[] fetchJokes() {
        String BASE_URL = this.ninjaConfig.getJokes().getUrl();
        String API_KEY = this.ninjaConfig.getApiKey();
        JokeDTO[] jokes = new JokeDTO[5];
        for (int i = 0; i < 5; i++) {
            jokes[i] = Objects.requireNonNull(this.restClient
                    .get()
                    .uri(BASE_URL)
                    .header("X-Api-Key", API_KEY)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(JokeDTO[].class))[0];
        }
        return jokes;
    }

    @Override
    public TriviaDTO[] fetchTrivia() {
        String BASE_URL = this.ninjaConfig.getTrivia().getUrl();
        String API_KEY = this.ninjaConfig.getApiKey();
        TriviaDTO[] trivia = new TriviaDTO[5];
        for (int i = 0; i < 5; i++) {
            trivia[i] = Objects.requireNonNull(
                    this.restClient
                            .get()
                            .uri(BASE_URL)
                            .header("X-Api-Key", API_KEY)
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .body(TriviaDTO[].class))[0];
        }
        return trivia;
    }

    @Override
    public QuoteDTO[] fetchQuotes() {
        String BASE_URL = this.ninjaConfig.getQuotes().getUrl();
        String API_KEY = this.ninjaConfig.getApiKey();
        QuoteDTO[] quotes = new QuoteDTO[5];
        for (int i = 0; i < 5; i++) {
            quotes[i] = Objects.requireNonNull(
                    this.restClient
                            .get()
                            .uri(BASE_URL)
                            .header("X-Api-Key", API_KEY)
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .body(QuoteDTO[].class))[0];

        }
        return quotes;
    }

    @Override
    public FactDTO[] getFacts() {
        return this.factRepository.findAll()
                .stream()
                .map(fact -> this.modelMapper.map(fact, FactDTO.class))
                .toArray(FactDTO[]::new);
    }

    @Override
    public JokeDTO[] getJokes() {
        return this.jokeRepository.findAll()
                .stream()
                .map(joke -> this.modelMapper.map(joke, JokeDTO.class))
                .toArray(JokeDTO[]::new);
    }

    @Override
    public TriviaDTO[] getTrivia() {
        return this.triviaRepository.findAll()
                .stream()
                .map(trivia -> this.modelMapper.map(trivia, TriviaDTO.class))
                .toArray(TriviaDTO[]::new);
    }

    @Override
    public QuoteDTO[] getQuotes() {
        return this.quoteRepository.findAll()
                .stream()
                .map(quote -> this.modelMapper.map(quote, QuoteDTO.class))
                .toArray(QuoteDTO[]::new);
    }

    @Override
    public FeedbackDTO[] getFeedbacks() {
        return this.feedbackRepository.findAll()
                .stream()
                .map(feedback -> this.modelMapper.map(feedback, FeedbackDTO.class))
                .toArray(FeedbackDTO[]::new);
    }

    @Override
    public FeedbackDTO postFeedback(FeedbackDTO feedbackDTO) {
        feedbackDTO.setApproved(false);
        feedbackDTO.setDisapproved(false);
        return this.modelMapper.map(this.feedbackRepository.saveAndFlush(
                this.modelMapper.map(feedbackDTO, Feedback.class)), FeedbackDTO.class);
    }

    @Override
    public FeedbackDTO approveFeedback(UUID id) {
        Feedback feedback = this.feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback with UUID: " + id + " not found!"));
        feedback.setDisapproved(false);
        feedback.setApproved(true);
        this.feedbackRepository.saveAndFlush(feedback);
        return this.modelMapper.map(feedback, FeedbackDTO.class);
    }

    @Override
    public FeedbackDTO disapproveFeedback(UUID id) {
        Feedback feedback = this.feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback with UUID: " + id + " not found!"));
        feedback.setDisapproved(true);
        feedback.setApproved(false);
        this.feedbackRepository.saveAndFlush(feedback);
        return this.modelMapper.map(feedback, FeedbackDTO.class);
    }

}
