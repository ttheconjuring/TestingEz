package com.testingez.testingez.api.ninja;

import com.testingez.testingez.api.ninja.dtos.FactDTO;
import com.testingez.testingez.api.ninja.dtos.JokeDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@AllArgsConstructor
@Service
public class NinjaServiceImpl implements NinjaService {

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
}
