/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vistas;

import Controllador.facturasController;
import Controllador.item_facturaController;
import Controllador.serviciosController;
import DAO.FacturasDAO; 
import DAO.Item_FacturasDAO;
import DAO.ServiciosDAO;
import Model.Entities.Facturas;
import Model.Entities.Items_factura;
import Model.Entities.Servicios;
import Model.Enums.EstadoFacturas;
import Model.Enums.MetodoPago;
import Model.Enums.itemsFactura;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map; 
import java.util.Scanner;

/**
 *
 * @author camper
 */
public class Modulo4 {
  private static facturasController facturasController;
    private static serviciosController serviciosController;
    private static item_facturaController itemFacturaController;
    
    private static Scanner scanner = new Scanner(System.in); 
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void main(String[] args) {
        FacturasDAO facturasDAO = new FacturasDAO(); 
        ServiciosDAO serviciosDAO = new ServiciosDAO(); 
        Item_FacturasDAO itemFacturasDAO = new Item_FacturasDAO();

        // Inicialización de Controllers
        facturasController = new facturasController(facturasDAO);
        serviciosController = new serviciosController(serviciosDAO);
        itemFacturaController = new item_facturaController(itemFacturasDAO);
        
        while (true) {
            mostrarMenuPrincipal();
            int opcion = leerEntero("Seleccione una opción: ");
            
            switch (opcion) {
                case 1:
                    menuServicios();
                    break;
                case 2:
                    menuFacturas();
                    break;
                case 3:
                    menuReportes();
                    break;
                case 0:
                    System.out.println("Saliendo del Módulo 4...");
                    return;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
                    break;
            }
        }
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n=== Módulo de Facturación y Servicios ===");
        System.out.println("1. Gestión de Servicios (CRUD)");
        System.out.println("2. Gestión de Facturas (CRUD y Reporte Ítems)");
        System.out.println("3. Reportes Gerenciales"); 
        System.out.println("0. Volver al menú principal (Salir del Módulo 4)");
    }
    
    // 1. GESTIÓN DE SERVICIOS 
 

    private static void menuServicios() { 
        while (true) {
            System.out.println("\n--- Menú de Gestión de Servicios ---");
            System.out.println("1. Registrar nuevo servicio");
            System.out.println("2. Listar todos los servicios");
            System.out.println("3. Buscar servicio por ID");
            System.out.println("4. Actualizar servicio");
            System.out.println("5. Inactivar servicio (Eliminación lógica)");
            System.out.println("0. Volver al menú principal");
            
            int opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1: registrarServicio(); break;
                case 2: listarServicios(); break;
                case 3: obtenerServicio(); break;
                case 4: actualizarServicio(); break;
                case 5: eliminarServicio(); break; // Se implementa como inactivar
                case 0: return;
                default: System.out.println("Opción inválida."); break;
            }
        }
    }
    
    private static void registrarServicio() {
        System.out.println("\n--- Registrar Nuevo Servicio ---");
        String nombre = leerTexto("Nombre del Servicio: ");
        String descripcion = leerTextoOpcional("Descripción (opcional): ");
        String categoria = leerTextoOpcional("Categoría (opcional): ");
        BigDecimal precioBase = leerBigDecimal("Precio Base: ");
        int duracion = leerEntero("Duración estimada en minutos: ");
        
        Servicios s = new Servicios(nombre, descripcion, categoria, precioBase, duracion);
        
        if (serviciosController.registrarServicio(s)) {
            System.out.println(" Servicio '" + nombre + "' registrado exitosamente (ID: " + s.getId() + ").");
        } else {
            System.out.println(" Error al registrar el servicio.");
        }
    }
    
    private static void listarServicios() { 
        System.out.println("\n--- Listado de Servicios ---");
        
        try {
            List<Servicios> servicios = serviciosController.listarServicios();
            
            if (servicios == null || servicios.isEmpty()) {
                System.out.println("No hay servicios registrados.");
                return;
            }
            
            System.out.println("==========================================================================================================");
            System.out.printf("| %-4s | %-25s | %-12s | %-12s | %-15s | %-6s |%n", 
                              "ID", "Nombre", "Precio Base", "Duración (min)", "Categoría", "Activo");
            System.out.println("==========================================================================================================");
            
            for (Servicios s : servicios) {
                String activoStr = s.isActivo() ? "SI" : "NO";
                String categoriaStr = s.getCategoria() != null ? s.getCategoria() : "N/A";
                // Limitar nombre
                String nombreCorto = s.getNombre().substring(0, Math.min(s.getNombre().length(), 25)); 
                
                System.out.printf("| %-4d | %-25s | $%-11.2f | %-14d | %-15s | %-6s |%n", 
                                  s.getId(), 
                                  nombreCorto, 
                                  s.getPrecioBase(), 
                                  s.getDuracionEstimadaMinutos(),
                                  categoriaStr,
                                  activoStr);
            }
            System.out.println("==========================================================================================================");
            
        } catch (Exception e) {
            System.out.println(" Error al obtener el listado de servicios: " + e.getMessage());
        }
    }
    
    private static void obtenerServicio() {
        System.out.println("\n--- Buscar Servicio por ID ---");
        int id = leerEntero("Ingrese ID del servicio: ");
        
        Servicios s = serviciosController.obtenerServicioPorId(id);
        
        if (s == null) {
            System.out.println("Servicio con ID " + id + " no encontrado.");
        } else {
            System.out.println("\n--- Detalles del Servicio ID: " + s.getId() + " ---");
            System.out.println("Nombre: " + s.getNombre());
            System.out.println("Descripción: " + (s.getDescripcion() != null ? s.getDescripcion() : "N/A"));
            System.out.println("Categoría: " + (s.getCategoria() != null ? s.getCategoria() : "N/A"));
            System.out.println("Precio Base: $" + s.getPrecioBase());
            System.out.println("Duración Estimada: " + s.getDuracionEstimadaMinutos() + " minutos");
            System.out.println("Estado Activo: " + (s.isActivo() ? "Sí" : "No"));
        }
    }
    
    private static void actualizarServicio() {
        System.out.println("\n--- Actualizar Servicio ---");
        int id = leerEntero("Ingrese ID del servicio a actualizar: ");
        
        Servicios s = serviciosController.obtenerServicioPorId(id);
        
        if (s == null) {
            System.out.println("Servicio con ID " + id + " no encontrado.");
            return;
        }
        
        System.out.println("Editando Servicio: " + s.getNombre());
        
        String nombre = leerTexto("Nuevo Nombre (Actual: " + s.getNombre() + "): ");
        String descripcion = leerTextoOpcional("Nueva Descripción (Actual: " + (s.getDescripcion() != null ? s.getDescripcion() : "N/A") + "): ");
        String categoria = leerTextoOpcional("Nueva Categoría (Actual: " + (s.getCategoria() != null ? s.getCategoria() : "N/A") + "): ");
        BigDecimal precioBase = leerBigDecimal("Nuevo Precio Base (Actual: " + s.getPrecioBase() + "): ");
        int duracion = leerEntero("Nueva Duración en minutos (Actual: " + s.getDuracionEstimadaMinutos() + "): ");
        String activoStr = leerTexto("¿Está activo? (true/false) (Actual: " + s.isActivo() + "): ");

        // Asignación de nuevos datos 
        if (!nombre.isEmpty()) s.setNombre(nombre);
        if (!descripcion.isEmpty()) s.setDescripcion(descripcion);
        if (!categoria.isEmpty()) s.setCategoria(categoria);
        if (precioBase.compareTo(BigDecimal.ZERO) >= 0) s.setPrecioBase(precioBase);
        if (duracion > 0) s.setDuracionEstimadaMinutos(duracion);
        if (!activoStr.isEmpty()) s.setActivo(Boolean.parseBoolean(activoStr));

        if (serviciosController.actualizarServicio(s)) {
            System.out.println(" Servicio ID " + id + " actualizado exitosamente.");
        } else {
            System.out.println(" Error al actualizar el servicio.");
        }
    }
    
    private static void eliminarServicio() {
        System.out.println("\n--- Inactivar Servicio (Borrado Lógico) ---");
        int id = leerEntero("Ingrese ID del servicio a inactivar: ");
        
        if (serviciosController.eliminarServicio(id)) { 
            System.out.println(" Servicio ID " + id + " ha sido **inactivado** exitosamente.");
        } else {
            System.out.println(" Error al inactivar el servicio o ID no encontrado.");
        }
    }


    // 2. GESTIÓN DE FACTURAS


    private static void menuFacturas() {
        while (true) {
            System.out.println("\n--- Menú de Facturas ---");
            System.out.println("1. Registrar nueva factura");
            System.out.println("2. Listar todas las facturas");
            System.out.println("3. Buscar factura por ID");
            System.out.println("4. Listar ítems de una factura");
            System.out.println("5. Actualizar factura (Totales/Estado/Pago)");
            System.out.println("6. Eliminar factura (Borrado físico)");
            System.out.println("7. **Generar Factura en Texto Plano**");
            System.out.println("0. Volver al menú principal");
            
            int opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1: registrarFactura(); break;
                case 2: listarFacturas(); break;
                case 3: obtenerFactura(); break;
                case 4: listarItemsFactura(); break;
                case 5: actualizarFactura(); break;
                case 6: eliminarFactura(); break;
                case 7: generarFacturaEnTextoPlano(); break;
                case 0: return;
                default: System.out.println("Opción inválida."); break;
            }
        }
    }
    
    private static void registrarFactura() {
        System.out.println("\n--- Registrar Nueva Factura ---");
        int duenoId = leerEntero("ID del Dueño asociado: ");
        String numeroFactura = leerTexto("Número de Factura: ");
        
        System.out.println("NOTA: Ingrese totales iniciales. Se recomienda '0' y usar Actualizar Totales tras agregar ítems.");
        BigDecimal subtotal = leerBigDecimal("Subtotal: ");
        BigDecimal impuesto = leerBigDecimal("Impuesto (0 si no aplica): ");
        BigDecimal descuento = leerBigDecimal("Descuento (0 si no aplica): ");
        
        BigDecimal total = subtotal.add(impuesto).subtract(descuento);
        System.out.println("Total calculado: $" + total);
        
        String metodoPagoStr = leerTextoOpcional("Método de Pago (Efectivo, Tarjeta, Transferencia, Mixto) [Opcional]: ");
        MetodoPago metodoPago = null;
        try {
             if (!metodoPagoStr.isEmpty()) metodoPago = MetodoPago.valueOf(metodoPagoStr.toUpperCase());
        } catch (IllegalArgumentException e) {
             System.out.println(" Método de pago inválido. Se dejará nulo.");
        }
        
        String observaciones = leerTextoOpcional("Observaciones (opcional): ");

        Facturas f = new Facturas(duenoId, numeroFactura, subtotal, impuesto, descuento, total, metodoPago, EstadoFacturas.PENDIENTE, observaciones);
        f.setFechaEmision(LocalDateTime.now()); 
        
        if (facturasController.registrarFactura(f)) {
            System.out.println(" Factura No. " + f.getNumeroFactura() + " registrada con ID: " + f.getId());
            System.out.println("¿Desea agregar ítems a esta factura ahora? (s/n)");
            if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
                registrarItemsParaFactura(f.getId());
            }
        }
    }
    private static void registrarItemsParaFactura(int facturaId) {
        
        while (true) {
            System.out.println("\n--- Agregar Ítem a Factura ID: " + facturaId + " ---");
            System.out.println("Tipo de Ítem (PRODUCTO/SERVICIO): ");
            String tipoStr = leerTexto("Tipo: ");
            itemsFactura tipo;
            try {
                tipo = itemsFactura.valueOf(tipoStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Tipo de ítem inválido. Debe ser PRODUCTO o SERVICIO.");
                continue;
            }
            
            Integer productoId = null;
            Integer servicioId = null;
            String servicioDesc = null;
            
            if (tipo == itemsFactura.PRODUCTO) {
                productoId = leerEntero("ID del Producto: ");
            } else if (tipo == itemsFactura.SERVICIO) {
                System.out.println("¿Es un servicio registrado (1) o una descripción libre (2)?");
                int op = leerEntero("(1/2): ");
                if (op == 1) {
                    servicioId = leerEntero("ID del Servicio registrado: ");
                } else if (op == 2) {
                    servicioDesc = leerTexto("Descripción del servicio libre: ");
                } else {
                    System.out.println("Opción inválida. Ítem no agregado.");
                    continue;
                }
            }
            
            int cantidad = leerEntero("Cantidad: ");
            BigDecimal precioUnitario = leerBigDecimal("Precio Unitario: ");
            
            BigDecimal subtotalCalculado = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
            
            Items_factura item = new Items_factura(
                0, 
                facturaId, 
                tipo, 
                productoId, 
                servicioId, 
                servicioDesc, 
                cantidad, 
                precioUnitario, 
                subtotalCalculado
            );

            
            if (itemFacturaController.registrarItemFactura(item)) {
                System.out.println(" Ítem agregado a la factura.");
                System.out.println("️ RECUERDE: Actualizar los totales de la factura principal (Opción 5 en Menú Facturas).");
            } else {
                System.out.println(" Error al registrar el ítem. Verifique IDs y datos.");
            }
            
            System.out.println("¿Desea agregar otro ítem? (s/n)");
            if (!scanner.nextLine().trim().equalsIgnoreCase("s")) break;
        }
    }
    
    private static void listarFacturas() {
        System.out.println("\n--- Listado de Facturas ---");
        List<Facturas> facturas = facturasController.listarFacturas();
        
        if (facturas == null || facturas.isEmpty()) {
            System.out.println("No hay facturas registradas.");
            return;
        }

        System.out.println("==============================================================================================");
        System.out.printf("| %-4s | %-10s | %-18s | %-10s | %-10s | %-10s | %-10s |%n", 
                          "ID", "No. Factura", "Fecha Emisión", "Subtotal", "Impuesto", "Total", "Estado");
        System.out.println("==============================================================================================");

        for (Facturas f : facturas) {
            System.out.printf("| %-4d | %-10s | %-18s | $%-9.2f | $%-9.2f | $%-9.2f | %-10s |%n", 
                              f.getId(), 
                              f.getNumeroFactura(), 
                              f.getFechaEmision().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), 
                              f.getSubtotal(), 
                              f.getImpuesto(), 
                              f.getTotal(),
                              f.getEstado() != null ? f.getEstado().name() : "N/A");
        }
        System.out.println("==============================================================================================");
    }
    
    private static void obtenerFactura() {
        int id = leerEntero("Ingrese ID de la factura a buscar: ");
        Facturas f = facturasController.obtenerFacturaPorId(id);
        
        if (f == null) {
            System.out.println("Factura con ID " + id + " no encontrada.");
        } else {
            System.out.println("\n--- Detalles de Factura ID: " + f.getId() + " ---");
            System.out.println("Número: " + f.getNumeroFactura());
            System.out.println("Dueño ID: " + f.getDuenoId());
            System.out.println("Fecha: " + f.getFechaEmision().format(FORMATTER));
            System.out.println("Subtotal: $" + f.getSubtotal());
            System.out.println("Impuesto: $" + f.getImpuesto());
            System.out.println("Descuento: $" + f.getDescuento());
            System.out.println("TOTAL: $" + f.getTotal());
            System.out.println("Método Pago: " + (f.getMetodoPago() != null ? f.getMetodoPago().name() : "N/A"));
            System.out.println("Estado: " + (f.getEstado() != null ? f.getEstado().name() : "N/A"));
            System.out.println("Observaciones: " + (f.getObservaciones() != null ? f.getObservaciones() : ""));
        }
    }
    
    private static void listarItemsFactura() {
        int id = leerEntero("Ingrese ID de la factura para listar sus ítems: ");
        Facturas f = facturasController.obtenerFacturaPorId(id);
        
        if (f == null) {
            System.out.println("Factura con ID " + id + " no encontrada.");
            return;
        }

        System.out.println("\n--- Ítems de Factura No. " + f.getNumeroFactura() + " (ID: " + id + ") ---");
        List<Items_factura> items = itemFacturaController.listarItemsPorFactura(id);

        if (items.isEmpty()) {
            System.out.println("Esta factura no tiene ítems registrados.");
            return;
        }

        System.out.println("------------------------------------------------------------------------------------------");
        System.out.printf("| %-4s | %-10s | %-5s | %-30s | %-10s | %-10s |%n", 
                          "ID", "Tipo", "Cant", "Descripción/ID", "P. Unitario", "Subtotal");
        System.out.println("------------------------------------------------------------------------------------------");

        for (Items_factura item : items) {
            String descripcionItem;
            String tipoStr = item.getTipoItem() != null ? item.getTipoItem().name() : "N/A";
            
            if (item.getTipoItem() == itemsFactura.PRODUCTO) {
                descripcionItem = "P: [ID " + item.getProductoId() + "] (NOMBRE PROD PENDIENTE)"; 
            } else if (item.getTipoItem() == itemsFactura.SERVICIO) {
                if (item.getServicioId() != null) {
                    descripcionItem = "S: [ID " + item.getServicioId() + "] (NOMBRE SERV PENDIENTE)";
                } else {
                    descripcionItem = "S: " + (item.getServicioDescripcion() != null ? item.getServicioDescripcion() : "Servicio Libre"); 
                }
            } else {
                descripcionItem = "Item Desconocido";
            }
            
            String descCorta = descripcionItem.substring(0, Math.min(descripcionItem.length(), 30));
            
            System.out.printf("| %-4d | %-10s | %-5d | %-30s | $%-9.2f | $%-9.2f |%n",
                              item.getId(),
                              tipoStr,
                              item.getCantidad(),
                              descCorta,
                              item.getPrecioUnitario(),
                              item.getSubtotal());
        }
        System.out.println("------------------------------------------------------------------------------------------");
    }
    
    private static void actualizarFactura() {
        System.out.println("\n--- Actualizar Factura ---");
        int id = leerEntero("Ingrese ID de la factura a actualizar: ");
        
        Facturas f = facturasController.obtenerFacturaPorId(id);
        
        if (f == null) {
            System.out.println("Factura con ID " + id + " no encontrada.");
            return;
        }
        
        System.out.println("Editando Factura No.: " + f.getNumeroFactura());
        
        System.out.println("\nOpciones de Actualización:");
        System.out.println("1. Recalcular Totales (Suma ítems)");
        System.out.println("2. Cambiar Estado y Método de Pago");
        System.out.println("3. Editar Observaciones");
        System.out.println("0. Cancelar");
        
        int opcion = leerEntero("Seleccione una opción: ");
        
        switch(opcion) {
            case 1:
                recalcularTotalesFactura(f);
                break;
            case 2:
                cambiarEstadoMetodoPago(f);
                break;
            case 3:
                editarObservacionesFactura(f);
                break;
            case 0:
                System.out.println("Actualización cancelada.");
                return;
            default:
                System.out.println("Opción inválida.");
                return;
        }
       
        if (facturasController.actualizarFactura(f)) {
            System.out.println(" Factura ID " + id + " actualizada exitosamente.");
        } else {
            System.out.println(" Error al actualizar la factura en la base de datos.");
        }
    }
    
    private static void recalcularTotalesFactura(Facturas f) {
        List<Items_factura> items = itemFacturaController.listarItemsPorFactura(f.getId());
        BigDecimal nuevoSubtotal = BigDecimal.ZERO;

        for (Items_factura item : items) {
            nuevoSubtotal = nuevoSubtotal.add(item.getSubtotal());
        }
        
        System.out.println("Subtotal actual en BD: $" + f.getSubtotal());
        System.out.println("Subtotal calculado de ítems: $" + nuevoSubtotal);
        
        f.setSubtotal(nuevoSubtotal);
        
        // Permite ajustar impuesto y descuento
        BigDecimal nuevoImpuesto = leerBigDecimal("Nuevo Impuesto (Actual: " + f.getImpuesto() + "): ");
        BigDecimal nuevoDescuento = leerBigDecimal("Nuevo Descuento (Actual: " + f.getDescuento() + "): ");
        
        f.setImpuesto(nuevoImpuesto);
        f.setDescuento(nuevoDescuento);
        
        BigDecimal nuevoTotal = nuevoSubtotal.add(nuevoImpuesto).subtract(nuevoDescuento);
        f.setTotal(nuevoTotal);
        
        System.out.println("Totales de Factura actualizados:");
        System.out.println("-> Nuevo Subtotal: $" + nuevoSubtotal);
        System.out.println("-> Nuevo Impuesto: $" + nuevoImpuesto);
        System.out.println("-> Nuevo Descuento: $" + nuevoDescuento);
        System.out.println("-> NUEVO TOTAL: $" + nuevoTotal);
    }
    
    private static void cambiarEstadoMetodoPago(Facturas f) {
        // Cambiar Estado
        System.out.print("Nuevo Estado (PENDIENTE/PAGADA/ANULADA) (Actual: " + f.getEstado() + "): ");
        String estadoStr = scanner.nextLine().trim().toUpperCase();
        try {
            if (!estadoStr.isEmpty()) f.setEstado(EstadoFacturas.valueOf(estadoStr));
        } catch (IllegalArgumentException e) {
            System.out.println("⚠️ Estado inválido. Se mantiene el actual.");
        }
        
        // Cambiar Método de Pago
        System.out.print("Nuevo Método Pago (Efectivo/Tarjeta/Transferencia/Mixto) (Actual: " + f.getMetodoPago() + "): ");
        String metodoStr = scanner.nextLine().trim().toUpperCase();
        try {
            if (!metodoStr.isEmpty()) f.setMetodoPago(MetodoPago.valueOf(metodoStr));
        } catch (IllegalArgumentException e) {
            System.out.println("⚠️ Método de pago inválido. Se mantiene el actual.");
        }
    }
    
    private static void editarObservacionesFactura(Facturas f) {
        String observaciones = leerTextoOpcional("Nuevas Observaciones (Actual: " + (f.getObservaciones() != null ? f.getObservaciones() : "") + "): ");
        f.setObservaciones(observaciones);
    }

    private static void eliminarFactura() {
        System.out.println("\n--- Eliminar Factura ---");
        int id = leerEntero("Ingrese ID de la factura a eliminar (Esta acción es PERMANENTE y eliminará ÍTEMS asociados): ");
        System.out.println("⚠️ ADVERTENCIA: Esta es una eliminación física. Confirma para continuar (si/no)");
        String confirmacion = scanner.nextLine().trim();
        
        if (confirmacion.equalsIgnoreCase("si")) {
            if (facturasController.eliminarFactura(id)) {
                System.out.println("✅ Factura ID " + id + " eliminada exitosamente.");
            } else {
                System.out.println("❌ Error al eliminar la factura o ID no encontrado.");
            }
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }

    // Generar Factura en Texto Plano 
    private static void generarFacturaEnTextoPlano() {
        int id = leerEntero("Ingrese ID de la factura a generar en texto plano: ");
        Facturas f = facturasController.obtenerFacturaPorId(id);
        
        if (f == null) {
            System.out.println("Factura con ID " + id + " no encontrada.");
            return;
        }
        
        List<Items_factura> items = itemFacturaController.listarItemsPorFactura(id);
        
        System.out.println("\n=======================================================");
        System.out.println("       FACTURA DE VENTA - VETERINARIA VITAL          ");
        System.out.println("=======================================================");
        
        // --- 1. Datos de la Clínica y Cliente ---
        System.out.println("Clínica: Veterinaria Vital");
        System.out.println("Dirección: Calle Ficticia #123");
        System.out.println("Teléfono: (555) 123-4567");
        System.out.println("-------------------------------------------------------");
        
        String nombreDueno = "Cliente ID: " + f.getDuenoId() + " (PENDIENTE OBTENER NOMBRE)"; // Recordatorio de mejora
        
        System.out.println("Cliente: " + nombreDueno);
        System.out.println("Factura No.: " + f.getNumeroFactura());
        System.out.println("Fecha Emisión: " + f.getFechaEmision().format(FORMATTER));
        System.out.println("-------------------------------------------------------");
        
        //  2.  Ítems 
        System.out.printf("| %-4s | %-30s | %-10s | %-10s |%n", "Cant", "Descripción", "P. Unitario", "Subtotal");
        System.out.println("|------|--------------------------------|------------|------------|");
        
        if (items.isEmpty()) {
            System.out.println("| ** NO HAY ÍTEMS REGISTRADOS PARA ESTA FACTURA **");
        } else {
            for (Items_factura item : items) {
                String descripcionItem;
                
                if (item.getTipoItem() == itemsFactura.PRODUCTO) {
                    descripcionItem = "P: [ID " + item.getProductoId() + "] (NOMBRE PROD PENDIENTE)"; 
                } else if (item.getTipoItem() == itemsFactura.SERVICIO) {
                    if (item.getServicioId() != null) {
                        descripcionItem = "S: [ID " + item.getServicioId() + "] (NOMBRE SERV PENDIENTE)";
                    } else {
                        descripcionItem = "S: " + item.getServicioDescripcion(); 
                    }
                } else {
                    descripcionItem = "Item Desconocido";
                }
                
                String descCorta = descripcionItem.length() > 30 ? descripcionItem.substring(0, 27) + "..." : descripcionItem;
                
                System.out.printf("| %-4d | %-30s | $%-9.2f | $%-9.2f |%n", 
                                  item.getCantidad(), 
                                  descCorta, 
                                  item.getPrecioUnitario(), 
                                  item.getSubtotal());
            }
        }
        System.out.println("-------------------------------------------------------");
        
        //  Resumen y Totales 
        System.out.printf("SUBTOTAL: %54s $%.2f%n", "", f.getSubtotal());
        System.out.printf("IMPUESTO: %54s $%.2f%n", "", f.getImpuesto());
        System.out.printf("DESCUENTO: %53s -$%.2f%n", "", f.getDescuento());
        System.out.println("-------------------------------------------------------");
        System.out.printf("TOTAL A PAGAR: %49s **$%.2f**%n", "", f.getTotal());
        System.out.println("-------------------------------------------------------");
        
        // 4. Información Adicional 
        System.out.println("Método de Pago: " + (f.getMetodoPago() != null ? f.getMetodoPago().toString() : "N/A"));
        System.out.println("Estado: " + f.getEstado().toString());
        if (f.getObservaciones() != null && !f.getObservaciones().trim().isEmpty()) {
            System.out.println("Observaciones: " + f.getObservaciones());
        }
        System.out.println("=======================================================");
        System.out.println("             ¡Gracias por su visita!                 ");
        System.out.println("=======================================================\n");
    }

    // 3. REPORTE


    private static void menuReportes() {
        while (true) {
            System.out.println("\n--- Menú de Reportes Gerenciales ---");
            System.out.println("1. Servicios Más Solicitados");
            System.out.println("2. Análisis de Facturación por Período");
            System.out.println("3. Desempeño del Equipo Veterinario (PENDIENTE)");
            System.out.println("4. Estado del Inventario (PENDIENTE)");
            System.out.println("0. Volver al menú principal");
            
            int opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1: reporteServiciosMasSolicitados(); break;
                case 2: reporteFacturacionPorPeriodo(); break;
                case 3: System.out.println("Reporte de Desempeño: Lógica de Controller/DAO pendiente en esta versión."); break;
                case 4: System.out.println("Reporte de Inventario: Lógica de Controller/DAO pendiente en esta versión."); break;
                case 0: return;
                default: System.out.println("Opción inválida."); break;
            }
        }
    }
    
    private static void reporteServiciosMasSolicitados() {
        System.out.println("\n--- Reporte: Servicios Más Solicitados ---");
        
        try {
            List<Map<String, Object>> reporte = itemFacturaController.getReporteServiciosMasSolicitados();
            
            if (reporte == null || reporte.isEmpty()) {
                System.out.println("No hay servicios registrados o facturados como servicio.");
                return;
            }
            
            System.out.println("\n| # | Servicio                      | Cantidad Total Vendida |");
            System.out.println("|---|-------------------------------|------------------------|");
            int rank = 1;
            for (Map<String, Object> item : reporte) {
                String nombre = (String) item.get("nombre_servicio");
                if (nombre == null) nombre = "Servicio Desconocido";
                Integer cantidad = (Integer) item.get("total_vendido");
                
                String nombreCorto = nombre.length() > 27 ? nombre.substring(0, 24) + "..." : nombre;
                
                System.out.printf("| %-1d | %-27s | %22d |%n", rank++, nombreCorto, cantidad);
            }
            System.out.println("----------------------------------------------------------");
            
        } catch (Exception e) {
            System.out.println(" Error al obtener el reporte de servicios: " + e.getMessage());
        }
    }
    
    private static void reporteFacturacionPorPeriodo() {
        System.out.println("\n--- Reporte: Análisis de Facturación por Período ---");
        try {
            List<Map<String, Object>> reporte = facturasController.getReporteFacturacionPorPeriodo();
            if (reporte == null || reporte.isEmpty()) {
                System.out.println("No hay datos de facturación PAGADA en el período.");
                return;
            }
            
            System.out.println("\n| Período | Total Facturado | No. Facturas |");
            System.out.println("|---------|-----------------|--------------|");

            for (Map<String, Object> item : reporte) {
                String periodo = (String) item.get("periodo");
                BigDecimal total = (BigDecimal) item.get("total_facturado");
                Long numFacturas = (Long) item.get("num_facturas");
                
                System.out.printf("| %-7s | $%-15.2f | %-12d |%n", periodo, total, numFacturas);
            }
            System.out.println("------------------------------------------------");
            
        } catch (Exception e) {
            System.out.println(" Error al obtener el reporte de facturación: " + e.getMessage());
        }
    }


    
    // Entrada de Usuario
  
    
    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private static String leerTextoOpcional(String mensaje) {
        System.out.print(mensaje);
        String input = scanner.nextLine();
        return input.trim().isEmpty() ? null : input.trim();
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Debe ingresar un número entero válido: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();  
        return valor;
    }
    
    private static BigDecimal leerBigDecimal(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextDouble()) {
            System.out.print("Debe ingresar un número decimal válido: ");
            scanner.next();
        }
        double valor = scanner.nextDouble();
        scanner.nextLine();  
        return BigDecimal.valueOf(valor);
    }
}

