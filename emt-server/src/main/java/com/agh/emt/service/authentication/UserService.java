package com.agh.emt.service.authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public static UserDetails getLoggedUser() throws NoLoggedUserException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return (UserDetails)principal;
        } else {
            throw new NoLoggedUserException("Brak zalogowanego użytkownika");
        }
    }
}
