package com.agh.emt.service.authentication;

import com.agh.emt.model.authentication.UserCredentials;
import com.agh.emt.model.authentication.UserCredentialsRepository;
import com.agh.emt.utils.authentication.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    public static UserDetails getLoggedUser() throws NoLoggedUserException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return (UserDetails)principal;
        } else {
            throw new NoLoggedUserException("Brak zalogowanego użytkownika");
        }
    }

    public UserCredentials registerNewUserAccount(SignUpRequest signUpRequest) throws UserAlreadyExistException {
        if (emailExist(signUpRequest.getEmail())) {
            throw new UserAlreadyExistException("Istnieje już konto z takim adresem email: "
                    + signUpRequest.getEmail());
        }

        UserCredentials newUser = new UserCredentials();
        newUser.setEmail(signUpRequest.getEmail());
        newUser.setPassword(signUpRequest.getPassword());
        // Assuming that the new user is a student
        newUser.setRole(Role.ROLE_STUDENT);
        return userCredentialsRepository.save(newUser);
    }

    public void confirmUserAccount(UserCredentials userCredentials) {
        userCredentials.setEnabled(true);
        userCredentialsRepository.save(userCredentials);
    }

    private boolean emailExist(String email) {
        return userCredentialsRepository.findByEmail(email).isPresent();
    }
}
