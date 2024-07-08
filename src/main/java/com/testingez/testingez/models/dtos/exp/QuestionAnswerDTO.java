package com.testingez.testingez.models.dtos.exp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionAnswerDTO {

    private Long id;

    private String question;

    private String answer1;

    private String answer2;

    private String answer3;

    private String answer4;

    private Integer points;

}
