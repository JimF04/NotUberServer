package com.apiproyect.NotUberServer.Model;

// Employee.java
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase que representa a un empleado en el sistema.
 */
@Data
@XmlRootElement
public class Employee {
    private String companyID;
    private String name;
    private String email;
    private String password;
    private String location;
    private double rating;
    private String friends = ""; // un String de enteros separados por una coma

    /**
     * Retorna un String de enteros con los companyID de los amigos del conductor,
     * separados por comas
     *
     * @return List<Integer> lista de amigos
     */
    public String getFriends(){ return friends; }

    /**
     * Añade un amigo a la lista de amigos del conductor
     *
     * @param friendID companyID del amigo
     */
//    public void addFriend(Integer friendID){
//        this.friends = friends + "," + friendID.toString();
//    }

    /**
     * Obtiene el ID de la empresa del empleado.
     *
     * @return ID de la empresa del empleado.
     */
    public String getCompanyID() {
        return companyID;
    }

    /**
     * Establece el ID de la empresa del empleado.
     *
     * @param companyID ID de la empresa del empleado.
     */
    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    /**
     * Obtiene el nombre del empleado.
     *
     * @return Nombre del empleado.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del empleado.
     *
     * @param name Nombre del empleado.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el correo electrónico del empleado.
     *
     * @return Correo electrónico del empleado.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del empleado.
     *
     * @param email Correo electrónico del empleado.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la contraseña del empleado.
     *
     * @return Contraseña del empleado.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del empleado.
     *
     * @param password Contraseña del empleado.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene la ubicación del empleado.
     *
     * @return Ubicación del empleado.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Establece la ubicación del empleado.
     *
     * @param location Ubicación del empleado.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Obtiene la calificación del empleado.
     *
     * @return Calificación del empleado.
     */
    public double getRating() {
        return rating;
    }

    /**
     * Establece la calificación del empleado.
     *
     * @param rating Calificación del empleado.
     */
    public void setRating(double rating) {
        this.rating = rating;
    }
}

