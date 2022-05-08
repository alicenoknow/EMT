package com.agh.emt.service.authentication;

import com.agh.emt.model.authentication.UserCredentials;
import com.agh.emt.model.authentication.UserCredentialsRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserCredentialsRepository userCredentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserCredentials> userCredentials;

        userCredentials = userCredentialsRepository.findByEmail(email);

        if (userCredentials.isPresent()) {
            return UserDetailsImpl.build(userCredentials.get());
        } else {
            throw new UsernameNotFoundException("Nie znaleziono użytkownika o mailu: " + email);
        }
    }
}
