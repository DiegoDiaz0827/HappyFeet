/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entities;
import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 *
 * @author camper
 */

// MÓDULO 5: ACTIVIDADES ESPECIALES 
public class ClubMascotas {
    private int id;
    private int duenoId;
    private int puntosAcumulados;
    private int puntosCanjeados;
    private int puntosDisponibles;
    private String nivel;
    private LocalDate fechaInscripcion;
    private LocalDateTime fechaUltimaActualizacion;
    private boolean activo;

    // --- CONSTRUCTORES ---

    public ClubMascotas(int pId, int pDuenoId, int pPuntosAcumulados, int pPuntosCanjeados, int pPuntosDisponibles, String pNivel, LocalDate pFechaInscripcion, LocalDateTime pFechaUltimaActualizacion, boolean pActivo) {
        this.id = pId;
        this.duenoId = pDuenoId;
        this.puntosAcumulados = pPuntosAcumulados;
        this.puntosCanjeados = pPuntosCanjeados;
        this.puntosDisponibles = pPuntosDisponibles;
        this.nivel = pNivel;
        this.fechaInscripcion = pFechaInscripcion;
        this.fechaUltimaActualizacion = pFechaUltimaActualizacion;
        this.activo = pActivo;
    }

    
    public ClubMascotas(int pDuenoId, LocalDate pFechaInscripcion) {
        this.duenoId = pDuenoId;
        this.fechaInscripcion = pFechaInscripcion;
        
       
        this.puntosAcumulados = 0;
        this.puntosCanjeados = 0;
        this.puntosDisponibles = 0;
        this.nivel = "Bronce";
        this.activo = true;
        this.fechaUltimaActualizacion = LocalDateTime.now(); 
    }


    public int getId() {
        return id;
    }

    public int getDuenoId() {
        return duenoId;
    }

    public int getPuntosAcumulados() {
        return puntosAcumulados;
    }

    public int getPuntosCanjeados() {
        return puntosCanjeados;
    }

    public int getPuntosDisponibles() {
        return puntosDisponibles;
    }

    public String getNivel() {
        return nivel;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public LocalDateTime getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    public boolean isActivo() {
        return activo;
    }

   
    public void setId(int pId) {
        this.id = pId;
    }

    public void setDuenoId(int pDuenoId) {
        this.duenoId = pDuenoId;
    }

    public void setPuntosAcumulados(int pPuntosAcumulados) {
        this.puntosAcumulados = pPuntosAcumulados;
    }

    public void setPuntosCanjeados(int pPuntosCanjeados) {
        this.puntosCanjeados = pPuntosCanjeados;
    }

    public void setPuntosDisponibles(int pPuntosDisponibles) {
        this.puntosDisponibles = pPuntosDisponibles;
    }

    public void setNivel(String pNivel) {
        this.nivel = pNivel;
    }

    public void setFechaInscripcion(LocalDate pFechaInscripcion) {
        this.fechaInscripcion = pFechaInscripcion;
    }

    public void setFechaUltimaActualizacion(LocalDateTime pFechaUltimaActualizacion) {
        this.fechaUltimaActualizacion = pFechaUltimaActualizacion;
    }

    public void setActivo(boolean pActivo) {
        this.activo = pActivo;
    }

    @Override
    public String toString() {
        return "ClubMascotas(" +
                "id: " + id +
                ", duenoId: " + duenoId +
                ", puntosAcumulados: " + puntosAcumulados +
                ", puntosDisponibles: " + puntosDisponibles +
                ", nivel: '" + nivel + '\'' +
                ", activo: " + activo +
                ')';
    }
}

