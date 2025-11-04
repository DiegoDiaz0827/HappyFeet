/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;

import DAO.Registro_jornada_vacunacionDAO;
import Model.Entities.registro_jornada_vacunacion;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
/**
 *
 * @author camper
 */

public class registro_jornada_vacunacionController {
    private final Registro_jornada_vacunacionDAO registroDAO;
    

    public registro_jornada_vacunacionController(Registro_jornada_vacunacionDAO registroDAO) {
        this.registroDAO = registroDAO;
    }


    public void registrarRegistro(registro_jornada_vacunacion r){
        try {
            if (r.getFechaHora() == null) {
                r.setFechaHora(Timestamp.valueOf(LocalDateTime.now()));
            }
            
            registroDAO.agregar(r);
            System.out.println(" El registro de vacunación ID " + r.getId() + " ha sido **registrado exitosamente**.");
        } catch (Exception e) {
            System.err.println(" Error al registrar la jornada de vacunación: " + e.getMessage());
        }
    }


    //               REGISTRO MASIVO Y RÁPIDO

    public boolean registrarLoteVacunacion(List<registro_jornada_vacunacion> registros) {
        if (registros == null || registros.isEmpty()) {
            System.out.println("️ No hay registros para procesar en el lote.");
            return false;
        }

        System.out.println("\n--- PROCESANDO LOTE DE VACUNACIÓN MASIVA (" + registros.size() + " mascotas) ---");
        int registrosExitosos = 0;
        long inicio = System.currentTimeMillis();
        
        for (registro_jornada_vacunacion r : registros) {
            try {
                // 1. Asignar Fecha/Hora 
                if (r.getFechaHora() == null) {
                    r.setFechaHora(Timestamp.valueOf(LocalDateTime.now()));
                }
                
                // 2. Validación mínima
                if (r.getMascotaId() <= 0 || r.getVacunaId() <= 0 || r.getJornadaId() <= 0) {
                    System.out.println("❌ ERROR: Registro para Mascota ID " + r.getMascotaId() + " tiene IDs inválidos. Saltando.");
                    continue; 
                }

                // 3. Ejecutar la inserción
                registroDAO.agregar(r); 
                registrosExitosos++;
                
            } catch (Exception e) {
                System.out.println("Fallo en la inserción para Mascota ID " + r.getMascotaId() + ": " + e.getMessage());
            }
        }
        
        long fin = System.currentTimeMillis();
        long tiempoTotal = fin - inicio;

        System.out.println(" PROCESO MASIVO FINALIZADO.");
        System.out.println("    Registros procesados: " + registros.size());
        System.out.println("    Registros exitosos: " + registrosExitosos);
        System.out.println("    Tiempo total (simulado): " + tiempoTotal + "ms");
        
        return registrosExitosos > 0;
    }
    // =========================================================================
    
    // --- 2. Actualizar
    public boolean actualizarRegistro(registro_jornada_vacunacion r) {
        System.out.println("\n--- ACTUALIZANDO REGISTRO DE VACUNACIÓN ID: " + r.getId() + " ---");
        boolean actualizado = registroDAO.actualizar(r);
        if (actualizado) {
            System.out.println("Registro ID " + r.getId() + " **actualizado correctamente**.");
        } else {
            System.out.println(" No se pudo actualizar el registro ID " + r.getId() + ".");
        }
        return actualizado;
    }
    
    // --- 3. Eliminar
    public boolean eliminarRegistro(int id) {
        System.out.println("\n--- ELIMINANDO REGISTRO DE VACUNACIÓN ID: " + id + " ---");
        boolean eliminado = registroDAO.eliminar(id);
        if (eliminado) {
            System.out.println("️ Registro ID " + id + " **eliminado correctamente**.");
        } else {
            System.out.println(" No se pudo eliminar el registro ID " + id + ".");
        }
        return eliminado;
    }

    // --- 4. Buscar por ID
    public registro_jornada_vacunacion buscarRegistroPorId(int id) {
        System.out.println("\n--- BUSCANDO REGISTRO DE VACUNACIÓN ID: " + id + " ---");
        registro_jornada_vacunacion registro = registroDAO.obtenerPorId(id);
        if (registro != null) {
            System.out.println(" Encontrado: Registro de Mascota ID " + registro.getMascotaId() + ".");
        } else {
            System.out.println("Registro ID " + id + " **no encontrado**.");
        }
        return registro;
    }

    // --- 5. Obtener Todos
    public List<registro_jornada_vacunacion> obtenerTodosLosRegistros() {
        System.out.println("\n--- LISTANDO TODOS LOS REGISTROS DE VACUNACIÓN ---");
        List<registro_jornada_vacunacion> lista = registroDAO.listar();
        System.out.println(" Se encontraron **" + lista.size() + " registros** de vacunación.");
        return lista;
    }
}
