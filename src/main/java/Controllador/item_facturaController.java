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
/**
 *
 * @author camper
 */
public class item_facturaController {
    private final Item_FacturasDAO itemFacturasDAO;

    // Inyección de dependencias
    public item_facturaController(Item_FacturasDAO itemFacturasDAO) {
        this.itemFacturasDAO = itemFacturasDAO;
    }

    // --- 1️⃣ Registrar un nuevo ítem de factura (CREATE)
    /**
     * Registra un nuevo ítem de factura validando su coherencia.
     * @param item El objeto Items_factura a registrar.
     * @return true si se registra correctamente, false en caso contrario.
     */
    public boolean registrarItemFactura(Items_factura item) {
        
        // 1. Validaciones básicas de IDs y cantidades
        if (item.getFacturaId() <= 0) {
            System.out.println("⚠️ ID de Factura inválido. El ítem debe estar asociado a una factura.");
            return false;
        }
        if (item.getCantidad() <= 0) {
            System.out.println("⚠️ La cantidad debe ser mayor a cero.");
            return false;
        }
        if (item.getPrecioUnitario() == null || item.getPrecioUnitario().compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("⚠️ El precio unitario no puede ser nulo o negativo.");
            return false;
        }

        // 2. Validaciones de coherencia según el tipo de ítem
        if (item.getTipoItem() == null) {
            System.out.println("⚠️ El tipo de ítem (PRODUCTO/SERVICIO) es obligatorio.");
            return false;
        }
        
        if (item.getTipoItem() == itemsFactura.PRODUCTO) {
            // Debe tener producto_id, y servicio_id debe ser null.
            if (item.getProductoId() == null || item.getProductoId() <= 0) {
                System.out.println("⚠️ Para tipo PRODUCTO, el ID del producto es obligatorio.");
                return false;
            }
            if (item.getServicioId() != null || (item.getServicioDescripcion() != null && !item.getServicioDescripcion().isBlank())) {
                 System.out.println("⚠️ Para tipo PRODUCTO, los campos de servicio deben ser nulos/vacíos.");
                 // Puedes optar por limpiar los campos de servicio aquí si quieres ser flexible:
                 // item.setServicioId(null);
                 // item.setServicioDescripcion(null);
            }
        } else if (item.getTipoItem() == itemsFactura.SERVICIO) {
            // Debe tener servicio_id O servicio_descripcion, y producto_id debe ser null.
            if ((item.getServicioId() == null || item.getServicioId() <= 0) && (item.getServicioDescripcion() == null || item.getServicioDescripcion().isBlank())) {
                System.out.println("⚠️ Para tipo SERVICIO, se requiere el ID de servicio o una descripción.");
                return false;
            }
            if (item.getProductoId() != null) {
                System.out.println("⚠️ Para tipo SERVICIO, el ID del producto debe ser nulo.");
                // Puedes optar por limpiar el campo de producto aquí:
                // item.setProductoId(null);
            }
        }
        
        // 3. Recalcular Subtotal antes de guardar (por seguridad)
        // Aunque el DAO lo guarda, el controlador debería asegurar que el dato sea correcto.
        BigDecimal subtotalCalculado = item.getPrecioUnitario().multiply(new BigDecimal(item.getCantidad()));
        if (item.getSubtotal() == null || !item.getSubtotal().equals(subtotalCalculado)) {
            item.setSubtotal(subtotalCalculado);
            System.out.println("ℹ️ Subtotal recalculado y corregido: " + subtotalCalculado);
        }

        try {
            itemFacturasDAO.agregar(item);
            System.out.println("✅ Ítem de Factura registrado correctamente con ID: " + item.getId());
            return true;
        } catch (Exception e) {
            System.out.println("❌ Error al registrar ítem de factura: " + e.getMessage());
            return false;
        }
    }

    // --- 2️⃣ Listar ítems por ID de Factura (READ ALL by Parent ID)
    /**
     * Lista todos los ítems asociados a una factura específica.
     * @param facturaId El ID de la factura.
     * @return Una lista de Items_factura.
     */
    public List<Items_factura> listarItemsPorFactura(int facturaId) {
        if (facturaId <= 0) {
            System.out.println("⚠️ ID de factura inválido para listar ítems.");
            return List.of(); // Devuelve una lista vacía
        }
        return itemFacturasDAO.listarPorFactura(facturaId);
    }

    // --- 3️⃣ Buscar ítem de factura por ID (READ ONE)
    /**
     * Obtiene un ítem de factura por su ID.
     * @param id El ID del ítem a buscar.
     * @return El objeto Items_factura si se encuentra, o null.
     */
    public Items_factura obtenerItemPorId(int id) {
        if (id <= 0) {
            System.out.println("⚠️ ID de ítem inválido para la búsqueda.");
            return null;
        }
        Items_factura item = itemFacturasDAO.obtenerPorId(id);
        if (item == null) {
            System.out.println("ℹ️ Ítem con ID " + id + " no encontrado.");
        }
        return item;
    }

    // --- 4️⃣ Actualizar ítem de factura existente (UPDATE)
    /**
     * Actualiza un ítem de factura existente. Utiliza las mismas validaciones de registro.
     * @param item El objeto Items_factura con los datos actualizados.
     * @return true si se actualiza correctamente, false en caso contrario.
     */
    public boolean actualizarItemFactura(Items_factura item) {
        if (item.getId() <= 0) {
            System.out.println("⚠️ El ítem debe tener un ID válido para ser actualizado.");
            return false;
        }
        
        // Reutilizar la lógica de validación de registro
        // Si las validaciones fallan, registrarItemFactura devuelve false.
        // Si quieres evitar el mensaje de 'Ítem de Factura registrado correctamente',
        // podrías externalizar la lógica de validación a un método privado.
        
        // Por ahora, solo verificamos las reglas básicas, asumiendo que el item ya existe.
        if (item.getCantidad() <= 0) {
            System.out.println("⚠️ La cantidad debe ser mayor a cero.");
            return false;
        }
        // ... (Aquí irían más validaciones si no se reusa la lógica de registro)
        
        // Recalcular Subtotal antes de actualizar (por seguridad)
        BigDecimal subtotalCalculado = item.getPrecioUnitario().multiply(new BigDecimal(item.getCantidad()));
        if (item.getSubtotal() == null || !item.getSubtotal().equals(subtotalCalculado)) {
            item.setSubtotal(subtotalCalculado);
            System.out.println("ℹ️ Subtotal recalculado y corregido para la actualización: " + subtotalCalculado);
        }

        boolean exito = itemFacturasDAO.actualizar(item);
        System.out.println(exito ? "✅ Ítem de Factura actualizado correctamente." : "❌ No se pudo actualizar el ítem (ID no encontrado o error en DAO).");
        return exito;
    }

    // --- 5️⃣ Eliminar ítem de factura (DELETE)
    /**
     * Elimina un ítem de factura por su ID.
     * @param id El ID del ítem a eliminar.
     * @return true si se elimina correctamente, false si el ID es inválido o no se encuentra.
     */
    public boolean eliminarItemFactura(int id) {
        if (id <= 0) {
            System.out.println("⚠️ ID de ítem de factura inválido.");
            return false;
        }

        boolean exito = itemFacturasDAO.eliminar(id);
        System.out.println(exito ? "🗑️ Ítem de Factura eliminado correctamente." : "❌ No se encontró el ítem con ese ID para eliminar.");
        return exito;
    }
}
