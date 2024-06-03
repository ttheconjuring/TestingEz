package com.testingez.testingez.config;

import com.testingez.testingez.repositories.UserRepository;
import com.testingez.testingez.utils.NotTakenValidator;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public NotTakenValidator notTakenValidator(UserRepository userRepository) {
        return new NotTakenValidator(userRepository);
    }

}
