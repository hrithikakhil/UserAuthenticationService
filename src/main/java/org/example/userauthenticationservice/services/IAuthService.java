package org.example.userauthenticationservice.services;

import org.example.userauthenticationservice.exceptions.InvalidCredentials;
import org.example.userauthenticationservice.exceptions.UserAlreadyExists;
import org.example.userauthenticationservice.models.User;

public interface IAuthService {

    User signUp(String email, String password) throws UserAlreadyExists;

    User login(String email, String password) throws InvalidCredentials;

    User logout(String email);
}
