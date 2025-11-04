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
    // Corregido a 'scanner' para claridad.
    private static Scanner scanner = new Scanner(System.in); 
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void main(String[] args) {
        
        FacturasDAO facturasDAO = new FacturasDAO(); 
        ServiciosDAO serviciosDAO = new ServiciosDAO(); 
        Item_FacturasDAO itemFacturasDAO = new Item_FacturasDAO();

        facturasController = new facturasController(facturasDAO);
        serviciosController = new serviciosController(serviciosDAO);
        itemFacturaController = new item_facturaController(itemFacturasDAO);
        
        while (true) {
            mostrarMenuPrincipal();
            int opcion = leerEntero("Seleccione una opción: ");
            
            // CORRECCIÓN CLAVE: Uso de la sintaxis tradicional 'switch' (case X: ... break;)
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
        System.out.println("1. Gestión de Servicios");
        System.out.println("2. Gestión de Facturas");
        System.out.println("3. Reportes Gerenciales"); 
        System.out.println("0. Volver al menú principal (Salir del Módulo 4)");
    }
    
    // --------------------------------------------------------------------------
    // --- LÓGICA CRUD SERVICIOS (Métodos omitidos por brevedad, asumo que existen) ---
    // --------------------------------------------------------------------------
    private static void menuServicios() { /* Implementación aquí */ }
    private static void registrarServicio() { /* Implementación aquí */ }
    private static void listarServicios() { /* Implementación aquí */ }
    private static void obtenerServicio() { /* Implementación aquí */ }
    private static void actualizarServicio() { /* Implementación aquí */ }
    private static void eliminarServicio() { /* Implementación aquí */ }


    // --------------------------------------------------------------------------
    // --- LÓGICA CRUD FACTURAS (Mantenida con la corrección del constructor) ---
    // --------------------------------------------------------------------------
    private static void menuFacturas() {
        while (true) {
            System.out.println("\n--- Menú de Facturas ---");
            System.out.println("1. Registrar nueva factura");
            System.out.println("2. Listar todas las facturas");
            System.out.println("3. Buscar factura por ID");
            System.out.println("4. Listar ítems de una factura");
            System.out.println("5. Actualizar factura (Totales/Estado/Pago)");
            System.out.println("6. Eliminar factura");
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
        
        System.out.println("NOTA: Los ítems se agregarán después de crear la factura. Ingrese totales iniciales:");
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
             System.out.println("Método de pago inválido. Se dejará nulo.");
        }
        
        String observaciones = leerTextoOpcional("Observaciones (opcional): ");

        Facturas f = new Facturas(duenoId, numeroFactura, subtotal, impuesto, descuento, total, metodoPago, EstadoFacturas.PENDIENTE, observaciones);
        f.setFechaEmision(LocalDateTime.now()); // Asegurar la fecha
        
        if (facturasController.registrarFactura(f)) {
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
                System.out.println("Tipo de ítem inválido.");
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
                } else {
                    servicioDesc = leerTexto("Descripción del servicio libre: ");
                }
            }
            
            int cantidad = leerEntero("Cantidad: ");
            BigDecimal precioUnitario = leerBigDecimal("Precio Unitario: ");
            
            // CORRECCIÓN DEL CONSTRUCTOR: Se usa el constructor de 9 parámetros con 0 para ID
            BigDecimal subtotalCalculado = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
            
            Items_factura item = new Items_factura(
                0, // ID (lo asigna el DAO)
                facturaId, 
                tipo, 
                productoId, 
                servicioId, 
                servicioDesc, 
                cantidad, 
                precioUnitario, 
                subtotalCalculado
            );
            // FIN DE CORRECCIÓN
            
            itemFacturaController.registrarItemFactura(item);
            
            System.out.println("¿Desea agregar otro ítem? (s/n)");
            if (!scanner.nextLine().trim().equalsIgnoreCase("s")) break;
        }
    }
    
    private static void listarFacturas() { /* Implementación aquí */ }
    private static void obtenerFactura() { /* Implementación aquí */ }
    private static void listarItemsFactura() { /* Implementación aquí */ }
    private static void actualizarFactura() { /* Implementación aquí */ }
    private static void eliminarFactura() { /* Implementación aquí */ }
    
    // ====================================================================
    // === Generación de Factura en Texto Plano (Módulo 4 Requerimiento) ===
    // ====================================================================
    private static void generarFacturaEnTextoPlano() {
        int id = leerEntero("Ingrese ID de la factura a generar en texto plano: ");
        Facturas f = facturasController.obtenerFacturaPorId(id);
        
        if (f == null) {
            System.out.println("Factura con ID " + id + " no encontrada.");
            return;
        }
        
        List<Items_factura> items = itemFacturaController.listarItemsPorFactura(id);
        
        System.out.println("\n=======================================================");
        System.out.println("         FACTURA DE VENTA - VETERINARIA VITAL          ");
        System.out.println("=======================================================");
        
        // --- 1. Datos de la Clínica y Cliente ---
        System.out.println("Clínica: Veterinaria Vital");
        System.out.println("Dirección: Calle Ficticia #123");
        System.out.println("Teléfono: (555) 123-4567");
        System.out.println("-------------------------------------------------------");
        
        String nombreDueno = "Cliente ID: " + f.getDuenoId() + " (PENDIENTE OBTENER NOMBRE)"; 
        
        System.out.println("Cliente: " + nombreDueno);
        System.out.println("Factura No.: " + f.getNumeroFactura());
        System.out.println("Fecha Emisión: " + f.getFechaEmision().format(FORMATTER));
        System.out.println("-------------------------------------------------------");
        
        // --- 2. Desglose de Ítems (Servicios y Productos) ---
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
        
        // --- 3. Resumen y Totales ---
        System.out.printf("SUBTOTAL: %54s $%.2f%n", "", f.getSubtotal());
        System.out.printf("IMPUESTO: %54s $%.2f%n", "", f.getImpuesto());
        System.out.printf("DESCUENTO: %53s -$%.2f%n", "", f.getDescuento());
        System.out.println("-------------------------------------------------------");
        System.out.printf("TOTAL A PAGAR: %49s **$%.2f**%n", "", f.getTotal());
        System.out.println("-------------------------------------------------------");
        
        // --- 4. Información Adicional ---
        System.out.println("Método de Pago: " + (f.getMetodoPago() != null ? f.getMetodoPago().toString() : "N/A"));
        System.out.println("Estado: " + f.getEstado().toString());
        if (f.getObservaciones() != null && !f.getObservaciones().trim().isEmpty()) {
            System.out.println("Observaciones: " + f.getObservaciones());
        }
        System.out.println("=======================================================");
        System.out.println("             ¡Gracias por su visita!             ");
        System.out.println("=======================================================\n");
    }


    // --------------------------------------------------------------------------
    // === REPORTES GERENCIALES (Módulo 4 Requerimiento) ===
    // --------------------------------------------------------------------------
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
                case 3: System.out.println("Reporte de Desempeño: Lógica de Controller/DAO pendiente."); break;
                case 4: System.out.println("Reporte de Inventario: Lógica de Controller/DAO pendiente."); break;
                case 0: return;
                default: System.out.println("Opción inválida."); break;
            }
        }
    }
    
    private static void reporteServiciosMasSolicitados() {
        System.out.println("\n--- Reporte: Servicios Más Solicitados ---");
        
        try {
            // Llama al Controller (que a su vez llama al DAO)
            List<Map<String, Object>> reporte = itemFacturaController.getReporteServiciosMasSolicitados();
            
            if (reporte == null || reporte.isEmpty()) {
                System.out.println("No hay servicios registrados o facturados como servicio.");
                return;
            }
            
            System.out.println("\n| # | Servicio                    | Cantidad Total Vendida |");
            System.out.println("|---|-----------------------------|------------------------|");
            int rank = 1;
            for (Map<String, Object> item : reporte) {
                String nombre = (String) item.get("nombre_servicio");
                Integer cantidad = (Integer) item.get("total_vendido");
                
                System.out.printf("| %-1d | %-27s | %22d |%n", rank++, nombre, cantidad);
            }
            System.out.println("----------------------------------------------------------");
            
        } catch (Exception e) {
            System.out.println("Error al obtener el reporte de servicios. Verifique su Controller y DAO.");
        }
    }
    
    private static void reporteFacturacionPorPeriodo() {
        System.out.println("\n--- Reporte: Análisis de Facturación por Período ---");
        try {
            List<Map<String, Object>> reporte = facturasController.getReporteFacturacionPorPeriodo();
            if (reporte == null || reporte.isEmpty()) {
                System.out.println("No hay datos de facturación en el período.");
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
            System.out.println("Error al obtener el reporte de facturación. Verifique su Controller y DAO.");
        }
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private static String leerTextoOpcional(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
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

