/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entities;

import java.time.LocalDate;


/**
 *
 * @author camper
 */


// modulo 2
public class Veterinarios {
    private int id;
    private String nombreCompleto;
    private String licenciaprofesional;
    private String documentoIdentidad;
    private String telefono;
    private String email;
    
    private String especialidad;
    private LocalDate fechaRegistro;
    private boolean activo;

    // Constructor con todos los atributos
    public Veterinarios(int pId, String pNombreCompleto, String pDocumentoIdentidad,
                        String pTelefono, String pEmail, String pLicencia,
                        String pEspecialidad, LocalDate pFechaRegistro, boolean pActivo) {

        id = pId;
        nombreCompleto = pNombreCompleto;
        documentoIdentidad = pDocumentoIdentidad;
        licenciaprofesional = pLicencia;
        telefono = pTelefono;
        email = pEmail;
        
        especialidad = pEspecialidad;
        fechaRegistro = pFechaRegistro;
        activo = pActivo;
    }

    // Constructor sin id ni fechaRegistro (por ejemplo, al crear un nuevo veterinario)
    public Veterinarios(String pNombreCompleto, String pDocumentoIdentidad,String pLicencia, 
                        String pTelefono, String pEmail, 
                        String pEspecialidad, boolean pActivo) {

        nombreCompleto = pNombreCompleto;
        documentoIdentidad = pDocumentoIdentidad;
        telefono = pTelefono;
        email = pEmail;
        licenciaprofesional = pLicencia;
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
    
     public String getlicencia() {
        return licenciaprofesional;
    }


    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

   

    public String getEspecialidad() {
        return especialidad;
    }

    public LocalDate getFechaRegistro() {
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
    
        public void setLicencia(String pLicencia) {
        licenciaprofesional = pLicencia;
    }

    public void setTelefono(String pTelefono) {
        telefono = pTelefono;
    }

    public void setEmail(String pEmail) {
        email = pEmail;
    }

   

    public void setEspecialidad(String pEspecialidad) {
        especialidad = pEspecialidad;
    }

    public void setFechaRegistro(LocalDate pFechaRegistro) {
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
               
               ", especialidad: " + especialidad +
               ", fechaRegistro: " + fechaRegistro +
               ", activo: " + activo + ")";
    }
}
