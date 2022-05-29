package com.agh.emt.controller;

import com.agh.emt.service.authentication.NoLoggedUserException;
import com.agh.emt.service.authentication.UserAlreadyExistException;
import com.agh.emt.service.authentication.UserNotEnabledException;
import com.agh.emt.service.email_sender.NoSuchConfirmationTokenException;
import com.agh.emt.service.form.RecruitmentFormExistsException;
import com.agh.emt.service.form.RecruitmentFormNotFoundException;
import com.agh.emt.service.news.NewsNotFoundException;
import com.agh.emt.service.one_drive.OneDriveConnectionException;
import com.agh.emt.service.parameters.ParameterNotFoundException;
import com.agh.emt.service.student.StudentNotFoundException;
import com.agh.emt.utils.authentication.signup_validator.InvalidAghEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    // http internal server error codes: >=512
    @ExceptionHandler(InvalidAghEmailException.class)
    protected ResponseEntity<String> handleInvalidAghEmailException(InvalidAghEmailException ex) {
        return ResponseEntity.status(512).body(ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    protected ResponseEntity<String> handleUserAlreadyExistException(UserAlreadyExistException ex) {
        return ResponseEntity.status(513).body(ex.getMessage());
    }

    @ExceptionHandler(NoSuchConfirmationTokenException.class)
    protected ResponseEntity<String> handleNoSuchConfirmationTokenException(NoSuchConfirmationTokenException ex) {
        return ResponseEntity.status(514).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotEnabledException.class)
    protected ResponseEntity<String> handleUserNotEnabledException(UserNotEnabledException ex) {
        return ResponseEntity.status(515).body(ex.getMessage());
    }

    @ExceptionHandler(StudentNotFoundException.class)
    protected ResponseEntity<String> handleStudentNotFoundException(StudentNotFoundException ex) {
        return ResponseEntity.status(516).body(ex.getMessage());
    }

    @ExceptionHandler(RecruitmentFormExistsException.class)
    protected ResponseEntity<String> handleRecruitmentFormExistsException(RecruitmentFormExistsException ex) {
        return ResponseEntity.status(517).body(ex.getMessage());
    }

    @ExceptionHandler(RecruitmentFormNotFoundException.class)
    protected ResponseEntity<String> handleRecruitmentFormNotFoundException(RecruitmentFormNotFoundException ex) {
        return ResponseEntity.status(518).body(ex.getMessage());
    }

    @ExceptionHandler(NoLoggedUserException.class)
    protected ResponseEntity<String> handleNoLoggedUserException(NoLoggedUserException ex) {
        return ResponseEntity.status(519).body(ex.getMessage());
    }

    @ExceptionHandler(NewsNotFoundException.class)
    protected ResponseEntity<String> handleNewsNotFoundException(NewsNotFoundException ex) {
        return ResponseEntity.status(520).body(ex.getMessage());
    }

    @ExceptionHandler(OneDriveConnectionException.class)
    protected ResponseEntity<String> handleOneDriveConnectionException(OneDriveConnectionException ex) {
        return ResponseEntity.status(521).body(ex.getMessage());
    }

    @ExceptionHandler(ParameterNotFoundException.class)
    protected ResponseEntity<String> handleParameterNotFoundException(ParameterNotFoundException ex) {
        return ResponseEntity.status(522).body(ex.getMessage());
    }

    @ExceptionHandler()
    protected ResponseEntity<String> defaultHandle(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
