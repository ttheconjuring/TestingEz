package com.testingez.testingez.models.dtos.imp;

import com.testingez.testingez.models.annotations.ConfirmPasswords;
import com.testingez.testingez.models.annotations.NotTaken;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ConfirmPasswords
public class UserSignUpDataDTO {

    @NotNull(message = "Username should not be null.")
    @Size(min = 5, max = 25,
            message = "Username should be between 5 and 25 symbols.")
    @NotTaken(message = "This username is already in use.")
    private String username;

    @NotNull(message = "Password should not be null.")
    @Size(min = 8,
            message = "Password should be at least 8 symbols.")
    private String password;

    @NotNull(message = "Password should not be null.")
    @Size(min = 8,
            message = "Password should be at least 8 symbols.")
    private String confirmPassword;

    @NotBlank(message = "First name should not be null or empty.")
    private String firstName;

    @NotBlank(message = "Last name should not be null or empty.")
    private String lastName;

    @NotBlank(message = "Email should not be null or empty.")
    @Email(message = "Email should be in valid format.")
    @NotTaken(message = "This email is already in use.")
    private String email;

    @NotNull(message = "Phone should not be null.")
    @NotTaken(message = "This phone is already in use.")
    @Pattern(regexp = "^[0-9]{3}-[0-9]{3}-[0-9]{4}$", message = "Phone should be in valid format.")
    private String phone;

}
