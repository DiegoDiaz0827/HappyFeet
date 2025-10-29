/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entities;


import java.time.LocalDateTime;

/**
 *
 * @author camper
 */

// modulo 1
public class Dueños {
    private int id;
    private String nombreCompleto;
    private String documentoIdentidad;
    private String direccion;
    private String telefono;
    private String email;
    private String contactoEmergencia;
    private LocalDateTime fechaRegistro;
    private boolean activo;
   

    // Constructor con todos los atributos
    public Dueños(int pId, String pNombreCompleto, String pDocumentoIdentidad, String pDireccion,
                  String pTelefono, String pEmail, String pContactoEmergencia,
                  LocalDateTime pFechaRegistro, boolean pActivo) {
        id = pId;
        nombreCompleto = pNombreCompleto;
        documentoIdentidad = pDocumentoIdentidad;
        direccion = pDireccion;
        telefono = pTelefono;
        email = pEmail;
        contactoEmergencia = pContactoEmergencia;
        fechaRegistro = pFechaRegistro;
        activo = pActivo;
    }

    // Constructor sin id ni fecha (por ejemplo, antes de guardar en DB)
    public Dueños(String pNombreCompleto, String pDocumentoIdentidad, String pDireccion,
                  String pTelefono, String pEmail, String pContactoEmergencia, boolean pActivo) {
        nombreCompleto = pNombreCompleto;
        documentoIdentidad = pDocumentoIdentidad;
        direccion = pDireccion;
        telefono = pTelefono;
        email = pEmail;
        contactoEmergencia = pContactoEmergencia;
        activo = pActivo;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getContactoEmergencia() {
        return contactoEmergencia;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public boolean isActivo() {
        return activo;
    }

    // Setters
    public void setId(int pId) {
        id = pId;
    }

    public void setNombreCompleto(String pNombreCompleto) {
        nombreCompleto = pNombreCompleto;
    }

    public void setDocumentoIdentidad(String pDocumentoIdentidad) {
        documentoIdentidad = pDocumentoIdentidad;
    }

    public void setDireccion(String pDireccion) {
        direccion = pDireccion;
    }

    public void setTelefono(String pTelefono) {
        telefono = pTelefono;
    }

    public void setEmail(String pEmail) {
        email = pEmail;
    }

    public void setContactoEmergencia(String pContactoEmergencia) {
        contactoEmergencia = pContactoEmergencia;
    }

    public void setFechaRegistro(LocalDateTime pFechaRegistro) {
        fechaRegistro = pFechaRegistro;
    }

    public void setActivo(boolean pActivo) {
        activo = pActivo;
    }

    @Override
    public String toString() {
        return "Dueno(id: " + id +
               ", nombreCompleto: " + nombreCompleto +
               ", documentoIdentidad: " + documentoIdentidad +
               ", direccion: " + direccion +
               ", telefono: " + telefono +
               ", email: " + email +
               ", contactoEmergencia: " + contactoEmergencia +
               ", fechaRegistro: " + fechaRegistro +
               ", activo: " + activo + ")";
    }
}
