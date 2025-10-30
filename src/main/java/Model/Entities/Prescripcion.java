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
public class Prescripcion {
    
    private int id;
    private Integer consultaId;
    private Integer procedimientoId;
    private int productoId;
    private int cantidad;
    private String dosis;
    private String frecuencia;
    private Integer duracionDias;
    private String instrucciones;
    private LocalDateTime fechaPrescripcion;

    // --- Constructor ---
     public Prescripcion(int pId, Integer pConsultaId, Integer pProcedimientoId, int pProductoId,
                        int pCantidad, String pDosis, String pFrecuencia, Integer pDuracionDias,
                        String pInstrucciones, LocalDateTime pFechaPrescripcion) {
        this.id = pId;
        this.consultaId = pConsultaId;
        this.procedimientoId = pProcedimientoId;
        this.productoId = pProductoId;
        this.cantidad = pCantidad;
        this.dosis = pDosis;
        this.frecuencia = pFrecuencia;
        this.duracionDias = pDuracionDias;
        this.instrucciones = pInstrucciones;
        this.fechaPrescripcion = pFechaPrescripcion;
    }

    // --- Getters y Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getConsultaId() { return consultaId; }
    public void setConsultaId(Integer consultaId) { this.consultaId = consultaId; }

    public Integer getProcedimientoId() { return procedimientoId; }
    public void setProcedimientoId(Integer procedimientoId) { this.procedimientoId = procedimientoId; }

    public int getProductoId() { return productoId; }
    public void setProductoId(int productoId) { this.productoId = productoId; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }

    public String getFrecuencia() { return frecuencia; }
    public void setFrecuencia(String frecuencia) { this.frecuencia = frecuencia; }

    public Integer getDuracionDias() { return duracionDias; }
    public void setDuracionDias(Integer duracionDias) { this.duracionDias = duracionDias; }

    public String getInstrucciones() { return instrucciones; }
    public void setInstrucciones(String instrucciones) { this.instrucciones = instrucciones; }

    public LocalDateTime getFechaPrescripcion() { return fechaPrescripcion; }
    public void setFechaPrescripcion(LocalDateTime fechaPrescripcion) { this.fechaPrescripcion = fechaPrescripcion; }
}

    

