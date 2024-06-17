package com.testingez.testingez.models.dtos.imp;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TestQuestionsDTO {

    @NotNull(message = "Test questions should not be null.")
    @NotEmpty(message = "There should be at least one test question.")
    List<QuestionCreateDTO> questions;

}
