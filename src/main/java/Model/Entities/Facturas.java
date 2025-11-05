/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entities;

import Model.Enums.MetodoPago;
import Model.Enums.EstadoFacturas;
import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 *
 * @author USUARIO
 */

//modulo 4
public class Facturas {
private int id;
    private int duenoId;
    private String numeroFactura;
    private LocalDateTime fechaEmision;
    private BigDecimal subtotal;
    private BigDecimal impuesto;
    private BigDecimal descuento;
    private BigDecimal total;
    private MetodoPago metodoPago;
    private EstadoFacturas estado;   
    private String observaciones;

// Constructores
    public Facturas(int pId, int pDuenoId, String pNumeroFactura, LocalDateTime pFechaEmision,
                    BigDecimal pSubtotal, BigDecimal pImpuesto, BigDecimal pDescuento,
                    BigDecimal pTotal, MetodoPago pMetodoPago, EstadoFacturas pEstado,
                    String pObservaciones) {
        id = pId;
        duenoId = pDuenoId;
        numeroFactura = pNumeroFactura;
        fechaEmision = pFechaEmision;
        subtotal = pSubtotal;
        impuesto = pImpuesto;
        descuento = pDescuento;
        total = pTotal;
        metodoPago = pMetodoPago;
        estado = pEstado;
        observaciones = pObservaciones;
    }

    public Facturas(int pDuenoId, String pNumeroFactura, BigDecimal pSubtotal,
                    BigDecimal pImpuesto, BigDecimal pDescuento, BigDecimal pTotal,
                    MetodoPago pMetodoPago, EstadoFacturas pEstado, String pObservaciones) {
        duenoId = pDuenoId;
        numeroFactura = pNumeroFactura;
        subtotal = pSubtotal;
        impuesto = pImpuesto;
        descuento = pDescuento;
        total = pTotal;
        metodoPago = pMetodoPago;
        estado = pEstado;
        observaciones = pObservaciones;
    }

    public int getId() {
        return id;
    }

    public int getDuenoId() {
        return duenoId;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getImpuesto() {
        return impuesto;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public EstadoFacturas getEstado() {
        return estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setId(int pId) {
        id = pId;
    }

    public void setDuenoId(int pDuenoId) {
        duenoId = pDuenoId;
    }

    public void setNumeroFactura(String pNumeroFactura) {
        numeroFactura = pNumeroFactura;
    }

    public void setFechaEmision(LocalDateTime pFechaEmision) {
        fechaEmision = pFechaEmision;
    }

    public void setSubtotal(BigDecimal pSubtotal) {
        subtotal = pSubtotal;
    }

    public void setImpuesto(BigDecimal pImpuesto) {
        impuesto = pImpuesto;
    }

    public void setDescuento(BigDecimal pDescuento) {
        descuento = pDescuento;
    }

    public void setTotal(BigDecimal pTotal) {
        total = pTotal;
    }

    public void setMetodoPago(MetodoPago pMetodoPago) {
        metodoPago = pMetodoPago;
    }

    public void setEstado(EstadoFacturas pEstado) {
        estado = pEstado;
    }

    public void setObservaciones(String pObservaciones) {
        observaciones = pObservaciones;
    }

    @Override
    public String toString() {
        return "Factura(id: " + id +
                ", duenoId: " + duenoId +
                ", numeroFactura: " + numeroFactura +
                ", fechaEmision: " + fechaEmision +
                ", subtotal: " + subtotal +
                ", impuesto: " + impuesto +
                ", descuento: " + descuento +
                ", total: " + total +
                ", metodoPago: " + metodoPago +
                ", estado: " + estado +
                ", observaciones: " + observaciones + ")";
    }


}
