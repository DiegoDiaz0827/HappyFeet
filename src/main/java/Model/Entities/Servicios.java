/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entities;

import java.math.BigDecimal;

/**
 *
 * @author USUARIO
 */

//modulo 4 
public class Servicios {
    private int id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private BigDecimal precioBase;
    private int duracionEstimadaMinutos;
    private boolean activo;

    // Constructor con todos los atributos 
    public Servicios(int id, String nombre, String descripcion, String categoria,
                    BigDecimal precioBase, int duracionEstimadaMinutos, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precioBase = precioBase;
        this.duracionEstimadaMinutos = duracionEstimadaMinutos;
        this.activo = activo;
    }

    // Constructor para crear un nuevo servicio 
    public Servicios(String nombre, String descripcion, String categoria,
                    BigDecimal precioBase, int duracionEstimadaMinutos) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precioBase = precioBase;
        this.duracionEstimadaMinutos = duracionEstimadaMinutos;
        // TRUE por defecto
        this.activo = true; 
    }

    public Servicios() {
       
    }

   



    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public BigDecimal getPrecioBase() {
        return precioBase;
    }

    public int getDuracionEstimadaMinutos() {
        return duracionEstimadaMinutos;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPrecioBase(BigDecimal precioBase) {
        if (precioBase.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio base no puede ser negativo.");
        }
        this.precioBase = precioBase;
    }

    public void setDuracionEstimadaMinutos(int duracionEstimadaMinutos) {
        this.duracionEstimadaMinutos = duracionEstimadaMinutos;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", categoria='" + categoria + '\'' +
                ", precioBase=" + precioBase +
                ", duracionEstimadaMinutos=" + duracionEstimadaMinutos +
                ", activo=" + activo +
                '}';
    }
}
