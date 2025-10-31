/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entities;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 *
 * @author camper
 */
public class Inventario {

    private int id;
    private String nombreProducto;
    private int productoTipoId;
    private String descripcion;
    private String fabricante;
    private Integer proveedorId;
    private String lote;
    private int cantidadStock;
    private int stockMinimo;
    private String unidadMedida;
    private LocalDate fechaVencimiento;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;
    private boolean requiereReceta;
    private boolean activo;
    private LocalDateTime fechaRegistro;

    // --- Constructores ---
    
    public Inventario(){}
    public Inventario(int pId, String pNombreProducto, int pProductoTipoId, String pDescripcion,
                      String pFabricante, Integer pProveedorId, String pLote, int pCantidadStock,
                      int pStockMinimo, String pUnidadMedida, LocalDate pFechaVencimiento,
                      BigDecimal pPrecioCompra, BigDecimal pPrecioVenta, boolean pRequiereReceta,
                      boolean pActivo, LocalDateTime pFechaRegistro) {
        this.id = pId;
        this.nombreProducto = pNombreProducto;
        this.productoTipoId = pProductoTipoId;
        this.descripcion = pDescripcion;
        this.fabricante = pFabricante;
        this.proveedorId = pProveedorId;
        this.lote = pLote;
        this.cantidadStock = pCantidadStock;
        this.stockMinimo = pStockMinimo;
        this.unidadMedida = pUnidadMedida;
        this.fechaVencimiento = pFechaVencimiento;
        this.precioCompra = pPrecioCompra;
        this.precioVenta = pPrecioVenta;
        this.requiereReceta = pRequiereReceta;
        this.activo = pActivo;
        this.fechaRegistro = pFechaRegistro;
    }

    // --- Getters y Setters ---
    public int getId() { 
        return id; 
    }
    public void setId(int id) { 
        this.id = id; 
    }

    public String getNombreProducto() {
        return nombreProducto; 
    }
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto; 
    }

    public int getProductoTipoId() { 
        return productoTipoId;
    }
    public void setProductoTipoId(int productoTipoId) {
        this.productoTipoId = productoTipoId; 
    }

    public String getDescripcion() { 
        return descripcion; 
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFabricante() { 
        return fabricante; 
    }
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante; 
    }

    public Integer getProveedorId() { 
        return proveedorId;
    }
    public void setProveedorId(Integer proveedorId) {
        this.proveedorId = proveedorId; 
    }

    public String getLote() { return lote; }
    public void setLote(String lote) {
        this.lote = lote; 
    }

    public int getCantidadStock() { 
        return cantidadStock; 
    }
    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public int getStockMinimo() {
        return stockMinimo; 
    }
    public void setStockMinimo(int stockMinimo) { 
        this.stockMinimo = stockMinimo;
    }

    public String getUnidadMedida() { 
        return unidadMedida; 
    }
    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public LocalDate getFechaVencimiento() { 
        return fechaVencimiento;
    }
    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento; 
    }

    public BigDecimal getPrecioCompra() { 
        return precioCompra; 
    }
    public void setPrecioCompra(BigDecimal precioCompra) { 
        this.precioCompra = precioCompra;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }
    public void setPrecioVenta(BigDecimal precioVenta) { 
        this.precioVenta = precioVenta;
    }

    public boolean isRequiereReceta() { 
        return requiereReceta; 
    }
    public void setRequiereReceta(boolean requiereReceta) {
        this.requiereReceta = requiereReceta; 
    }

    public boolean isActivo() { 
        return activo; 
    }
    public void setActivo(boolean activo) { 
        this.activo = activo; 
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro; 
    }
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}

    

