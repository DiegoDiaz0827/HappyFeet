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
    // 1. Registrar una nueva factura 

    public boolean registrarFactura(Facturas factura) {
        if (factura.getDuenoId() <= 0) {
            System.out.println("️ ID de dueño inválido. No se puede registrar la factura sin dueño.");
            return false;
        }
        if (factura.getTotal() == null || factura.getTotal().compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("️ El total de la factura no puede ser nulo o negativo.");
            return false;
        }
        if (factura.getNumeroFactura() == null || factura.getNumeroFactura().isBlank()) {
            System.out.println("️ El número de factura es obligatorio.");
            return false;
        }

        if (factura.getEstado() == null) {
            factura.setEstado(EstadoFacturas.PENDIENTE);
        }

        try {
            boolean exito = facturasDAO.agregar(factura); 
            
            if (exito) {
                System.out.println(" Factura registrada correctamente.");
            } else {
                System.out.println(" Error: El DAO reportó un fallo al registrar la factura.");
            }
            return exito;
        } catch (Exception e) {
            System.out.println(" Error al registrar factura en la base de datos: " + e.getMessage());
            return false;
        }
    }
    // 2. Listar todas las facturas

    public List<Facturas> listarFacturas() {
        return facturasDAO.listar();
    }

    // 3. Buscar factura por ID 
    public Facturas obtenerFacturaPorId(int id) {
        if (id <= 0) {
            System.out.println("⚠️ ID inválido.");
            return null;
        }
        return facturasDAO.obtenerPorId(id);
    }


    // 4. Actualizar factura existente 

    public boolean actualizarFactura(Facturas factura) {
        if (factura.getId() <= 0) {
            System.out.println("️ La factura debe tener un ID válido para ser actualizada.");
            return false;
        }
        if (factura.getTotal() == null || factura.getTotal().compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("️ El total actualizado no puede ser nulo o negativo.");
            return false;
        }

        boolean exito = facturasDAO.actualizar(factura);
        System.out.println(exito ? " Factura actualizada correctamente." : " No se pudo actualizar la factura (ID no encontrado o error en DAO).");
        return exito;
    }


    // 5. Eliminar factura 
    public boolean eliminarFactura(int id) {
        if (id <= 0) {
            System.out.println("️ ID de factura inválido.");
            return false;
        }

        boolean exito = facturasDAO.eliminar(id);
        System.out.println(exito ? "Factura eliminada correctamente." : " No se encontró la factura con ese ID para eliminar.");
        return exito;
    }
    
 
    // 6. Reporte de Facturación por Período

   
    public List<Map<String, Object>> getReporteFacturacionPorPeriodo() {
        System.out.println("Generando reporte de Facturación por Período...");
        
        try {
            return facturasDAO.getReporteFacturacionPorPeriodo(); 
        } catch (Exception e) {
            System.out.println("❌ Error en el Controller al obtener el reporte de facturación: " + e.getMessage());
   
            return Collections.emptyList(); 
        }
    }


    // 7. Método Auxiliar 


    public int obtenerUltimoIdRegistrado() {
        return 0; 
    }
}
