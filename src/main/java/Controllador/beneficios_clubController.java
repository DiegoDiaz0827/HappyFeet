/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;

import DAO.Beneficios_clubDAO;
import Model.Entities.beneficios_club;
import Model.Entities.ClubMascotas;
import java.util.List;

/**
 *
 * @author camper
 */
public class beneficios_clubController {
    private final Beneficios_clubDAO beneficiosDAO;

    public beneficios_clubController(Beneficios_clubDAO beneficiosDAO) {
        this.beneficiosDAO = beneficiosDAO;
        System.out.println("✅ beneficios_clubController inicializado y listo para usar.");
    }

    public void registrarBeneficio(beneficios_club b){
        try {
            if (!b.isActivo()) {
                b.setActivo(true);
            }
            
            beneficiosDAO.agregar(b);
            System.out.println(" Beneficio **'" + b.getNombre() + "'** registrado exitosamente con ID: " + b.getId());
        } catch (Exception e) {
            System.err.println(" Error al registrar el beneficio: " + e.getMessage());
        }
    }
    
    // --- 2. Actualizar Beneficio 
    public boolean actualizarBeneficio(beneficios_club b) {
        System.out.println("\n--- ACTUALIZANDO BENEFICIO ID: " + b.getId() + " ---");
        boolean actualizado = beneficiosDAO.actualizar(b);
        if (actualizado) {
            System.out.println(" Beneficio ID " + b.getId() + " **actualizado correctamente**.");
        } else {
            System.out.println(" No se pudo actualizar el beneficio ID " + b.getId() + ".");
        }
        return actualizado;
    }
    
    // --- 3. Eliminar Beneficio
    public boolean eliminarBeneficio(int id) {
        System.out.println("\n--- ELIMINANDO BENEFICIO ID: " + id + " ---");
        boolean eliminado = beneficiosDAO.eliminar(id);
        if (eliminado) {
            System.out.println(" Beneficio ID " + id + " **eliminado correctamente**.");
        } else {
            System.out.println(" No se pudo eliminar el beneficio ID " + id + ".");
        }
        return eliminado;
    }

    // --- 4. Buscar Beneficio por ID 
    public beneficios_club buscarBeneficioPorId(int id) {
        System.out.println("\n--- BUSCANDO BENEFICIO ID: " + id + " ---");
        beneficios_club beneficio = beneficiosDAO.obtenerPorId(id);
        if (beneficio != null) {
            System.out.println("? Encontrado: **" + beneficio.getNombre() + "** (Costo: " + beneficio.getPuntosNecesarios() + " pts)");
        } else {
            System.out.println("⚠️ Beneficio ID " + id + " **no encontrado**.");
        }
        return beneficio;
    }

    // --- 5. Obtener Todos los Beneficios Activos 
    public List<beneficios_club> obtenerBeneficiosActivos() {
        System.out.println("\n--- LISTANDO BENEFICIOS DISPONIBLES EN EL CATÁLOGO ---");
        List<beneficios_club> lista = beneficiosDAO.listarActivos();
        System.out.println(" Catálogo: **" + lista.size() + " beneficios** activos disponibles.");
        return lista;
    }
    
    // --- 6. Verificar Canje (Lógica de negocio)
    public boolean verificarCanje(ClubMascotas club, beneficios_club beneficio) {
        System.out.println("\n--- VERIFICANDO CANJE DE BENEFICIO ---");
        
        if (!beneficio.isActivo()) {
            System.out.println(" El beneficio '" + beneficio.getNombre() + "' no está activo.");
            return false;
        }
        
        // 1. Verificar Puntos
        if (club.getPuntosDisponibles() < beneficio.getPuntosNecesarios()) {
            System.out.println(" Puntos insuficientes. Necesitas " + beneficio.getPuntosNecesarios() + " y tienes " + club.getPuntosDisponibles() + ".");
            return false;
        }
        
        // 2. Verificar Nivel Requerido
        String nivelRequerido = beneficio.getNivelRequerido();
        if (nivelRequerido != null && !nivelRequerido.isEmpty() && !club.getNivel().equalsIgnoreCase(nivelRequerido)) {
            System.out.println("⚠️ Nivel no cumple. Requiere nivel '" + nivelRequerido + "' y el club es '" + club.getNivel() + "'.");
            return false;
        }
        
        System.out.println(" ¡Canje permitido! Puntos y nivel cumplen los requisitos.");
        return true;
    }
}