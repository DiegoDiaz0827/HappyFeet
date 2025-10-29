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
public class EventosTipos {
    
    private int id;
    private String nombre;
    private String descripcion;
    
    public EventosTipos(int pid, String pNombre, String pdescripcion ){
    
        id =pid;
        nombre = pNombre;
        descripcion = pdescripcion;
        
    }
    
    public EventosTipos( String pNombre, String pdescripcion ){
    
        
        nombre = pNombre;
        descripcion = pdescripcion;
        
    }
    
    public String getNombre(){
    
    return nombre;
    }
    
     public int getID(){
    
    return id;
    }
     
    public String getdescripcion(){
    
    return descripcion;
    
    }
    
     public void setNombre(String pNombre){
    
    nombre = pNombre;
    }
   
     public void setid(int pid){
    
    id = pid;
    }
     
      public void setdescripciom(String pdescripcion){
    
    descripcion = pdescripcion;
    }
      
      @Override
      
      public String toString(){
      return "EventosTipos(id: " + id +
               ", nombre: " + nombre +
               ", descripcion: " + descripcion + ")";
      
          
      }
    
     
    
    
}
