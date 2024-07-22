package com.testingez.testingez.services.impls;

import com.testingez.testingez.config.NinjasApiConfig;
import com.testingez.testingez.exceptions.custom.NinjaMicroServiceException;
import com.testingez.testingez.models.dtos.ninja.FactDTO;
import com.testingez.testingez.models.dtos.ninja.JokeDTO;
import com.testingez.testingez.models.dtos.ninja.QuoteDTO;
import com.testingez.testingez.models.dtos.ninja.TriviaDTO;
import com.testingez.testingez.services.NinjaService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class NinjaServiceImpl implements NinjaService {

    private final RestClient restClient;
    private final NinjasApiConfig ninjasApiConfig;

    @Cacheable(value = "home", key = "'trivia'")
    @Override
    public List<TriviaDTO> fetchTrivia() throws NinjaMicroServiceException {
        List<TriviaDTO> triviaDTOList;
        try {
            triviaDTOList = new ArrayList<>(List.of(
                    Objects.requireNonNull(this.restClient
                            .get()
                            .uri(this.ninjasApiConfig.getTrivia().getUrl())
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .body(TriviaDTO[].class))
            ));
        } catch (Exception error) {
            throw new NinjaMicroServiceException("We couldn't fetch the " +
                    "required trivia due to NinjaMicroService issues.", error);
        }
        return triviaDTOList;
    }

    @Cacheable(value = "home", key = "'facts'")
    @Override
    public List<FactDTO> fetchFacts() throws NinjaMicroServiceException {
        List<FactDTO> factDTOList;
        try {
            factDTOList = new ArrayList<>(List.of(Objects.requireNonNull(this.restClient
                    .get()
                    .uri(this.ninjasApiConfig.getFacts().getUrl())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(FactDTO[].class))));
        } catch (Exception error) {
            throw new NinjaMicroServiceException("We couldn't fetch the " +
                    "required facts due to NinjaMicroService issues.", error);
        }
        return factDTOList;
    }

    @Cacheable(value = "home", key = "'jokes'")
    @Override
    public List<JokeDTO> fetchJokes() throws NinjaMicroServiceException {
        List<JokeDTO> jokeDTOList;
        try {
            jokeDTOList = new ArrayList<>(List.of(
                    Objects.requireNonNull(this.restClient
                            .get()
                            .uri(this.ninjasApiConfig.getJokes().getUrl())
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .body(JokeDTO[].class))
            ));
        } catch (Exception error) {
            throw new NinjaMicroServiceException("We couldn't fetch the " +
                    "required jokes due to NinjaMicroService issues.", error);
        }
        return jokeDTOList;
    }

    @Cacheable(value = "home", key = "'quotes'")
    @Override
    public List<QuoteDTO> fetchQuotes() throws NinjaMicroServiceException {
        List<QuoteDTO> quoteDTOList;
        try {
            quoteDTOList = new ArrayList<>(List.of(
                    Objects.requireNonNull(this.restClient
                            .get()
                            .uri(this.ninjasApiConfig.getQuotes().getUrl())
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .body(QuoteDTO[].class))
            ));
        } catch (Exception error) {
            throw new NinjaMicroServiceException("We couldn't fetch the" +
                    "required quotes due to NinjaMicroService issues.", error);
        }
        return quoteDTOList;
    }

}