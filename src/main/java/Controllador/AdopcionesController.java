/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;
import DAO.AdopcionesDAO;
import Model.Entities.adopciones;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author camper
 */

public class AdopcionesController {
private final AdopcionesDAO adopcionesDAO;

    public AdopcionesController(AdopcionesDAO adopcionesDAO) {
        this.adopcionesDAO = adopcionesDAO;
    }

    // 1️ Registrar una nueva adopción (CREATE)
   
    public boolean registrarAdopcion(adopciones adopcion) {
        
        // 1. Validaciones  de IDs
        if (adopcion.getMascotaAdopcionId() <= 0) {
            System.out.println("⚠️ ID de Mascota Adopción inválido. Debe especificar la mascota.");
            return false;
        }
        if (adopcion.getDuenoId() <= 0) {
            System.out.println("⚠️ ID de Dueño inválido. Debe especificar el adoptante.");
            return false;
        }

        // 2. Validaciones de fechas
        Date hoy = new Date();
        if (adopcion.getFechaAdopcion() == null || adopcion.getFechaAdopcion().after(hoy)) {
            System.out.println("⚠️ La fecha de adopción es obligatoria y no puede ser futura.");
            return false;
        }
        
        // 3. Validar seguimiento si es requerido
        if (adopcion.isSeguimientoRequerido()) {
            if (adopcion.getFechaPrimerSeguimiento() == null) {
                System.out.println("⚠️ Si el seguimiento es requerido, la fecha del primer seguimiento es obligatoria.");
                return false;
            }
            if (adopcion.getFechaPrimerSeguimiento().before(adopcion.getFechaAdopcion())) {
                 System.out.println("⚠️ La fecha del primer seguimiento no puede ser anterior a la fecha de adopción.");
                 return false;
            }
             
            // Ejemplo de lógica adicional: asegurar que el seguimiento sea al menos 7 días después
            long diff = adopcion.getFechaPrimerSeguimiento().getTime() - adopcion.getFechaAdopcion().getTime();
           
            long dias = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            if (dias < 7) {
                 System.out.println("ℹ️ Advertencia: El primer seguimiento debe ser al menos 7 días después de la adopción.");
            }
        } else {
            if (adopcion.getFechaPrimerSeguimiento() != null) {
                System.out.println("⚠️ Error: Si el seguimiento NO es requerido, la fecha del primer seguimiento debe ser nula.");
                return false;
            }
        }


        try {
            adopcionesDAO.agregar(adopcion);
            System.out.println("✅ Adopción registrada correctamente con ID: " + adopcion.getId());
            return true;
        } catch (Exception e) {
            System.out.println("❌ Error al registrar adopción: " + e.getMessage());
            return false;
        }
    }

    // --- 2️ Listar todas las adopciones (READ ALL)

    public List<adopciones> listarAdopciones() {
        return adopcionesDAO.listar();
    }

    // 3️ Buscar adopción por ID (READ ONE)

    public adopciones obtenerAdopcionPorId(int id) {
        if (id <= 0) {
            System.out.println("⚠️ ID inválido para la búsqueda.");
            return null;
        }
        adopciones adopcion = adopcionesDAO.obtenerPorId(id);
        if (adopcion == null) {
            System.out.println("ℹ️ Registro de adopción con ID " + id + " no encontrado.");
        }
        return adopcion;
    }

    // --- 4️ Actualizar adopción existente (UPDATE)

    public boolean actualizarAdopcion(adopciones adopcion) {
        if (adopcion.getId() <= 0) {
            System.out.println("️ El registro debe tener un ID válido para ser actualizado.");
            return false;
        }
        

        if (adopcion.getMascotaAdopcionId() <= 0 || adopcion.getDuenoId() <= 0) {
            System.out.println("️ Los IDs de Mascota y Dueño no pueden ser inválidos al actualizar.");
            return false;
        }
        
        boolean exito = adopcionesDAO.actualizar(adopcion);
        System.out.println(exito ? " Adopción actualizada correctamente." : " No se pudo actualizar la adopción (ID no encontrado o error en DAO).");
        return exito;
    }

    // 5️ Eliminar adopción (DELETE)
    public boolean eliminarAdopcion(int id) {
        if (id <= 0) {
            System.out.println("⚠️ ID de adopción inválido.");
            return false;
        }

        boolean exito = adopcionesDAO.eliminar(id);
        System.out.println(exito ? "🗑️ Adopción eliminada correctamente." : " No se encontró la adopción con ese ID para eliminar.");
        return exito;
    }
}
