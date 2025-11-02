/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;

import DAO.Transacciones_puntosDAO;
import Model.Entities.transacciones_puntos;
import Model.Enums.TipoTransacciones;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
/**
 *
 * @author camper
 */
public class transacciones_puntosController {
// Instancia del DAO para interactuar con la base de datos
    Transacciones_puntosDAO transaccionDAO = new Transacciones_puntosDAO();

    // --- 1. Registrar Transacción (CREATE)
    public void registrarTransaccion(transacciones_puntos t){
        try {
            if (t.getFecha() == null) {
                t.setFecha(Timestamp.valueOf(LocalDateTime.now()));
            }

            transaccionDAO.agregar(t);
            System.out.println("🎉 Transacción de puntos registrada exitosamente. Tipo: " + t.getTipo().name());
        } catch (Exception e) {
            System.err.println("❌ Error al registrar la transacción de puntos: " + e.getMessage());
        }
    }
    
    // --- 2. Listar Transacciones por Club ID
    public List<transacciones_puntos> listarHistorialPorClub(int clubMascotasId) {
        System.out.println("\n--- LISTANDO HISTORIAL DE TRANSACCIONES PARA CLUB ID: " + clubMascotasId + " ---");
        List<transacciones_puntos> lista = transaccionDAO.listarPorClub(clubMascotasId);
        System.out.println("📊 Se encontraron **" + lista.size() + " transacciones**.");
        return lista;
    }

    // --- 3. Buscar Transacción por ID 
    public transacciones_puntos buscarTransaccionPorId(int id) {
        System.out.println("\n--- BUSCANDO TRANSACCIÓN ID: " + id + " ---");
        transacciones_puntos transaccion = transaccionDAO.obtenerPorId(id);
        if (transaccion != null) {
            System.out.println("🔎 Transacción encontrada. Monto: " + transaccion.getPuntos() + " Tipo: " + transaccion.getTipo().name());
        } else {
            System.out.println("⚠️ Transacción ID " + id + " **no encontrada**.");
        }
        return transaccion;
    }
    
    // --- 4. Actualizar Transacción 
    public boolean actualizarTransaccion(transacciones_puntos t) {
        System.out.println("\n--- INTENTANDO ACTUALIZAR TRANSACCIÓN ID: " + t.getId() + " ---");
        boolean actualizado = transaccionDAO.actualizar(t);
        if (actualizado) {
            System.out.println("⚠️ Transacción ID " + t.getId() + " **actualizada correctamente**. ¡ATENCIÓN! La modificación de historial puede causar inconsistencias.");
        } else {
            System.out.println("❌ No se pudo actualizar la transacción ID " + t.getId() + ".");
        }
        return actualizado;
    }
    
    // --- 5. Eliminar Transacción 
    public boolean eliminarTransaccion(int id) {
        System.out.println("\n--- INTENTANDO ELIMINAR TRANSACCIÓN ID: " + id + " ---");
        
        
        boolean eliminado = transaccionDAO.eliminar(id);
        if (eliminado) {
            System.out.println("⚠️ Transacción ID " + id + " **eliminada correctamente**. ¡ALERTA! Un registro de historial ha sido borrado.");
        } else {
            System.out.println("❌ No se pudo eliminar la transacción ID " + id + ".");
        }
        return eliminado;
    }
}
