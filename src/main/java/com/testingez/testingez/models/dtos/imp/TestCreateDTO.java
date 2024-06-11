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

    @NotNull(message = "Test name should not be null.")
    @Size(min = 5, max = 25, message = "Test name should be between 5 and 25 symbols.")
    private String name;

    @NotNull(message = "Description should not be null.")
    @Size(max = 50, message = "Description should be up to 50 symbols.")
    private String description;

    @NotNull(message = "Code should not be null.")
    @Pattern(regexp = "^(?=.*[A-Za-z].*[A-Za-z])(?=.*\\d.*\\d)(?=.*[!@#$%^&*].*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{6}$")
    private String code;

    @NotNull(message = "Response time should not be null.")
    @Positive(message = "Response time should be a positive number.")
    private Integer responseTime;

    @NotNull(message = "Passing score should not be null.")
    private Integer passingScore;

    @NotNull(message = "Questions count should not be null.")
    @Positive(message = "Questions count should be positive number.")
    private Integer questionsCount;

    @NotBlank(message = "Test status should not be null or empty.")
    private TestStatus status;

}
