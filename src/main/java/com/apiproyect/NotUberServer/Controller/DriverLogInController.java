package com.apiproyect.NotUberServer.Controller;

import com.apiproyect.NotUberServer.Model.LogInDriver;
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
public class DriverLogInController {
    @Autowired
    AppRepository appRepository;

    @PostMapping("/user/driver/login")
    public ResponseEntity logInUser(@RequestBody LogInDriver logInDriver){
        String email = logInDriver.getEmail();
        String password = logInDriver.getPassword();
        String role = logInDriver.getRole();

        if (email.isEmpty() || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Please complete all fields");
        }

        User user = appRepository.findByEmail(email);

        if (user == null){
            return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);
        }

        if (BCrypt.checkpw(password, user.getPassword())){
            if ("driver".equals(user.getRole())) {
                return new ResponseEntity<>("User logged in successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Incorrect role for driver", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("Incorrect password or email", HttpStatus.UNAUTHORIZED);
        }
    }
}
