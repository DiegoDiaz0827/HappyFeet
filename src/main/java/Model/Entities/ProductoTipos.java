/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entities;

/**
 *
 * @author camper
 */

// tablas consulta
public class ProductoTipos {
   private int id;
    private String nombre;
    private String descripcion;

 
    public ProductoTipos(int pId, String pNombre, String pDescripcion) {
        id = pId;
        nombre = pNombre;
        descripcion = pDescripcion;
    }

    
    public ProductoTipos(String pNombre, String pDescripcion) {
        nombre = pNombre;
        descripcion = pDescripcion;
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

  
    public void setId(int pId) {
        id = pId;
    }

    public void setNombre(String pNombre) {
        nombre = pNombre;
    }

    public void setDescripcion(String pDescripcion) {
        descripcion = pDescripcion;
    }

    @Override
    public String toString() {
        return "ProductoTipo(id: " + id +
               ", nombre: " + nombre +
               ", descripcion: " + descripcion + ")";
    }
}
 

