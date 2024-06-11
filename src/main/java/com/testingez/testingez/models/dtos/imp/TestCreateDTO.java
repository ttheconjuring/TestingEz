package com.testingez.testingez.models.dtos.imp;

import com.testingez.testingez.models.annotations.NotTaken;
import jakarta.validation.constraints.*;
import lombok.*;

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
    @Pattern(regexp = "^(?=.*[A-Za-z].*[A-Za-z])(?=.*\\d.*\\d)(?=.*[!@#$%^&*].*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{6}$",
            message = "Code should contain 2 letter, 2 digits and 2 special symbols.")
    @NotTaken(message = "This code is already in use.") // TODO: edit validator
    private String code;

    @NotNull(message = "Response time should not be null.")
    @Positive(message = "Response time should be a positive number.")
    @Max(value = 10, message = "Response time should up to 10 minutes.")
    private Integer responseTime;

    @NotNull(message = "Passing score should not be null.")
    @Min(0)
    private Integer passingScore;

    @NotNull(message = "Questions count should not be null.")
    @Positive(message = "Questions count should be positive number.")
    @Max(value = 50, message = "Questions count should be up to 50 questions.")
    private Integer questionsCount;

    @NotNull(message = "Status should not be null.")
    @Pattern(regexp = "^(open|closed)$", message = "Status should be either open or closed.")
    private String status;

}
