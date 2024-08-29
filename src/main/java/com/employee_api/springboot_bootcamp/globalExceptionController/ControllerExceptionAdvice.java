package com.employee_api.springboot_bootcamp.globalExceptionController;

import com.employee_api.springboot_bootcamp.globalExceptionController.model.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException exception, WebRequest request) {
        log.error("Illegal argument with: {}", exception.getMessage(), exception);
        return createResponse(exception, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception, WebRequest request) {
        log.error("Argument type mismatch with: {}", exception.getMessage(), exception);
        return createResponse(exception, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request) {
        List<String> errors = exception.getBindingResult().getAllErrors()
                .stream().map(ObjectError::getDefaultMessage).toList();
        log.error("Argument not valid with: {}", errors, exception);
        return createResponse(HttpStatus.BAD_REQUEST, request, errors);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> handleNoSuchElementException(NoSuchElementException exception, WebRequest request) {
        log.error("Not found with: {}", exception.getMessage(), exception);
        return createResponse(exception, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException exception, WebRequest request) {
        log.error("Unauthorized with: {}", exception.getMessage(), exception);
        return createResponse(exception, HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> handleUsernameNotFoundException(UsernameNotFoundException exception, WebRequest request) {
        log.error("Username not found with: {}", exception.getMessage(), exception);
        return createResponse(exception, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleInternalServerErrorException(Exception exception, WebRequest request) {
        log.error("Internal server error with: {}", exception.getMessage(), exception);
        return createResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<ApiError> createResponse(String message, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                Collections.singletonList(message),
                request.getDescription(false),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, status);
    }

    private ResponseEntity<ApiError> createResponse(Exception exception, HttpStatus status, WebRequest request) {
        Throwable rootCause = exception;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        ApiError apiError = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                Collections.singletonList(rootCause.getMessage()),
                request.getDescription(false),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, status);
    }

    private ResponseEntity<ApiError> createResponse(HttpStatus status, WebRequest request, List<String> errors) {
        ApiError apiError = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                errors,
                request.getDescription(false),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, status);
    }
}
