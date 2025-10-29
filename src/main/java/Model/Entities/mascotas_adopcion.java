/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entities;

import Model.Enums.EstadoAdopcion;
import java.util.Date;
/**
 *
 * @author camper
 */
public class mascotas_adopcion {
    private int id;
    private int mascotaId;
    private Date fechaIngreso;
    private String motivoIngreso;
    private EstadoAdopcion estado; 
    private String historia;
    private String temperamento;
    private String necesidadesEspeciales;
    private String fotoAdicionalUrl;

    // CONSTRUCTOR 1: COMPLETO (Con ID)
    public mascotas_adopcion(int pId, int pMascotaId, Date pFechaIngreso, String pMotivoIngreso, 
                             EstadoAdopcion pEstado, String pHistoria, String pTemperamento, 
                             String pNecesidadesEspeciales, String pFotoAdicionalUrl) {
        this.id = pId;
        this.mascotaId = pMascotaId;
        this.fechaIngreso = pFechaIngreso;
        this.motivoIngreso = pMotivoIngreso;
        this.estado = pEstado;
        this.historia = pHistoria;
        this.temperamento = pTemperamento;
        this.necesidadesEspeciales = pNecesidadesEspeciales;
        this.fotoAdicionalUrl = pFotoAdicionalUrl;
    }
    
    // CONSTRUCTOR 2: BASE PRIVADO (Inicializa todos los campos sin ID - Único constructor de 8 parámetros)
    private mascotas_adopcion(int pMascotaId, Date pFechaIngreso, String pMotivoIngreso, 
                              EstadoAdopcion pEstado, String pHistoria, String pTemperamento, 
                              String pNecesidadesEspeciales, String pFotoAdicionalUrl) {
        this.mascotaId = pMascotaId;
        this.fechaIngreso = pFechaIngreso;
        this.motivoIngreso = pMotivoIngreso;
        this.estado = pEstado;
        this.historia = pHistoria;
        this.temperamento = pTemperamento;
        this.necesidadesEspeciales = pNecesidadesEspeciales;
        this.fotoAdicionalUrl = pFotoAdicionalUrl;
    }

    // Getters
    public int getId() { return id; }
    public int getMascotaId() { return mascotaId; }
    public Date getFechaIngreso() { return fechaIngreso; }
    public String getMotivoIngreso() { return motivoIngreso; }
    public EstadoAdopcion getEstado() { return estado; }
    public String getHistoria() { return historia; }
    public String getTemperamento() { return temperamento; }
    public String getNecesidadesEspeciales() { return necesidadesEspeciales; }
    public String getFotoAdicionalUrl() { return fotoAdicionalUrl; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setMascotaId(int mascotaId) { this.mascotaId = mascotaId; }
    public void setFechaIngreso(Date fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    public void setMotivoIngreso(String motivoIngreso) { this.motivoIngreso = motivoIngreso; }
    public void setEstado(EstadoAdopcion estado) { this.estado = estado; }
    public void setHistoria(String historia) { this.historia = historia; }
    public void setTemperamento(String temperamento) { this.temperamento = temperamento; }
    public void setNecesidadesEspeciales(String necesidadesEspeciales) { this.necesidadesEspeciales = necesidadesEspeciales; }
    public void setFotoAdicionalUrl(String fotoAdicionalUrl) { this.fotoAdicionalUrl = fotoAdicionalUrl; }

    @Override
    public String toString() {
        return "mascotas_adopcion{" +
                "id=" + id +
                ", mascotaId=" + mascotaId +
                ", fechaIngreso=" + fechaIngreso +
                ", motivoIngreso='" + motivoIngreso + '\'' +
                ", estado=" + estado +
                ", historia='" + historia + '\'' +
                ", temperamento='" + temperamento + '\'' +
                ", necesidadesEspeciales='" + necesidadesEspeciales + '\'' +
                ", fotoAdicionalUrl='" + fotoAdicionalUrl + '\'' +
                '}';
    }
}

