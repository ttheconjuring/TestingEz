package com.testingez.testingez.models.dtos.imp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseCreateDTO {

    private String responseText;

    private Long questionId;

    // for navigating to the next question in ResponseController
    private Long testId;
    private Integer questionNumber;

}
