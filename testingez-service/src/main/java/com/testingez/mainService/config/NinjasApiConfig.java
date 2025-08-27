package com.testingez.mainService.config;

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
@ConfigurationProperties(prefix = "ninja.api")
public class NinjasApiConfig {

    private ApiConfig trivia = new ApiConfig();
    private ApiConfig facts = new ApiConfig();
    private ApiConfig jokes = new ApiConfig();
    private ApiConfig quotes = new ApiConfig();
    private ApiConfig feedback = new ApiConfig();

    @PostConstruct
    public void checkConfiguration() {
        trivia.checkConfiguration("trivia");
        facts.checkConfiguration("facts");
        jokes.checkConfiguration("jokes");
        quotes.checkConfiguration("quotes");
        feedback.checkConfiguration("feedback");
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ApiConfig {

        private String url;

        public void checkConfiguration(String configName) {
            verifyNotNullOrEmpty(configName + ".url", url);
        }

    }

    private static void verifyNotNullOrEmpty(String name, String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Property " + name + " cannot accept null or empty value!");
        }
    }

}
