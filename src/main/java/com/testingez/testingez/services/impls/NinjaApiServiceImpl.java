package com.testingez.testingez.services.impls;

import com.testingez.testingez.config.NinjasApiConfig;
import com.testingez.testingez.models.dtos.ninja.FactDTO;
import com.testingez.testingez.services.NinjaApiService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@AllArgsConstructor
@Service
public class NinjaApiServiceImpl implements NinjaApiService {

    private final RestClient restClient;
    private final NinjasApiConfig ninjasApiConfig;

    @Override
    public FactDTO[] fetchFacts() {
        return this.restClient
                .get()
                .uri(ninjasApiConfig.getUrl())
                .header("X-Api-Key", this.ninjasApiConfig.getApiKey())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(FactDTO[].class);
    }
}
