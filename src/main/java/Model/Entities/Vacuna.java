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
public class Vacuna {
    private int id;
    private String nombre;
    private String tipo; // Ej: "Antirrábica", "Parvovirus"
    private String fabricante;
    private String lote;
    private int cantidad;
    private LocalDate fechaVencimiento;
    private double precioCompra;
    private double precioVenta;
    private boolean activa;

    public Vacuna() {}

    public Vacuna(int id, String nombre, String tipo, String fabricante, String lote,
                  int cantidad, LocalDate fechaVencimiento, double precioCompra,
                  double precioVenta, boolean activa) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.fabricante = fabricante;
        this.lote = lote;
        this.cantidad = cantidad;
        this.fechaVencimiento = fechaVencimiento;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.activa = activa;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getFabricante() { return fabricante; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public double getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(double precioCompra) { this.precioCompra = precioCompra; }

    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }

    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }

    @Override
    public String toString() {
        return nombre + " - Lote " + lote + " (" + cantidad + " uds.)";
    }
    
}
