package com.testingez.testingez.models.dtos.exp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TestPeekDTO {

    private Long id;

    private String name;

    private String status;

    private LocalDateTime dateUpdated;

}
