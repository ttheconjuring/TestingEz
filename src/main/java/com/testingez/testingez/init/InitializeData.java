package com.testingez.testingez.init;

import com.testingez.testingez.api.ninja.NinjaService;
import com.testingez.testingez.api.ninja.entities.Fact;
import com.testingez.testingez.api.ninja.entities.Joke;
import com.testingez.testingez.api.ninja.entities.Trivia;
import com.testingez.testingez.api.ninja.repositories.FactRepository;
import com.testingez.testingez.api.ninja.repositories.JokeRepository;
import com.testingez.testingez.api.ninja.repositories.TriviaRepository;
import com.testingez.testingez.models.entities.Role;
import com.testingez.testingez.models.enums.UserRole;
import com.testingez.testingez.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@AllArgsConstructor
@Component
public class InitializeData implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final FactRepository factRepository;
    private final JokeRepository jokeRepository;
    private final TriviaRepository triviaRepository;
    private final NinjaService ninjaService;
    private final ModelMapper modelMapper;

    @Override
    public void run(String... args) {
        seedRoles();
        seedTrivia();
        seedJokes();
        seedFacts();
    }

    private void seedRoles() {
        if (this.roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setRole(UserRole.ADMINISTRATOR);
            this.roleRepository.saveAndFlush(adminRole);
            Role standartRole = new Role();
            standartRole.setRole(UserRole.STANDARD);
            this.roleRepository.saveAndFlush(standartRole);
        }
    }

    private void seedJokes() {
        if (this.jokeRepository.count() == 0) {
            Arrays.stream(this.ninjaService.fetchJokes())
                    .forEach(jokeDTO ->
                            this.jokeRepository.saveAndFlush(
                                    this.modelMapper.map(jokeDTO, Joke.class)
                            )
                    );
        }
    }

    private void seedFacts() {
        if (this.factRepository.count() == 0) {
            Arrays.stream(this.ninjaService.fetchFacts())
                    .forEach(factDTO ->
                            this.factRepository.saveAndFlush(
                                    this.modelMapper.map(factDTO, Fact.class)
                            )
                    );
        }
    }

    private void seedTrivia() {
        if (this.triviaRepository.count() == 0) {
            Arrays.stream(this.ninjaService.fetchTrivia())
                    .forEach(triviaDTO ->
                            this.triviaRepository.saveAndFlush(
                                    this.modelMapper.map(triviaDTO, Trivia.class)
                            )
                    );
        }
    }

}
