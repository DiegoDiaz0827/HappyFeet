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
public class AlertaInventario {
     private int id;
    private String tipo; // "STOCK_BAJO" o "VENCIMIENTO"
    private String producto;
    private String categoria; // Medicamento, Vacuna, Material Médico
    private int cantidadActual;
    private int stockMinimo;
    private LocalDate fechaVencimiento;
    private boolean atendida;

    public AlertaInventario() {}

    public AlertaInventario(int id, String tipo, String producto, String categoria,
                            int cantidadActual, int stockMinimo, LocalDate fechaVencimiento, boolean atendida) {
        this.id = id;
        this.tipo = tipo;
        this.producto = producto;
        this.categoria = categoria;
        this.cantidadActual = cantidadActual;
        this.stockMinimo = stockMinimo;
        this.fechaVencimiento = fechaVencimiento;
        this.atendida = atendida;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public int getCantidadActual() { return cantidadActual; }
    public void setCantidadActual(int cantidadActual) { this.cantidadActual = cantidadActual; }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public boolean isAtendida() { return atendida; }
    public void setAtendida(boolean atendida) { this.atendida = atendida; }

    @Override
    public String toString() {
        return "[" + tipo + "] " + producto + " - " + categoria;
    }
    
}
