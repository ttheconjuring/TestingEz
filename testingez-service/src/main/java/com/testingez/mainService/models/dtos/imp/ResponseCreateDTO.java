package com.testingez.mainService.models.dtos.imp;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseCreateDTO {

    private String responseText;

    @NotNull(message = "{property.should.not.be.null}")
    private Long questionId;

    // for navigating to the next question in ResponseController
    @NotNull(message = "{property.should.not.be.null}")
    private Long testId;
    @NotNull(message = "{property.should.not.be.null}")
    private Integer questionNumber;

}
