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

    // 🔹 Registrar consulta con validación de FKs
    public boolean registrarConsulta(ConsultasMedicas c) {
        if (c.getMascotaId() <= 0 || mascotasDAO.obtenerPorId(c.getMascotaId()) == null) {
            System.out.println("Error: Mascota no encontrada.");
            return false;
        }
        if (c.getVeterinarioId() <= 0 || veterinariosDAO.obtenerPorId(c.getVeterinarioId()) == null) {
            System.out.println("Error: Veterinario no encontrado.");
            return false;
        }
        if (c.getCitaId() != null && citasDAO.obtenerPorId(c.getCitaId()) == null) {
            System.out.println("Error: Cita no encontrada.");
            return false;
        }
        if (c.getFechaHora() == null) {
            System.out.println("Error: La fecha y hora son obligatorias.");
            return false;
        }
        if (c.getMotivo() == null || c.getMotivo().isEmpty()) {
            System.out.println("Error: El motivo de la consulta es obligatorio.");
            return false;
        }
        if (c.getSintomas() == null || c.getSintomas().isEmpty()) {
            System.out.println("Error: Los síntomas son obligatorios.");
            return false;
        }

        consultasDAO.insertar(c);
        return true;
    }

    // 🔹 Actualizar consulta con validación de FKs
    public boolean actualizarConsulta(ConsultasMedicas c) {
        if (c.getId() <= 0 || consultasDAO.obtenerPorId(c.getId()) == null) {
            System.out.println("Error: Consulta no encontrada.");
            return false;
        }
        if (c.getMascotaId() <= 0 || mascotasDAO.obtenerPorId(c.getMascotaId()) == null) {
            System.out.println("Error: Mascota no encontrada.");
            return false;
        }
        if (c.getVeterinarioId() <= 0 || veterinariosDAO.obtenerPorId(c.getVeterinarioId()) == null) {
            System.out.println("Error: Veterinario no encontrado.");
            return false;
        }
        if (c.getCitaId() != null && citasDAO.obtenerPorId(c.getCitaId()) == null) {
            System.out.println("Error: Cita no encontrada.");
            return false;
        }
        if (c.getFechaHora() == null) {
            System.out.println("Error: La fecha y hora son obligatorias.");
            return false;
        }

        return consultasDAO.actualizar(c);
    }

    // 🔹 Eliminar consulta
    public boolean eliminarConsulta(int id) {
        if (id <= 0 || consultasDAO.obtenerPorId(id) == null) {
            System.out.println("Error: Consulta no encontrada.");
            return false;
        }
        return consultasDAO.eliminar(id);
    }

    // 🔹 Ver consulta por ID
    public ConsultasMedicas verConsulta(int id) {
        if (id <= 0) {
            System.out.println("Error: ID inválido.");
            return null;
        }
        return consultasDAO.obtenerPorId(id);
    }

    // 🔹 Listar todas las consultas
    public List<ConsultasMedicas> listarConsultas() {
        return consultasDAO.listarTodos();
    }
}
