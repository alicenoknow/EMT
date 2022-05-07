package com.agh.emt.service.authentication.email_validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AghEmailValidator implements ConstraintValidator<ValidEmail, String> {
    private Pattern pattern;
    private Matcher matcher;

    private static final String AGH_EMAIL_PATTERN =
            "^[_A-Za-z0-9-+]+([.][_A-Za-z0-9-]+)*@([A-Za-z0-9-]+[.])*agh[.]edu[.]pl$";

    @Override
    public void initialize(ValidEmail constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context){
        return (validateEmail(email));
    }

    private boolean validateEmail(String email) {
        pattern = Pattern.compile(AGH_EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
