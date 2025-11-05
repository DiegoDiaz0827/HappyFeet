/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;

import DAO.CitasDAO;
import DAO.ConsultasMedicasDAO;
import DAO.MascotasDAO;
import DAO.VeterinariosDAO;
import Model.Entities.ConsultasMedicas;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author USUARIO
 */
public class ConsultacitasController {
    
    
    private ConsultasMedicasDAO consultasDAO;
    private MascotasDAO mascotasDAO;
    private VeterinariosDAO veterinariosDAO;
    private CitasDAO citasDAO;

    public ConsultacitasController(ConsultasMedicasDAO consultasDAO, MascotasDAO mascotasDAO, VeterinariosDAO veterinariosDAO, CitasDAO citasDAO) {
        this.consultasDAO = consultasDAO;
        this.mascotasDAO = mascotasDAO;
        this.veterinariosDAO = veterinariosDAO;
        this.citasDAO = citasDAO;
    }

    // ? Registrar consulta con validación de FKs
    public boolean registrarConsulta(ConsultasMedicas c) throws IllegalArgumentException {
        if (c.getMascotaId() <= 0 || mascotasDAO.obtenerPorId(c.getMascotaId()) == null) {
            throw new IllegalArgumentException("Error: Mascota no encontrada.");
            
        }
        if (c.getVeterinarioId() <= 0 || veterinariosDAO.obtenerPorId(c.getVeterinarioId()) == null) {
             throw new IllegalArgumentException("Error: Veterinario no encontrado.");
           
        }
        if (c.getCitaId() != null && citasDAO.obtenerPorId(c.getCitaId()) == null) {
            throw new IllegalArgumentException("Error: Cita no encontrada.");
            
        }
        
         if (c.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("️ La nueva fecha no puede ser pasada.");
           
        }
       
        if (c.getMotivo() == null || c.getMotivo().isEmpty()) {
            throw new IllegalArgumentException("Error: El motivo de la consulta es obligatorio.");
            
        }
        if (c.getSintomas() == null || c.getSintomas().isEmpty()) {
            throw new IllegalArgumentException("Error: Los síntomas son obligatorios.");
            
        }

        consultasDAO.insertar(c);
        return true;
    }

    // actualizar consulta
    public boolean actualizarConsulta(ConsultasMedicas c) throws IllegalArgumentException {
        if (c.getId() <= 0 || consultasDAO.obtenerPorId(c.getId()) == null) {
           throw new IllegalArgumentException("Error: Consulta no encontrada.");
            
        }
        if (c.getMascotaId() <= 0 || mascotasDAO.obtenerPorId(c.getMascotaId()) == null) {
            throw new IllegalArgumentException("Error: Mascota no encontrada.");
            
        }
        if (c.getVeterinarioId() <= 0 || veterinariosDAO.obtenerPorId(c.getVeterinarioId()) == null) {
            throw new IllegalArgumentException("Error: Veterinario no encontrado.");
           
        }
        if (c.getCitaId() != null && citasDAO.obtenerPorId(c.getCitaId()) == null) {
            throw new IllegalArgumentException("Error: Cita no encontrada.");
           
        }
        if (c.getFechaHora() == null) {
            throw new IllegalArgumentException("Error: La fecha y hora son obligatorias.");
           
        }
        
        if (c.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("️ La nueva fecha no puede ser pasada.");
           
        }

        return consultasDAO.actualizar(c);
    }

    // eliminar consulta
    public boolean eliminarConsulta(int id) {
        if (id <= 0 || consultasDAO.obtenerPorId(id) == null) {
            System.out.println("Error: Consulta no encontrada.");
            return false;
        }
        return consultasDAO.eliminar(id);
    }

    //  Ver consulta por ID
    public ConsultasMedicas verConsulta(int id) throws IllegalArgumentException {
        if (id <= 0) {
            System.out.println("Error: ID inválido.");
            return null;
        }
        
        ConsultasMedicas ce = consultasDAO.obtenerPorId(id);
        if(ce == null){
        throw new IllegalArgumentException("no existe consulta por ese id");
        }
        
        return consultasDAO.obtenerPorId(id);
    }

    //  Listar todas las consultas
    public List<ConsultasMedicas> listarConsultas() {
        return consultasDAO.listarTodos();
    }
}
