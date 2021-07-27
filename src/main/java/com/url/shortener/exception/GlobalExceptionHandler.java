package com.url.shortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = UrlNotProvidedException.class)
    protected ResponseEntity<Object> handleUrlNotProvidedException(UrlNotProvidedException ex, WebRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = UrlExpiredException.class)
    protected ResponseEntity<Object> handleUrlExpiredException(UrlExpiredException ex, WebRequest request) {

        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(ex.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<Object> handleAllException(RuntimeException ex, WebRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}
