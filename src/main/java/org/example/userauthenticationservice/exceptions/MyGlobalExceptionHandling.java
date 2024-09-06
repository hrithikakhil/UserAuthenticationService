package org.example.userauthenticationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyGlobalExceptionHandling {

    @ExceptionHandler({UserAlreadyExists.class})
    public ResponseEntity<?> myUserAlreadyExistsExp(UserAlreadyExists ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler({InvalidCredentials.class})
    public ResponseEntity<?> invalidCredentialExp(InvalidCredentials ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
