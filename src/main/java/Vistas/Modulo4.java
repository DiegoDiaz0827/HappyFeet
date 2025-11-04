/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vistas;

import Controllador.DueñosController;
import Controllador.InventarioController;
import Controllador.facturasController;
import Controllador.item_facturaController;
import Controllador.serviciosController;
import DAO.DueñoDAO;
import DAO.FacturasDAO; 
import DAO.Item_FacturasDAO;
import DAO.ServiciosDAO;
import Model.Entities.Dueños;
import Model.Entities.Facturas;
import Model.Entities.Inventario;
import Model.Entities.Items_factura;
import Model.Entities.Servicios;
import Model.Enums.EstadoFacturas;
import Model.Enums.MetodoPago;
import Model.Enums.itemsFactura;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private static DueñosController duenocontroller;
    private static InventarioController inventariocontroller;
    // Corregido a 'scanner' para claridad.
    private static Scanner scanner = new Scanner(System.in); 
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void main(String[] args) {
        DueñoDAO dueñodao = new DueñoDAO();
        FacturasDAO facturasDAO = new FacturasDAO(); 
        ServiciosDAO serviciosDAO = new ServiciosDAO(); 
        Item_FacturasDAO itemFacturasDAO = new Item_FacturasDAO();

        facturasController = new facturasController(facturasDAO,dueñodao);
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
        System.out.println("1. Gestión de Servicios");
        System.out.println("2. Gestión de Facturas");
        System.out.println("3. Reportes Gerenciales"); 
        System.out.println("0. Volver al menú principal (Salir del Módulo 4)");
    }

    private static void menuServicios() { }
    private static void registrarServicio() {}
    private static void listarServicios() { }
    private static void obtenerServicio() {}
    private static void actualizarServicio() {}
    private static void eliminarServicio() {}

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
                case 2: Listartodas(); break;
                case 3: Obtenerporid(); break;
                case 4: ListarItems(); break;
               case 5: actualizarFactura(); break;
                //case 6: eliminarFactura(); break;
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

            
            itemFacturaController.registrarItemFactura(item);
            
            System.out.println("¿Desea agregar otro ítem? (s/n)");
            if (!scanner.nextLine().trim().equalsIgnoreCase("s")) break;
        }
    }
    

    
   
    
   
  


    private static void Listartodas(){
        System.out.println("--listar todas--");
         List<Facturas> facturas = facturasController.listarFacturas();
    
         if(facturas.isEmpty()){
             System.out.println("no hay facturas registradas");
         }else{
         for(Facturas f: facturas){
             System.out.println("ID: "+f.getId()+" || dueño ID: "+f.getDuenoId()+" || #factura: " + f.getNumeroFactura()
             +"|| fecha emision: "+f.getFechaEmision()+"|| total: "+ f.getTotal());
         }
         }
    }
    
    private static void Obtenerporid(){
        System.out.println("OBTENIENDO POR ID");
        Facturas f = null;
        while(true){
            int id =leerEntero("id de la factura: ");
        
            try {
                f = facturasController.obtenerFacturaPorId(id);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Error al obtener la factura: " + e.getMessage());
            }
        
        }
        
         System.out.println("ID: "+f.getId()+" || dueño ID: "+f.getDuenoId()+" || #factura: " + f.getNumeroFactura()
             +"|| fecha emision: "+f.getFechaEmision()+"|| total: "+ f.getTotal());
         
    }
    
    
    private static void ListarItems(){
        System.out.println("Listando items");
        Facturas f = null;
        int id = 0;
        while(true){
            id =leerEntero("id de la factura: ");
        
            try {
                f = facturasController.obtenerFacturaPorId(id);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Error al obtener la factura: " + e.getMessage());
            }
        
        }
        
        try {
            List<Items_factura> items = itemFacturaController.listarItemsPorFactura(id);
            System.out.println("encontrada");
             System.out.println("Items de la factura " + id + ":");
        for (Items_factura item : items) {
            System.out.println(item); // o formatea como quieras
        }
        } catch (IllegalArgumentException e) {
             System.out.println("❌ Error al obtener la factura: " + e.getMessage());
        }
        }
    
    
    private static void actualizarFactura(){
    
        System.out.println("ACTUALIZANDO FACTURA");
        Facturas f = null;
        while(true){
            int id =leerEntero("id de la factura: ");
        
            try {
                f = facturasController.obtenerFacturaPorId(id);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Error al obtener la factura: " + e.getMessage());
            }
        }
        
            int dueñoid = leerEntero("Dueño id :");
            f.setDuenoId(dueñoid);
            String numerofac = leerTexto("Nuevo numero factura:");
            LocalDateTime fechaemision = leerFechaHora("nueva fecha:");
            f.setFechaEmision(fechaemision);
            BigDecimal subtotal = leerBigDecimal("subtotal");
            f.setSubtotal(subtotal);
            BigDecimal impuesto = leerBigDecimal("impuesto:");
            f.setImpuesto(impuesto);
            BigDecimal descuento = leerBigDecimal("total");
            f.setDescuento(descuento);
            String metodoPago = leerTexto("Método de pago (EFECTIVO, TARJETA, TRANSFERENCIA): ");
            f.setMetodoPago(MetodoPago.valueOf(metodoPago.toUpperCase()));
         
            
         try {
            facturasController.actualizarFactura(f);
        } catch (Exception e) {
        }
   
            
        } 
        
        
    
    
    
    
    
   
    // ====================================================================
    // === Generación de Factura en Texto Plano (Módulo 4 Requerimiento) ===
    // ====================================================================

 private static void generarFacturaEnTextoPlano() {
    int id = leerEntero("Ingrese ID de la factura a generar en texto plano: ");
    Facturas f = facturasController.obtenerFacturaPorId(id);

    if (f == null) {
        System.out.println("❌ Factura con ID " + id + " no encontrada.");
        return;
    }

    // Obtener nombre del dueño
     Dueños d = duenocontroller.buscarDuenoPorId(f.getDuenoId());
    String nombreDueno = (d != null) ? d.getNombreCompleto(): "Desconocido";

    // Obtener ítems
    List<Items_factura> items = itemFacturaController.listarItemsPorFactura(id);

    // Armar el contenido en un StringBuilder
    StringBuilder sb = new StringBuilder();

    sb.append("\n=======================================================\n");
    sb.append("         FACTURA DE VENTA - VETERINARIA VITAL          \n");
    sb.append("=======================================================\n");

    sb.append("Clínica: Veterinaria Vital\n");
    sb.append("Dirección: Calle Ficticia #123\n");
    sb.append("Teléfono: (555) 123-4567\n");
    sb.append("-------------------------------------------------------\n");

    sb.append("Cliente: ").append(nombreDueno)
      .append(" (ID: ").append(f.getDuenoId()).append(")\n");
    sb.append("Factura No.: ").append(f.getNumeroFactura()).append("\n");
    sb.append("Fecha Emisión: ").append(f.getFechaEmision().format(FORMATTER)).append("\n");
    sb.append("-------------------------------------------------------\n");

    sb.append(String.format("| %-4s | %-30s | %-10s | %-10s |\n", "Cant", "Descripción", "P.Unitario", "Subtotal"));
    sb.append("|------|--------------------------------|------------|------------|\n");

    if (items.isEmpty()) {
        sb.append("| ** NO HAY ÍTEMS REGISTRADOS PARA ESTA FACTURA **\n");
    } else {
        for (Items_factura item : items) {
            String descripcionItem;

            if (item.getTipoItem() == itemsFactura.PRODUCTO) {
                Inventario i = inventariocontroller.(item.getProductoId());
                descripcionItem = (i != null)
                        ? "Producto: " + i.getNombre()
                        : "Producto ID " + item.getProductoId();
            } 
             else {
                descripcionItem = "Item Desconocido";
            }

            String descCorta = descripcionItem.length() > 30
                    ? descripcionItem.substring(0, 27) + "..."
                    : descripcionItem;

            sb.append(String.format("| %-4d | %-30s | $%-9.2f | $%-9.2f |\n",
                    item.getCantidad(),
                    descCorta,
                    item.getPrecioUnitario(),
                    item.getSubtotal()));
        }
    }

    sb.append("-------------------------------------------------------\n");
    sb.append(String.format("SUBTOTAL: %54s $%.2f%n", "", f.getSubtotal()));
    sb.append(String.format("IMPUESTO: %54s $%.2f%n", "", f.getImpuesto()));
    sb.append(String.format("DESCUENTO: %53s -$%.2f%n", "", f.getDescuento()));
    sb.append("-------------------------------------------------------\n");
    sb.append(String.format("TOTAL A PAGAR: %49s **$%.2f**%n", "", f.getTotal()));
    sb.append("-------------------------------------------------------\n");

    sb.append("Método de Pago: ")
      .append(f.getMetodoPago() != null ? f.getMetodoPago() : "N/A").append("\n");
    sb.append("Estado: ")
      .append(f.getEstado() != null ? f.getEstado() : "N/A").append("\n");

    if (f.getObservaciones() != null && !f.getObservaciones().trim().isEmpty()) {
        sb.append("Observaciones: ").append(f.getObservaciones()).append("\n");
    }

    sb.append("=======================================================\n");
    sb.append("             ¡Gracias por su visita!             \n");
    sb.append("=======================================================\n\n");

    // Mostrar factura por consola
    System.out.println(sb.toString());

    // Preguntar si desea guardar
    String opcion = leerTexto("¿Desea guardar la factura en un archivo .txt? (S/N): ");
    if (opcion.equalsIgnoreCase("S")) {
        try {
            File archivo = new File("factura_" + f.getNumeroFactura() + ".txt");
            try (FileWriter fw = new FileWriter(archivo)) {
                fw.write(sb.toString());
            }
            System.out.println("✅ Factura guardada como: " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("❌ Error al guardar la factura: " + e.getMessage());
        }
    }
}

        // 5. REPORTES
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
    String texto;
    do {
        System.out.print(mensaje);
        texto = scanner.nextLine().trim(); // trim() elimina espacios al inicio y final
        if (texto.isEmpty()) {
            System.out.println("⚠️ Debes ingresar un valor. Intenta de nuevo.");
        }
    } while (texto.isEmpty());
    return texto;
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
    
     public static LocalDateTime leerFechaHora(String mensaje) {
         String fechaStr;
         String horaStr;
        do{System.out.println(mensaje);
        System.out.print("Fecha (YYYY-MM-DD): ");
        fechaStr = scanner.nextLine();
         if (fechaStr.isEmpty()) {
            System.out.println("⚠️ Debes ingresar un valor. Intenta de nuevo.");
        }
        }while(fechaStr.isEmpty());
        
        do{System.out.print("Hora (HH:MM): ");
        horaStr = scanner.nextLine();
         if (horaStr.isEmpty()) {
            System.out.println("⚠️ Debes ingresar un valor. Intenta de nuevo.");
        }}while( horaStr.isEmpty());

        LocalDate fecha = LocalDate.parse(fechaStr);
        LocalTime hora = LocalTime.parse(horaStr);

        return LocalDateTime.of(fecha, hora);
        
    }

}

