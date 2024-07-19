package bg.softuni.testmicroservice.ninja.config;

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
public class NinjasConfig {

    private String apiKey;
    private ApiConfig facts = new ApiConfig();
    private ApiConfig jokes = new ApiConfig();
    private ApiConfig trivia = new ApiConfig();
    private ApiConfig quotes = new ApiConfig();

    @PostConstruct
    public void checkConfiguration() {
        verifyNotNullOrEmpty("apiKey", apiKey);
        facts.checkConfiguration("facts");
        jokes.checkConfiguration("jokes");
        trivia.checkConfiguration("trivia");
        trivia.checkConfiguration("quotes");
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
