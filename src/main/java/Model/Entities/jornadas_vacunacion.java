/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entities;

import Model.Enums.EstadoVacunacion;
import java.sql.Time;
import java.util.Date;
/**
 *
 * @author camper
 */

//modulo 5
public class jornadas_vacunacion {
   private int id;
    private String nombre;
    private Date fecha;
    private Time horaInicio; 
    private Time horaFin;    
    private String ubicacion;
    private String descripcion;
    private Integer capacidadMaxima; 
    private EstadoVacunacion estado; 

    // CONSTRUCTORES
    
    
    public jornadas_vacunacion(){};
            
    public jornadas_vacunacion(int pId, String pNombre, Date pFecha, Time pHoraInicio, 
                               Time pHoraFin, String pUbicacion, String pDescripcion, 
                               Integer pCapacidadMaxima, EstadoVacunacion pEstado) {
        this.id = pId;
        this.nombre = pNombre;
        this.fecha = pFecha;
        this.horaInicio = pHoraInicio;
        this.horaFin = pHoraFin;
        this.ubicacion = pUbicacion;
        this.descripcion = pDescripcion;
        this.capacidadMaxima = pCapacidadMaxima;
        this.estado = pEstado;
    }
    
    private jornadas_vacunacion(String pNombre, Date pFecha, Time pHoraInicio, Time pHoraFin, 
                                String pUbicacion, String pDescripcion, Integer pCapacidadMaxima, 
                                EstadoVacunacion pEstado) {
        this.nombre = pNombre;
        this.fecha = pFecha;
        this.horaInicio = pHoraInicio;
        this.horaFin = pHoraFin;
        this.ubicacion = pUbicacion;
        this.descripcion = pDescripcion;
        this.capacidadMaxima = pCapacidadMaxima;
        this.estado = pEstado;
    }


    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public Date getFecha() { return fecha; }
    public Time getHoraInicio() { return horaInicio; }
    public Time getHoraFin() { return horaFin; }
    public String getUbicacion() { return ubicacion; }
    public String getDescripcion() { return descripcion; }
    public Integer getCapacidadMaxima() { return capacidadMaxima; }
    public EstadoVacunacion getEstado() { return estado; } 


    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public void setHoraInicio(Time horaInicio) { this.horaInicio = horaInicio; }
    public void setHoraFin(Time horaFin) { this.horaFin = horaFin; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setCapacidadMaxima(Integer capacidadMaxima) { this.capacidadMaxima = capacidadMaxima; }
    public void setEstado(EstadoVacunacion estado) { this.estado = estado; } 

    @Override
    public String toString() {
        return "jornadas_vacunacion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fecha=" + fecha +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                ", ubicacion='" + ubicacion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", capacidadMaxima=" + capacidadMaxima +
                ", estado=" + estado +
                '}';
    }
}