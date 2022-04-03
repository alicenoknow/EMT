package com.agh.emt.utils.authentication;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class LoginRequest {
    String email;
    String password;
}
