package org.example.userauthenticationservice.controllers;


import org.example.userauthenticationservice.dtos.LoginRequestDTO;
import org.example.userauthenticationservice.dtos.LogoutRequestDTO;
import org.example.userauthenticationservice.dtos.SignUpRequestDTO;
import org.example.userauthenticationservice.dtos.UserDTO;
import org.example.userauthenticationservice.exceptions.InvalidCredentials;
import org.example.userauthenticationservice.exceptions.UserAlreadyExists;
import org.example.userauthenticationservice.models.User;
import org.example.userauthenticationservice.services.IAuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) throws UserAlreadyExists {
        if(signUpRequestDTO.getEmail()==null || signUpRequestDTO.getPassword()==null){
            return new ResponseEntity<>("Email or Password cannot be null", HttpStatus.BAD_REQUEST);
        }

        try{
            User user = authService.signUp(signUpRequestDTO.getEmail(), signUpRequestDTO.getPassword());
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
        }catch (UserAlreadyExists ex){
            throw ex;
        }

    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) throws InvalidCredentials {
        try {
            User user = authService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
            if(user==null){
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }catch (InvalidCredentials ex) {
            throw ex;
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<UserDTO> logout(@RequestBody LogoutRequestDTO logoutRequestDTO) {
        User user = authService.logout(logoutRequestDTO.getEmail());
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
