package bg.softuni.ninjamicroservice.utils;

import bg.softuni.ninjamicroservice.ninja.entities.Fact;
import bg.softuni.ninjamicroservice.ninja.entities.Joke;
import bg.softuni.ninjamicroservice.ninja.entities.Quote;
import bg.softuni.ninjamicroservice.ninja.entities.Trivia;
import bg.softuni.ninjamicroservice.ninja.repositories.FactRepository;
import bg.softuni.ninjamicroservice.ninja.repositories.JokeRepository;
import bg.softuni.ninjamicroservice.ninja.repositories.QuoteRepository;
import bg.softuni.ninjamicroservice.ninja.repositories.TriviaRepository;
import bg.softuni.ninjamicroservice.ninja.service.NinjaService;
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
    private final NinjaService ninjaService;
    private final ModelMapper modelMapper;

    // @Scheduled(fixedRate = 600000) // 10 min
    @Scheduled(cron = "0 0 * * * ?")
    public void updateData() {
        LOGGER.info("Updating data");
        this.triviaRepository.deleteAll();
        this.factRepository.deleteAll();
        this.jokeRepository.deleteAll();
        this.quoteRepository.deleteAll();
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