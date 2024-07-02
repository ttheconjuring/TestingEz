package com.testingez.testingez.services.impls;

import com.testingez.testingez.config.NinjasApiConfig;
import com.testingez.testingez.models.dtos.api.FunFactDTO;
import com.testingez.testingez.services.FunFactService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@AllArgsConstructor
@Service
public class FunFactServiceImpl implements FunFactService {

    private final RestClient restClient;
    private final NinjasApiConfig ninjasApiConfig;

    @Override
    public FunFactDTO[] fetch() {
        return this.restClient
                .get()
                .uri(ninjasApiConfig.getUrl())
                .header("X-Api-Key", this.ninjasApiConfig.getApiKey())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(FunFactDTO[].class);
    }
}
