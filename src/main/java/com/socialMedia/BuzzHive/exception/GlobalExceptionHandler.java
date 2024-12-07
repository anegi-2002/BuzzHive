package com.socialMedia.BuzzHive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ImageUploadException.class)
    ResponseEntity<ExceptionResponse> handleOrderNotFoundException(ImageUploadException notFoundException){
        return new ResponseEntity(new ExceptionResponse(notFoundException.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
