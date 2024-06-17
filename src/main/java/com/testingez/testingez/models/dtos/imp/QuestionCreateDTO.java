package com.testingez.testingez.models.dtos.imp;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class QuestionCreateDTO {

    @NotNull(message = "Question should not be null.")
    @NotEmpty(message = "Question should not be empty.")
    private String question;

    @NotNull(message = "Answers should not be null.")
    @NotEmpty(message = "Answers should not be empty.")
    private String answer1;

    @NotNull(message = "Answers should not be null.")
    @NotEmpty(message = "Answers should not be empty.")
    private String answer2;

    @NotNull(message = "Answers should not be null.")
    @NotEmpty(message = "Answers should not be empty.")
    private String answer3;

    @NotNull(message = "Answers should not be null.")
    @NotEmpty(message = "Answers should not be empty.")
    private String answer4;

    @NotNull(message = "Answers should not be null.")
    @NotEmpty(message = "Answers should not be empty.")
    private String correctAnswer;

    @NotNull(message = "Points should not be null.")
    @Digits(integer = 1, fraction = 0, message = "Points should be a number.")
    @Max(value = 100, message = "Points should be up to 100.")
    private Integer points;

}
