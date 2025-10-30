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

    // 1️⃣ Registrar una nueva cita
    public boolean registrarCita(Citas cita) {
        if (cita.getMascotaId() <= 0) {
            System.out.println("⚠️ ID de mascota inválido.");
            return false;
        }
        
            Mascotas mascota = mascotasDAO.obtenerPorId(cita.getMascotaId());
        if (mascota == null) {
            System.out.println("⚠️ No existe una mascota con ese ID.");
            return false;
        }
        
        
           Veterinarios veterinario = veterinariosDAO.obtenerPorId(cita.getVeterinarioId());
        if (veterinario == null) {
            System.out.println("⚠️ No existe un veterinario con ese ID.");
            return false;
        }
        
        
        if (cita.getFechaHora().isBefore(LocalDateTime.now())) {
            System.out.println("⚠️ La fecha de la cita no puede ser anterior a la actual.");
            return false;
        }
        if (cita.getMotivo() == null || cita.getMotivo().isBlank()) {
            System.out.println("⚠️ El motivo de la cita es obligatorio.");
            return false;
        }

        try {
            citasDAO.agregar(cita);
            System.out.println("✅ Cita registrada correctamente.");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Error al registrar cita: " + e.getMessage());
            return false;
        }
    }

    // 2️⃣ Listar todas las citas
    public List<Citas> listarCitas() {
        return citasDAO.listar();
    }

    // 3️⃣ Buscar cita por ID
    public Citas obtenerCitaPorId(int id) {
        if (id <= 0) {
            System.out.println("⚠️ ID inválido.");
            return null;
        }
        
        Citas cita = citasDAO.obtenerPorId(id);

    if (cita == null) {
        System.out.println("⚠️ No existe una cita con el ID especificado.");
        return null;
    }
        return cita;
    }

    // 4️⃣ Actualizar cita existente
    public boolean actualizarCita(Citas cita) {
        if (cita.getId() <= 0) {
            System.out.println("⚠️ La cita debe tener un ID válido.");
            return false;
        }
        
        Veterinarios veterinario = veterinariosDAO.obtenerPorId(cita.getVeterinarioId());
        if (veterinario == null) {
            System.out.println("⚠️ No existe un veterinario con ese ID.");
            return false;
        }
        
        
         Mascotas mascota = mascotasDAO.obtenerPorId(cita.getMascotaId());
        if (mascota == null) {
            System.out.println("⚠️ No existe una mascota con ese ID.");
            return false;
        }
        
        if (cita.getFechaHora().isBefore(LocalDateTime.now())) {
            System.out.println("⚠️ La nueva fecha no puede ser pasada.");
            return false;
        }

        boolean exito = citasDAO.actualizar(cita);
        System.out.println(exito ? "✅ Cita actualizada correctamente." : "❌ No se pudo actualizar la cita.");
        return exito;
    }

    // 5️⃣ Eliminar cita
    public boolean eliminarCita(int id) {
        if (id <= 0) {
            System.out.println("⚠️ ID inválido.");
            return false;
        }
        
         Citas cita = citasDAO.obtenerPorId(id);

    if (cita == null) {
        System.out.println("⚠️ No existe una cita con el ID especificado.");
        return false;
    }
        

        boolean exito = citasDAO.eliminar(id);
        System.out.println(exito ? "🗑️ Cita eliminada correctamente." : "❌ No se encontró la cita para eliminar.");
        return exito;
    }
    
}
