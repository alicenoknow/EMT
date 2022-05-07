package com.agh.emt.service.authentication;

import com.agh.emt.service.authentication.email_validation.ValidEmail;
import com.agh.emt.service.authentication.password_validation.PasswordMatches;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Value
@Getter
@Setter
@PasswordMatches
public class SignUpRequest {
    @NotNull
    @NotEmpty
    @ValidEmail
    String email;

    @NotNull
    @NotEmpty
    String password;
    String matchingPassword;
}
