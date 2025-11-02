/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;

import DAO.Procedimientos_especialesDAO;
import Model.Entities.ProcedimientosEspeciales;
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

    public ProcedimientosEspecialesController() {
        this.dao = new Procedimientos_especialesDAO();
    }

    // 🔹 Crear un nuevo procedimiento especial
    public boolean crearProcedimiento(ProcedimientosEspeciales pe) {

        // ✅ Validaciones básicas
        if (pe.getMascotaId() <= 0) {
            System.out.println("⚠️ Error: ID de mascota inválido.");
            return false;
        }
        if (pe.getVeterinarioId() <= 0) {
            System.out.println("⚠️ Error: ID de veterinario inválido.");
            return false;
        }
        if (pe.getTipoProcedimiento() == null || pe.getTipoProcedimiento().isBlank()) {
            System.out.println("⚠️ Error: el tipo de procedimiento no puede estar vacío.");
            return false;
        }
        if (pe.getNombreProcedimiento() == null || pe.getNombreProcedimiento().isBlank()) {
            System.out.println("⚠️ Error: el nombre del procedimiento no puede estar vacío.");
            return false;
        }
        if (pe.getFechaHora() == null || pe.getFechaHora().isBefore(LocalDateTime.now())) {
            System.out.println("⚠️ Error: la fecha del procedimiento debe ser futura.");
            return false;
        }
        if (pe.getProximoControl() != null && pe.getProximoControl().isBefore(LocalDate.now())) {
            System.out.println("⚠️ Error: la fecha de control no puede ser pasada.");
            return false;
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
    public List<ProcedimientosEspeciales> listarProcedimientos() {
        return dao.listar();
    }

    // 🔹 Obtener un procedimiento por ID
    public ProcedimientosEspeciales obtenerPorId(int id) {
        if (id <= 0) {
            System.out.println("⚠️ ID inválido.");
            return null;
        }
        ProcedimientosEspeciales p = dao.obtenerPorId(id);
        if (p == null) {
            System.out.println("⚠️ No se encontró el procedimiento con ID " + id);
        }
        return p;
    }

    // 🔹 Actualizar un procedimiento existente
    public boolean actualizarProcedimiento(ProcedimientosEspeciales p) {
        if (p == null || p.getId() <= 0) {
            System.out.println("⚠️ No se puede actualizar: procedimiento o ID inválido.");
            return false;
        }

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

