/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entities;

/**
 *
 * @author camper
 */
public class MaterialMedico {
     private int id;
    private String nombre;
    private String tipo; // Ej: "Descartable", "Esterilizable"
    private String unidadMedida; // Ej: "Unidades", "Cajas"
    private int cantidadStock;
    private int stockMinimo;
    private String proveedor;
    private boolean activo;

    public MaterialMedico() {}

    public MaterialMedico(int id, String nombre, String tipo, String unidadMedida,
                          int cantidadStock, int stockMinimo, String proveedor, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.unidadMedida = unidadMedida;
        this.cantidadStock = cantidadStock;
        this.stockMinimo = stockMinimo;
        this.proveedor = proveedor;
        this.activo = activo;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id; 
    }

    public String getNombre() {
        return nombre; 
    }
    public void setNombre(String nombre) {
        this.nombre = nombre; 
    }

    public String getTipo() {
        return tipo; 
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUnidadMedida() { 
        return unidadMedida; 
    }
    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
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

    public String getProveedor() {
        return proveedor; 
    }
    public void setProveedor(String proveedor) { 
        this.proveedor = proveedor; 
    }

    public boolean isActivo() {
        return activo; 
    }
    public void setActivo(boolean activo) { 
        this.activo = activo;
    }

    @Override
    public String toString() {
        return nombre + " (" + cantidadStock + " " + unidadMedida + ")";
    }
    
}
