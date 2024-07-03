package com.testingez.testingez.api.ninja;

import com.testingez.testingez.api.ninja.dtos.FactDTO;
import com.testingez.testingez.api.ninja.dtos.JokeDTO;

public interface NinjaService {

    FactDTO[] fetchFacts();

    JokeDTO[] fetchJokes();

}
