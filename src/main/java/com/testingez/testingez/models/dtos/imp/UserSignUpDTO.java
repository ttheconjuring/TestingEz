package com.testingez.testingez.models.dtos.imp;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSignUpDTO {

    @NotNull(message = "Username should not be null.")
    @Size(min = 5, max = 25, message = "Username should be between 5 and 25 symbols.")
    private String username;

    @NotNull(message = "Password should not be null.")
    @Size(min = 8, message = "Password should be at least 8 symbols.")
    private String password;

    @NotNull(message = "Password should not be null.")
    @Size(min = 8, message = "Password should be at least 8 symbols.")
    private String confirmPassword;

    @NotBlank(message = "First name should not be null or empty.")
    private String firstName;

    @NotBlank(message = "Last name should not be null or empty.")
    private String lastName;

    @NotBlank(message = "Email should not be null or empty.")
    @Email(message = "Email should be in valid format.")
    private String email;

    @NotBlank(message = "Phone should no be null or empty.")
    @Pattern(regexp = "^[0-9]{3}-[0-9]{3}-[0-9]{4}$", message = "Phone should be in valid format.")
    private String phone;

}
