package bg.softuni.ninjaService.utils;

import bg.softuni.ninjaService.ninja.entities.Fact;
import bg.softuni.ninjaService.ninja.entities.Joke;
import bg.softuni.ninjaService.ninja.entities.Quote;
import bg.softuni.ninjaService.ninja.entities.Trivia;
import bg.softuni.ninjaService.ninja.repository.*;
import bg.softuni.ninjaService.ninja.service.NinjaService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Logger;

@AllArgsConstructor
@Component
public class UpdateData {

    private static final Logger LOGGER = Logger.getLogger(UpdateData.class.getName());

    private final TriviaRepository triviaRepository;
    private final FactRepository factRepository;
    private final JokeRepository jokeRepository;
    private final QuoteRepository quoteRepository;
    private final FeedbackRepository feedbackRepository;
    private final NinjaService ninjaService;
    private final ModelMapper modelMapper;

    @Transactional
    // @Scheduled(fixedRate = 600000) // 10 min
    @Scheduled(cron = "0 0 * * * ?")
    public void updateData() {
        LOGGER.info("Updating data");
        this.triviaRepository.deleteAll();
        this.factRepository.deleteAll();
        this.jokeRepository.deleteAll();
        this.quoteRepository.deleteAll();
        this.feedbackRepository.deleteAllByDisapprovedTrue();
        addFacts();
        addJokes();
        addQuotes();
        addTrivia();
        LOGGER.info("Data updated");
    }

    private void addFacts() {
        Arrays.stream(this.ninjaService.fetchFacts())
                .forEach(factDTO ->
                        this.factRepository.saveAndFlush(
                                this.modelMapper.map(factDTO, Fact.class)
                        )
                );
    }

    private void addJokes() {
        Arrays.stream(this.ninjaService.fetchJokes())
                .forEach(jokeDTO ->
                        this.jokeRepository.saveAndFlush(
                                this.modelMapper.map(jokeDTO, Joke.class)
                        )
                );
    }

    private void addQuotes() {
        Arrays.stream(this.ninjaService.fetchQuotes())
                .forEach(quoteDTO ->
                        this.quoteRepository.saveAndFlush(
                                this.modelMapper.map(quoteDTO, Quote.class)
                        )
                );
    }

    private void addTrivia() {
        Arrays.stream(this.ninjaService.fetchTrivia())
                .forEach(triviaDTO ->
                        this.triviaRepository.saveAndFlush(
                                this.modelMapper.map(triviaDTO, Trivia.class)
                        )
                );
    }


}