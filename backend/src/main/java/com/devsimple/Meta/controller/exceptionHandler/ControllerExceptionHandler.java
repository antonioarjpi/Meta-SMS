package com.devsimple.Meta.controller.exceptionHandler;

import com.devsimple.Meta.exceptions.RuleOfBusinessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.time.OffsetDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(RuleOfBusinessException.class)
    public ResponseEntity<StandardError> ruleOfBusinessException(RuleOfBusinessException e, HttpServletRequest request) {
        StandardError err = new StandardError(OffsetDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Rule Of Business Exception.", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        StandardError err = new StandardError(OffsetDateTime.now(), HttpStatus.BAD_REQUEST.value(), "MethodArgumentNotValidException", e.getFieldError().getDefaultMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request) {
        StandardError err = new StandardError(OffsetDateTime.now(), HttpStatus.BAD_REQUEST.value(), "SQLIntegrityConstraintViolationException",
                e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> constraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();

        String field = null;
        for (Path.Node node : violation.getPropertyPath()) {
            field = violation.getMessageTemplate();
        }
        StandardError err = new StandardError(OffsetDateTime.now(), HttpStatus.BAD_REQUEST.value(), "ConstraintViolationException", field, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}