package com.testingez.testingez.api.ninja;

import com.testingez.testingez.api.ninja.dtos.imp.FactDTO;
import com.testingez.testingez.api.ninja.dtos.imp.JokeDTO;
import com.testingez.testingez.api.ninja.dtos.imp.TriviaDTO;

public interface NinjaService {

    FactDTO[] fetchFacts();

    JokeDTO[] fetchJokes();

    TriviaDTO[] fetchTrivia();

}
