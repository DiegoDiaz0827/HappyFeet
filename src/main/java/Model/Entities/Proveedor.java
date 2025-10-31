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




public class Proveedor {
    private int id;
    private String nombreEmpresa;
    private String contacto;
    private String telefono;
    private String email;
    private String direccion;
    private String sitioWeb;
    private boolean activo;
    private LocalDateTime fechaRegistro;

    // --- Constructores ---
    public Proveedor(){}

    public Proveedor(int pId, String pNombreEmpresa, String pContacto, String pTelefono,
                     String pEmail, String pDireccion, String pSitioWeb,
                     boolean pActivo, LocalDateTime pFechaRegistro) {
        this.id = pId;
        this.nombreEmpresa = pNombreEmpresa;
        this.contacto = pContacto;
        this.telefono = pTelefono;
        this.email = pEmail;
        this.direccion = pDireccion;
        this.sitioWeb = pSitioWeb;
        this.activo = pActivo;
        this.fechaRegistro = pFechaRegistro;
    }

    // --- Getters y Setters ---

   
    public int getId() { 
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa; 
    }
    public void setNombreEmpresa(String nombreEmpresa) { 
        this.nombreEmpresa = nombreEmpresa; 
    }

    public String getContacto() { 
        return contacto; 
    }
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelefono() { 
        return telefono;
    }
    public void setTelefono(String telefono) { 
        this.telefono = telefono; 
    }

    public String getEmail() {
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getDireccion() { 
        return direccion; 
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion; 
    }

    public String getSitioWeb() {
        return sitioWeb; 
    }
    public void setSitioWeb(String sitioWeb) { 
        this.sitioWeb = sitioWeb; 
    }

    public boolean isActivo() { 
        return activo; 
    }
    public void setActivo(boolean activo) { 
        this.activo = activo; 
    }

    public LocalDateTime getFechaRegistro() { 
        return fechaRegistro; }
    
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro; 
    }
}
