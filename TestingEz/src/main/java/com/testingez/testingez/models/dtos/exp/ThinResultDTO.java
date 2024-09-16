package com.testingez.testingez.models.dtos.exp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ThinResultDTO {

    private Long id;

    private String avatarUrl;

    private String username;

    private String result;

    private String status;

}
