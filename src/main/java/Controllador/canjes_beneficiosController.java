/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;

import DAO.Canjes_beneficiosDAO;
import Model.Entities.canjes_beneficios;
import Model.Enums.EstadoCanjees;
import java.util.List;
import java.sql.Timestamp;
/**
 *
 * @author camper
 */
public class canjes_beneficiosController {
    
 // Instancia del DAO para interactuar con la base de datos
    Canjes_beneficiosDAO canjesDao = new Canjes_beneficiosDAO();

    /**
     * Registra un nuevo canje de beneficio.
     * Asigna la fecha de canje actual si no está establecida.
     * @param c El objeto canjes_beneficios a registrar.
     */
    public void registrarCanje(canjes_beneficios c){
        try {
            if (c.getFechaCanje() == null) {
                c.setFechaCanje(new Timestamp(System.currentTimeMillis()));
            }
            
            if (c.getEstado() == null) {
                c.setEstado(EstadoCanjees.PENDIENTE);
            }
            
            canjesDao.agregar(c);
            System.out.println(" Canje de beneficio ID " + c.getId() + " (Puntos: " + c.getPuntosCanjeados() + ") registrado exitosamente.");
        } catch (Exception e) {
            System.err.println(" Error al registrar el canje de beneficio: " + e.getMessage());
        }
    }
    
   
    public boolean actualizarEstadoCanje(int id, EstadoCanjees nuevoEstado, Integer facturaId) {
        System.out.println("\n--- ACTUALIZANDO ESTADO DE CANJE ID: " + id + " a " + nuevoEstado.name() + " ---");
        boolean actualizado = canjesDao.actualizarEstado(id, nuevoEstado, facturaId);
        if (actualizado) {
            System.out.println(" Estado del canje ID " + id + " actualizado a " + nuevoEstado.name() + " correctamente.");
        } else {
            System.out.println(" No se pudo actualizar el estado del canje ID " + id + ".");
        }
        return actualizado;
    }
    
 
    public boolean eliminarCanje(int id) {
        System.out.println("\n--- ELIMINANDO CANJE ID: " + id + " ---");
        boolean eliminado = canjesDao.eliminar(id);
        if (eliminado) {
            System.out.println(" Canje ID " + id + " eliminado correctamente.");
        } else {
            System.out.println(" No se pudo eliminar el canje ID " + id + ".");
        }
        return eliminado;
    }


    public canjes_beneficios buscarCanjePorId(int id) {
        System.out.println("\n--- BUSCANDO CANJE ID: " + id + " ---");
        canjes_beneficios canje = canjesDao.obtenerPorId(id);
        if (canje != null) {
            System.out.println("🔎 Encontrado: Canje ID " + id + ", Estado: " + canje.getEstado().name());
        } else {
            System.out.println("⚠️ Canje ID " + id + " no encontrado.");
        }
        return canje;
    }

  
    public List<canjes_beneficios> obtenerCanjesPorClubId(int clubMascotasId) {
        System.out.println("\n--- LISTANDO CANJES PARA CLUB ID: " + clubMascotasId + " ---");
        List<canjes_beneficios> lista = canjesDao.listarPorClubId(clubMascotasId);
        System.out.println("📊 Se encontraron " + lista.size() + " canjes para el Club ID " + clubMascotasId + ".");
        return lista;
    }
}