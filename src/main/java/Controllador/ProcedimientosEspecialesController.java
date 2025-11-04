/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;

import DAO.MascotasDAO;
import DAO.Procedimientos_especialesDAO;
import DAO.VeterinariosDAO;
import Model.Entities.Mascotas;
import Model.Entities.ProcedimientosEspeciales;
import Model.Entities.Veterinarios;
import Model.Enums.EstadoProcedimientos;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author USUARIO
 */
public class ProcedimientosEspecialesController {
    private final Procedimientos_especialesDAO dao;
    private final MascotasDAO mascotasdao = new MascotasDAO();
    private final VeterinariosDAO veterinariodao = new VeterinariosDAO();

    public ProcedimientosEspecialesController() {
        this.dao = new Procedimientos_especialesDAO();
    }

    // 🔹 Crear un nuevo procedimiento especial
    public boolean crearProcedimiento(ProcedimientosEspeciales pe) throws IllegalArgumentException {

        // ✅ Validaciones básicas
         Mascotas mascota = mascotasdao.obtenerPorId(pe.getMascotaId());
        if (mascota == null) {
             throw new IllegalArgumentException("⚠️ No existe una mascota con ese ID.");
            
        }
        
        
           Veterinarios veterinario = veterinariodao.obtenerPorId(pe.getVeterinarioId());
        if (veterinario == null) {
            throw new IllegalArgumentException("⚠️ No existe un veterinario con ese ID.");
            
        }
        
        if (pe.getTipoProcedimiento() == null || pe.getTipoProcedimiento().isBlank()) {
            throw new IllegalArgumentException("⚠️ Error: el tipo de procedimiento no puede estar vacío.");
            
        }
        if (pe.getNombreProcedimiento() == null || pe.getNombreProcedimiento().isBlank()) {
             throw new IllegalArgumentException("⚠️ Error: el nombre del procedimiento no puede estar vacío.");
            
        }
        if (pe.getFechaHora() == null || pe.getFechaHora().isBefore(LocalDateTime.now())) {
             throw new IllegalArgumentException("⚠️ Error: la fecha del procedimiento debe ser futura.");
            
        }
        if (pe.getProximoControl() != null) {
    LocalDate hoy = LocalDate.now();
    LocalDate fechaProcedimiento = pe.getFechaHora().toLocalDate();

    if (pe.getProximoControl().isBefore(hoy)) {
        throw new IllegalArgumentException("⚠️ Error: la fecha de control no puede ser anterior a la fecha actual.");
    }

    if (pe.getProximoControl().isBefore(fechaProcedimiento)) {
        throw new IllegalArgumentException("⚠️ Error: la fecha de control debe ser posterior a la fecha del procedimiento.");
    }
         }

        // ✅ Crear el objeto modelo
    
        try {
            dao.agregar(pe);
            System.out.println("✅ Procedimiento registrado correctamente con ID: " + pe.getId());
            return true;
        } catch (Exception e) {
            System.out.println("❌ Error al registrar el procedimiento: " + e.getMessage());
            return false;
        }
    }

    // 🔹 Listar todos los procedimientos
    public List<ProcedimientosEspeciales> listarProcedimientos() throws IllegalArgumentException{
        return dao.listar();
    }

    // 🔹 Obtener un procedimiento por ID
    public ProcedimientosEspeciales obtenerPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("⚠️ ID inválido.");
            
        }
        ProcedimientosEspeciales p = dao.obtenerPorId(id);
        if (p == null) {
             throw new IllegalArgumentException("⚠️ No se encontró el procedimiento con ID " + id);
        }
        return p;
    }

    // 🔹 Actualizar un procedimiento existente
   public boolean actualizarProcedimiento(ProcedimientosEspeciales p) throws IllegalArgumentException {
    // Validar mascota
    Mascotas mascota = mascotasdao.obtenerPorId(p.getMascotaId());
    if (mascota == null) {
        throw new IllegalArgumentException("⚠️ No existe una mascota con ese ID.");
    }

    // Validar veterinario
    Veterinarios veterinario = veterinariodao.obtenerPorId(p.getVeterinarioId());
    if (veterinario == null) {
        throw new IllegalArgumentException("⚠️ No existe un veterinario con ese ID.");
    }

    // Validar campos obligatorios
    if (p.getTipoProcedimiento() == null || p.getTipoProcedimiento().isBlank()) {
        throw new IllegalArgumentException("⚠️ Error: el tipo de procedimiento no puede estar vacío.");
    }
    if (p.getNombreProcedimiento() == null || p.getNombreProcedimiento().isBlank()) {
        throw new IllegalArgumentException("⚠️ Error: el nombre del procedimiento no puede estar vacío.");
    }

    // Validar fecha del procedimiento
    if (p.getFechaHora() == null || p.getFechaHora().isBefore(LocalDateTime.now())) {
        throw new IllegalArgumentException("⚠️ Error: la fecha del procedimiento debe ser futura.");
    }

    // Validar próxima fecha de control
    if (p.getProximoControl() != null) {
        LocalDate hoy = LocalDate.now();
        LocalDate fechaProcedimiento = p.getFechaHora().toLocalDate();

        if (p.getProximoControl().isBefore(hoy)) {
            throw new IllegalArgumentException("⚠️ Error: la fecha de control no puede ser anterior a la fecha actual.");
        }

        if (p.getProximoControl().isBefore(fechaProcedimiento)) {
            throw new IllegalArgumentException("⚠️ Error: la fecha de control debe ser posterior a la fecha del procedimiento.");
        }
    }

    // Si todo está bien, actualizar
    boolean actualizado = dao.actualizar(p);
    if (actualizado) {
        System.out.println("✅ Procedimiento actualizado correctamente.");
    } else {
        System.out.println("⚠️ No se encontró el procedimiento a actualizar.");
    }

    return actualizado;
}
    // 🔹 Eliminar un procedimiento
    public boolean eliminarProcedimiento(int id) {
        if (id <= 0) {
            System.out.println("⚠️ ID inválido para eliminación.");
            return false;
        }

        boolean eliminado = dao.eliminar(id);
        if (eliminado) {
            System.out.println("🗑️ Procedimiento eliminado correctamente.");
        } else {
            System.out.println("⚠️ No se encontró el procedimiento con ID " + id);
        }
        return eliminado;
    }
}

