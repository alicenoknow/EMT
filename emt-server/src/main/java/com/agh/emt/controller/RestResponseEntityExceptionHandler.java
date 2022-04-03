package com.agh.emt.controller;

import com.agh.emt.service.authentication.NoLoggedUserException;
import com.agh.emt.service.form.RecruitmentFormExistsException;
import com.agh.emt.service.form.RecruitmentFormNotFoundException;
import com.agh.emt.service.news.NewsNotFoundException;
import com.agh.emt.service.student.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NewsNotFoundException.class,
            NoLoggedUserException.class,
            RecruitmentFormNotFoundException.class,
            RecruitmentFormExistsException.class,
            StudentNotFoundException.class,
    })
    protected ResponseEntity<String> defaultHandle(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
