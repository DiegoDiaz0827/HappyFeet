/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;

import DAO.VeterinariosDAO;
import Model.Entities.Veterinarios;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

   
    public boolean registrarVeterinario(Veterinarios v) {
        if (v.getDocumentoIdentidad() == null || v.getDocumentoIdentidad().length() != 10) {
        throw new IllegalArgumentException("El documento debe tener exactamente 10 números.");
    }

    if (!v.getDocumentoIdentidad().chars().allMatch(Character::isDigit)) {
        throw new IllegalArgumentException("El documento solo debe contener números.");
    }

    if (v.getTelefono() == null || v.getTelefono().length() != 10) {
        throw new IllegalArgumentException("El teléfono debe tener exactamente 10 números.");
    }

    if (!v.getTelefono().chars().allMatch(Character::isDigit)) {
        throw new IllegalArgumentException("El teléfono solo debe contener números.");
    }

   
    if (v.getFechaRegistro() == null) {
        v.setFechaRegistro(LocalDate.now());
    }

        veterinariosDAO.insertar(v);
        return true;
    }

   
    public boolean actualizarVeterinario(Veterinarios v) {
        Veterinarios existente = veterinariosDAO.obtenerPorId(v.getId());
        if (existente == null) {
            System.out.println(" Veterinario no encontrado con ID " + v.getId());
            return false;
        }

        return veterinariosDAO.actualizar(v);
    }

   
    public boolean eliminarVeterinario(int id) {
        Veterinarios existente = veterinariosDAO.obtenerPorId(id);
        if (existente == null) {
            System.out.println(" No existe un veterinario con ID " + id);
            return false;
        }

        return veterinariosDAO.eliminar(id);
    }

   
    public Veterinarios verVeterinario(int id) {
        Veterinarios v = veterinariosDAO.obtenerPorId(id);
        if (v == null) {
            System.out.println("⚠️ Veterinario no encontrado.");
        }
        return v;
    }
    
    

    
    public List<Veterinarios> listarVeterinarios() {
        List<Veterinarios> lista = veterinariosDAO.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("️ No hay veterinarios registrados.");
        }
        return lista;
    }
    
    
    
}
