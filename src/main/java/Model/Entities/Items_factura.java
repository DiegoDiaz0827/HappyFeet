/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entities;

import Model.Enums.itemsFactura;
import java.math.BigDecimal;

/**
 *
 * @author camper
 */

//modulo 4
public class Items_factura { 
    
   private int id;
    private int facturaId;
    private itemsFactura tipoItem; 
    private Integer productoId; 
    private Integer servicioId; 
    private String servicioDescripcion;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    // CONSTRUCTOR 1:
    public Items_factura(int pId, int pFacturaId, itemsFactura pTipoItem, Integer pProductoId, 
                         Integer pServicioId, String pServicioDescripcion, int pCantidad, 
                         BigDecimal pPrecioUnitario, BigDecimal pSubtotal) {
        id = pId;
        facturaId = pFacturaId;
        tipoItem = pTipoItem; 
        productoId = pProductoId;
        servicioId = pServicioId;
        servicioDescripcion = pServicioDescripcion;
        cantidad = pCantidad;
        precioUnitario = pPrecioUnitario;
        subtotal = pSubtotal;
    }
    
    
    
    //CONSTRUCTOR 2
   public Items_factura(int pFacturaId, Integer pProductoId, int pCantidad, 
                     BigDecimal pPrecioUnitario, BigDecimal pSubtotal) {

    this(pFacturaId, itemsFactura.PRODUCTO, pProductoId, 
         null, 
         null, 
         pCantidad, 
         pPrecioUnitario, 
         pSubtotal); 
}

    // CONSTRUCTOR 3: 
    public Items_factura(int pFacturaId, Integer pServicioId, String pServicioDescripcion, int pCantidad, 
                         BigDecimal pPrecioUnitario, BigDecimal pSubtotal) {
        this(pFacturaId, itemsFactura.SERVICIO, null, pServicioId, pServicioDescripcion, pCantidad, pPrecioUnitario, pSubtotal);
    }
    
    // CONSTRUCTOR BASE PRIVADO
    private Items_factura(int pFacturaId, itemsFactura pTipoItemFactura, Integer pProductoId, 
                          Integer pServicioId, String pServicioDescripcion, int pCantidad, 
                          BigDecimal pPrecioUnitario, BigDecimal pSubtotal) {
 
        facturaId = pFacturaId;
        tipoItem = pTipoItemFactura; 
        productoId = pProductoId;
        servicioId = pServicioId;
        servicioDescripcion = pServicioDescripcion;
        cantidad = pCantidad;
        precioUnitario = pPrecioUnitario;
        subtotal = pSubtotal;
    }
    
    public int getId() {
        return id;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public itemsFactura getTipoItem() { 
        return tipoItem; 
    }

    public Integer getProductoId() {
        return productoId;
    }

    public Integer getServicioId() {
        return servicioId;
    }

    public String getServicioDescripcion() {
        return servicioDescripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setId(int pId) {
        id = pId;
    }

    public void setFacturaId(int pFacturaId) {
        facturaId = pFacturaId;
    }

    public void setTipoItem(itemsFactura pTipoItem) { 
        tipoItem = pTipoItem; 
    }

    public void setProductoId(Integer pProductoId) {
        productoId = pProductoId;
    }

    public void setServicioId(Integer pServicioId) {
        servicioId = pServicioId;
    }

    public void setServicioDescripcion(String pServicioDescripcion) {
        servicioDescripcion = pServicioDescripcion;
    }

    public void setCantidad(int pCantidad) {
        cantidad = pCantidad;
    }

    public void setPrecioUnitario(BigDecimal pPrecioUnitario) {
        precioUnitario = pPrecioUnitario;
    }

    public void setSubtotal(BigDecimal pSubtotal) {
        subtotal = pSubtotal;
    }

    @Override
    public String toString() {
        return "Items_factura(id: " + id +
                ", facturaId: " + facturaId +
                ", tipoItem: " + tipoItem +
                ", productoId: " + (productoId != null ? productoId : "N/A") +
                ", servicioId: " + (servicioId != null ? servicioId : "N/A") +
                ", descripcion: " + (servicioDescripcion != null ? servicioDescripcion : "N/A") +
                ", cantidad: " + cantidad +
                ", precioUnitario: " + precioUnitario +
                ", subtotal: " + subtotal + ")";
    }
}