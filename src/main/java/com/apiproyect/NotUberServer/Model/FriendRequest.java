package com.apiproyect.NotUberServer.Model;

/**
 * Clase que representa una solicitud de amistad.
 */
public class FriendRequest {
    private String email; // Correo electrónico del usuario que envía la solicitud.
    private String friend; // Correo electrónico del amigo al que se envía la solicitud.

    /**
     * Constructor de la clase FriendRequest.
     *
     * @param email  Correo electrónico del usuario que envía la solicitud.
     * @param friend Correo electrónico del amigo al que se envía la solicitud.
     */
    public FriendRequest(String email, String friend) {
        this.email = email;
        this.friend = friend;
    }

    /**
     * Obtiene el correo electrónico del usuario que envía la solicitud.
     *
     * @return Correo electrónico del usuario que envía la solicitud.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Obtiene el correo electrónico del amigo al que se envía la solicitud.
     *
     * @return Correo electrónico del amigo al que se envía la solicitud.
     */
    public String getFriend() {
        return friend;
    }

    /**
     * Establece un nuevo correo electrónico para el usuario que envía la solicitud.
     *
     * @param newEmail Nuevo correo electrónico.
     */
    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    /**
     * Establece un nuevo correo electrónico para el amigo al que se envía la solicitud.
     *
     * @param newFriend Nuevo correo electrónico del amigo.
     */
    public void setFriend(String newFriend) {
        this.friend = newFriend;
    }
}
