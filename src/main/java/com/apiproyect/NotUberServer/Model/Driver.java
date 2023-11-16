package com.apiproyect.NotUberServer.Model;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa a un conductor en el sistema.
 */
@Data
@XmlRootElement
public class Driver {
    private String companyID;
    private String name;
    private String email;
    private String password;
    private double rating;
    private int rides;
    private List<Integer> friends = new ArrayList<Integer>();

    /**
     * Retorna un arraylist de enteros con los companyID de los amigos del conductor
     *
     * @return List<Integer> lista de amigos
     */
    public List<Integer> getFriends(){ return friends; }

    /**
     * Añade un amigo a la lista de amigos del conductor
     *
     * @param friendID companyID del amigo
     */
    public void addAFriend(int friendID){ this.friends.add(friendID);}

    /**
     * Obtiene el ID de la empresa del conductor.
     *
     * @return ID de la empresa del conductor.
     */
    public String getCompanyID() {
        return companyID;
    }

    /**
     * Establece el ID de la empresa del conductor.
     *
     * @param companyID ID de la empresa del conductor.
     */
    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    /**
     * Obtiene el nombre del conductor.
     *
     * @return Nombre del conductor.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del conductor.
     *
     * @param name Nombre del conductor.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el correo electrónico del conductor.
     *
     * @return Correo electrónico del conductor.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del conductor.
     *
     * @param email Correo electrónico del conductor.
     */
    public void setEmail(String email) {
        this.email = email;
    }


    /**
     * Obtiene la contraseña del conductor.
     *
     * @return Contraseña del conductor.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del conductor.
     *
     * @param password Contraseña del conductor.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene la calificación del conductor.
     *
     * @return Calificación del conductor.
     */
    public double getRating() {
        return rating;
    }


    /**
     * Establece la calificación del conductor.
     *
     * @param rating Calificación del conductor.
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Obtiene el número de viajes del conductor.
     *
     * @return Número de viajes del conductor.
     */
    public int getRides() {
        return rides;
    }

    /**
     * Establece el número de viajes del conductor.
     *
     * @param rides Número de viajes del conductor.
     */
    public void setRides(int rides) {
        this.rides = rides;
    }

}
