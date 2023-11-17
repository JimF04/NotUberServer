package com.apiproyect.NotUberServer.Controller;

import com.apiproyect.NotUberServer.Model.Driver;
import com.apiproyect.NotUberServer.Model.Employee;
import com.apiproyect.NotUberServer.XMLHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    /**
     * Obtiene la ubicación de un empleado basándose en su correo electrónico.
     *
     * @param email Correo electrónico del empleado.
     * @return ResponseEntity con la ubicación del empleado y el código de estado HTTP correspondiente.
     */
    @GetMapping(value = "/employee/location/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getEmployeeLocation(@PathVariable String email) {
        Map<String, String> response = new HashMap<>();

        if (email.isEmpty()) {
            response.put("error", "Please provide an email");
            return ResponseEntity.badRequest().body(response);
        }

        // Obtener todos los empleados del XML
        List<Employee> employees = xmlHandler.getAllUsers("employee", Employee.class);

        // Buscar el empleado con el correo proporcionado
        Optional<Employee> employeeOptional = employees.stream()
                .filter(employee -> employee.getEmail().equals(email))
                .findFirst();

        if (employeeOptional.isPresent()) {
            // Obtener la ubicación del empleado
            Employee employee = employeeOptional.get();
            String location = employee.getLocation();

            response.put("location", location);
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Employee not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/driver/top5")
    public List<Driver> getTop5DriversByRides() {
        List<Driver> drivers = xmlHandler.getAllUsers("driver", Driver.class);

        // Ordenar la lista de conductores por la cantidad de viajes (rides) utilizando Insertion Sort
        for (int i = 1; i < drivers.size(); i++) {
            Driver keyDriver = drivers.get(i);
            int j = i - 1;

            // Mover los elementos mayores que keyDriver a una posición adelante de su posición actual
            while (j >= 0 && drivers.get(j).getRides() < keyDriver.getRides()) {
                drivers.set(j + 1, drivers.get(j));
                j = j - 1;
            }
            drivers.set(j + 1, keyDriver);
        }

        // Obtener los primeros 5 conductores después de ordenar
        int topCount = Math.min(5, drivers.size());
        return drivers.subList(0, topCount);
    }
}

