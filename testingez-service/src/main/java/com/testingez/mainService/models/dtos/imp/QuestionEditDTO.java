package com.testingez.mainService.models.dtos.imp;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionEditDTO {

    private Long id;

    @NotNull(message = "{property.should.not.be.null}")
    @NotEmpty(message = "{property.should.not.be.empty}")
    private String question;

    @NotNull(message = "{property.should.not.be.null}")
    @NotEmpty(message = "{property.should.not.be.empty}")
    private String answer1;

    @NotNull(message = "{property.should.not.be.null}")
    @NotEmpty(message = "{property.should.not.be.empty}")
    private String answer2;

    @NotNull(message = "{property.should.not.be.null}")
    @NotEmpty(message = "{property.should.not.be.empty}")
    private String answer3;

    @NotNull(message = "{property.should.not.be.null}")
    @NotEmpty(message = "{property.should.not.be.empty}")
    private String answer4;

    @NotNull(message = "{property.should.not.be.null}")
    @NotEmpty(message = "{property.should.not.be.empty}")
    private String correctAnswer;

    private Integer number;

    private Long testId;

}
