package com.testingez.testingez.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TestJoinDTO {

    @NotBlank(message = "{property.should.not.be.null.or.empty}")
    private String code;

}
