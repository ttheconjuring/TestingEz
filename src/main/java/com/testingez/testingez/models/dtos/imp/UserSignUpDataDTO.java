package com.testingez.testingez.models.dtos.imp;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpDataDTO {

    @NotNull
    @Size(min = 5, max = 25)
    private String username;

    @NotNull
    @Size(min = 8)
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    @Email
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$")
    private String email;

    @NotNull
    @Pattern(regexp = "^(?:\\+359 \\d{2} \\d{3} \\d{4}|0\\d{9})$")
    private String phone;

}
