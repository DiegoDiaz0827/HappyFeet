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
public class citaEstados {
    
    private int id;
    private String nombre;
    private String descripcion;

    // Constructor con todos los atributos
    public citaEstados(int pId, String pNombre, String pDescripcion) {
        id = pId;
        nombre = pNombre;
        descripcion = pDescripcion;
    }


    public citaEstados(String pNombre, String pDescripcion) {
        nombre = pNombre;
        descripcion = pDescripcion;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // Setters
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
        return "CitaEstado(id: " + id +
               ", nombre: " + nombre +
               ", descripcion: " + descripcion + ")";
    }
    
}
