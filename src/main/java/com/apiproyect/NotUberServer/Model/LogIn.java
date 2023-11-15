package com.apiproyect.NotUberServer.Model;


/**
 * Clase que representa las credenciales de inicio de sesión.
 */
public class LogIn {
    private String email;
    private String password;

    /**
     * Constructor con argumentos de la clase LogIn.
     *
     * @param email    Correo electrónico para iniciar sesión.
     * @param password Contraseña para iniciar sesión.
     */
    public LogIn(String email, String password) {
        this.email = email;
        this.password = password;
    }


    /**
     * Obtiene el correo electrónico para iniciar sesión.
     *
     * @return Correo electrónico para iniciar sesión.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Obtiene la contraseña para iniciar sesión.
     *
     * @return Contraseña para iniciar sesión.
     */
    public String getPassword() {
        return this.password;
    }
}
