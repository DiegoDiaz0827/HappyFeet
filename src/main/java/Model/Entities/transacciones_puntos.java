/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entities;

import Model.Enums.TipoTransacciones;
import java.sql.Timestamp;
/**
 *
 * @author camper
 */

//modulo 5
public class transacciones_puntos {
    private int id;
    private int clubMascotasId;      
    private Integer facturaId;         
    private int puntos;
    private TipoTransacciones tipo; 
    private Timestamp fecha;           
    private String descripcion;
    private int saldoAnterior;
    private int saldoNuevo;

    // CONSTRUCTORES
    public transacciones_puntos(int pId, int pClubMascotasId, Integer pFacturaId, int pPuntos, 
                                TipoTransacciones pTipo, Timestamp pFecha, String pDescripcion, 
                                int pSaldoAnterior, int pSaldoNuevo) {
        this.id = pId;
        this.clubMascotasId = pClubMascotasId;
        this.facturaId = pFacturaId;
        this.puntos = pPuntos;
        this.tipo = pTipo;
        this.fecha = pFecha;
        this.descripcion = pDescripcion;
        this.saldoAnterior = pSaldoAnterior;
        this.saldoNuevo = pSaldoNuevo;
    }
    
    private transacciones_puntos(int pClubMascotasId, Integer pFacturaId, int pPuntos, 
                                 TipoTransacciones pTipo, Timestamp pFecha, String pDescripcion, 
                                 int pSaldoAnterior, int pSaldoNuevo) {
        this.clubMascotasId = pClubMascotasId;
        this.facturaId = pFacturaId;
        this.puntos = pPuntos;
        this.tipo = pTipo;
        this.fecha = pFecha;
        this.descripcion = pDescripcion;
        this.saldoAnterior = pSaldoAnterior;
        this.saldoNuevo = pSaldoNuevo;
    }

    
    public int getId() { return id; }
    public int getClubMascotasId() { return clubMascotasId; }
    public Integer getFacturaId() { return facturaId; }
    public int getPuntos() { return puntos; }
    public TipoTransacciones getTipo() { return tipo; } 
    public Timestamp getFecha() { return fecha; }
    public String getDescripcion() { return descripcion; }
    public int getSaldoAnterior() { return saldoAnterior; }
    public int getSaldoNuevo() { return saldoNuevo; }

   
    public void setId(int id) { this.id = id; }
    public void setClubMascotasId(int clubMascotasId) { this.clubMascotasId = clubMascotasId; }
    public void setFacturaId(Integer facturaId) { this.facturaId = facturaId; }
    public void setPuntos(int puntos) { this.puntos = puntos; }
    public void setTipo(TipoTransacciones tipo) { this.tipo = tipo; } 
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setSaldoAnterior(int saldoAnterior) { this.saldoAnterior = saldoAnterior; }
    public void setSaldoNuevo(int saldoNuevo) { this.saldoNuevo = saldoNuevo; }

    @Override
    public String toString() {
        return "transacciones_puntos{" +
                "id=" + id +
                ", clubMascotasId=" + clubMascotasId +
                ", facturaId=" + facturaId +
                ", puntos=" + puntos +
                ", tipo=" + tipo +
                ", fecha=" + fecha +
                ", descripcion='" + descripcion + '\'' +
                ", saldoAnterior=" + saldoAnterior +
                ", saldoNuevo=" + saldoNuevo +
                '}';
    }

}
