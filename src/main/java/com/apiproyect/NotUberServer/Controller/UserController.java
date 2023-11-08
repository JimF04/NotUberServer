package com.apiproyect.NotUberServer.Controller;

import com.apiproyect.NotUberServer.Model.LogIn;
import com.apiproyect.NotUberServer.Model.User;
import com.apiproyect.NotUberServer.Repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private AppRepository appRepository;

    // Obtener todos los usuarios
    @GetMapping("/users/get")
    public ResponseEntity getAllUsers() {
        return new ResponseEntity<>(appRepository.findAll(), HttpStatus.OK);
    }

    // Obtener un usuario por ID
    @GetMapping("/users/get/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(appRepository.findById(id), HttpStatus.OK);
    }

    // Crear un nuevo usuario
    @PostMapping("/users/new")
    public ResponseEntity createUser(@RequestBody User user) {
        appRepository.save(user);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    // Actualizar un usuario existente
    @PutMapping("/users/update/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User existingUser = appRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setCompanyID(updatedUser.getCompanyID());
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setLocation(updatedUser.getLocation());
            existingUser.setRole(updatedUser.getRole());
            appRepository.save(existingUser);
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar un usuario por ID
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        appRepository.deleteById(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    @PostMapping("/user/logintest")
    public ResponseEntity login(@RequestBody LogIn logIn) {
        String email = logIn.getEmail();
        String password = logIn.getPassword();

        if (email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Please complete all fields", HttpStatus.BAD_REQUEST);
        }

        User user = appRepository.findByEmail(email);

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);
        }

        if (BCrypt.checkpw(password, user.getPassword())) {
            // Contraseña válida, el usuario ha iniciado sesión correctamente
            return new ResponseEntity<>("User logged in successfully", HttpStatus.OK);
        } else {
            // Contraseña incorrecta, el inicio de sesión falló
            return new ResponseEntity<>("Incorrect password or email", HttpStatus.UNAUTHORIZED);
        }
    }
}
