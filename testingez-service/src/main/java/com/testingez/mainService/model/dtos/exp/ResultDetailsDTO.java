package com.testingez.mainService.model.dtos.exp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ResultDetailsDTO {

    private String testName;

    private String status;

    private String result;

    private Integer points;

    private LocalDateTime completedAt;

}
