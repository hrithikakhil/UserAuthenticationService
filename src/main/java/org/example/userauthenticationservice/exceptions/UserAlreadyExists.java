package org.example.userauthenticationservice.exceptions;

public class UserAlreadyExists extends Exception{

    public UserAlreadyExists(String message) {
        super(message);
    }
}
