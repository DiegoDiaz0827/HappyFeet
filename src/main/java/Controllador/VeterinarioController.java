/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;

import DAO.VeterinariosDAO;
import Model.Entities.Veterinarios;
import java.util.List;

/**
 *
 * @author USUARIO
 */
public class VeterinarioController {

    private final VeterinariosDAO veterinarioDAO;

    public VeterinarioController(VeterinariosDAO veterinarioDAO) {
        this.veterinarioDAO = veterinarioDAO;
    }

    // 🔹 Registrar veterinario
    public void registrarVeterinario(Veterinarios v) {
        if (v == null) {
            System.out.println("❌ Error: los datos del veterinario son nulos.");
            return;
        }

        try {
            veterinarioDAO.insertar(v);
            System.out.println("✅ Veterinario registrado correctamente.");
        } catch (Exception e) {
            System.out.println("❌ Error al registrar veterinario: " + e.getMessage());
        }
    }

    // 🔹 Listar veterinarios
    public List<Veterinarios> listarVeterinarios() {
        List<Veterinarios> lista = veterinarioDAO.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("⚠️ No hay veterinarios registrados.");
        }
        return lista;
    }

    // 🔹 Buscar veterinario por ID
    public Veterinarios buscarVeterinarioPorId(int id) {
        if (id <= 0) {
            System.out.println("⚠️ ID inválido. Debe ser mayor que 0.");
            return null;
        }

        Veterinarios v = veterinarioDAO.obtenerPorId(id);
        if (v == null) {
            System.out.println("⚠️ No se encontró un veterinario con el ID: " + id);
        }
        return v;
    }

    // 🔹 Actualizar veterinario (validando existencia)
    public void actualizarVeterinario(Veterinarios v) {
        if (v == null || v.getId() <= 0) {
            System.out.println("⚠️ Veterinario o ID inválido.");
            return;
        }

        Veterinarios existente = veterinarioDAO.obtenerPorId(v.getId());
        if (existente == null) {
            System.out.println("❌ No se puede actualizar: el veterinario con ID " + v.getId() + " no existe.");
            return;
        }

        boolean actualizado = veterinarioDAO.actualizar(v);
        if (actualizado) {
            System.out.println("✅ Veterinario actualizado correctamente.");
        } else {
            System.out.println("❌ Error al actualizar el veterinario con ID " + v.getId());
        }
    }

    // 🔹 Eliminar veterinario (validando existencia)
    public void eliminarVeterinario(int id) {
        if (id <= 0) {
            System.out.println("⚠️ ID inválido. Debe ser mayor que 0.");
            return;
        }

        Veterinarios existente = veterinarioDAO.obtenerPorId(id);
        if (existente == null) {
            System.out.println("❌ No se puede eliminar: el veterinario con ID " + id + " no existe.");
            return;
        }

        boolean eliminado = veterinarioDAO.eliminar(id);
        if (eliminado) {
            System.out.println("🗑️ Veterinario eliminado correctamente.");
        } else {
            System.out.println("❌ Error al eliminar el veterinario con ID " + id);
        }
    }
}
