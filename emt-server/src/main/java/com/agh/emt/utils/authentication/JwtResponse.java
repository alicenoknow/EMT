package com.agh.emt.utils.authentication;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class JwtResponse {
    String token;
    String email;
    List<String> roles;
}
