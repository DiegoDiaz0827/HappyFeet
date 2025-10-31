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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author camper
 */
public class Modulo4 {
    private static facturasController facturasController;
    private static serviciosController serviciosController;
    private static item_facturaController itemFacturaController;
    private static Scanner sc = new Scanner(System.in);

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
            switch (opcion) {
                case 1 -> menuServicios();
                case 2 -> menuFacturas();
                case 0 -> {
                    System.out.println("Saliendo del Módulo 4...");
                    return;
                }
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n=== Módulo de Facturación y Servicios ===");
        System.out.println("1. Gestión de Servicios");
        System.out.println("2. Gestión de Facturas");
        System.out.println("0. Volver al menú principal (Salir del Módulo 4)");
    }
    
    // --- Menú de Servicios ---
    private static void menuServicios() {
        while (true) {
            System.out.println("\n--- Menú de Servicios ---");
            System.out.println("1. Registrar nuevo servicio");
            System.out.println("2. Listar todos los servicios");
            System.out.println("3. Buscar servicio por ID");
            System.out.println("4. Actualizar servicio");
            System.out.println("5. Eliminar servicio");
            System.out.println("0. Volver al menú principal");
            
            int opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1 -> registrarServicio();
                case 2 -> listarServicios();
                case 3 -> obtenerServicio();
                case 4 -> actualizarServicio();
                case 5 -> eliminarServicio();
                case 0 -> { return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }
    
    // --- Lógica de Servicios ---
    private static void registrarServicio() {
        System.out.println("\n--- Registrar Nuevo Servicio ---");
        String nombre = leerTexto("Nombre del servicio: ");
        String descripcion = leerTextoOpcional("Descripción (opcional): ");
        String categoria = leerTextoOpcional("Categoría (opcional): ");
        BigDecimal precioBase = leerBigDecimal("Precio Base: ");
        int duracion = leerEntero("Duración estimada (minutos): ");
        
        Servicios s = new Servicios(nombre, descripcion, categoria, precioBase, duracion);
        s.setNombre(nombre);
        s.setDescripcion(descripcion);
        s.setCategoria(categoria);
        s.setPrecioBase(precioBase);
        s.setDuracionEstimadaMinutos(duracion);
        s.setActivo(true);
        
        serviciosController.registrarServicio(s);
    }
    
    private static void listarServicios() {
        System.out.println("\n--- Lista de Servicios ---");
        List<Servicios> servicios = serviciosController.listarServicios();
        if (servicios.isEmpty()) {
            System.out.println("No hay servicios registrados.");
            return;
        }
        for (Servicios s : servicios) {
            System.out.printf("ID: %d | Nombre: %s | Precio: $%.2f | Duración: %d min%n", 
                    s.getId(), s.getNombre(), s.getPrecioBase(), s.getDuracionEstimadaMinutos());
        }
    }
    
    private static void obtenerServicio() {
        int id = leerEntero("Ingrese ID del servicio: ");
        Servicios s = serviciosController.obtenerServicioPorId(id);
        if (s != null) {
             System.out.printf("\nDetalle del Servicio %d:%n", s.getId());
             System.out.println("  Nombre: " + s.getNombre());
             System.out.println("  Descripción: " + s.getDescripcion());
             System.out.println("  Categoría: " + s.getCategoria());
             System.out.println("  Precio Base: $" + s.getPrecioBase());
             System.out.println("  Duración: " + s.getDuracionEstimadaMinutos() + " minutos");
             System.out.println("  Activo: " + (s.isActivo() ? "Sí" : "No"));
        }
    }
    
    private static void actualizarServicio() {
        int id = leerEntero("Ingrese ID del servicio a actualizar: ");
        Servicios s = serviciosController.obtenerServicioPorId(id);
        if (s == null) return;
        
        System.out.println("Deje el campo vacío/0 para no actualizar.");

        String nombre = leerTextoOpcional("Nombre (" + s.getNombre() + "): ");
        if (!nombre.isEmpty()) s.setNombre(nombre);

        String descripcion = leerTextoOpcional("Descripción (" + s.getDescripcion() + "): ");
        if (!descripcion.isEmpty()) s.setDescripcion(descripcion);

        BigDecimal precio = leerBigDecimalOpcional("Precio Base (" + s.getPrecioBase() + "): ");
        if (precio != null && precio.compareTo(BigDecimal.ZERO) > 0) s.setPrecioBase(precio);
        
        int duracion = leerEnteroOpcional("Duración estimada en minutos (" + s.getDuracionEstimadaMinutos() + "): ");
        if (duracion > 0) s.setDuracionEstimadaMinutos(duracion);
        
        serviciosController.actualizarServicio(s);
    }
    
    private static void eliminarServicio() {
        int id = leerEntero("Ingrese ID del servicio a eliminar: ");
        serviciosController.eliminarServicio(id);
    }

    // --- Menú de Facturas ---
    private static void menuFacturas() {
        while (true) {
            System.out.println("\n--- Menú de Facturas ---");
            System.out.println("1. Registrar nueva factura");
            System.out.println("2. Listar todas las facturas");
            System.out.println("3. Buscar factura por ID");
            System.out.println("4. Listar ítems de una factura");
            System.out.println("5. Actualizar factura (Totales/Estado/Pago)");
            System.out.println("6. Eliminar factura");
            System.out.println("0. Volver al menú principal");
            
            int opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1 -> registrarFactura();
                case 2 -> listarFacturas();
                case 3 -> obtenerFactura();
                case 4 -> listarItemsFactura();
                case 5 -> actualizarFactura();
                case 6 -> eliminarFactura();
                case 0 -> { return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }
    
    // --- Lógica de Facturas ---
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

        Facturas f = new Facturas(duenoId, numeroFactura, subtotal, impuesto, descuento, total, metodoPago, EstadoFacturas.PAGADA, observaciones);
        f.setDuenoId(duenoId);
        f.setNumeroFactura(numeroFactura);
        f.setFechaEmision(LocalDateTime.now());
        f.setSubtotal(subtotal);
        f.setImpuesto(impuesto);
        f.setDescuento(descuento);
        f.setTotal(total);
        f.setMetodoPago(metodoPago);
        f.setEstado(EstadoFacturas.PENDIENTE); // Siempre inicia como Pendiente
        f.setObservaciones(observaciones);

        if (facturasController.registrarFactura(f)) {
            // Lógica para registrar ítems si se requiere inmediatamente después
            System.out.println("¿Desea agregar ítems a esta factura ahora? (s/n)");
            if (sc.nextLine().trim().equalsIgnoreCase("s")) {
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
            
            Items_factura item = new Items_factura(0, productoId, 0, precioUnitario, precioUnitario);
            item.setFacturaId(facturaId);
            item.setTipoItem(tipo);
            item.setProductoId(productoId);
            item.setServicioId(servicioId);
            item.setServicioDescripcion(servicioDesc);
            item.setCantidad(cantidad);
            item.setPrecioUnitario(precioUnitario);
            
            itemFacturaController.registrarItemFactura(item);
            
            System.out.println("¿Desea agregar otro ítem? (s/n)");
            if (!sc.nextLine().trim().equalsIgnoreCase("s")) break;
        }
    }
    
    private static void listarFacturas() {
        System.out.println("\n--- Lista de Facturas ---");
        List<Facturas> facturas = facturasController.listarFacturas();
        if (facturas.isEmpty()) {
            System.out.println("No hay facturas registradas.");
            return;
        }
        for (Facturas f : facturas) {
            System.out.printf("ID: %d | No. Factura: %s | Dueño ID: %d | Total: $%.2f | Estado: %s%n", 
                    f.getId(), f.getNumeroFactura(), f.getDuenoId(), f.getTotal(), f.getEstado());
        }
    }
    
    private static void obtenerFactura() {
        int id = leerEntero("Ingrese ID de la factura: ");
        Facturas f = facturasController.obtenerFacturaPorId(id);
        if (f != null) {
            System.out.printf("\nDetalle de Factura %d:%n", f.getId());
            System.out.println("  Número: " + f.getNumeroFactura());
            System.out.println("  Dueño ID: " + f.getDuenoId());
            System.out.println("  Fecha Emisión: " + f.getFechaEmision());
            System.out.println("  Subtotal: $" + f.getSubtotal());
            System.out.println("  Impuesto: $" + f.getImpuesto());
            System.out.println("  Descuento: $" + f.getDescuento());
            System.out.println("  Total: $" + f.getTotal());
            System.out.println("  Método Pago: " + f.getMetodoPago());
            System.out.println("  Estado: " + f.getEstado());
            System.out.println("  Observaciones: " + f.getObservaciones());
        }
    }
    
    private static void listarItemsFactura() {
        int id = leerEntero("Ingrese ID de la factura para listar sus ítems: ");
        List<Items_factura> items = itemFacturaController.listarItemsPorFactura(id);
        if (items.isEmpty()) {
            System.out.println("No se encontraron ítems para la Factura ID " + id);
            return;
        }
        System.out.println("\n--- Ítems de Factura ID " + id + " ---");
        for (Items_factura item : items) {
            String detalle = item.getTipoItem() == itemsFactura.PRODUCTO ? 
                             "Producto ID: " + item.getProductoId() : 
                             "Servicio ID: " + item.getServicioId() + " (" + item.getServicioDescripcion() + ")";
            System.out.printf("  [ID: %d] Tipo: %s | Detalle: %s | Cantidad: %d | Precio Unitario: $%.2f | Subtotal: $%.2f%n",
                    item.getId(), item.getTipoItem(), detalle, item.getCantidad(), item.getPrecioUnitario(), item.getSubtotal());
        }
    }
    
    private static void actualizarFactura() {
        int id = leerEntero("Ingrese ID de la factura a actualizar: ");
        Facturas f = facturasController.obtenerFacturaPorId(id);
        if (f == null) return;
        
        System.out.println("Deje el campo vacío/0 para no actualizar. (Solo se permiten actualizar TOTALES, PAGO y ESTADO)");

        System.out.println("Desea actualizar los totales? (s/n)");
        if (sc.nextLine().trim().equalsIgnoreCase("s")) {
            BigDecimal subtotal = leerBigDecimalOpcional("Nuevo Subtotal (" + f.getSubtotal() + "): ");
            if (subtotal != null && subtotal.compareTo(BigDecimal.ZERO) >= 0) f.setSubtotal(subtotal);

            BigDecimal impuesto = leerBigDecimalOpcional("Nuevo Impuesto (" + f.getImpuesto() + "): ");
            if (impuesto != null && impuesto.compareTo(BigDecimal.ZERO) >= 0) f.setImpuesto(impuesto);
            
            BigDecimal descuento = leerBigDecimalOpcional("Nuevo Descuento (" + f.getDescuento() + "): ");
            if (descuento != null && descuento.compareTo(BigDecimal.ZERO) >= 0) f.setDescuento(descuento);
            
            if (f.getSubtotal() != null && f.getImpuesto() != null && f.getDescuento() != null) {
                 BigDecimal nuevoTotal = f.getSubtotal().add(f.getImpuesto()).subtract(f.getDescuento());
                 f.setTotal(nuevoTotal);
                 System.out.println("Nuevo Total recalculado: $" + nuevoTotal);
            }
        }
        
        String estadoStr = leerTextoOpcional("Nuevo Estado (Pendiente/Pagada/Anulada) [" + f.getEstado() + "]: ");
        if (!estadoStr.isEmpty()) {
            try {
                f.setEstado(EstadoFacturas.valueOf(estadoStr.toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Estado inválido. No se actualizó.");
            }
        }
        
        String metodoPagoStr = leerTextoOpcional("Nuevo Método de Pago (Efectivo/Tarjeta/Transferencia/Mixto) [" + f.getMetodoPago() + "]: ");
        if (!metodoPagoStr.isEmpty()) {
            try {
                f.setMetodoPago(MetodoPago.valueOf(metodoPagoStr.toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("Método de Pago inválido. No se actualizó.");
            }
        }

        facturasController.actualizarFactura(f);
    }
    
    private static void eliminarFactura() {
        int id = leerEntero("Ingrese ID de la factura a eliminar: ");
        facturasController.eliminarFactura(id);
    }


    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine();
    }

    private static String leerTextoOpcional(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            System.out.print("Debe ingresar un número entero válido: ");
            sc.next();
        }
        int valor = sc.nextInt();
        sc.nextLine(); 
        return valor;
    }
    
    private static int leerEnteroOpcional(String mensaje) {
        System.out.print(mensaje);
        String linea = sc.nextLine().trim();
        if (linea.isEmpty()) return -1;
        try {
            return Integer.parseInt(linea);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static BigDecimal leerBigDecimal(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextDouble()) {
            System.out.print("Debe ingresar un número decimal válido: ");
            sc.next();
        }
        double valor = sc.nextDouble();
        sc.nextLine(); 
        return BigDecimal.valueOf(valor);
    }
    
    private static BigDecimal leerBigDecimalOpcional(String mensaje) {
        System.out.print(mensaje);
        String linea = sc.nextLine().trim();
        if (linea.isEmpty()) return null;
        try {
            return new BigDecimal(linea);
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido. Se omite.");
            return null;
        }
    }
}

