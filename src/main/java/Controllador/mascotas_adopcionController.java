/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;
import DAO.Mascotas_adopcionDAO;
import Model.Entities.mascotas_adopcion;
import Model.Enums.EstadoAdopcion;
import java.util.Date;
import java.util.List;
/**
 *
 * @author camper
 */
public class mascotas_adopcionController {
    
private final Mascotas_adopcionDAO mascotasAdopcionDAO;


    public mascotas_adopcionController(Mascotas_adopcionDAO mascotasAdopcionDAO) {
        this.mascotasAdopcionDAO = mascotasAdopcionDAO;
    }

    // 1️ Registrar un nuevo registro de adopción 
 
    public boolean registrarMascotaAdopcion(mascotas_adopcion mascotaAdopcion) {
        
        if (mascotaAdopcion.getMascotaId() <= 0) {
            System.out.println("⚠️ ID de mascota inválido. Debe estar asociado a una mascota existente.");
            return false;
        }
        if (mascotaAdopcion.getFechaIngreso() == null || mascotaAdopcion.getFechaIngreso().after(new Date())) {
            System.out.println("⚠️ La fecha de ingreso es obligatoria y no puede ser futura.");
            return false;
        }
        if (mascotaAdopcion.getMotivoIngreso() == null || mascotaAdopcion.getMotivoIngreso().isBlank()) {
            System.out.println("⚠️ El motivo de ingreso al sistema de adopción es obligatorio.");
            return false;
        }
        
        if (mascotaAdopcion.getEstado() == null) {
            mascotaAdopcion.setEstado(EstadoAdopcion.DISPONIBLE);
        } else if (mascotaAdopcion.getEstado() == EstadoAdopcion.ADOPTADA) {
             System.out.println("⚠️ No se puede registrar inicialmente una mascota en estado ADOPTADA. Estableciendo a DISPONIBLE.");
             mascotaAdopcion.setEstado(EstadoAdopcion.DISPONIBLE);
        }

        try {
            mascotasAdopcionDAO.agregar(mascotaAdopcion);
            System.out.println("✅ Mascota registrada para adopción correctamente con ID: " + mascotaAdopcion.getId());
            return true;
        } catch (Exception e) {
            System.out.println("❌ Error al registrar mascota para adopción: " + e.getMessage());
            return false;
        }
    }

    //  2️ Listar todos los registros de adopción (READ)
    public List<mascotas_adopcion> listarMascotasAdopcion() {
        return mascotasAdopcionDAO.listar();
    }

    // Buscar registro de adopción por ID 
   
    public mascotas_adopcion obtenerMascotaAdopcionPorId(int id) {
        if (id <= 0) {
            System.out.println("⚠️ ID inválido para la búsqueda.");
            return null;
        }
        mascotas_adopcion mascota = mascotasAdopcionDAO.obtenerPorId(id);
        if (mascota == null) {
            System.out.println("ℹ️ Registro de adopción con ID " + id + " no encontrado.");
        }
        return mascota;
    }

    //  4️ Actualizar registro de adopción existente (UPDATE)
   
    public boolean actualizarMascotaAdopcion(mascotas_adopcion mascotaAdopcion) {
        if (mascotaAdopcion.getId() <= 0) {
            System.out.println("⚠️ El registro debe tener un ID válido para ser actualizado.");
            return false;
        }
        if (mascotaAdopcion.getMascotaId() <= 0) {
             System.out.println("⚠️ El ID de mascota no puede ser inválido al actualizar.");
             return false;
        }
        if (mascotaAdopcion.getEstado() == null) {
             System.out.println("⚠️ El estado de adopción es obligatorio.");
             return false;
        }

        boolean exito = mascotasAdopcionDAO.actualizar(mascotaAdopcion);
        System.out.println(exito ? "✅ Registro de Adopción actualizado correctamente." : "❌ No se pudo actualizar el registro (ID no encontrado o error en DAO).");
        return exito;
    }

    //  5️ Eliminar registro de adopción (DELETE)
 
    public boolean eliminarMascotaAdopcion(int id) {
        if (id <= 0) {
            System.out.println("⚠️ ID de registro de adopción inválido.");
            return false;
        }

        boolean exito = mascotasAdopcionDAO.eliminar(id);
        System.out.println(exito ? "🗑️ Registro de Adopción eliminado correctamente." : "❌ No se encontró el registro con ese ID para eliminar.");
        return exito;
    }
    

    public boolean marcarComoAdoptada(int id) {
        mascotas_adopcion mascotaAdopcion = obtenerMascotaAdopcionPorId(id);
        
        if (mascotaAdopcion == null) {
            System.out.println("❌ No se encontró el registro para marcar como adoptado.");
            return false;
        }
        
        if (mascotaAdopcion.getEstado() == EstadoAdopcion.DISPONIBLE) {
            mascotaAdopcion.setEstado(EstadoAdopcion.ADOPTADA);
            System.out.println("✨ Mascota marcada como ADOPTADA. ¡Felicidades!");
            return actualizarMascotaAdopcion(mascotaAdopcion);
        } else {
            System.out.println("⚠️ El estado actual es " + mascotaAdopcion.getEstado().name() + ". Solo se puede marcar como ADOPTADA si está DISPONIBLE.");
            return false;
        }
    }
}
