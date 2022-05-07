package com.agh.emt.controller;

import com.agh.emt.service.authentication.NoLoggedUserException;
import com.agh.emt.service.authentication.UserAlreadyExistException;
import com.agh.emt.service.authentication.UserNotEnabledException;
import com.agh.emt.service.authentication.email_sender.NoSuchConfirmationTokenException;
import com.agh.emt.service.form.RecruitmentFormExistsException;
import com.agh.emt.service.form.RecruitmentFormNotFoundException;
import com.agh.emt.service.news.NewsNotFoundException;
import com.agh.emt.service.student.StudentNotFoundException;
import com.agh.emt.utils.authentication.signup_validator.InvalidAghStudentEmailException;
import com.agh.emt.utils.authentication.signup_validator.PasswordNotMatchingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            NewsNotFoundException.class,
            NoLoggedUserException.class,
            RecruitmentFormNotFoundException.class,
            RecruitmentFormExistsException.class,
            StudentNotFoundException.class,
            UserNotEnabledException.class,
            NoSuchConfirmationTokenException.class,
            UserAlreadyExistException.class,
            PasswordNotMatchingException.class,
            InvalidAghStudentEmailException.class
    })
    protected ResponseEntity<String> defaultHandle(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
