package com.testingez.testingez.models.dtos.exp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TestDetailsDTO {

    private String name;

    private String description;

    private String code;

    private Integer responseTime;

    private Integer passingScore;

    private Integer questionsCount;

    private String status;

    private LocalDateTime dateCreated;

    private LocalDateTime dateUpdated;

    private String creatorUsername;

}
