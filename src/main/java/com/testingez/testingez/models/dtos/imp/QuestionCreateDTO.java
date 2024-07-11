package com.testingez.testingez.models.dtos.imp;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class QuestionCreateDTO {

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

    @NotNull(message = "{property.should.not.be.null}")
    @Digits(integer = 3, fraction = 0, message = "{points.should.be.a.number}")
    @Max(value = 100, message = "{points.should.be.up.to}")
    @Positive(message = "{property.should.be.positive}")
    private Integer points;

}
