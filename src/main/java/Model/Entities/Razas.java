/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entities;

/**
 *
 * @author camper
 */
public class Razas {
    
        private int id;
    private int especieId;
    private String nombre;
    private String caracteristicas;

    
    public Razas(int pId, int pEspecieId, String pNombre, String pCaracteristicas) {
        id = pId;
        especieId = pEspecieId;
        nombre = pNombre;
        caracteristicas = pCaracteristicas;
    }

    
    public Razas(int pEspecieId, String pNombre, String pCaracteristicas) {
        especieId = pEspecieId;
        nombre = pNombre;
        caracteristicas = pCaracteristicas;
    }


    public int getId() {
        return id;
    }

    public int getEspecieId() {
        return especieId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

   
    public void setId(int pId) {
        id = pId;
    }

    public void setEspecieId(int pEspecieId) {
        especieId = pEspecieId;
    }

    public void setNombre(String pNombre) {
        nombre = pNombre;
    }

    public void setCaracteristicas(String pCaracteristicas) {
        caracteristicas = pCaracteristicas;
    }

    @Override
    public String toString() {
        return "Raza(id: " + id +
               ", especieId: " + especieId +
               ", nombre: " + nombre +
               ", caracteristicas: " + caracteristicas + ")";
    }
}

