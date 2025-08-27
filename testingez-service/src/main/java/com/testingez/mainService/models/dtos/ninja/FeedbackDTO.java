package com.testingez.mainService.models.dtos.ninja;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class FeedbackDTO {

    private UUID id;

    @NotNull(message = "{property.should.not.be.null}")
    @Size(min = 25, max = 255, message = "Please, make you idea more descriptive (at least 25 symbols).")
    private String idea;

    private Boolean approved;

    private Boolean disapproved;

}

