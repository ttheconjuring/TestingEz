package com.testingez.testingez.models.dtos.exp;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileDTO {

    @NotNull(message = "{property.should.not.be.null}")
    @Size(min = 5, max = 25, message = "{username.should.be.between}")
    private String username;

    @NotBlank(message = "{property.should.not.be.null.or.empty}")
    private String firstName;

    @NotBlank(message = "{property.should.not.be.null.or.empty}")
    private String lastName;

    @NotBlank(message = "{property.should.not.be.null.or.empty}")
    @Email(message = "{property.should.be.in.valid.format}")
    private String email;

    @NotNull(message = "{property.should.not.be.null}")
    @Pattern(regexp = "^[0-9]{3}-[0-9]{3}-[0-9]{4}$", message = "{property.should.be.in.valid.format}")
    private String phone;

    @Pattern(regexp = "^(STANDARD|ADMINISTRATOR)$", message = "{user.role.should.be.either}")
    private String role;

}
