package com.testingez.testingez.models.dtos.ninja;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class TriviaDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String category;

    private String question;

    private String answer;

}
