package com.agh.emt.utils.authentication;

import org.springframework.security.core.AuthenticationException;

public class InconclusiveUsernameException extends AuthenticationException {
    public InconclusiveUsernameException(String msg) {
        super(msg);
    }
}
