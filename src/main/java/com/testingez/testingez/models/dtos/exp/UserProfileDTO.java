package com.testingez.testingez.models.dtos.exp;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserProfileDTO {

    @NotNull(message = "Username should not be null.")
    @Size(min = 5, max = 25, message = "Username should be between 5 and 25 symbols.")
    private String username;

    @NotBlank(message = "First name should not be null or empty.")
    private String firstName;

    @NotBlank(message = "First name should not be null or empty.")
    private String lastName;

    @NotBlank(message = "Email should not be null or empty.")
    @Email(message = "Email should be in valid format.")
    private String email;

    @NotNull(message = "Phone should no be null.")
    @Pattern(regexp = "^[0-9]{3}-[0-9]{3}-[0-9]{4}$", message = "Phone should be in valid format.")
    private String phone;

    @NotNull(message = "Role should not be null.")
    @Pattern(regexp = "^(STANDARD|ADMINISTRATOR)$", message = "Role should be either STANDARD or ADMINISTRATOR.")
    private String Role;

}
