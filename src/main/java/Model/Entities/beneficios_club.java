/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entities;

import Model.Enums.TipoBeneficio;
import java.math.BigDecimal;
/**
 *
 * @author camper
 */
public class beneficios_club {
    private int id;
    private String nombre;
    private String descripcion;
    private String nivelRequerido;
    private int puntosNecesarios;
    private TipoBeneficio tipoBeneficio; 
    private BigDecimal valorBeneficio;   
    private boolean activo;              

    // CONSTRUCTOR COMPLETO (Con ID)
    public beneficios_club(int pId, String pNombre, String pDescripcion, String pNivelRequerido, 
                           int pPuntosNecesarios, TipoBeneficio pTipoBeneficio, 
                           BigDecimal pValorBeneficio, boolean pActivo) {
        this.id = pId;
        this.nombre = pNombre;
        this.descripcion = pDescripcion;
        this.nivelRequerido = pNivelRequerido;
        this.puntosNecesarios = pPuntosNecesarios;
        this.tipoBeneficio = pTipoBeneficio;
        this.valorBeneficio = pValorBeneficio;
        this.activo = pActivo;
    }
    
    // CONSTRUCTOR BASE PRIVADO (Inicializa todos los campos sin ID - Para inserción/llamadas internas)
    private beneficios_club(String pNombre, String pDescripcion, String pNivelRequerido, 
                            int pPuntosNecesarios, TipoBeneficio pTipoBeneficio, 
                            BigDecimal pValorBeneficio, boolean pActivo) {
        this.nombre = pNombre;
        this.descripcion = pDescripcion;
        this.nivelRequerido = pNivelRequerido;
        this.puntosNecesarios = pPuntosNecesarios;
        this.tipoBeneficio = pTipoBeneficio;
        this.valorBeneficio = pValorBeneficio;
        this.activo = pActivo;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getNivelRequerido() { return nivelRequerido; }
    public int getPuntosNecesarios() { return puntosNecesarios; }
    public TipoBeneficio getTipoBeneficio() { return tipoBeneficio; }
    public BigDecimal getValorBeneficio() { return valorBeneficio; }
    public boolean isActivo() { return activo; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setNivelRequerido(String nivelRequerido) { this.nivelRequerido = nivelRequerido; }
    public void setPuntosNecesarios(int puntosNecesarios) { this.puntosNecesarios = puntosNecesarios; }
    public void setTipoBeneficio(TipoBeneficio tipoBeneficio) { this.tipoBeneficio = tipoBeneficio; }
    public void setValorBeneficio(BigDecimal valorBeneficio) { this.valorBeneficio = valorBeneficio; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return "beneficios_club{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", nivelRequerido='" + nivelRequerido + '\'' +
                ", puntosNecesarios=" + puntosNecesarios +
                ", tipoBeneficio=" + tipoBeneficio +
                ", valorBeneficio=" + valorBeneficio +
                ", activo=" + activo +
                '}';
    }

}
