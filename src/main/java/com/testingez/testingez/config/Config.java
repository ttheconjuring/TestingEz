package com.testingez.testingez.config;

import com.testingez.testingez.models.dtos.exp.UserProfileDTO;
import com.testingez.testingez.models.entities.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class Config {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<User, UserProfileDTO> propertyMapper = modelMapper.createTypeMap(User.class, UserProfileDTO.class);
        propertyMapper.addMappings(
                mapper -> mapper.map(
                        src -> src.getRole().getRole(), UserProfileDTO::setRole)
        );
        return modelMapper;
    }

    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }

}
