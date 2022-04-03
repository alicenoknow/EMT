package com.agh.emt.utils.authentication;

import lombok.AllArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Value
@AllArgsConstructor
public class LoginRequest {
    @NotNull
    @NotEmpty
    String email;

    @NotNull
    @NotEmpty
    String password;
}
