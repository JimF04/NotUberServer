package com.apiproyect.NotUberServer.Controller;

import com.apiproyect.NotUberServer.Model.LogIn;
import com.apiproyect.NotUberServer.Model.User;
import com.apiproyect.NotUberServer.Repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loginemployee")
public class EmployeeLogInController {
    @Autowired
    AppRepository appRepository;

    @PostMapping("/user/employee")
    public ResponseEntity logInUser(@RequestBody LogIn logIn){
        String email = logIn.getEmail();
        String password = logIn.getPassword();
        String role = logIn.getRole();

        if (email.isEmpty() || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Please complete all fields");
        }

        User user = appRepository.findByEmail(email);

        if (user == null){
            return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);
        }

        if (password.equals(user.getPassword())){
            if ("employee".equals(user.getRole())) {
                return new ResponseEntity<>("User logged in successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Incorrect role for employee", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("Incorrect password or email", HttpStatus.UNAUTHORIZED);
        }
    }
}
