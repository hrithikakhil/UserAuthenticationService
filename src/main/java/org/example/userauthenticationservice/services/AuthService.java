package org.example.userauthenticationservice.services;

import org.example.userauthenticationservice.exceptions.InvalidCredentials;
import org.example.userauthenticationservice.exceptions.UserAlreadyExists;
import org.example.userauthenticationservice.models.State;
import org.example.userauthenticationservice.models.User;
import org.example.userauthenticationservice.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User signUp(String email, String password) throws UserAlreadyExists {
        Optional<User> userFromDB = userRepo.findUserByEmail(email);
        if(userFromDB.isPresent()) {
            throw new UserAlreadyExists("Error : Email already exists, please try to login");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setState(State.ACTIVE);
        return userRepo.save(user);


    }

    @Override
    public User login(String email, String password) throws InvalidCredentials {
        Optional<User> user = userRepo.findUserByEmail(email);
        if(user.isPresent()) {
            User userFromDB = user.get();
            if(!bCryptPasswordEncoder.matches(password, userFromDB.getPassword())) {
                throw new InvalidCredentials("Please provide correct credentials");
            }

            return userFromDB;
        }
        return null;
    }

    @Override
    public User logout(String email) {
        return null;
    }
}
