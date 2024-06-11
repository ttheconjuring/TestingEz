package com.testingez.testingez.services.impls;

import com.testingez.testingez.models.dtos.imp.TestCreateDTO;
import com.testingez.testingez.models.entities.Test;
import com.testingez.testingez.models.enums.TestStatus;
import com.testingez.testingez.repositories.TestRepository;
import com.testingez.testingez.services.CurrentUser;
import com.testingez.testingez.services.TestService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;

    @Override
    public void create(TestCreateDTO testCreateDTO) {
        Test newTest = modelMapper.map(testCreateDTO, Test.class);
        if (testCreateDTO.getStatus().equals("open")) {
            newTest.setStatus(TestStatus.OPEN);
        } else {
            newTest.setStatus(TestStatus.CLOSED);
        }
        newTest.setDateCreated(LocalDateTime.now());
        newTest.setDateUpdated(LocalDateTime.now());
        newTest.setCreator(currentUser.getUser());
        this.testRepository.saveAndFlush(newTest);
    }

}