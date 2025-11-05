/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;

import DAO.CitasDAO;
import DAO.MascotasDAO;
import DAO.VeterinariosDAO;
import Model.Entities.Citas;
import Model.Entities.Mascotas;
import Model.Entities.Veterinarios;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author camper
 */
public class CitasController {
    private MascotasDAO mascotasDAO = new MascotasDAO();
    private CitasDAO citasDAO;
    private VeterinariosDAO veterinariosDAO = new VeterinariosDAO();

    public CitasController(CitasDAO citasDAO) {
        this.citasDAO = citasDAO;
    }

    //registrar nueva cita
    public boolean registrarCita(Citas cita) throws IllegalArgumentException {
        if (cita.getMascotaId() <= 0) {
            throw new IllegalArgumentException(" ID de mascota inválido.");
            
        }
        
            Mascotas mascota = mascotasDAO.obtenerPorId(cita.getMascotaId());
        if (mascota == null) {
             throw new IllegalArgumentException("️ No existe una mascota con ese ID.");
            
        }
        
        
           Veterinarios veterinario = veterinariosDAO.obtenerPorId(cita.getVeterinarioId());
        if (veterinario == null) {
            throw new IllegalArgumentException("️ No existe un veterinario con ese ID.");
            
        }
        
        
        if (cita.getFechaHora().isBefore(LocalDateTime.now())) {
             throw new IllegalArgumentException("️ La fecha de la cita no puede ser anterior a la actual.");
           
        }
        if (cita.getMotivo() == null || cita.getMotivo().isBlank()) {
             throw new IllegalArgumentException("️ El motivo de la cita es obligatorio.");
            
        }
        
        
       citasDAO.agregar(cita);
    

    return true; 
    }

    // listar citas
    public List<Citas> listarCitas() {
        return citasDAO.listar();
    }

    // buscar cita por id
    public Citas obtenerCitaPorId(int id) throws IllegalArgumentException {
        if (id <= 0) {
            System.out.println("⚠️ ID inválido.");
            return null;
        }
        
        Citas cita = citasDAO.obtenerPorId(id);

    if (cita == null) {
        throw new IllegalArgumentException("️ No existe una cita con el ID especificado.");
        
    }
        return cita;
    }

    //  actualizar cita
    public boolean actualizarCita(Citas cita) throws IllegalArgumentException {
        if (cita.getId() <= 0 ) {
            System.out.println("️ La cita debe tener un ID válido.");
            return false;
        }
        
        
        
        Veterinarios veterinario = veterinariosDAO.obtenerPorId(cita.getVeterinarioId());
        if (veterinario == null) {
            throw new IllegalArgumentException("️ No existe un veterinario con ese ID.");
            
        }
        
        
         Mascotas mascota = mascotasDAO.obtenerPorId(cita.getMascotaId());
        if (mascota == null) {
            throw new IllegalArgumentException("️ No existe una mascota con ese ID.");
            
        }
        
        if (cita.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("️ La nueva fecha no puede ser pasada.");
           
        }

        boolean exito = citasDAO.actualizar(cita);
        System.out.println(exito ? " Cita actualizada correctamente." : "No se pudo actualizar la cita.");
        return exito;
    }

    // eliminar cita
    public boolean eliminarCita(int id) {
        if (id <= 0) {
            System.out.println("️ ID inválido.");
            return false;
        }
        
         Citas cita = citasDAO.obtenerPorId(id);

    if (cita == null) {
        System.out.println(" No existe una cita con el ID especificado.");
        return false;
    }
        

        boolean exito = citasDAO.eliminar(id);
        System.out.println(exito ? "️ Cita eliminada correctamente." : " No se encontró la cita para eliminar.");
        return exito;
    }
    
}
