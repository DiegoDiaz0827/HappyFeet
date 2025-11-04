/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;
import DAO.Item_FacturasDAO;
import Model.Entities.Items_factura;
import Model.Enums.itemsFactura;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
/**
 *
 * @author camper
 */
public class item_facturaController {
    private final Item_FacturasDAO itemFacturasDAO;

    public item_facturaController(Item_FacturasDAO itemFacturasDAO) {
        this.itemFacturasDAO = itemFacturasDAO;
    }

    // --- 1️ Registrar un nuevo ítem de factura 

    public boolean registrarItemFactura(Items_factura item) {
        
        if (item.getFacturaId() <= 0) {
            System.out.println("️ ID de Factura inválido. El ítem debe estar asociado a una factura.");
            return false;
        }
        if (item.getCantidad() <= 0) {
            System.out.println("️ La cantidad debe ser mayor a cero.");
            return false;
        }
        if (item.getPrecioUnitario() == null || item.getPrecioUnitario().compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("️ El precio unitario no puede ser nulo o negativo.");
            return false;
        }

        if (item.getTipoItem() == null) {
            System.out.println("️ El tipo de ítem (PRODUCTO/SERVICIO) es obligatorio.");
            return false;
        }
        
        if (item.getTipoItem() == itemsFactura.PRODUCTO) {
            if (item.getProductoId() == null || item.getProductoId() <= 0) {
                System.out.println("️ Para tipo PRODUCTO, el ID del producto es obligatorio.");
                return false;
            }
            if (item.getServicioId() != null || (item.getServicioDescripcion() != null && !item.getServicioDescripcion().isBlank())) {
                 System.out.println("️ Para tipo PRODUCTO, los campos de servicio deben ser nulos/vacíos.");
                 
            }
        } else if (item.getTipoItem() == itemsFactura.SERVICIO) {
            
            if ((item.getServicioId() == null || item.getServicioId() <= 0) && (item.getServicioDescripcion() == null || item.getServicioDescripcion().isBlank())) {
                System.out.println(" Para tipo SERVICIO, se requiere el ID de servicio o una descripción.");
                return false;
            }
            if (item.getProductoId() != null) {
                System.out.println(" Para tipo SERVICIO, el ID del producto debe ser nulo.");
    
            }
        }
        BigDecimal subtotalCalculado = item.getPrecioUnitario().multiply(new BigDecimal(item.getCantidad()));
        if (item.getSubtotal() == null || !item.getSubtotal().equals(subtotalCalculado)) {
            item.setSubtotal(subtotalCalculado);
            System.out.println("️ Subtotal recalculado y corregido: " + subtotalCalculado);
        }

        try {
            itemFacturasDAO.agregar(item);
            System.out.println(" Ítem de Factura registrado correctamente con ID: " + item.getId());
            return true;
        } catch (Exception e) {
            System.out.println(" Error al registrar ítem de factura: " + e.getMessage());
            return false;
        }
    }

    // 2️ Listar ítems por ID de Factura 
    
    public List<Items_factura> listarItemsPorFactura(int facturaId) throws IllegalArgumentException {
        if (facturaId <= 0) {
            throw new IllegalArgumentException("️ ID de factura inválido para listar ítems.");
            
        }
        
        List<Items_factura> items =itemFacturasDAO.listarPorFactura(facturaId);
        if(items.isEmpty()){
        throw new IllegalArgumentException("no hay items de la factura");
        }
        
        return itemFacturasDAO.listarPorFactura(facturaId);
    }

    // 3️ Buscar ítem de factura por ID 

    public Items_factura obtenerItemPorId(int id) {
        if (id <= 0) {
            System.out.println("️ ID de ítem inválido para la búsqueda.");
            return null;
        }
        Items_factura item = itemFacturasDAO.obtenerPorId(id);
        if (item == null) {
            System.out.println("️ Ítem con ID " + id + " no encontrado.");
        }
        return item;
    }

    // 4️ Actualizar ítem de factura existente 

    public boolean actualizarItemFactura(Items_factura item) {
        if (item.getId() <= 0) {
            System.out.println(" El ítem debe tener un ID válido para ser actualizado.");
            return false;
        }
        
        if (item.getCantidad() <= 0) {
            System.out.println("⚠️ La cantidad debe ser mayor a cero.");
            return false;
        }
        
        BigDecimal subtotalCalculado = item.getPrecioUnitario().multiply(new BigDecimal(item.getCantidad()));
        if (item.getSubtotal() == null || !item.getSubtotal().equals(subtotalCalculado)) {
            item.setSubtotal(subtotalCalculado);
            System.out.println("ℹ️ Subtotal recalculado y corregido para la actualización: " + subtotalCalculado);
        }

        boolean exito = itemFacturasDAO.actualizar(item);
        System.out.println(exito ? " Ítem de Factura actualizado correctamente." : " No se pudo actualizar el ítem (ID no encontrado o error en DAO).");
        return exito;
    }

    // 5️ Eliminar ítem de factura 

    public boolean eliminarItemFactura(int id) {
        if (id <= 0) {
            System.out.println("️ ID de ítem de factura inválido.");
            return false;
        }

        boolean exito = itemFacturasDAO.eliminar(id);
        System.out.println(exito ? "️ Ítem de Factura eliminado correctamente." : " No se encontró el ítem con ese ID para eliminar.");
        return exito;
    }
    
    //  6️ REPORTE: Servicios Más Solicitados
    
    public List<Map<String, Object>> getReporteServiciosMasSolicitados() {
        
        System.out.println("Generando reporte de Servicios Más Solicitados...");
        try {
            return itemFacturasDAO.getReporteServiciosMasSolicitados();
        } catch (Exception e) {
            System.out.println("Error en el Controller al obtener el reporte: " + e.getMessage());
            return List.of(); 
        }
    }
}
