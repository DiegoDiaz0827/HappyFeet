/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;
import DAO.ServiciosDAO;
import Model.Entities.Servicios;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author camper
 */
public class serviciosController {
    
private final ServiciosDAO serviciosDAO;

    public serviciosController(ServiciosDAO serviciosDAO) {
        this.serviciosDAO = serviciosDAO;
    }

    // --- 1️⃣ Registrar un nuevo servicio (CREATE)
    /**
     * Registra un nuevo servicio después de validar sus campos.
     */
    public boolean registrarServicio(Servicios servicio) {
        // Validaciones de lógica de negocio
        if (servicio.getNombre() == null || servicio.getNombre().isBlank()) {
            System.out.println("⚠️ El nombre del servicio es obligatorio.");
            return false;
        }
        if (servicio.getPrecioBase() == null || servicio.getPrecioBase().compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("⚠️ El precio base debe ser mayor a cero.");
            return false;
        }
        if (servicio.getDuracionEstimadaMinutos() <= 0) {
            System.out.println("⚠️ La duración estimada debe ser positiva.");
            return false;
        }
        
        // ❌ SE ELIMINA LA LÍNEA con el error: 
        // Ya que 'activo' es un 'boolean' primitivo, ya tiene un valor (true o false). 
        // No puede ser null, por lo que no es necesario forzar su valor aquí si ya se maneja en el constructor.
        // if (servicio.isActivo() == null) { ... }

        try {
            serviciosDAO.agregar(servicio);
            System.out.println("✅ Servicio registrado correctamente con ID: " + servicio.getId());
            return true;
        } catch (Exception e) {
            System.out.println("❌ Error al registrar servicio: " + e.getMessage());
            return false;
        }
    }

    // --- 2️⃣ Listar todos los servicios (READ ALL)
    public List<Servicios> listarServicios() {
        return serviciosDAO.listar();
    }

    // --- 3️⃣ Buscar servicio por ID (READ ONE)
    public Servicios obtenerServicioPorId(int id) {
        if (id <= 0) {
            System.out.println("⚠️ ID inválido para la búsqueda.");
            return null;
        }
        Servicios servicio = serviciosDAO.obtenerPorId(id);
        if (servicio == null) {
            System.out.println("ℹ️ Servicio con ID " + id + " no encontrado.");
        }
        return servicio;
    }

    // --- 4️⃣ Actualizar servicio existente (UPDATE)
    public boolean actualizarServicio(Servicios servicio) {
        if (servicio.getId() <= 0) {
            System.out.println("⚠️ El servicio debe tener un ID válido para ser actualizado.");
            return false;
        }
        if (servicio.getNombre() == null || servicio.getNombre().isBlank()) {
            System.out.println("⚠️ El nombre del servicio es obligatorio para la actualización.");
            return false;
        }
        if (servicio.getPrecioBase() == null || servicio.getPrecioBase().compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("⚠️ El nuevo precio base debe ser mayor a cero.");
            return false;
        }

        boolean exito = serviciosDAO.actualizar(servicio);
        System.out.println(exito ? "✅ Servicio actualizado correctamente." : "❌ No se pudo actualizar el servicio (ID no encontrado o error en DAO).");
        return exito;
    }

    // --- 5️⃣ Eliminar servicio (DELETE)
    public boolean eliminarServicio(int id) {
        if (id <= 0) {
            System.out.println("⚠️ ID de servicio inválido.");
            return false;
        }

        boolean exito = serviciosDAO.eliminar(id);
        System.out.println(exito ? "🗑️ Servicio eliminado correctamente." : "❌ No se encontró el servicio con ese ID para eliminar.");
        return exito;
    }
}