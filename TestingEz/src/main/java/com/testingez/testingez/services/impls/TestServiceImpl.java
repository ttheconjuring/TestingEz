package com.testingez.testingez.services.impls;

import com.testingez.testingez.models.dtos.exp.TestPreviewDTO;
import com.testingez.testingez.models.dtos.imp.TestCreateDTO;
import com.testingez.testingez.models.entities.Test;
import com.testingez.testingez.models.enums.TestStatus;
import com.testingez.testingez.repositories.TestRepository;
import com.testingez.testingez.services.TestService;
import com.testingez.testingez.services.UserHelperService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final ModelMapper modelMapper;
    private final UserHelperService userHelperService;

    @Override
    public void create(TestCreateDTO testCreateDTO) {
        Test newTest = modelMapper.map(testCreateDTO, Test.class);
        newTest.setStatus(testCreateDTO.getStatus().equals("open") ? TestStatus.OPEN : TestStatus.CLOSED);
        newTest.setDateCreated(LocalDateTime.now());
        newTest.setDateUpdated(LocalDateTime.now());
        newTest.setCreator(this.userHelperService.getLoggedUser());
        this.testRepository.saveAndFlush(newTest);
    }

    @Override
    public void delete(Long id) {
        if (id == -1) {
            this.testRepository.deleteById(
                    this.testRepository.findLastAdded()
                            .orElseThrow(() -> new IllegalArgumentException("No test found with id: " + id))
                            .getId()
            );
        } else {
            this.testRepository.deleteById(id);
        }
    }

    @Override
    public String checkUponTest(String code) {
        Optional<Test> byCode = this.testRepository.findByCode(code);
        if (byCode.isEmpty()) {
            return "not found";
        }
        if (byCode.get().getStatus().equals(TestStatus.CLOSED)) {
            return "closed";
        }
        return "ok";
    }

    @Override
    public TestPreviewDTO getTestPreviewData(String code) {
        return this.modelMapper.map(
                this.testRepository.findByCode(code)
                        .orElseThrow(() ->
                                new IllegalArgumentException("No test found with code: " + code)),
                TestPreviewDTO.class);
    }

}
