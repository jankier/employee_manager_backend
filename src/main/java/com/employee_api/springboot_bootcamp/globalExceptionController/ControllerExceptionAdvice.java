package com.employee_api.springboot_bootcamp.globalExceptionController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException exception, WebRequest request) {
        return createResponse(exception, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception, WebRequest request) {
        return createResponse(exception, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request) {
        List<String> errors = exception.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();
        return createResponse(exception, HttpStatus.BAD_REQUEST, request, errors);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException exception, WebRequest request) {
        return createResponse(exception, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleNoSuchElementException(Exception exception, WebRequest request) {
        return createResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<Object> createResponse(Exception exception, HttpStatus status, WebRequest request) {
        Throwable rootCause = exception;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("message", status);
        body.put("info", rootCause.getMessage());
        body.put("path", request.getDescription(false));
        body.put("timestamp", java.time.LocalDateTime.now().toString());
        return new ResponseEntity<>(body, status);
    }

    private ResponseEntity<Object> createResponse(Exception exception, HttpStatus status, WebRequest request, List<String> errors) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("message", status);
        body.put("info", exception.getMessage());
        Map<String, List<String>> errorsBody = new HashMap<>();
        errorsBody.put("info", errors);
        body.put("errors", errorsBody);
        body.put("path", request.getDescription(false));
        body.put("timestamp", java.time.LocalDateTime.now().toString());
        return new ResponseEntity<>(body, status);
    }
}
