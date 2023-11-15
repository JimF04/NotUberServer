package com.apiproyect.NotUberServer.Controller;

import com.apiproyect.NotUberServer.Model.Driver;
import com.apiproyect.NotUberServer.Model.Employee;
import com.apiproyect.NotUberServer.XMLHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador que gestiona las operaciones relacionadas con los usuarios, incluyendo conductores y empleados.
 */
@RestController
@RequestMapping("/api")
public class UserController {

    private final XMLHandler xmlHandler;

    /**
     * Constructor que inyecta el manejador XML al controlador.
     *
     * @param xmlHandler Manejador XML utilizado para interactuar con los datos de los usuarios.
     */
    @Autowired
    public UserController(XMLHandler xmlHandler) {
        this.xmlHandler = xmlHandler;
    }

    /**
     * Obtiene todos los conductores almacenados en el sistema.
     *
     * @return Lista de conductores.
     */
    @GetMapping("/driver")
    public List<Driver> getAllDrivers() {
        // Lógica para obtener todos los conductores del XML
        return xmlHandler.getAllUsers("driver", Driver.class);
    }

    /**
     * Obtiene todos los empleados almacenados en el sistema.
     *
     * @return Lista de empleados.
     */
    @GetMapping("/employee")
    public List<Employee> getAllEmployees() {
        // Lógica para obtener todos los empleados del XML
        return xmlHandler.getAllUsers("employee", Employee.class);
    }

    /**
     * Registra un nuevo conductor en el sistema.
     *
     * @param driver Objeto Driver que contiene la información del nuevo conductor.
     * @return ResponseEntity con un mensaje indicando el resultado del registro y el código de estado HTTP correspondiente.
     */
    @PostMapping("/driver/register")
    public ResponseEntity registerNewDriver(@RequestBody Driver driver) {
        // Verifica si ya existe un usuario con el mismo correo o CompanyID
        if (userExists(driver.getEmail(), driver.getCompanyID())) {
            return new ResponseEntity<>("User with the same email or CompanyID already exists", HttpStatus.BAD_REQUEST);
        }

        xmlHandler.registerUser("driver", driver);
        return new ResponseEntity<>("Driver registered successfully", HttpStatus.OK);
    }

    /**
     * Registra un nuevo empleado en el sistema.
     *
     * @param employee Objeto Employee que contiene la información del nuevo empleado.
     * @return ResponseEntity con un mensaje indicando el resultado del registro y el código de estado HTTP correspondiente.
     */
    @PostMapping("/employee/register")
    public ResponseEntity registerNewEmployee(@RequestBody Employee employee) {
        // Verifica si ya existe un usuario con el mismo correo o CompanyID
        if (userExists(employee.getEmail(), employee.getCompanyID())) {
            return new ResponseEntity<>("User with the same email or CompanyID already exists", HttpStatus.BAD_REQUEST);
        }

        xmlHandler.registerUser("employee", employee);
        return new ResponseEntity<>("Employee registered successfully", HttpStatus.OK);
    }

    /**
     * Verifica si ya existe un usuario con el mismo correo o CompanyID en la lista de conductores o empleados.
     *
     * @param email     Correo del usuario a verificar.
     * @param companyID CompanyID del usuario a verificar.
     * @return true si el usuario ya existe, false en caso contrario.
     */
    private boolean userExists(String email, String companyID) {
        List<Driver> drivers = xmlHandler.getAllUsers("driver", Driver.class);
        List<Employee> employees = xmlHandler.getAllUsers("employee", Employee.class);

        // Verifica en la lista de conductores
        for (Driver driver : drivers) {
            if (driver.getEmail().equals(email) || driver.getCompanyID().equals(companyID)) {
                return true;
            }
        }

        // Verifica en la lista de empleados
        for (Employee employee : employees) {
            if (employee.getEmail().equals(email) || employee.getCompanyID().equals(companyID)) {
                return true;
            }
        }

        return false;
    }
}

