package com.apiproyect.NotUberServer.Controller;

import com.apiproyect.NotUberServer.Model.Driver;
import com.apiproyect.NotUberServer.Model.Employee;
import com.apiproyect.NotUberServer.XMLHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

