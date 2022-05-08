package com.agh.emt.service.authentication;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Value
@Getter
@Setter
public class SignUpRequest {
    @NotNull
    @NotEmpty
    String email;

    @NotNull
    @NotEmpty
    String password;
    String matchingPassword;
}
