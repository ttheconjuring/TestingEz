package com.testingez.testingez.models.dtos.imp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseCreateDTO {

    @NotBlank(message = "{property.should.not.be.null.or.empty}")
    private String responseText;

    @NotBlank(message = "{property.should.not.be.null.or.empty}")
    private Long questionId;

    // for navigating to the next question in ResponseController
    @NotBlank(message = "{property.should.not.be.null.or.empty}")
    private Long testId;
    @NotBlank(message = "{property.should.not.be.null.or.empty}")
    private Integer questionNumber;

}
