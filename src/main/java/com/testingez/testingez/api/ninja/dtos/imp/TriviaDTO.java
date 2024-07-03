package com.testingez.testingez.api.ninja.dtos.imp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TriviaDTO {

    private String category;

    private String question;

    private String answer;

}
