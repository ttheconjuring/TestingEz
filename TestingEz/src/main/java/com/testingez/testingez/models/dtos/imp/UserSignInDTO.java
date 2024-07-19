package com.testingez.testingez.models.dtos.imp;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSignInDTO {

    // TODO: try if it works without it

    @NotNull(message = "{property.should.not.be.null}")
    @Size(min = 5, max = 25, message = "Username should be between 5 and 25 symbols.")
    private String username;

    @NotNull(message = "{property.should.not.be.null}")
    @Size(min = 8, message = "Password should be at least 8 symbols.")
    private String password;

}
