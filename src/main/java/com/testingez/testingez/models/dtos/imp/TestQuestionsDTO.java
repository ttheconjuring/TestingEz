package com.testingez.testingez.models.dtos.imp;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestQuestionsDTO {

    @NotNull(message = "Question list should not be null.")
    private List<QuestionCreateDTO> questions;

}
