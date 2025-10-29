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
public class Citas {
 private int id;
    private int mascotaId;
    private Integer veterinarioId; // Puede ser null (ON DELETE SET NULL)
    private LocalDateTime fechaHora;
    private String motivo;
    private int estadoId;
    private String observaciones;
    private LocalDateTime fechaCreacion;

    // Constructor con todos los atributos
    public Citas(int pId, int pMascotaId, Integer pVeterinarioId,
                 LocalDateTime pFechaHora, String pMotivo, int pEstadoId,
                 String pObservaciones, LocalDateTime pFechaCreacion) {

        id = pId;
        mascotaId = pMascotaId;
        veterinarioId = pVeterinarioId;
        fechaHora = pFechaHora;
        motivo = pMotivo;
        estadoId = pEstadoId;
        observaciones = pObservaciones;
        fechaCreacion = pFechaCreacion;
    }

    // Constructor sin id ni fechaCreacion (al crear una nueva cita)
    public Citas(int pMascotaId, Integer pVeterinarioId,
                 LocalDateTime pFechaHora, String pMotivo, int pEstadoId,
                 String pObservaciones) {

        mascotaId = pMascotaId;
        veterinarioId = pVeterinarioId;
        fechaHora = pFechaHora;
        motivo = pMotivo;
        estadoId = pEstadoId;
        observaciones = pObservaciones;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getMascotaId() {
        return mascotaId;
    }

    public Integer getVeterinarioId() {
        return veterinarioId;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    // Setters
    public void setId(int pId) {
        id = pId;
    }

    public void setMascotaId(int pMascotaId) {
        mascotaId = pMascotaId;
    }

    public void setVeterinarioId(Integer pVeterinarioId) {
        veterinarioId = pVeterinarioId;
    }

    public void setFechaHora(LocalDateTime pFechaHora) {
        fechaHora = pFechaHora;
    }

    public void setMotivo(String pMotivo) {
        motivo = pMotivo;
    }

    public void setEstadoId(int pEstadoId) {
        estadoId = pEstadoId;
    }

    public void setObservaciones(String pObservaciones) {
        observaciones = pObservaciones;
    }

    public void setFechaCreacion(LocalDateTime pFechaCreacion) {
        fechaCreacion = pFechaCreacion;
    }

    @Override
    public String toString() {
        return "Cita(id: " + id +
               ", mascotaId: " + mascotaId +
               ", veterinarioId: " + veterinarioId +
               ", fechaHora: " + fechaHora +
               ", motivo: " + motivo +
               ", estadoId: " + estadoId +
               ", observaciones: " + observaciones +
               ", fechaCreacion: " + fechaCreacion + ")";
    }
    
    
}
