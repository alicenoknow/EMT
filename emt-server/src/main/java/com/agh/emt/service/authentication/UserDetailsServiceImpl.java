package com.agh.emt.service.authentication;

import com.agh.emt.model.user.User;
import com.agh.emt.model.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return UserDetailsImpl.build(user.get());
        } else {
            throw new UsernameNotFoundException("Nie znaleziono użytkownika o mailu: " + email);
        }
    }
}
