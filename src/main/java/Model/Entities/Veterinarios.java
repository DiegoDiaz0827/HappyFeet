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


// modulo 2
public class Veterinarios {
     private int id;
    private String nombreCompleto;
    private String documentoIdentidad;
    private String telefono;
    private String email;
    private String direccion;
    private String especialidad;
    private LocalDateTime fechaRegistro;
    private boolean activo;

    // Constructor con todos los atributos
    public Veterinarios(int pId, String pNombreCompleto, String pDocumentoIdentidad,
                        String pTelefono, String pEmail, String pDireccion,
                        String pEspecialidad, LocalDateTime pFechaRegistro, boolean pActivo) {

        id = pId;
        nombreCompleto = pNombreCompleto;
        documentoIdentidad = pDocumentoIdentidad;
        telefono = pTelefono;
        email = pEmail;
        direccion = pDireccion;
        especialidad = pEspecialidad;
        fechaRegistro = pFechaRegistro;
        activo = pActivo;
    }

    // Constructor sin id ni fechaRegistro (por ejemplo, al crear un nuevo veterinario)
    public Veterinarios(String pNombreCompleto, String pDocumentoIdentidad,
                        String pTelefono, String pEmail, String pDireccion,
                        String pEspecialidad, boolean pActivo) {

        nombreCompleto = pNombreCompleto;
        documentoIdentidad = pDocumentoIdentidad;
        telefono = pTelefono;
        email = pEmail;
        direccion = pDireccion;
        especialidad = pEspecialidad;
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

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEspecialidad() {
        return especialidad;
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

    public void setTelefono(String pTelefono) {
        telefono = pTelefono;
    }

    public void setEmail(String pEmail) {
        email = pEmail;
    }

    public void setDireccion(String pDireccion) {
        direccion = pDireccion;
    }

    public void setEspecialidad(String pEspecialidad) {
        especialidad = pEspecialidad;
    }

    public void setFechaRegistro(LocalDateTime pFechaRegistro) {
        fechaRegistro = pFechaRegistro;
    }

    public void setActivo(boolean pActivo) {
        activo = pActivo;
    }

    @Override
    public String toString() {
        return "Veterinario(id: " + id +
               ", nombreCompleto: " + nombreCompleto +
               ", documentoIdentidad: " + documentoIdentidad +
               ", telefono: " + telefono +
               ", email: " + email +
               ", direccion: " + direccion +
               ", especialidad: " + especialidad +
               ", fechaRegistro: " + fechaRegistro +
               ", activo: " + activo + ")";
    }
}
