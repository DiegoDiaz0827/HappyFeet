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
public class HistorialMedico {
   
     private int id;
    private int mascotaId;
    private LocalDate fechaEvento;
    private int eventoTipoId;
    private String descripcion;
    private String diagnostico;
    private String tratamientoRecomendado;
    private Integer veterinarioId;   
    private Integer consultaId;      
    private Integer procedimientoId; 

    // Constructor completo
    public HistorialMedico(int pId, int pMascotaId, LocalDate pFechaEvento, int pEventoTipoId,
                           String pDescripcion, String pDiagnostico, String pTratamientoRecomendado,
                           Integer pVeterinarioId, Integer pConsultaId, Integer pProcedimientoId) {

        id = pId;
        mascotaId = pMascotaId;
        fechaEvento = pFechaEvento;
        eventoTipoId = pEventoTipoId;
        descripcion = pDescripcion;
        diagnostico = pDiagnostico;
        tratamientoRecomendado = pTratamientoRecomendado;
        veterinarioId = pVeterinarioId;
        consultaId = pConsultaId;
        procedimientoId = pProcedimientoId;
    }

    // Constructor sin ID (para nuevas inserciones)
    public HistorialMedico(int pMascotaId, LocalDate pFechaEvento, int pEventoTipoId,
                           String pDescripcion, String pDiagnostico, String pTratamientoRecomendado,
                           Integer pVeterinarioId, Integer pConsultaId, Integer pProcedimientoId) {

        mascotaId = pMascotaId;
        fechaEvento = pFechaEvento;
        eventoTipoId = pEventoTipoId;
        descripcion = pDescripcion;
        diagnostico = pDiagnostico;
        tratamientoRecomendado = pTratamientoRecomendado;
        veterinarioId = pVeterinarioId;
        consultaId = pConsultaId;
        procedimientoId = pProcedimientoId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getMascotaId() {
        return mascotaId;
    }

    public LocalDate getFechaEvento() {
        return fechaEvento;
    }

    public int getEventoTipoId() {
        return eventoTipoId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String getTratamientoRecomendado() {
        return tratamientoRecomendado;
    }

    public Integer getVeterinarioId() {
        return veterinarioId;
    }

    public Integer getConsultaId() {
        return consultaId;
    }

    public Integer getProcedimientoId() {
        return procedimientoId;
    }

    // Setters
    public void setId(int pId) {
        id = pId;
    }

    public void setMascotaId(int pMascotaId) {
        mascotaId = pMascotaId;
    }

    public void setFechaEvento(LocalDate pFechaEvento) {
        fechaEvento = pFechaEvento;
    }

    public void setEventoTipoId(int pEventoTipoId) {
        eventoTipoId = pEventoTipoId;
    }

    public void setDescripcion(String pDescripcion) {
        descripcion = pDescripcion;
    }

    public void setDiagnostico(String pDiagnostico) {
        diagnostico = pDiagnostico;
    }

    public void setTratamientoRecomendado(String pTratamientoRecomendado) {
        tratamientoRecomendado = pTratamientoRecomendado;
    }

    public void setVeterinarioId(Integer pVeterinarioId) {
        veterinarioId = pVeterinarioId;
    }

    public void setConsultaId(Integer pConsultaId) {
        consultaId = pConsultaId;
    }

    public void setProcedimientoId(Integer pProcedimientoId) {
        procedimientoId = pProcedimientoId;
    }

    @Override
    public String toString() {
        return "HistorialMedico(id: " + id +
               ", mascotaId: " + mascotaId +
               ", fechaEvento: " + fechaEvento +
               ", eventoTipoId: " + eventoTipoId +
               ", descripcion: " + descripcion +
               ", diagnostico: " + diagnostico +
               ", tratamientoRecomendado: " + tratamientoRecomendado +
               ", veterinarioId: " + veterinarioId +
               ", consultaId: " + consultaId +
               ", procedimientoId: " + procedimientoId + ")";
    }
    
    
}
