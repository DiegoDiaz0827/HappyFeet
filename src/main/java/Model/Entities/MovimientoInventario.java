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
public class MovimientoInventario {
    
    private int id;
    private int productoId;
    private String tipoMovimiento; 
    private int cantidad;
    private int stockAnterior;
    private int stockNuevo;
    private String motivo;
    private Integer referenciaConsultaId;
    private Integer referenciaProcedimientoId;
    private String usuario;
    private LocalDateTime fechaMovimiento;

    // --- Constructores ---
   public MovimientoInventario(int pId, int pProductoId, String pTipoMovimiento, int pCantidad,
                                int pStockAnterior, int pStockNuevo, String pMotivo,
                                Integer pReferenciaConsultaId, Integer pReferenciaProcedimientoId,
                                String pUsuario, LocalDateTime pFechaMovimiento) {
        this.id = pId;
        this.productoId = pProductoId;
        this.tipoMovimiento = pTipoMovimiento;
        this.cantidad = pCantidad;
        this.stockAnterior = pStockAnterior;
        this.stockNuevo = pStockNuevo;
        this.motivo = pMotivo;
        this.referenciaConsultaId = pReferenciaConsultaId;
        this.referenciaProcedimientoId = pReferenciaProcedimientoId;
        this.usuario = pUsuario;
        this.fechaMovimiento = pFechaMovimiento;
    }

    // --- Getters y Setters ---
    public int getId() { 
        return id; 
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getProductoId() {
        return productoId;
    }
    public void setProductoId(int productoId) {
        this.productoId = productoId; 
    }

    public String getTipoMovimiento() { 
        return tipoMovimiento; 
    }
    public void setTipoMovimiento(String tipoMovimiento) { 
        this.tipoMovimiento = tipoMovimiento; 
    }

    public int getCantidad() { 
        return cantidad; 
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad; 
    }

    public int getStockAnterior() { 
        return stockAnterior; 
    }
    public void setStockAnterior(int stockAnterior) {
        this.stockAnterior = stockAnterior; 
    }

    public int getStockNuevo() {
        return stockNuevo; 
    }
    public void setStockNuevo(int stockNuevo) { 
        this.stockNuevo = stockNuevo; 
    }

    public String getMotivo() { 
        return motivo; 
    }
    public void setMotivo(String motivo) { 
        this.motivo = motivo;
    }

    public Integer getReferenciaConsultaId() {
        return referenciaConsultaId; 
    }
    public void setReferenciaConsultaId(Integer referenciaConsultaId) {
        this.referenciaConsultaId = referenciaConsultaId;
    }

    public Integer getReferenciaProcedimientoId() {
        return referenciaProcedimientoId;
    }
    public void setReferenciaProcedimientoId(Integer referenciaProcedimientoId) {
        this.referenciaProcedimientoId = referenciaProcedimientoId; 
    }

    public String getUsuario() { 
        return usuario; 
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario; 
    }

    public LocalDateTime getFechaMovimiento() { 
        return fechaMovimiento;
    }
    public void setFechaMovimiento(LocalDateTime fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento; 
    }
}

    

