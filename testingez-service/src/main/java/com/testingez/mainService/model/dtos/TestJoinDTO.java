package com.testingez.mainService.model.dtos;

import jakarta.validation.constraints.NotBlank;
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
