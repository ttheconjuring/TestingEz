package com.testingez.testingez.services.impls;

import com.testingez.testingez.SampleObjects;
import com.testingez.testingez.models.dtos.imp.TestCreateDTO;
import com.testingez.testingez.models.entities.User;
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

import java.util.List;

import static org.assertj.core.api.Assertions.*;

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
    void createMethodShouldSuccessfullyCreateTest() {
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
    void createMethodShouldThrowAnExceptionWhenSourceIsNull() {
        // given
        TestCreateDTO testCreateDTO = null;

        // then
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() ->
                        // when
                        underTest.create(testCreateDTO));
    }

    @Test
    void createMethodShouldThrowAnExceptionWhenThereIsMappingProblem() {
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

}