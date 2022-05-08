package com.agh.emt.controller.authentication;

import com.agh.emt.model.authentication.UserCredentials;
import com.agh.emt.model.confirmation_token.ConfirmationToken;
import com.agh.emt.model.student.StudentRepository;
import com.agh.emt.service.authentication.*;
import com.agh.emt.service.authentication.email_sender.EmailSenderService;
import com.agh.emt.service.authentication.email_sender.NoSuchConfirmationTokenException;
import com.agh.emt.utils.authentication.JwtResponse;
import com.agh.emt.utils.authentication.JwtUtils;
import com.agh.emt.utils.authentication.LoginRequest;
import com.agh.emt.utils.authentication.signup_validator.InvalidAghStudentEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;
    @Autowired
    EmailSenderService emailSenderService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
            throws UserNotEnabledException {

        if(!userService.isUserEnabled(loginRequest.getEmail())) {
            throw new UserNotEnabledException("Konto użytkownika nie jest potwierdzone");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUserAccount(@RequestBody @Valid SignUpRequest signUpRequest)
            throws UserAlreadyExistException, InvalidAghStudentEmailException {

        UserCredentials registeredUserCredentials = userService.registerNewUserAccount(signUpRequest);
        ConfirmationToken confirmationToken = emailSenderService.createConfirmationToken(registeredUserCredentials);
        emailSenderService.sendConfirmationEmail(confirmationToken, registeredUserCredentials);
        return ResponseEntity.ok("successfulRegistration");
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationTokenString)
            throws NoSuchConfirmationTokenException {

        ConfirmationToken confirmationToken =
                emailSenderService.getConfirmationTokenByTokenString(confirmationTokenString);
        userService.confirmUserAccount(confirmationToken.getUserCredentials());
        return ResponseEntity.ok("successfulAccountConfirmation");
    }
}
