/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vistas;

/**
 *
 * @author camper
 */

import Controllador.InventarioController;
import Controllador.ProveedorController;
import Controllador.PrescripcionController;
import DAO.InventarioDAO;
import DAO.ProveedorDAO;
import DAO.PrescripcionDAO;
import Model.Entities.Inventario;
import Model.Entities.Proveedor;
import Model.Entities.Prescripcion;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime; // Necesario para setear la fecha de registro antes de enviar al DAO
import java.util.List;
import java.util.Scanner;

public class Modulo3 {

    private static PrescripcionController prescripcionController;
    private static ProveedorController proveedorController;
    private static InventarioController inventarioController;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Inicializar los controladores con los DAOs reales
        prescripcionController = new PrescripcionController(new PrescripcionDAO());
        proveedorController = new ProveedorController(new ProveedorDAO());
        inventarioController = new InventarioController(new InventarioDAO());

        while (true) {
            mostrarMenuPrincipal();
            int opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1 -> registrarPrescripcion();
                case 2 -> listarPrescripciones();
                case 3 -> registrarProveedor();
                case 4 -> listarProveedores();
                case 5 -> registrarInventario();
                case 6 -> listarInventario();
                case 0 -> {
                    System.out.println("Saliendo del sistema...");
                    return;
                }
                default -> System.out.println("Opción inválida, intente de nuevo.");
            }
        }
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n=== Sistema de Farmacia ===");
        System.out.println("1. Registrar prescripción");
        System.out.println("2. Listar prescripciones");
        System.out.println("3. Registrar proveedor");
        System.out.println("4. Listar proveedores");
        System.out.println("5. Registrar inventario");
        System.out.println("6. Listar inventario");
        System.out.println("0. Salir");
    }

    // -------------------- PRESCRIPCIÓN --------------------
    private static void registrarPrescripcion() {
        System.out.println("\n--- Registrar Prescripción ---");
        // Corregida Convención: camellCase para variables locales
        int consultaId = leerEntero("ID de la consulta: ");
        int procedimientoId = leerEntero("ID del procedimiento: ");
        int productoId = leerEntero("ID del producto: ");
        int cantidad = leerEntero("Cantidad: ");
        String dosis = leerTexto("Dosis: ");
        String frecuencia = leerTexto("Frecuencia: ");
        Integer duracion = leerEnteroOpcional("Duración en días (opcional): ");
        String instrucciones = leerTexto("Instrucciones: "); // Corregida Convención

        Prescripcion p = new Prescripcion();
        p.setConsultaId(consultaId);
        p.setProcedimientoId(procedimientoId);
        p.setProductoId(productoId);
        p.setCantidad(cantidad);
        p.setDosis(dosis);
        p.setFrecuencia(frecuencia);
        p.setDuracionDias(duracion);
        p.setInstrucciones(instrucciones);
        
        // Se asume que la entidad Prescripcion tiene un setter para fechaPrescripcion
        // y que el Controller/DAO le asignará un valor (LocalDateTime.now())

        prescripcionController.registrarPrescripcion(p);
    }

    private static void listarPrescripciones() {
        System.out.println("\n--- Lista de Prescripciones ---");
        List<Prescripcion> lista = prescripcionController.listarPrescripciones();
        if (lista.isEmpty()) {
            System.out.println("No hay prescripciones registradas.");
            return;
        }
        for (Prescripcion p : lista) {
            System.out.println("ID: " + p.getId() + " | Producto ID: " + p.getProductoId() +
                             " | Cantidad: " + p.getCantidad() + " | Dosis: " + p.getDosis());
        }
    }

    // -------------------- PROVEEDOR --------------------
    private static void registrarProveedor() {
        System.out.println("\n--- Registrar Proveedor ---");
        String nombre = leerTexto("Nombre de la empresa: ");
        String contacto = leerTexto("Contacto: "); 
        String telefono = leerTextoOpcional("Teléfono (opcional): ");
        String email = leerTextoOpcional("Correo electrónico (opcional): ");
        String direccion = leerTextoOpcional("Direccion(opcional): ");
        String sitioWeb = leerTextoOpcional("Sitio web(opcional): ");
        
        // método auxiliar para leer el booleano
        boolean esActivo = leerBooleano("Es activo?"); 
        
        Proveedor p = new Proveedor();
        p.setNombreEmpresa(nombre);
        p.setContacto(contacto);
        p.setTelefono(telefono);
        p.setEmail(email);
        p.setDireccion(direccion);
        p.setSitioWeb(sitioWeb);
        p.setActivo(esActivo); // Se asigna el valor leído del usuario
        
        // Se asume que la entidad Proveedor tiene un setter para fechaRegistro
        // y que el Controller/DAO le asignará un valor (LocalDateTime.now())
        
        proveedorController.registrarProveedor(p);
    }

    private static void listarProveedores() {
        System.out.println("\n--- Lista de Proveedores ---");
        List<Proveedor> lista = proveedorController.listarProveedores();
        if (lista.isEmpty()) {
            System.out.println("No hay proveedores registrados.");
            return;
        }
        for (Proveedor p : lista) {
            System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombreEmpresa() +
                             " | Email: " + p.getEmail());
        }
    }

    // -------------------- INVENTARIO --------------------
    private static void registrarInventario() {
        System.out.println("\n--- Registrar Inventario ---");
        int proveedorId = leerEntero("ID del proveedor: "); 
        String nombre = leerTexto("Nombre del producto: ");
        int productoTipoId = leerEntero("ID del tipo de producto: ");
        String descripcion = leerTexto("Descripcion del producto: "); 
        String fabricante = leerTexto("Fabricante: "); 
        String lote = leerTexto("Lote: "); 
        int cantidad = leerEntero("Cantidad en stock: ");
        int stockMinimo = leerEntero("Stock Minimo: "); 
        BigDecimal precioCompra = leerDecimal("Precio de compra: ");
        BigDecimal precioVenta = leerDecimal("Precio de venta: ");
        String unidadMedida = leerTexto("Unidad de Medida: "); 
        
        //  método auxiliar para leer el booleano
        boolean requiereReceta = leerBooleano("Requiere receta?"); 
        boolean esActivo = leerBooleano("Es activo?"); 
        
        LocalDate fechaVencimiento = leerFechaOpcional("Fecha de vencimiento (AAAA-MM-DD, opcional): ");

        Inventario inv = new Inventario();
        inv.setProveedorId(proveedorId);
        inv.setNombreProducto(nombre);
        inv.setProductoTipoId(productoTipoId);
        inv.setDescripcion(descripcion);
        inv.setFabricante(fabricante);
        inv.setLote(lote);
        inv.setCantidadStock(cantidad);
        inv.setStockMinimo(stockMinimo);
        inv.setPrecioCompra(precioCompra);
        inv.setPrecioVenta(precioVenta);
        inv.setUnidadMedida(unidadMedida);
        inv.setRequiereReceta(requiereReceta); 
        inv.setActivo(esActivo); 
        inv.setFechaVencimiento(fechaVencimiento);
        
        // Establecer la fecha de registro en el objeto antes de enviarlo al DAO
        inv.setFechaRegistro(LocalDateTime.now());
        
        inventarioController.registrarInventario(inv);
    }

    private static void listarInventario() {
        System.out.println("\n--- Lista de Inventario ---");
        List<Inventario> lista = inventarioController.listarInventario();
        if (lista.isEmpty()) {
            System.out.println("No hay productos en inventario.");
            return;
        }
        for (Inventario i : lista) {
            System.out.println("ID: " + i.getId() + " | Producto: " + i.getNombreProducto() +
                             " | Stock: " + i.getCantidadStock() + " | Precio Venta: " + i.getPrecioVenta());
        }
    }

    // -------------------- MÉTODOS AUXILIARES --------------------
    
    // Nuevo método auxiliar para leer booleanos
    private static boolean leerBooleano(String mensaje) {
        System.out.print(mensaje + " (S/N): ");
        String input = sc.nextLine().trim().toUpperCase();
        return input.equals("S"); // Devuelve true si el usuario ingresa 'S'
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
            System.out.print("Debe ingresar un número: ");
            sc.next();
        }
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }

    private static Integer leerEnteroOpcional(String mensaje) {
        System.out.print(mensaje);
        String linea = sc.nextLine().trim();
        if (linea.isEmpty()) return null;
        try {
            return Integer.parseInt(linea);
        } catch (NumberFormatException e) {
            System.out.println("Formato numérico inválido, se usará valor nulo.");
            return null;
        }
    }

    private static BigDecimal leerDecimal(String mensaje) {
        System.out.print(mensaje);
        // Manejo de errores de formato decimal más robusto
        while (!sc.hasNextBigDecimal()) {
            System.out.print("Debe ingresar un número decimal válido (ej. 10.50): ");
            sc.next();
        }
        BigDecimal valor = sc.nextBigDecimal();
        sc.nextLine();
        return valor;
    }

    private static LocalDate leerFechaOpcional(String mensaje) {
        System.out.print(mensaje);
        String linea = sc.nextLine().trim();
        if (linea.isEmpty()) return null;
        try {
            return LocalDate.parse(linea);
        } catch (Exception e) {
            System.out.println("Formato de fecha inválido (debe ser AAAA-MM-DD), se usará valor nulo.");
            return null;
        }
    }
}