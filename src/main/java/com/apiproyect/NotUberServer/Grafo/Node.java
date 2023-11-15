package com.apiproyect.NotUberServer.Grafo;

/**
 * Clase que representa un nodo en un grafo.
 */
public class Node {
    private String name;
    private double latitude;
    private double longitude;

    /**
     * Constructor de la clase Node.
     *
     * @param name      Nombre del nodo.
     * @param latitude  Latitud del nodo.
     * @param longitude Longitud del nodo.
     */
    public Node(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Obtiene el nombre del nodo.
     *
     * @return Nombre del nodo.
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene la latitud del nodo.
     *
     * @return Latitud del nodo.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Obtiene la longitud del nodo.
     *
     * @return Longitud del nodo.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Representación de cadena del nodo (utiliza el nombre).
     *
     * @return Nombre del nodo como representación de cadena.
     */
    @Override
    public String toString() {
        return name;
    }
}
