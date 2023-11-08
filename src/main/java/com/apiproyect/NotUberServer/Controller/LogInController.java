package com.apiproyect.NotUberServer.Controller;

import com.apiproyect.NotUberServer.Model.LogIn;
import com.apiproyect.NotUberServer.Model.User;
import com.apiproyect.NotUberServer.Repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LogInController {

    @Autowired
    AppRepository appRepository;

    @PostMapping("/user/login")
    public ResponseEntity logInUser(@RequestBody LogIn logIn){
        String email = logIn.getEmail();
        String password = logIn.getPassword();

        if (email.isEmpty() || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Please complete all fields");
        }

        User user = appRepository.findByEmail(email);

        if (user == null){
            return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);
        }

        if (BCrypt.checkpw(password, user.getPassword())){
            return new ResponseEntity<>("User logged in successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Incorrect password or email", HttpStatus.UNAUTHORIZED);
        }
    }

}