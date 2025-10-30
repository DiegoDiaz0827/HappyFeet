/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;

import DAO.DueñoDAO;
import Model.Entities.Dueños;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author camper
 */
public class DueñosController {
    
    DueñoDAO dueñodao = new DueñoDAO();
    
    public void registrarDueño(Dueños d){
    try {
            if (d.getFechaRegistro() == null) {
                d.setFechaRegistro(LocalDateTime.now());
            }
            dueñodao.agregar(d) ;
            System.out.println("🎉 El dueño " + d.getNombreCompleto() + " ha sido registrado exitosamente.");
        } catch (Exception e) {
            System.err.println("❌ Error al registrar el dueño: " + e.getMessage());
        }
    }
    
    public boolean actualizarDueno(Dueños dueno) {
        System.out.println("\n--- ACTUALIZANDO DUEÑO ID: " + dueno.getId() + " ---");
        boolean actualizado = dueñodao.actualizar(dueno);
        if (actualizado) {
            System.out.println("✅ Datos del dueño ID " + dueno.getId() + " actualizados correctamente.");
        } else {
            System.out.println("❌ No se pudo actualizar el dueño ID " + dueno.getId() + ".");
        }
        return actualizado;
    }
    
    public boolean eliminarDueno(int id) {
        System.out.println("\n--- ELIMINANDO DUEÑO ID: " + id + " ---");
        boolean eliminado = dueñodao.eliminar(id);
        if (eliminado) {
            System.out.println("✅ Dueño ID " + id + " eliminado correctamente.");
        } else {
            System.out.println("❌ No se pudo eliminar el dueño ID " + id + ".");
        }
        return eliminado;
    }

    public Dueños buscarDuenoPorId(int id) {
        System.out.println("\n--- BUSCANDO DUEÑO ID: " + id + " ---");
        Dueños dueno = dueñodao.obtenerPorId(id);
        if (dueno != null) {
            System.out.println("🔎 Encontrado: " + dueno.getNombreCompleto());
        } else {
            System.out.println("⚠️ Dueño ID " + id + " no encontrado.");
        }
        return dueno;
    }

    public List<Dueños> obtenerTodosLosDuenos() {
        System.out.println("\n--- LISTANDO TODOS LOS DUEÑOS ---");
        List<Dueños> lista = dueñodao.listarTodos();
        System.out.println("📊 Se encontraron " + lista.size() + " dueños.");
        return lista;
    }
    
    
    }
    
    
    

