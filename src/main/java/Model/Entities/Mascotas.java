/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entities;

import Model.Enums.Sexo;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author camper
 */

// modulo 1
public class Mascotas {
      private int id;
    private int duenoId;
    private String nombre;
    private String NombreRaza;
    private int razaId;
    private LocalDate fechaNacimiento;
    private Sexo sexo;
    private double pesoActual;
    private String microchip;
    private String tatuaje;
    private String urlFoto;
    private String alergias;
    private String condicionesPreexistentes;
    private LocalDateTime fechaRegistro;
    private boolean activo;

    // Constructor con todos los atributos
    public Mascotas(int pId, int pDuenoId, String pNombre,String pNombreraza, int pRazaId,
                    LocalDate pFechaNacimiento, Sexo pSexo, double pPesoActual,
                    String pMicrochip, String pTatuaje, String pUrlFoto,
                    String pAlergias, String pCondicionesPreexistentes,
                    LocalDateTime pFechaRegistro, boolean pActivo) {

        id = pId;
        duenoId = pDuenoId;
        nombre = pNombre;
        NombreRaza = pNombreraza;
        razaId = pRazaId;
        fechaNacimiento = pFechaNacimiento;
        sexo = pSexo;
        pesoActual = pPesoActual;
        microchip = pMicrochip;
        tatuaje = pTatuaje;
        urlFoto = pUrlFoto;
        alergias = pAlergias;
        condicionesPreexistentes = pCondicionesPreexistentes;
        fechaRegistro = pFechaRegistro;
        activo = pActivo;
    }

    // Constructor sin id ni fecha de registro (al crear una nueva mascota)
    public Mascotas(int pDuenoId, String pNombre, int pRazaId,
                    LocalDate pFechaNacimiento, Sexo pSexo, double pPesoActual,
                    String pMicrochip, String pTatuaje, String pUrlFoto,
                    String pAlergias, String pCondicionesPreexistentes,
                    boolean pActivo) {

        duenoId = pDuenoId;
        nombre = pNombre;
        razaId = pRazaId;
        fechaNacimiento = pFechaNacimiento;
        sexo = pSexo;
        pesoActual = pPesoActual;
        microchip = pMicrochip;
        tatuaje = pTatuaje;
        urlFoto = pUrlFoto;
        alergias = pAlergias;
        condicionesPreexistentes = pCondicionesPreexistentes;
        activo = pActivo;
    }

    public Mascotas() {
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getDuenoId() {
        return duenoId;
    }

    public String getNombre() {
        return nombre;
    }
    
    public String getNombreraza(){
    return NombreRaza;
    }

    public int getRazaId() {
        return razaId;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public double getPesoActual() {
        return pesoActual;
    }

    public String getMicrochip() {
        return microchip;
    }

    public String getTatuaje() {
        return tatuaje;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public String getAlergias() {
        return alergias;
    }

    public String getCondicionesPreexistentes() {
        return condicionesPreexistentes;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public boolean isActivo() {
        return activo;
    }

    // Setters
    public void setId(int pId) {
        id = pId;
    }

    public void setDuenoId(int pDuenoId) {
        duenoId = pDuenoId;
    }

    public void setNombre(String pNombre) {
        nombre = pNombre;
    }
    
    public void setNombreRaza(String pNombreraza) {
        NombreRaza = pNombreraza;
    }
    

    public void setRazaId(int pRazaId) {
        razaId = pRazaId;
    }

    public void setFechaNacimiento(LocalDate pFechaNacimiento) {
        fechaNacimiento = pFechaNacimiento;
    }

    public void setSexo(Sexo pSexo) {
        sexo = pSexo;
    }

    public void setPesoActual(double pPesoActual) {
        pesoActual = pPesoActual;
    }

    public void setMicrochip(String pMicrochip) {
        microchip = pMicrochip;
    }

    public void setTatuaje(String pTatuaje) {
        tatuaje = pTatuaje;
    }

    public void setUrlFoto(String pUrlFoto) {
        urlFoto = pUrlFoto;
    }

    public void setAlergias(String pAlergias) {
        alergias = pAlergias;
    }

    public void setCondicionesPreexistentes(String pCondicionesPreexistentes) {
        condicionesPreexistentes = pCondicionesPreexistentes;
    }

    public void setFechaRegistro(LocalDateTime pFechaRegistro) {
        fechaRegistro = pFechaRegistro;
    }

    public void setActivo(boolean pActivo) {
        activo = pActivo;
    }

    @Override
    public String toString() {
        return "Mascota(id: " + id +
               ", duenoId: " + duenoId +
               ", nombre: " + nombre +
               ", razaId: " + razaId +
               ", fechaNacimiento: " + fechaNacimiento +
               ", sexo: " + sexo +
               ", pesoActual: " + pesoActual +
               ", microchip: " + microchip +
               ", tatuaje: " + tatuaje +
               ", urlFoto: " + urlFoto +
               ", alergias: " + alergias +
               ", condicionesPreexistentes: " + condicionesPreexistentes +
               ", fechaRegistro: " + fechaRegistro +
               ", activo: " + activo + ")";
    }
}
