package com.testingez.testingez.models.dtos.exp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ResultPeekDTO {

    private Long id;

    private String testName;

    private LocalDateTime completedAt;

    private String status;


}
