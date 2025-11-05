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

// modulo 2
public class ProcedimientosEspeciales {
    

    private int id;
    private int mascotaId;
    private int veterinarioId;
    private String tipoProcedimiento;
    private String nombreProcedimiento;
    private LocalDateTime fechaHora;
    private Integer duracionEstimadaMinutos;
    private String informacionPreoperatoria;
    private String detalleProcedimiento;
    private String complicaciones;
    private String seguimientoPostoperatorio;
    private LocalDate proximoControl;
    private EstadoProcedimiento estado;
    private Double costoProcedimiento;

    // Enum para el estado del procedimiento
    public enum EstadoProcedimiento {
        PROGRAMADO,
        EN_PROCESO,
        FINALIZADO,
        CANCELADO
    }

    // Constructor completo
    public ProcedimientosEspeciales(int pId, int pMascotaId, int pVeterinarioId, String pTipoProcedimiento,
                                    String pNombreProcedimiento, LocalDateTime pFechaHora, Integer pDuracionEstimadaMinutos,
                                    String pInformacionPreoperatoria, String pDetalleProcedimiento, String pComplicaciones,
                                    String pSeguimientoPostoperatorio, LocalDate pProximoControl,
                                    EstadoProcedimiento pEstado, Double pCostoProcedimiento) {

        id = pId;
        mascotaId = pMascotaId;
        veterinarioId = pVeterinarioId;
        tipoProcedimiento = pTipoProcedimiento;
        nombreProcedimiento = pNombreProcedimiento;
        fechaHora = pFechaHora;
        duracionEstimadaMinutos = pDuracionEstimadaMinutos;
        informacionPreoperatoria = pInformacionPreoperatoria;
        detalleProcedimiento = pDetalleProcedimiento;
        complicaciones = pComplicaciones;
        seguimientoPostoperatorio = pSeguimientoPostoperatorio;
        proximoControl = pProximoControl;
        estado = pEstado;
        costoProcedimiento = pCostoProcedimiento;
    }

    
    public ProcedimientosEspeciales(int pMascotaId, int pVeterinarioId, String pTipoProcedimiento,
                                    String pNombreProcedimiento, LocalDateTime pFechaHora, Integer pDuracionEstimadaMinutos,
                                    String pInformacionPreoperatoria, String pDetalleProcedimiento, String pComplicaciones,
                                    String pSeguimientoPostoperatorio, LocalDate pProximoControl,
                                    EstadoProcedimiento pEstado, Double pCostoProcedimiento) {

        mascotaId = pMascotaId;
        veterinarioId = pVeterinarioId;
        tipoProcedimiento = pTipoProcedimiento;
        nombreProcedimiento = pNombreProcedimiento;
        fechaHora = pFechaHora;
        duracionEstimadaMinutos = pDuracionEstimadaMinutos;
        informacionPreoperatoria = pInformacionPreoperatoria;
        detalleProcedimiento = pDetalleProcedimiento;
        complicaciones = pComplicaciones;
        seguimientoPostoperatorio = pSeguimientoPostoperatorio;
        proximoControl = pProximoControl;
        estado = pEstado;
        costoProcedimiento = pCostoProcedimiento;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getMascotaId() {
        return mascotaId;
    }

    public int getVeterinarioId() {
        return veterinarioId;
    }

    public String getTipoProcedimiento() {
        return tipoProcedimiento;
    }

    public String getNombreProcedimiento() {
        return nombreProcedimiento;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public Integer getDuracionEstimadaMinutos() {
        return duracionEstimadaMinutos;
    }

    public String getInformacionPreoperatoria() {
        return informacionPreoperatoria;
    }

    public String getDetalleProcedimiento() {
        return detalleProcedimiento;
    }

    public String getComplicaciones() {
        return complicaciones;
    }

    public String getSeguimientoPostoperatorio() {
        return seguimientoPostoperatorio;
    }

    public LocalDate getProximoControl() {
        return proximoControl;
    }

    public EstadoProcedimiento getEstado() {
        return estado;
    }

    public Double getCostoProcedimiento() {
        return costoProcedimiento;
    }

    // Setters
    public void setId(int pId) {
        id = pId;
    }

    public void setMascotaId(int pMascotaId) {
        mascotaId = pMascotaId;
    }

    public void setVeterinarioId(int pVeterinarioId) {
        veterinarioId = pVeterinarioId;
    }

    public void setTipoProcedimiento(String pTipoProcedimiento) {
        tipoProcedimiento = pTipoProcedimiento;
    }

    public void setNombreProcedimiento(String pNombreProcedimiento) {
        nombreProcedimiento = pNombreProcedimiento;
    }

    public void setFechaHora(LocalDateTime pFechaHora) {
        fechaHora = pFechaHora;
    }

    public void setDuracionEstimadaMinutos(Integer pDuracionEstimadaMinutos) {
        duracionEstimadaMinutos = pDuracionEstimadaMinutos;
    }

    public void setInformacionPreoperatoria(String pInformacionPreoperatoria) {
        informacionPreoperatoria = pInformacionPreoperatoria;
    }

    public void setDetalleProcedimiento(String pDetalleProcedimiento) {
        detalleProcedimiento = pDetalleProcedimiento;
    }

    public void setComplicaciones(String pComplicaciones) {
        complicaciones = pComplicaciones;
    }

    public void setSeguimientoPostoperatorio(String pSeguimientoPostoperatorio) {
        seguimientoPostoperatorio = pSeguimientoPostoperatorio;
    }

    public void setProximoControl(LocalDate pProximoControl) {
        proximoControl = pProximoControl;
    }

    public void setEstado(EstadoProcedimiento pEstado) {
        estado = pEstado;
    }

    public void setCostoProcedimiento(Double pCostoProcedimiento) {
        costoProcedimiento = pCostoProcedimiento;
    }

    @Override
    public String toString() {
        return "ProcedimientoEspecial(id: " + id +
               ", mascotaId: " + mascotaId +
               ", veterinarioId: " + veterinarioId +
               ", tipo: " + tipoProcedimiento +
               ", nombre: " + nombreProcedimiento +
               ", fechaHora: " + fechaHora +
               ", duracion: " + duracionEstimadaMinutos +
               ", estado: " + estado +
               ", costo: " + costoProcedimiento + ")";
    }
    }
