/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entities;

/**
 *
 * @author camper
 */
public class InsumoProcedimiento {
    private int id;
    private int procedimientoId;
    private int productoId;
    private int cantidadUsada;
    private String observaciones;

    // --- Constructor ---
    public InsumoProcedimiento(int pId, int pProcedimientoId, int pProductoId,
                               int pCantidadUsada, String pObservaciones) {
        this.id = pId;
        this.procedimientoId = pProcedimientoId;
        this.productoId = pProductoId;
        this.cantidadUsada = pCantidadUsada;
        this.observaciones = pObservaciones;
    }

    // --- Getters y Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProcedimientoId() { return procedimientoId; }
    public void setProcedimientoId(int procedimientoId) { this.procedimientoId = procedimientoId; }

    public int getProductoId() { return productoId; }
    public void setProductoId(int productoId) { this.productoId = productoId; }

    public int getCantidadUsada() { return cantidadUsada; }
    public void setCantidadUsada(int cantidadUsada) { this.cantidadUsada = cantidadUsada; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}

    

