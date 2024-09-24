package bg.softuni.ninjamicroservice.ninja.service.impl;

import bg.softuni.ninjamicroservice.ninja.config.NinjasConfig;
import bg.softuni.ninjamicroservice.ninja.dtos.*;
import bg.softuni.ninjamicroservice.ninja.entities.Feedback;
import bg.softuni.ninjamicroservice.ninja.repositories.*;
import bg.softuni.ninjamicroservice.ninja.service.NinjaService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class NinjaServiceImpl implements NinjaService {

    private final RestClient restClient;
    private final NinjasConfig ninjasConfig;
    private final FactRepository factRepository;
    private final JokeRepository jokeRepository;
    private final TriviaRepository triviaRepository;
    private final QuoteRepository quoteRepository;
    private final FeedbackRepository feedbackRepository;
    private final ModelMapper modelMapper;

    @Override
    public FactDTO[] fetchFacts() {
        String BASE_URL = this.ninjasConfig.getFacts().getUrl();
        String API_KEY = this.ninjasConfig.getApiKey();
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
        String BASE_URL = this.ninjasConfig.getJokes().getUrl();
        String API_KEY = this.ninjasConfig.getApiKey();
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
        String BASE_URL = this.ninjasConfig.getTrivia().getUrl();
        String API_KEY = this.ninjasConfig.getApiKey();
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
        String BASE_URL = this.ninjasConfig.getQuotes().getUrl();
        String API_KEY = this.ninjasConfig.getApiKey();
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
    public FeedbackDTO disapproveFeedback(UUID id) {
        Optional<Feedback> byId = this.feedbackRepository.findById(id);
        if (byId.isEmpty()) {
            return null;
        }
        byId.get().setDisapproved(true);
        this.feedbackRepository.saveAndFlush(byId.get());
        return this.modelMapper.map(byId.get(), FeedbackDTO.class);
    }

}
