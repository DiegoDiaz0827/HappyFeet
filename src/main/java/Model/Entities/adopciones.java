/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entities;

import java.util.Date;

/**
 *
 * @author camper
 */
//modulo 5
public class adopciones {
    private int id;
    private int mascotaAdopcionId; 
    private int duenoId;             
    private Date fechaAdopcion;     
    private String contratoTexto;      
    private String condicionesEspeciales; 
    private boolean seguimientoRequerido; 
    private Date fechaPrimerSeguimiento; 

    // CONSTRUCTORES
    public adopciones(int pId, int pMascotaAdopcionId, int pDuenoId, Date pFechaAdopcion, 
                      String pContratoTexto, String pCondicionesEspeciales, 
                      boolean pSeguimientoRequerido, Date pFechaPrimerSeguimiento) {
        this.id = pId;
        this.mascotaAdopcionId = pMascotaAdopcionId;
        this.duenoId = pDuenoId;
        this.fechaAdopcion = pFechaAdopcion;
        this.contratoTexto = pContratoTexto;
        this.condicionesEspeciales = pCondicionesEspeciales;
        this.seguimientoRequerido = pSeguimientoRequerido;
        this.fechaPrimerSeguimiento = pFechaPrimerSeguimiento;
    }
    
  
    private adopciones(int pMascotaAdopcionId, int pDuenoId, Date pFechaAdopcion, 
                       String pContratoTexto, String pCondicionesEspeciales, 
                       boolean pSeguimientoRequerido, Date pFechaPrimerSeguimiento) {
        this.mascotaAdopcionId = pMascotaAdopcionId;
        this.duenoId = pDuenoId;
        this.fechaAdopcion = pFechaAdopcion;
        this.contratoTexto = pContratoTexto;
        this.condicionesEspeciales = pCondicionesEspeciales;
        this.seguimientoRequerido = pSeguimientoRequerido;
        this.fechaPrimerSeguimiento = pFechaPrimerSeguimiento;
    }

    public int getId() { return id; }
    public int getMascotaAdopcionId() { return mascotaAdopcionId; }
    public int getDuenoId() { return duenoId; }
    public Date getFechaAdopcion() { return fechaAdopcion; }
    public String getContratoTexto() { return contratoTexto; }
    public String getCondicionesEspeciales() { return condicionesEspeciales; }
    public boolean isSeguimientoRequerido() { return seguimientoRequerido; }
    public Date getFechaPrimerSeguimiento() { return fechaPrimerSeguimiento; }

    public void setId(int id) { this.id = id; }
    public void setMascotaAdopcionId(int mascotaAdopcionId) { this.mascotaAdopcionId = mascotaAdopcionId; }
    public void setDuenoId(int duenoId) { this.duenoId = duenoId; }
    public void setFechaAdopcion(Date fechaAdopcion) { this.fechaAdopcion = fechaAdopcion; }
    public void setContratoTexto(String contratoTexto) { this.contratoTexto = contratoTexto; }
    public void setCondicionesEspeciales(String condicionesEspeciales) { this.condicionesEspeciales = condicionesEspeciales; }
    public void setSeguimientoRequerido(boolean seguimientoRequerido) { this.seguimientoRequerido = seguimientoRequerido; }
    public void setFechaPrimerSeguimiento(Date fechaPrimerSeguimiento) { this.fechaPrimerSeguimiento = fechaPrimerSeguimiento; }

    @Override
    public String toString() {
        return "adopciones{" +
                "id=" + id +
                ", mascotaAdopcionId=" + mascotaAdopcionId +
                ", duenoId=" + duenoId +
                ", fechaAdopcion=" + fechaAdopcion +
                ", contratoTexto='" + contratoTexto + '\'' +
                ", condicionesEspeciales='" + condicionesEspeciales + '\'' +
                ", seguimientoRequerido=" + seguimientoRequerido +
                ", fechaPrimerSeguimiento=" + fechaPrimerSeguimiento +
                '}';
    }

}
