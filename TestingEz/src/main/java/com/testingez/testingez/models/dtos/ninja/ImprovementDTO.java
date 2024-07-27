package com.testingez.testingez.models.dtos.ninja;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImprovementDTO {

    private String idea;

    private Boolean approved;

    private Boolean disapproved;

}

