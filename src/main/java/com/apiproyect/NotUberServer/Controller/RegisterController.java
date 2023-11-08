package com.apiproyect.NotUberServer.Controller;

import com.apiproyect.NotUberServer.Model.User;
import com.apiproyect.NotUberServer.Repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    AppRepository appRepository;

    @GetMapping(value = "/test")
    public String helloWorld(){
        return "Te amo Jose, mi cookie <3";
    }

    @PostMapping("/user/register")
    public ResponseEntity registerNewUser(@RequestBody User user){
        String companyID = user.getCompanyID();
        String name = user.getName();
        String email = user.getEmail();
        String password = user.getPassword();
        String location = user.getLocation();
        String role = user.getRole();

        if (companyID.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty() || location.isEmpty() || role.isEmpty()) {
            return new ResponseEntity<>("Please complete all fields", HttpStatus.BAD_REQUEST);
        }

        // Encrypt / Hash Password:
        // String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Registrar nuevo usuario
        User newUser = new User();
        newUser.setCompanyID(companyID);
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setLocation(location);
        newUser.setRole(role);

        appRepository.save(newUser);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
