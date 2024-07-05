package com.testingez.testingez.api.ninja;

import com.testingez.testingez.api.ninja.dtos.imp.FactDTO;
import com.testingez.testingez.api.ninja.dtos.imp.JokeDTO;
import com.testingez.testingez.api.ninja.dtos.imp.TriviaDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
@Service
public class NinjaServiceImpl implements NinjaService {

    private static final String[] categories = {
            "artliterature",
            "language",
            "sciencenature",
            "general",
            "fooddrink",
            "peopleplaces",
            "geography",
            "historyholidays",
            "entertainment",
            "toysgames",
            "music",
            "mathematics",
            "religionmythology",
            "sportsleisure",
    };


    private final RestClient restClient;
    private final NinjasConfig ninjasConfig;

    @Override
    public FactDTO[] fetchFacts() {
        FactDTO[] facts = new FactDTO[10];
        for (int i = 0; i < 10; i++) {
            facts[i] = Objects.requireNonNull(this.restClient
                    .get()
                    .uri(this.ninjasConfig.getFacts().getUrl())
                    .header("X-Api-Key", this.ninjasConfig.getApiKey())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(FactDTO[].class))[0];
        }
        return facts;
    }

    @Override
    public JokeDTO[] fetchJokes() {
        JokeDTO[] jokes = new JokeDTO[10];
        for (int i = 0; i < 10; i++) {
            jokes[i] = Objects.requireNonNull(this.restClient
                    .get()
                    .uri(this.ninjasConfig.getJokes().getUrl())
                    .header("X-Api-Key", this.ninjasConfig.getApiKey())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(JokeDTO[].class))[0];
        }
        return jokes;
    }

    @Override
    public TriviaDTO[] fetchTrivia() {
        String baseURL = this.ninjasConfig.getTrivia().getUrl();
        TriviaDTO[] trivia = new TriviaDTO[10];
        for (int i = 0; i < 10; i++) {
            trivia[i] = Objects.requireNonNull(
                    this.restClient
                            .get()
                            .uri(baseURL + "?category=" + categories[ThreadLocalRandom.current().nextInt(14)])
                            .header("X-Api-Key", this.ninjasConfig.getApiKey())
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .body(TriviaDTO[].class))[0];
        }
        return trivia;
    }

}
