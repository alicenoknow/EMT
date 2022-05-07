package com.agh.emt.controller.authentication;

import com.agh.emt.model.authentication.UserCredentials;
import com.agh.emt.model.confirmation_token.ConfirmationToken;
import com.agh.emt.model.student.StudentRepository;
import com.agh.emt.service.authentication.UserAlreadyExistException;
import com.agh.emt.service.authentication.UserDetailsImpl;
import com.agh.emt.service.authentication.SignUpRequest;
import com.agh.emt.service.authentication.UserService;
import com.agh.emt.service.authentication.email_sender.EmailSenderService;
import com.agh.emt.service.authentication.email_sender.NoSuchConfirmationTokenException;
import com.agh.emt.utils.authentication.JwtResponse;
import com.agh.emt.utils.authentication.JwtUtils;
import com.agh.emt.utils.authentication.LoginRequest;
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

//    @Autowired
//    UserCredentialsRepository userCredentialsRepository;
//    @Autowired
//    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;
    @Autowired
    EmailSenderService emailSenderService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
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

//    @GetMapping("/signup")
//    public String showRegistrationForm(WebRequest request, Model model) {
//        SignUpRequest signUpRequest = new SignUpRequest();
//        model.addAttribute("user", signUpRequest);
//        return "signup";
//    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUserAccount(@Valid SignUpRequest signUpRequest)
            throws UserAlreadyExistException {

        UserCredentials registeredUserCredentials = userService.registerNewUserAccount(signUpRequest);
        ConfirmationToken confirmationToken = emailSenderService.createConfirmationToken(registeredUserCredentials);
        emailSenderService.sendConfirmationEmail(confirmationToken, registeredUserCredentials);
        return ResponseEntity.ok("successfulRegistration");
    }

    @PostMapping("/confirm-account")
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationTokenString)
            throws NoSuchConfirmationTokenException {

        ConfirmationToken confirmationToken =
                emailSenderService.getConfirmationTokenByTokenString(confirmationTokenString);
        userService.confirmUserAccount(confirmationToken.getUserCredentials());
        return ResponseEntity.ok("successfulAccountConfirmation");
    }

//    @PostMapping("/signup")
//    public ResponseEntity<?> registerStudent(@Valid @RequestBody SignupRequest signUpRequest) {
//        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
//        }
//        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Email is already in use!"));
//        }
//        // Create new user's account
//        User user = new User(signUpRequest.getUsername(),
//                signUpRequest.getEmail(),
//                encoder.encode(signUpRequest.getPassword()));
//        Set<String> strRoles = signUpRequest.getRole();
//        Set<Role> roles = new HashSet<>();
//        if (strRoles == null) {
//            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                switch (role) {
//                    case "admin":
//                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//                        break;
//                    case "mod":
//                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(modRole);
//                        break;
//                    default:
//                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                }
//            });
//        }
//        user.setRoles(roles);
//        userRepository.save(user);
//        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//    }
}
