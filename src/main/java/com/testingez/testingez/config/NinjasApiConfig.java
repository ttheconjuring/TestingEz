package com.testingez.testingez.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "api.ninjas")
public class NinjasApiConfig {

    private String url;

    private String apiKey;

    @PostConstruct
    public void checkConfiguration() {
        verifyNotNullOrEmpty("url", url);
        verifyNotNullOrEmpty("apiKey", apiKey);
    }

    private static void verifyNotNullOrEmpty(String name, String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Property " + name + " cannot accept null or empty value!");
        }
    }

}
