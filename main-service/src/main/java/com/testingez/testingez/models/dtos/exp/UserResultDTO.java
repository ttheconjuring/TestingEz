package com.testingez.testingez.models.dtos.exp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResultDTO {

    private Long id;

    private String avatarUrl;

    private String username;

    private Integer points;

    private String result;

    private String status;

}
