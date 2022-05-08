package com.agh.emt.utils.authentication.signup_validator;

import com.agh.emt.service.authentication.SignUpRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpValidator {
    private static final String AGH_EMAIL_PATTERN =
            "^[_A-Za-z0-9-+]+([.][_A-Za-z0-9-]+)*@([A-Za-z0-9-]+[.])*agh[.]edu[.]pl$";

    private static final String AGH_STUDENT_EMAIL_PATTERN =
            "^[_A-Za-z0-9-+]+([.][_A-Za-z0-9-]+)*@student[.]agh[.]edu[.]pl$";

    public static void validateSignUpRequest(SignUpRequest signUpRequest)
            throws InvalidAghStudentEmailException, PasswordNotMatchingException {

        if(!validateAghEmail(signUpRequest.getEmail())) {
            throw new InvalidAghStudentEmailException("Podany adres email nie jest postaci **@student.agh.edu.pl");
        }
        if(!passwordsMatching(signUpRequest.getPassword(), signUpRequest.getMatchingPassword())) {
            throw new PasswordNotMatchingException("Podane hasła nie są jednakowe");
        }
    }

    private static boolean validateAghEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(AGH_STUDENT_EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private static boolean passwordsMatching(String pass1, String pass2) {
        return pass1.equals(pass2);
    }
}
