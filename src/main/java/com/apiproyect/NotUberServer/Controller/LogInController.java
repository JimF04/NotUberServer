package com.apiproyect.NotUberServer.Controller;

import com.apiproyect.NotUberServer.Model.Driver;
import com.apiproyect.NotUberServer.Model.Employee;
import com.apiproyect.NotUberServer.Model.LogIn;
import com.apiproyect.NotUberServer.XMLHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


/**
 * Controlador que gestiona las operaciones de inicio de sesión para conductores y empleados.
 */
@RestController
@RequestMapping("/api")
public class LogInController {

    @Autowired
    XMLHandler xmlHandler;

    /**
     * Procesa la solicitud de inicio de sesión para conductores.
     *
     * @param logIn Objeto LogIn que contiene la información de inicio de sesión del conductor.
     * @return ResponseEntity con un mensaje indicando el resultado del inicio de sesión y el código de estado HTTP correspondiente.
     */
    @PostMapping("/driver/login")
    public ResponseEntity logInDriver(@RequestBody LogIn logIn) {
        String email = logIn.getEmail();
        String password = logIn.getPassword();

        if (email.isEmpty() || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Please complete all fields");
        }

        // Obtener todos los conductores del XML
        List<Driver> drivers = xmlHandler.getAllUsers("driver", Driver.class);

        // Buscar el conductor con el correo proporcionado
        Optional<Driver> driverOptional = drivers.stream()
                .filter(driver -> driver.getEmail().equals(email))
                .findFirst();

        if (driverOptional.isPresent()) {
            // Verificar la contraseña
            Driver driver = driverOptional.get();
            if (driver.getPassword().equals(password)) {
                return new ResponseEntity<>("Driver logged in successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("Driver not found", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Procesa la solicitud de inicio de sesión para empleados.
     *
     * @param logIn Objeto LogIn que contiene la información de inicio de sesión del empleado.
     * @return ResponseEntity con un mensaje indicando el resultado del inicio de sesión y el código de estado HTTP correspondiente.
     */
    @PostMapping("/employee/login")
    public ResponseEntity logInEmployee(@RequestBody LogIn logIn) {
        String email = logIn.getEmail();
        String password = logIn.getPassword();

        if (email.isEmpty() || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Please complete all fields");
        }

        // Obtener todos los empleados del XML
        List<Employee> employees = xmlHandler.getAllUsers("employee", Employee.class);

        // Buscar el empleado con el correo proporcionado
        Optional<Employee> employeeOptional = employees.stream()
                .filter(employee -> employee.getEmail().equals(email))
                .findFirst();

        if (employeeOptional.isPresent()) {
            // Verificar la contraseña
            Employee employee = employeeOptional.get();
            if (employee.getPassword().equals(password)) {
                return new ResponseEntity<>("Employee logged in successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("Employee not found", HttpStatus.UNAUTHORIZED);
        }
    }
}
