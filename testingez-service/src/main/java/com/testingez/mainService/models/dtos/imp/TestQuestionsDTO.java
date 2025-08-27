package com.testingez.mainService.models.dtos.imp;

import jakarta.validation.Valid;
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

    @NotNull(message = "{property.should.not.be.null}")
    private Long testId;

    @NotNull(message = "{property.should.not.be.null}")
    private List<@Valid QuestionCreateDTO> questions;

}
