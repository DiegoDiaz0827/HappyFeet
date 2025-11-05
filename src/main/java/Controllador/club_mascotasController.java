/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;

import DAO.Club_mascotasDAO;
import Model.Entities.ClubMascotas;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import DAO.DueñoDAO;

/**
 *
 * @author camper
 */
public class club_mascotasController {
    
    private final Club_mascotasDAO clubDAO;
    private final DueñoDAO dueñoDAO;
    public club_mascotasController(Club_mascotasDAO clubDAO, DueñoDAO dueñoDAO) {
        this.clubDAO = clubDAO;
        this.dueñoDAO = dueñoDAO;
        System.out.println("✅ club_mascotasController inicializado con DAOs inyectados.");
    }

   
    public void registrarMembresia(ClubMascotas club){
        try {
            if (club.getFechaInscripcion() == null) {
                club.setFechaInscripcion(LocalDate.now());
            }
            if (club.getFechaUltimaActualizacion() == null) {
                club.setFechaUltimaActualizacion(LocalDateTime.now());
            }
            if (club.getPuntosAcumulados() == 0 && club.getPuntosDisponibles() == 0) {
                club.setPuntosAcumulados(0);
                club.setPuntosCanjeados(0);
                club.setPuntosDisponibles(0);
                club.setNivel("Bronce"); 
                club.setActivo(true);
            }
            
            clubDAO.agregar(club);
            System.out.println("🎉 Membresía de Club registrada exitosamente para el Dueño ID: " + club.getDuenoId());
        } catch (Exception e) {
            System.err.println(" Error al registrar la membresía del club: " + e.getMessage());
        }
    }
    
   
    public boolean actualizarMembresia(ClubMascotas club) {
        System.out.println("\n--- ACTUALIZANDO MEMBRESÍA CLUB DUEÑO ID: " + club.getDuenoId() + " ---");
        
        club.setFechaUltimaActualizacion(LocalDateTime.now());
        
        boolean actualizado = clubDAO.actualizar(club);
        if (actualizado) {
            System.out.println(" Membresía del Dueño ID " + club.getDuenoId() + " **actualizada correctamente**.");
        } else {
            System.out.println(" No se pudo actualizar la membresía del Dueño ID " + club.getDuenoId() + ".");
        }
        return actualizado;
    }
    
   
    public boolean eliminarMembresia(int duenoId) {
        System.out.println("\n--- ELIMINANDO MEMBRESÍA CLUB DUEÑO ID: " + duenoId + " ---");
        boolean eliminado = clubDAO.eliminar(duenoId);
        if (eliminado) {
            System.out.println(" Membresía del Dueño ID " + duenoId + " **eliminada correctamente**.");
        } else {
            System.out.println(" No se pudo eliminar la membresía del Dueño ID " + duenoId + " (quizás no existía).");
        }
        return eliminado;
    }

   
    public ClubMascotas buscarMembresiaPorDuenoId(int duenoId) {
        System.out.println("\n--- BUSCANDO MEMBRESÍA CLUB DUEÑO ID: " + duenoId + " ---");
        ClubMascotas club = clubDAO.obtenerPorDuenoId(duenoId);
        if (club != null) {
            System.out.println("? Membresía encontrada. Nivel actual: " + club.getNivel());
            System.out.println("   Puntos disponibles: " + club.getPuntosDisponibles());
        } else {
            System.out.println("️ Membresía de Club para el Dueño ID " + duenoId + " **no encontrada**.");
        }
        return club;
    }

   
    public List<ClubMascotas> obtenerTodasLasMembresias() {
        System.out.println("\n--- LISTANDO TODAS LAS MEMBRESÍAS DEL CLUB ---");
        List<ClubMascotas> lista = clubDAO.listar();
        System.out.println("? Se encontraron **" + lista.size() + " membresías** activas/inactivas.");
        return lista;
    }
    
    public boolean acumularPuntos(int duenoId, int puntosAñadir) {
        ClubMascotas club = buscarMembresiaPorDuenoId(duenoId);
        
        if (club != null) {
            int nuevosAcumulados = club.getPuntosAcumulados() + puntosAñadir;
            int nuevosDisponibles = club.getPuntosDisponibles() + puntosAñadir;
            
            club.setPuntosAcumulados(nuevosAcumulados);
            club.setPuntosDisponibles(nuevosDisponibles);
            
            System.out.println(" Añadiendo **" + puntosAñadir + " puntos** al Dueño ID " + duenoId + ".");
            return actualizarMembresia(club);
        } else {
            System.out.println(" No se puede añadir puntos. Membresía para el Dueño ID " + duenoId + " no encontrada.");
            return false;
        }
    }
}
