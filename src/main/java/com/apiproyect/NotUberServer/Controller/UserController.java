package com.apiproyect.NotUberServer.Controller;

import com.apiproyect.NotUberServer.Model.Driver;
import com.apiproyect.NotUberServer.Model.Employee;
import com.apiproyect.NotUberServer.XMLHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final XMLHandler xmlHandler;
    @Autowired
    public UserController(XMLHandler xmlHandler) {
        this.xmlHandler = xmlHandler;
    }

    @GetMapping("/drivers")
    public List<Driver> getAllDrivers() {
        // Lógica para obtener todos los conductores del XML
        return xmlHandler.getAllUsers("driver", Driver.class);
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        // Lógica para obtener todos los empleados del XML
        return xmlHandler.getAllUsers("employee", Employee.class);
    }

    @PostMapping("/register/driver")
    public ResponseEntity registerNewDriver(@RequestBody Driver driver) {
        xmlHandler.registerUser("driver", driver);
        return new ResponseEntity<>("Driver registered successfully", HttpStatus.OK);
    }

    @PostMapping("/register/employee")
    public ResponseEntity registerNewEmployee(@RequestBody Employee employee) {
        xmlHandler.registerUser("employee", employee);
        return new ResponseEntity<>("Employee registered successfully", HttpStatus.OK);
    }
}

