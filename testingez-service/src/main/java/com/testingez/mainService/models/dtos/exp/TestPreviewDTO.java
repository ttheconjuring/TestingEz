package com.testingez.mainService.models.dtos.exp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TestPreviewDTO {

    private Long id;

    private String name;

    private String description;

    private Integer responseTime;

    private Integer passingScore;

    private Integer questionsCount;

    private String status;

    private String creatorUsername;

}
