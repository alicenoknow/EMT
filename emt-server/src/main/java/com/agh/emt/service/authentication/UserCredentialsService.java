package com.agh.emt.service.authentication;

import com.agh.emt.model.user.User;
import com.agh.emt.model.user.UserRepository;
import com.agh.emt.utils.authentication.Role;
import com.agh.emt.utils.authentication.signup_validator.InvalidAghEmailException;
import com.agh.emt.utils.authentication.signup_validator.SignUpValidator;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserCredentialsService {
    private final UserRepository userRepository;

    public static UserDetails getLoggedUser() throws NoLoggedUserException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return (UserDetails)principal;
        } else {
            throw new NoLoggedUserException("Brak zalogowanego użytkownika");
        }
    }

    public User registerNewUserAccount(SignUpRequest signUpRequest)
            throws UserAlreadyExistException, InvalidAghEmailException {

        SignUpValidator.validateSignUpRequest(signUpRequest);

        if (emailExist(signUpRequest.getEmail())) {
            throw new UserAlreadyExistException("Istnieje już konto z takim adresem email: "
                    + signUpRequest.getEmail());
        }

        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setRole(Role.ROLE_STUDENT);

        return userRepository.save(user);
    }

    public void confirmUserAccount(User user) {
        user.setEnabled(true);
        userRepository.save(user);
    }

    public boolean isUserEnabled(String email) {
        Optional<User> userCredentials = userRepository.findByEmail(email);
        return userCredentials.map(User::isEnabled).orElse(false);
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
