package com.testingez.testingez.services.impls;

import com.testingez.testingez.config.NinjasApiConfig;
import com.testingez.testingez.models.dtos.ninja.FactDTO;
import com.testingez.testingez.models.dtos.ninja.JokeDTO;
import com.testingez.testingez.models.dtos.ninja.TriviaDTO;
import com.testingez.testingez.services.NinjaService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class NinjaServiceImpl implements NinjaService {

    private final RestClient restClient;
    private final NinjasApiConfig ninjasApiConfig;

    @Override
    public List<TriviaDTO> fetchTrivia() {
        return List.of(
                Objects.requireNonNull(this.restClient
                        .get()
                        .uri(this.ninjasApiConfig.getTrivia().getUrl())
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .body(TriviaDTO[].class))
        );
    }

    @Override
    public List<FactDTO> fetchFacts() {
        return List.of(
                Objects.requireNonNull(this.restClient
                        .get()
                        .uri(this.ninjasApiConfig.getFacts().getUrl())
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .body(FactDTO[].class))
        );
    }

    @Override
    public List<JokeDTO> fetchJokes() {
        return List.of(
                Objects.requireNonNull(this.restClient
                        .get()
                        .uri(this.ninjasApiConfig.getJokes().getUrl())
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .body(JokeDTO[].class))
        );
    }
}
