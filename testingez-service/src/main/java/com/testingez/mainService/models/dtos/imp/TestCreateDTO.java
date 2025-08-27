package com.testingez.mainService.models.dtos.imp;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class TestCreateDTO {

    @NotNull(message = "{property.should.not.be.null}")
    @Size(min = 5, max = 25, message = "{test.name.should.be.between}")
    private String name;

    @NotNull(message = "{property.should.not.be.null}")
    @Size(max = 50, message = "{description.should.be.up.to}")
    private String description;

    @NotNull(message = "{property.should.not.be.null}")
    @Pattern(regexp = "^(?=.*[A-Za-z].*[A-Za-z])(?=.*\\d.*\\d)(?=.*[!@#$%^&*].*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{6}$",
            message = "{code.should.contain}")
    private String code;

    @NotNull(message = "{property.should.not.be.null}")
    @Positive(message = "{property.should.be.positive}")
    @Max(value = 10, message = "{response.time.should.be.up.to}")
    private Integer responseTime;

    @NotNull(message = "{property.should.not.be.null}")
    @Min(value = 0, message = "{passing.score.should.be}")
    @Max(value = 100, message = "{passing.score.should.be.up.to}")
    private Integer passingScore;

    @NotNull(message = "{property.should.not.be.null}")
    @Positive(message = "{property.should.be.positive}")
    @Max(value = 50, message = "{questions.count.should.be.up.to}")
    private Integer questionsCount;

    @NotNull(message = "{property.should.not.be.null}")
    @Pattern(regexp = "^(open|closed)$", message = "{status.should.be.either}")
    private String status;

}
