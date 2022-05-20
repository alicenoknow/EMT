package com.agh.emt.controller.authentication;

import com.agh.emt.model.authentication.ConfirmationToken;
import com.agh.emt.model.user.User;
import com.agh.emt.service.authentication.*;
import com.agh.emt.service.email_sender.EmailSenderService;
import com.agh.emt.service.email_sender.NoSuchConfirmationTokenException;
import com.agh.emt.utils.authentication.JwtResponse;
import com.agh.emt.utils.authentication.JwtUtils;
import com.agh.emt.utils.authentication.LoginRequest;
import com.agh.emt.utils.authentication.signup_validator.InvalidAghEmailException;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class AuthenticationController {
    AuthenticationManager authenticationManager;

    PasswordEncoder encoder;
    JwtUtils jwtUtils;
    UserCredentialsService userCredentialsService;
    EmailSenderService emailSenderService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
            throws UserNotEnabledException {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));


        if(!userCredentialsService.isUserEnabled(loginRequest.getEmail())) {
            throw new UserNotEnabledException("Konto użytkownika nie jest potwierdzone");
        }

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
            throws UserAlreadyExistException, InvalidAghEmailException {

        User user = userCredentialsService.registerNewUserAccount(signUpRequest);
        ConfirmationToken confirmationToken = emailSenderService.createConfirmationToken(user);
        emailSenderService.sendConfirmationEmail(confirmationToken, user);
        return ResponseEntity.ok("successfulRegistration");
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationTokenString)
            throws NoSuchConfirmationTokenException {

        ConfirmationToken confirmationToken =
                emailSenderService.getConfirmationTokenByTokenString(confirmationTokenString);
        userCredentialsService.confirmUserAccount(confirmationToken.getUser());
        return ResponseEntity.ok("successfulAccountConfirmation");
    }
}
