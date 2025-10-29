/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entities;

import java.time.LocalDate;

/**
 *
 * @author camper
 */
public class Medicamento {
    private int id;
    private String nombre;
    private String tipo; // Ejemplo: "Antibiótico", "Analgésico", "Desparasitante"
    private String fabricante;
    private int cantidadStock;
    private int stockMinimo;
    private String unidadMedida; // Ej: "Tabletas", "ml", "g"
    private String lote;
    private LocalDate fechaVencimiento;
    private double precioCompra;
    private double precioVenta;
    private boolean requiereReceta;
    private boolean activo;

    public Medicamento() {}

    public Medicamento(int id, String nombre, String tipo, String fabricante, int cantidadStock, int stockMinimo,
                       String unidadMedida, String lote, LocalDate fechaVencimiento, double precioCompra,
                       double precioVenta, boolean requiereReceta, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.fabricante = fabricante;
        this.cantidadStock = cantidadStock;
        this.stockMinimo = stockMinimo;
        this.unidadMedida = unidadMedida;
        this.lote = lote;
        this.fechaVencimiento = fechaVencimiento;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.requiereReceta = requiereReceta;
        this.activo = activo;
    }

    // ===== Getters & Setters =====
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getFabricante() { return fabricante; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }

    public int getCantidadStock() { return cantidadStock; }
    public void setCantidadStock(int cantidadStock) { this.cantidadStock = cantidadStock; }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }

    public String getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(String unidadMedida) { this.unidadMedida = unidadMedida; }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public double getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(double precioCompra) { this.precioCompra = precioCompra; }

    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }

    public boolean isRequiereReceta() { return requiereReceta; }
    public void setRequiereReceta(boolean requiereReceta) { this.requiereReceta = requiereReceta; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return nombre + " (" + cantidadStock + " " + unidadMedida + ")";
    }
}

    

