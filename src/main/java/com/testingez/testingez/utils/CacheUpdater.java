package com.testingez.testingez.utils;

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

    private static final Logger logger = Logger.getLogger(CacheUpdater.class.getName());
    private final NinjaService ninjaService;
    private final ConcurrentMapCacheManager cacheManager;

    // TODO: test it
    @Scheduled(fixedRate = 360000) // 6 min
    public void updateCache() {
        logger.info("Updating cache");
        Objects.requireNonNull(cacheManager.getCache("home")).clear();
        this.ninjaService.fetchTrivia();
        this.ninjaService.fetchFacts();
        this.ninjaService.fetchJokes();
        this.ninjaService.fetchQuotes();
        logger.info("Cache updated");
    }

}
