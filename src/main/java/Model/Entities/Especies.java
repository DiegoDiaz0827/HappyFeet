/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entities;

/**
 *
 * @author camper
 */
public class Especies {
    
    private int id ;
    private String Nombre;
    private String Descripcion;
    
    public Especies (int pid, String pNombre,String pDescripcion){
    
    id = pid;
    Nombre = pNombre;
    Descripcion = pDescripcion;
    }
    
     public Especies ( String pNombre,String pDescripcion){
    
 
    Nombre = pNombre;
    Descripcion = pDescripcion;
    }
     
    
    public int getID(){
    
    return id;
    }
    
      public String getNombre(){
    
    return Nombre;
    }
     
        public String getDescripcion(){
    
    return Descripcion;
    }
    
    public void  setDescripcion(String pDescription){
    
    Descripcion = pDescription;

    }
     
       public void  setNombre(String pNombre){
    
    Nombre = pNombre;

    }
       
     public void  setID(int pID){
    
    id = pID;

    }  
     
    @Override
    
    public String toString(){
    return "Especie(id:"+id+" Nombre: " + Nombre +" Descripcion: "+Descripcion+")";
        
    
    }
    
    
        
     
    
    
    
    
}
