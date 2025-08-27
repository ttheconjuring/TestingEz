package com.testingez.mainService.model.dtos.ninja;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class QuoteDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 4L;

    private String quote;

    private String author;

}
