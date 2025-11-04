/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;

import DAO.FacturasDAO;
import Model.Entities.Facturas;
import Model.Enums.EstadoFacturas;
import java.math.BigDecimal;
import java.util.Map;
import java.util.List;
import java.util.Collections;
/**
 *
 * @author camper
 */
public class facturasController {
   // El DAO de Facturas
    private FacturasDAO facturasDAO;
    
    // --------------------------------------------------------------------------
    // CONSTRUCTOR
    // --------------------------------------------------------------------------
    public facturasController(FacturasDAO facturasDAO) {
        this.facturasDAO = facturasDAO;
    }

    // --------------------------------------------------------------------------
    // 1. Registrar una nueva factura (CREATE)
    // --------------------------------------------------------------------------
    public boolean registrarFactura(Facturas factura) {
        if (factura.getDuenoId() <= 0) {
            System.out.println("⚠️ ID de dueño inválido. No se puede registrar la factura sin dueño.");
            return false;
        }
        if (factura.getTotal() == null || factura.getTotal().compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("⚠️ El total de la factura no puede ser nulo o negativo.");
            return false;
        }
        if (factura.getNumeroFactura() == null || factura.getNumeroFactura().isBlank()) {
            System.out.println("⚠️ El número de factura es obligatorio.");
            return false;
        }
        
        // Asignar estado por defecto si no está definido
        if (factura.getEstado() == null) {
            factura.setEstado(EstadoFacturas.PENDIENTE);
        }

        try {
            // El método DAO debe retornar true/false o manejar la excepción internamente
            boolean exito = facturasDAO.agregar(factura); 
            
            if (exito) {
                System.out.println("✅ Factura registrada correctamente.");
            } else {
                System.out.println("❌ Error: El DAO reportó un fallo al registrar la factura.");
            }
            return exito;
        } catch (Exception e) {
            System.out.println("❌ Error al registrar factura en la base de datos: " + e.getMessage());
            return false;
        }
    }

    // --------------------------------------------------------------------------
    // 2. Listar todas las facturas (READ ALL)
    // --------------------------------------------------------------------------
    public List<Facturas> listarFacturas() {
        return facturasDAO.listar();
    }

    // --------------------------------------------------------------------------
    // 3. Buscar factura por ID (READ ONE)
    // --------------------------------------------------------------------------
    public Facturas obtenerFacturaPorId(int id) {
        if (id <= 0) {
            System.out.println("⚠️ ID inválido.");
            return null;
        }
        return facturasDAO.obtenerPorId(id);
    }

    // --------------------------------------------------------------------------
    // 4. Actualizar factura existente (UPDATE)
    // --------------------------------------------------------------------------
    public boolean actualizarFactura(Facturas factura) {
        if (factura.getId() <= 0) {
            System.out.println("⚠️ La factura debe tener un ID válido para ser actualizada.");
            return false;
        }
        if (factura.getTotal() == null || factura.getTotal().compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("⚠️ El total actualizado no puede ser nulo o negativo.");
            return false;
        }

        boolean exito = facturasDAO.actualizar(factura);
        System.out.println(exito ? "✅ Factura actualizada correctamente." : "❌ No se pudo actualizar la factura (ID no encontrado o error en DAO).");
        return exito;
    }

    // --------------------------------------------------------------------------
    // 5. Eliminar factura (DELETE)
    // --------------------------------------------------------------------------
    public boolean eliminarFactura(int id) {
        if (id <= 0) {
            System.out.println("⚠️ ID de factura inválido.");
            return false;
        }

        // Nota: En un sistema real, antes de eliminar la factura, se deben eliminar sus ítems
        // itemFacturaController.eliminarItemsPorFactura(id); 

        boolean exito = facturasDAO.eliminar(id);
        System.out.println(exito ? "✅ Factura eliminada correctamente." : "❌ No se encontró la factura con ese ID para eliminar.");
        return exito;
    }
    
    // --------------------------------------------------------------------------
    // 6. Reporte de Facturación por Período
    // --------------------------------------------------------------------------
    /**
     * Obtiene el reporte de facturación total por período (ej. mes o año)
     * @return Lista de Mapas con claves 'periodo', 'total_facturado', 'num_facturas'.
     */
    public List<Map<String, Object>> getReporteFacturacionPorPeriodo() {
        System.out.println("Generando reporte de Facturación por Período...");
        
        try {
            // Se asume que el método DAO ya existe y funciona correctamente
            return facturasDAO.getReporteFacturacionPorPeriodo(); 
        } catch (Exception e) {
            System.out.println("❌ Error en el Controller al obtener el reporte de facturación: " + e.getMessage());
            // Devuelve una lista vacía para manejar el error de forma segura en la Vista
            return Collections.emptyList(); // Usa Collections.emptyList() para compatibilidad
        }
    }

    // --------------------------------------------------------------------------
    // 7. Método Auxiliar (Asumiendo que el DAO te da el ID después de registrar)
    // --------------------------------------------------------------------------
    /**
     * Asumiendo que el DAO tiene un método para obtener el último ID generado 
     * tras un registro exitoso, necesario para enlazar ítems en la Vista.
     */
    public int obtenerUltimoIdRegistrado() {
        // Debes implementar este método en tu FacturasDAO
        // return facturasDAO.obtenerUltimoId(); 
        
        // Retornamos 0 temporalmente si no tienes el DAO listo para evitar un fallo.
        return 0; 
    }
}