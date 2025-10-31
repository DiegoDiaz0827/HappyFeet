/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;

import DAO.VeterinariosDAO;
import Model.Entities.Veterinarios;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author camper
 */
public class VeterinariosController {
    
     private final VeterinariosDAO veterinariosDAO;

    public VeterinariosController() {
        this.veterinariosDAO = new VeterinariosDAO();
    }

    // 🔹 Registrar nuevo veterinario
    public boolean registrarVeterinario(Veterinarios v) {
        if (v.getNombreCompleto() == null || v.getNombreCompleto().isBlank()) {
            System.out.println("❌ El nombre del veterinario no puede estar vacío.");
            return false;
        }
        if (v.getDocumentoIdentidad() == null || v.getDocumentoIdentidad().isBlank()) {
            System.out.println("❌ El documento de identidad es obligatorio.");
            return false;
        }
        if (v.getlicencia() == null || v.getlicencia().isBlank()) {
            System.out.println("❌ La licencia profesional es obligatoria.");
            return false;
        }

        // Fecha de contratación opcional, se puede asignar por defecto
        if (v.getFechaRegistro() == null) {
            v.setFechaRegistro(LocalDate.now());
        }

        veterinariosDAO.insertar(v);
        return true;
    }

    // 🔹 Actualizar veterinario existente
    public boolean actualizarVeterinario(Veterinarios v) {
        Veterinarios existente = veterinariosDAO.obtenerPorId(v.getId());
        if (existente == null) {
            System.out.println("❌ Veterinario no encontrado con ID " + v.getId());
            return false;
        }

        return veterinariosDAO.actualizar(v);
    }

    // 🔹 Eliminar veterinario
    public boolean eliminarVeterinario(int id) {
        Veterinarios existente = veterinariosDAO.obtenerPorId(id);
        if (existente == null) {
            System.out.println("❌ No existe un veterinario con ID " + id);
            return false;
        }

        return veterinariosDAO.eliminar(id);
    }

    // 🔹 Buscar veterinario por ID
    public Veterinarios verVeterinario(int id) {
        Veterinarios v = veterinariosDAO.obtenerPorId(id);
        if (v == null) {
            System.out.println("⚠️ Veterinario no encontrado.");
        }
        return v;
    }

    // 🔹 Listar todos los veterinarios
    public List<Veterinarios> listarVeterinarios() {
        List<Veterinarios> lista = veterinariosDAO.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("⚠️ No hay veterinarios registrados.");
        }
        return lista;
    }
    
    
    
}
