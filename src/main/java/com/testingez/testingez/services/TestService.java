package com.testingez.testingez.services;

import com.testingez.testingez.models.dtos.imp.TestCreateDTO;

public interface TestService {

    void create(TestCreateDTO testCreateDTO);

    void delete(Long id);

}
