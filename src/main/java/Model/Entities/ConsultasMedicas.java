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
public class ConsultasMedicas {
     private int id;
    private int mascotaId;
    private int veterinarioId;
    private Integer citaId; // puede ser null (ON DELETE SET NULL)
    private LocalDateTime fechaHora;
    private String motivo;
    private String sintomas;
    private String diagnostico;
    private String recomendaciones;
    private String observaciones;
    private Double pesoRegistrado;
    private Double temperatura;

    // Constructor con todos los atributos
    public ConsultasMedicas(int pId, int pMascotaId, int pVeterinarioId, Integer pCitaId,
                            LocalDateTime pFechaHora, String pMotivo, String pSintomas,
                            String pDiagnostico, String pRecomendaciones, String pObservaciones,
                            Double pPesoRegistrado, Double pTemperatura) {

        id = pId;
        mascotaId = pMascotaId;
        veterinarioId = pVeterinarioId;
        citaId = pCitaId;
        fechaHora = pFechaHora;
        motivo = pMotivo;
        sintomas = pSintomas;
        diagnostico = pDiagnostico;
        recomendaciones = pRecomendaciones;
        observaciones = pObservaciones;
        pesoRegistrado = pPesoRegistrado;
        temperatura = pTemperatura;
    }

    // Constructor sin id (para crear una nueva consulta)
    public ConsultasMedicas(int pMascotaId, int pVeterinarioId, Integer pCitaId,
                            LocalDateTime pFechaHora, String pMotivo, String pSintomas,
                            String pDiagnostico, String pRecomendaciones, String pObservaciones,
                            Double pPesoRegistrado, Double pTemperatura) {

        mascotaId = pMascotaId;
        veterinarioId = pVeterinarioId;
        citaId = pCitaId;
        fechaHora = pFechaHora;
        motivo = pMotivo;
        sintomas = pSintomas;
        diagnostico = pDiagnostico;
        recomendaciones = pRecomendaciones;
        observaciones = pObservaciones;
        pesoRegistrado = pPesoRegistrado;
        temperatura = pTemperatura;
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

    public Integer getCitaId() {
        return citaId;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getSintomas() {
        return sintomas;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public Double getPesoRegistrado() {
        return pesoRegistrado;
    }

    public Double getTemperatura() {
        return temperatura;
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

    public void setCitaId(Integer pCitaId) {
        citaId = pCitaId;
    }

    public void setFechaHora(LocalDateTime pFechaHora) {
        fechaHora = pFechaHora;
    }

    public void setMotivo(String pMotivo) {
        motivo = pMotivo;
    }

    public void setSintomas(String pSintomas) {
        sintomas = pSintomas;
    }

    public void setDiagnostico(String pDiagnostico) {
        diagnostico = pDiagnostico;
    }

    public void setRecomendaciones(String pRecomendaciones) {
        recomendaciones = pRecomendaciones;
    }

    public void setObservaciones(String pObservaciones) {
        observaciones = pObservaciones;
    }

    public void setPesoRegistrado(Double pPesoRegistrado) {
        pesoRegistrado = pPesoRegistrado;
    }

    public void setTemperatura(Double pTemperatura) {
        temperatura = pTemperatura;
    }

    @Override
    public String toString() {
        return "ConsultaMedica(id: " + id +
               ", mascotaId: " + mascotaId +
               ", veterinarioId: " + veterinarioId +
               ", citaId: " + citaId +
               ", fechaHora: " + fechaHora +
               ", motivo: " + motivo +
               ", sintomas: " + sintomas +
               ", diagnostico: " + diagnostico +
               ", recomendaciones: " + recomendaciones +
               ", observaciones: " + observaciones +
               ", pesoRegistrado: " + pesoRegistrado +
               ", temperatura: " + temperatura + ")";
    }
}
