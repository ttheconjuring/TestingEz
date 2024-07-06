package com.testingez.testingez.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TestJoinDTO {

    @NotNull(message = "Test code should not be null.")
    @NotEmpty(message = "Test code should not be empty.")
    private String code;

}
