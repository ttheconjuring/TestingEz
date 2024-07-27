package com.testingez.testingez.utils;

import com.testingez.testingez.exceptions.custom.NinjaMicroServiceException;
import com.testingez.testingez.services.NinjaService;
import lombok.AllArgsConstructor;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.logging.Logger;

@AllArgsConstructor
@Component
public class CacheUpdater {

    private static final Logger LOGGER = Logger.getLogger(CacheUpdater.class.getName());

    private final NinjaService ninjaService;
    private final ConcurrentMapCacheManager cacheManager;

    @Scheduled(fixedRate = 360000) // 6 min
    public void updateCache() throws NinjaMicroServiceException {
        LOGGER.info("Updating home page cache");
        Objects.requireNonNull(cacheManager.getCache("home")).clear();
        try {
            this.ninjaService.fetchTrivia();
            this.ninjaService.fetchFacts();
            this.ninjaService.fetchJokes();
            this.ninjaService.fetchQuotes();
            this.ninjaService.fetchImprovements();
        } catch (Exception error) {
            throw new NinjaMicroServiceException("We couldn't update cache due to " +
                    "NinjaMicroService issues.", error);
        }
        LOGGER.info("Home page cache updated");
    }

}
