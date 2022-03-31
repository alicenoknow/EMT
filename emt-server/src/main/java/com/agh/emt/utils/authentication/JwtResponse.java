package com.agh.emt.utils.authentication;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class JwtResponse {
    String token;
    Long id;
    String email;
    List<String> roles;
}
