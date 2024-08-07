package com.testingez.testingez.services.impls;

import com.testingez.testingez.SampleObjects;
import com.testingez.testingez.exceptions.custom.TestNotFoundException;
import com.testingez.testingez.models.dtos.exp.TestDetailsDTO;
import com.testingez.testingez.models.dtos.exp.TestPreviewDTO;
import com.testingez.testingez.models.dtos.imp.TestCreateDTO;
import com.testingez.testingez.models.entities.Result;
import com.testingez.testingez.models.entities.User;
import com.testingez.testingez.models.enums.TestStatus;
import com.testingez.testingez.repositories.ResultRepository;
import com.testingez.testingez.repositories.TestRepository;
import com.testingez.testingez.services.UserHelperService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.ErrorMessage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {

    @InjectMocks
    private TestServiceImpl underTest;

    @Mock
    private TestRepository testRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private UserHelperService userHelperService;
    @Mock
    private ResultRepository resultRepository;

    @Test
    void createShouldSuccessfullyCreateTest() {
        // given
        TestCreateDTO testCreateDTO = SampleObjects.testCreateDTO();
        com.testingez.testingez.models.entities.Test test = new com.testingez.testingez.models.entities.Test();
        User loggedUser = SampleObjects.user();

        when(modelMapper.map(testCreateDTO, com.testingez.testingez.models.entities.Test.class))
                .thenReturn(test);
        when(userHelperService.getLoggedUser()).thenReturn(loggedUser);
        when(testRepository.saveAndFlush(test)).thenReturn(test);
        test.setId(1L);

        // when
        Long index = underTest.create(testCreateDTO);

        // then
        assertThat(index).isNotNull();
        assertThat(index).isGreaterThan(0);
    }

    @Test
    void createShouldThrowAnExceptionWhenSourceIsNull() {
        // given
        TestCreateDTO testCreateDTO = null;

        // then
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() ->
                        // when
                        underTest.create(testCreateDTO));
    }

    @Test
    void createShouldThrowAnExceptionWhenThereIsMappingProblem() {
        // given
        TestCreateDTO testCreateDTO = SampleObjects.testCreateDTO();

        when(modelMapper.map(testCreateDTO, com.testingez.testingez.models.entities.Test.class))

                // when
                .thenThrow(new MappingException(List.of((
                        new ErrorMessage("Mapping was not possible!")))));

        // then
        assertThatExceptionOfType(MappingException.class)
                .isThrownBy(() -> underTest.create(testCreateDTO))
                .withMessageContaining("Mapping was not possible!");
    }

    @Test
    void checkUponTestShouldReturnOkStringWhenEverythingIsAlright() {
        // given
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        test.setStatus(TestStatus.OPEN);
        String code = test.getCode();
        User loggedUser = SampleObjects.user();

        when(testRepository.findByCode(code)).thenReturn(Optional.of(test));
        when(userHelperService.getLoggedUser()).thenReturn(loggedUser);
        when(resultRepository.findByTestIdAndUserId(test.getId(), loggedUser.getId())).thenReturn(Optional.empty());

        // when
        String result = underTest.checkUponTest(code);

        // then
        assertThat(result).isEqualTo("ok");
    }

    @Test
    void checkUponTestShouldReturnNotFoundStringWhenTheCodeProvidedIsInvalid() {
        // given
        String code = "invalid-code";

        when(testRepository.findByCode(code)).thenReturn(Optional.empty());

        // when
        String result = underTest.checkUponTest(code);

        // then
        assertThat(result).isEqualTo("not found");
    }

    @Test
    void checkUponTestShouldReturnClosedStringWhenTheTestFoundIsClosed() {
        // given
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        String code = test.getCode();

        when(testRepository.findByCode(code)).thenReturn(Optional.of(test));

        // when
        String result = underTest.checkUponTest(code);

        // then
        assertThat(result).isEqualTo("closed");
    }

    @Test
    void checkUponTestShouldReturnCompletedStringWhenTheTestFoundIsAlreadyAttendedByTheLoggedUser() {
        // given
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        test.setStatus(TestStatus.OPEN);
        String code = test.getCode();
        User loggedUser = SampleObjects.user();
        Result foundResult = SampleObjects.result();

        when(testRepository.findByCode(code)).thenReturn(Optional.of(test));
        when(userHelperService.getLoggedUser()).thenReturn(loggedUser);
        when(resultRepository.findByTestIdAndUserId(test.getId(), loggedUser.getId())).thenReturn(Optional.of(foundResult));

        // when
        String result = underTest.checkUponTest(code);

        // then
        assertThat(result).isEqualTo("completed");
    }

    @Test
    void checkUponTestShouldReturnNotFoundStringWhenTheCodeProvidedIsNull() {
        // given
        String code = null;

        when(testRepository.findByCode(code)).thenReturn(Optional.empty());

        // when
        String result = underTest.checkUponTest(code);

        // then
        assertThat(result).isEqualTo("not found");
    }

    @Test
    void getTestPreviewDataShouldWorkJustFineWhenTheCodeProvidedIsValid() {
        // given
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        String code = test.getCode();
        TestPreviewDTO testPreviewDTO = SampleObjects.testPreviewDTO();

        when(testRepository.findByCode(code)).thenReturn(Optional.of(test));
        when(modelMapper.map(test, TestPreviewDTO.class)).thenReturn(testPreviewDTO);

        // when
        TestPreviewDTO testPreviewData = underTest.getTestPreviewData(code);

        // then
        assertThat(testPreviewData).isNotNull();
        assertThat(testPreviewData.getName()).isEqualTo(test.getName());
        assertThat(testPreviewData.getDescription()).isEqualTo(test.getDescription());
        assertThat(testPreviewData.getPassingScore()).isEqualTo(test.getPassingScore());
        assertThat(testPreviewData.getResponseTime()).isEqualTo(test.getResponseTime());
        assertThat(testPreviewData.getQuestionsCount()).isEqualTo(test.getQuestionsCount());
        assertThat(testPreviewData.getStatus()).isEqualTo(test.getStatus().name());
    }

    @Test
    void getTestPreviewDataShouldThrowAnExceptionWhenTheCodeProvidedIsInvalid() {
        // given
        String code = "invalid-code";

        when(testRepository.findByCode(code)).thenReturn(Optional.empty());

        // then
        assertThatExceptionOfType(TestNotFoundException.class)
                .isThrownBy(() ->
                        // when
                        underTest.getTestPreviewData(code))
                .withMessage("We couldn't find test with code: " + code);
    }

    @Test
    void getTestPreviewDataShouldThrowAnExceptionWhenThereIsMappingProblem() {
        // given
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        String code = test.getCode();

        when(testRepository.findByCode(code)).thenReturn(Optional.of(test));
        when(modelMapper.map(test, TestPreviewDTO.class)).thenThrow(
                new MappingException(List.of(new ErrorMessage("Mapping was not possible!")))
        );

        // then
        assertThatExceptionOfType(MappingException.class)
                .isThrownBy(() ->
                        // when
                        underTest.getTestPreviewData(code))
                .withMessageContaining("Mapping was not possible!");
    }

    @Test
    void getTestDetailsShouldWorkJustFineWhenTheTestIdProvidedIsValid() {
        // given
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        test.setDateCreated(LocalDateTime.parse("2024-08-06T15:59:51.415775800"));
        test.setDateUpdated(LocalDateTime.parse("2024-08-06T15:59:51.415775800"));
        Long testId = test.getId();
        TestDetailsDTO testDetailsDTO = SampleObjects.testDetailsDTO();
        testDetailsDTO.setDateCreated(LocalDateTime.parse("2024-08-06T15:59:51.415775800"));
        testDetailsDTO.setDateUpdated(LocalDateTime.parse("2024-08-06T15:59:51.415775800"));

        when(testRepository.findById(testId)).thenReturn(Optional.of(test));
        when(modelMapper.map(test, TestDetailsDTO.class)).thenReturn(testDetailsDTO);

        // when
        TestDetailsDTO testDetails = underTest.getTestDetails(testId);

        // then
        assertThat(testDetails).isNotNull();
        assertThat(testDetailsDTO.getName()).isEqualTo(test.getName());
        assertThat(testDetailsDTO.getDescription()).isEqualTo(test.getDescription());
        assertThat(testDetailsDTO.getCode()).isEqualTo(test.getCode());
        assertThat(testDetailsDTO.getPassingScore()).isEqualTo(test.getPassingScore());
        assertThat(testDetailsDTO.getResponseTime()).isEqualTo(test.getResponseTime());
        assertThat(testDetailsDTO.getQuestionsCount()).isEqualTo(test.getQuestionsCount());
        assertThat(testDetailsDTO.getStatus()).isEqualTo(test.getStatus().name());
        assertThat(testDetailsDTO.getDateCreated()).isEqualTo(test.getDateCreated());
        assertThat(testDetailsDTO.getDateUpdated()).isEqualTo(test.getDateUpdated());
    }

    @Test
    void getTestDetailsShouldThrowAnExceptionWhenTheTestIdProvidedIsInvalid() {
        // given
        Long testId = -1L;

        when(testRepository.findById(testId)).thenReturn(Optional.empty());

        // then
        assertThatExceptionOfType(TestNotFoundException.class)
                .isThrownBy(() ->
                        // when
                        underTest.getTestDetails(testId))
                .withMessage("We couldn't find test with id: " + testId);
    }

    @Test
    void getTestDetailsShouldThrowAnExceptionWhenThereIsMappingProblem() {
        // given
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        Long testId = test.getId();

        when(testRepository.findById(testId)).thenReturn(Optional.of(test));

        when(modelMapper.map(test, TestDetailsDTO.class)).thenThrow(
                new MappingException(List.of(new ErrorMessage("Mapping was not possible!"))));

        // then
        assertThatExceptionOfType(MappingException.class)
                .isThrownBy(() ->
                        // when
                        underTest.getTestDetails(testId))
                .withMessageContaining("Mapping was not possible!");
    }

    @Test
    void changeTestStatusShouldChangeTestStatusToCLOSED() {
        // given
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        test.setStatus(TestStatus.OPEN);
        LocalDateTime before = test.getDateUpdated();
        Long id = test.getId();

        when(testRepository.findById(id)).thenReturn(Optional.of(test));

        /* otherwise the test executes too fast and can't find difference between
         * dateUpdated before and after the test execution
         */
        timeout(1000);

        // when
        underTest.changeTestStatus(id);

        // then
        assertThat(test.getStatus()).isEqualTo(TestStatus.CLOSED);
        assertThat(test.getDateUpdated()).isNotEqualTo(before);
    }

    @Test
    void changeTestStatusShouldChangeTestStatusToOPEN() {
        // given
        com.testingez.testingez.models.entities.Test test = SampleObjects.test();
        test.setStatus(TestStatus.CLOSED);
        LocalDateTime before = test.getDateUpdated();
        Long id = test.getId();

        when(testRepository.findById(id)).thenReturn(Optional.of(test));

        /* otherwise the test executes too fast and can't find difference between
         * dateUpdated before and after the test execution
         */
        timeout(1000);

        // when
        underTest.changeTestStatus(id);

        // then
        assertThat(test.getStatus()).isEqualTo(TestStatus.OPEN);
        assertThat(test.getDateUpdated()).isNotEqualTo(before);
    }

    @Test
    void changeTestStatusShouldThrowAnExceptionWhenTheIdProvidedIsInvalid() {
        // given
        Long id = -1L;

        when(testRepository.findById(id)).thenReturn(Optional.empty());

        // then
        assertThatExceptionOfType(TestNotFoundException.class)
                .isThrownBy(() ->
                        // when
                        underTest.changeTestStatus(id))
                .withMessage("We couldn't find test with id: " + id);
    }

}