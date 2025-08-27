package com.testingez.mainService.model.dtos.ninja;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class FactDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    private String fact;

}
