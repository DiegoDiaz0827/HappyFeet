/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entities;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author camper
 */
public class registro_jornada_vacunacion {
    private int id;
    private int jornadaId;
    private int mascotaId;
    private int duenoId;
    private int vacunaId;
    private Integer veterinarioId; // Usamos Integer para permitir NULL (ON DELETE SET NULL)
    private Timestamp fechaHora;   // Mapea DATETIME
    private String loteVacuna;
    private Date proximaDosis;
    private String observaciones;

    // CONSTRUCTOR COMPLETO (Con ID)
    public registro_jornada_vacunacion(int pId, int pJornadaId, int pMascotaId, int pDuenoId, 
                                       int pVacunaId, Integer pVeterinarioId, Timestamp pFechaHora, 
                                       String pLoteVacuna, Date pProximaDosis, String pObservaciones) {
        this.id = pId;
        this.jornadaId = pJornadaId;
        this.mascotaId = pMascotaId;
        this.duenoId = pDuenoId;
        this.vacunaId = pVacunaId;
        this.veterinarioId = pVeterinarioId;
        this.fechaHora = pFechaHora;
        this.loteVacuna = pLoteVacuna;
        this.proximaDosis = pProximaDosis;
        this.observaciones = pObservaciones;
    }
    
    // CONSTRUCTOR BASE PRIVADO (Inicializa todos los campos sin ID - Para inserción/llamadas internas)
    private registro_jornada_vacunacion(int pJornadaId, int pMascotaId, int pDuenoId, 
                                        int pVacunaId, Integer pVeterinarioId, Timestamp pFechaHora, 
                                        String pLoteVacuna, Date pProximaDosis, String pObservaciones) {
        this.jornadaId = pJornadaId;
        this.mascotaId = pMascotaId;
        this.duenoId = pDuenoId;
        this.vacunaId = pVacunaId;
        this.veterinarioId = pVeterinarioId;
        this.fechaHora = pFechaHora;
        this.loteVacuna = pLoteVacuna;
        this.proximaDosis = pProximaDosis;
        this.observaciones = pObservaciones;
    }

    // Getters
    public int getId() { return id; }
    public int getJornadaId() { return jornadaId; }
    public int getMascotaId() { return mascotaId; }
    public int getDuenoId() { return duenoId; }
    public int getVacunaId() { return vacunaId; }
    public Integer getVeterinarioId() { return veterinarioId; }
    public Timestamp getFechaHora() { return fechaHora; }
    public String getLoteVacuna() { return loteVacuna; }
    public Date getProximaDosis() { return proximaDosis; }
    public String getObservaciones() { return observaciones; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setJornadaId(int jornadaId) { this.jornadaId = jornadaId; }
    public void setMascotaId(int mascotaId) { this.mascotaId = mascotaId; }
    public void setDuenoId(int duenoId) { this.duenoId = duenoId; }
    public void setVacunaId(int vacunaId) { this.vacunaId = vacunaId; }
    public void setVeterinarioId(Integer veterinarioId) { this.veterinarioId = veterinarioId; }
    public void setFechaHora(Timestamp fechaHora) { this.fechaHora = fechaHora; }
    public void setLoteVacuna(String loteVacuna) { this.loteVacuna = loteVacuna; }
    public void setProximaDosis(Date proximaDosis) { this.proximaDosis = proximaDosis; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    @Override
    public String toString() {
        return "registro_jornada_vacunacion{" +
                "id=" + id +
                ", jornadaId=" + jornadaId +
                ", mascotaId=" + mascotaId +
                ", duenoId=" + duenoId +
                ", vacunaId=" + vacunaId +
                ", veterinarioId=" + veterinarioId +
                ", fechaHora=" + fechaHora +
                ", loteVacuna='" + loteVacuna + '\'' +
                ", proximaDosis=" + proximaDosis +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}
