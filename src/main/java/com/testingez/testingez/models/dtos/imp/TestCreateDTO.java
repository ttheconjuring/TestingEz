package com.testingez.testingez.models.dtos.imp;

import com.testingez.testingez.models.enums.TestStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestCreateDTO {

    @NotBlank
    private String name;

    @NotNull
    private String description;

    @NotBlank
    private String code;

    @NotNull
    @Digits(integer = 1, fraction = 0)
    private String responseTime;

    @Positive
    @NotNull
    private String passingScore;

    @Positive
    @NotNull
    private String questionsCount;

    @NotBlank
    private TestStatus status;

}
