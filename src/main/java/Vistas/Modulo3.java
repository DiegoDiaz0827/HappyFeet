/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vistas;


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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Modulo3 {

    private static Scanner sc = new Scanner(System.in);

    private static InventarioController inventarioController = new InventarioController(new InventarioDAO());
    private static ProveedorController proveedorController = new ProveedorController(new ProveedorDAO());
    private static PrescripcionController prescripcionController = new PrescripcionController(new PrescripcionDAO());

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== MÓDULO DE INVENTARIO Y FARMACIA ===");
            System.out.println("1. Gestionar Proveedores");
            System.out.println("2. Gestionar Inventario");
            System.out.println("3. Gestionar Prescripciones");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1 -> menuProveedores();
                case 2 -> menuInventario();
                case 3 -> menuPrescripciones();
                case 0 -> {
                    System.out.println("👋 Saliendo del sistema...");
                    return;
                }
                default -> System.out.println("⚠️ Opción inválida. Intente de nuevo.");
            }
        }
    }

    // ================== MENÚ DE PROVEEDORES ==================
    private static void menuProveedores() {
        while (true) {
            System.out.println("\n--- Gestión de Proveedores ---");
            System.out.println("1. Registrar proveedor");
            System.out.println("2. Listar proveedores");
            System.out.println("3. Actualizar proveedor");
            System.out.println("4. Eliminar proveedor");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1 -> registrarProveedor();
                case 2 -> listarProveedores();
                case 3 -> actualizarProveedor();
                case 4 -> eliminarProveedor();
                case 0 -> { return; }
                default -> System.out.println("⚠️ Opción inválida.");
            }
        }
    }

    private static void registrarProveedor() {
        System.out.println("\n--- Registrar nuevo proveedor ---");
        System.out.print("Nombre de la empresa: ");
        String nombre = sc.nextLine();
        System.out.print("Contacto: ");
        String contacto = sc.nextLine();
        System.out.print("Teléfono: ");
        String telefono = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Dirección: ");
        String direccion = sc.nextLine();
        System.out.print("Sitio web: ");
        String web = sc.nextLine();

        Proveedor p = new Proveedor(0, nombre, contacto, telefono, email, direccion, web, true, LocalDateTime.now());
        proveedorController.registrarProveedor(p);
    }

    private static void listarProveedores() {
        System.out.println("\n--- Lista de Proveedores ---");
        List<Proveedor> lista = proveedorController.listarProveedores();
        if (lista.isEmpty()) {
            System.out.println("No hay proveedores registrados.");
            return;
        }
        lista.forEach(p ->
                System.out.println("ID: " + p.getId() + " | Empresa: " + p.getNombreEmpresa() + " | Contacto: " + p.getContacto())
        );
    }

    private static void actualizarProveedor() {
        listarProveedores();
        System.out.print("\nIngrese ID del proveedor a actualizar: ");
        int id = leerEntero();
        Proveedor existente = proveedorController.obtenerProveedorPorId(id);
        if (existente == null) return;

        System.out.print("Nuevo nombre de empresa (" + existente.getNombreEmpresa() + "): ");
        String nombre = sc.nextLine();
        if (!nombre.isBlank()) existente.setNombreEmpresa(nombre);

        System.out.print("Nuevo contacto (" + existente.getContacto() + "): ");
        String contacto = sc.nextLine();
        if (!contacto.isBlank()) existente.setContacto(contacto);

        proveedorController.actualizarProveedor(existente);
    }

    private static void eliminarProveedor() {
        listarProveedores();
        System.out.print("\nIngrese ID del proveedor a eliminar: ");
        int id = leerEntero();
        proveedorController.eliminarProveedor(id);
    }

    // ================== MENÚ DE INVENTARIO ==================
    private static void menuInventario() {
        while (true) {
            System.out.println("\n--- Gestión de Inventario ---");
            System.out.println("1. Registrar producto");
            System.out.println("2. Listar inventario");
            System.out.println("3. Actualizar producto");
            System.out.println("4. Eliminar producto");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1 -> registrarInventario();
                case 2 -> listarInventario();
                case 3 -> actualizarInventario();
                case 4 -> eliminarInventario();
                case 0 -> { return; }
                default -> System.out.println("⚠️ Opción inválida.");
            }
        }
    }

    private static void registrarInventario() {
        System.out.println("\n--- Registrar nuevo producto en inventario ---");

        // Mostrar proveedores disponibles
        List<Proveedor> proveedores = proveedorController.listarProveedores();
        if (proveedores.isEmpty()) {
            System.out.println("⚠️ No hay proveedores registrados. Registre uno primero.");
            return;
        }
        proveedores.forEach(p -> System.out.println("ID: " + p.getId() + " | Empresa: " + p.getNombreEmpresa()));

        System.out.print("Ingrese ID del proveedor: ");
        int proveedorId = leerEntero();

        System.out.print("Ingrese ID del tipo de producto (ej: 1=Medicamento, 2=Insumo): ");
        int productoTipoId = leerEntero();

        System.out.print("Nombre del producto: ");
        String nombre = sc.nextLine();
        System.out.print("Descripción: ");
        String descripcion = sc.nextLine();
        System.out.print("Fabricante: ");
        String fabricante = sc.nextLine();
        System.out.print("Número de lote: ");
        String lote = sc.nextLine();
        System.out.print("Cantidad en stock: ");
        int cantidad = leerEntero();
        System.out.print("Stock mínimo: ");
        int stockMinimo = leerEntero();
        System.out.print("Unidad de medida: ");
        String unidad = sc.nextLine();
        System.out.print("Precio de compra: ");
        BigDecimal precioCompra = new BigDecimal(sc.nextLine());
        System.out.print("Precio de venta: ");
        BigDecimal precioVenta = new BigDecimal(sc.nextLine());

        Inventario inv = new Inventario(
                0,
                nombre,
                productoTipoId,
                descripcion,
                fabricante,
                proveedorId,
                lote,
                cantidad,
                stockMinimo,
                unidad,
                null,
                precioCompra,
                precioVenta,
                false,
                true,
                LocalDateTime.now()
        );

        inventarioController.registrarInventario(inv);
    }

    private static void listarInventario() {
        System.out.println("\n--- Inventario actual ---");
        List<Inventario> lista = inventarioController.listarInventario();
        if (lista.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        lista.forEach(inv ->
                System.out.println("ID: " + inv.getId() + " | " + inv.getNombreProducto() + " | Stock: " + inv.getCantidadStock() + " | Precio venta: " + inv.getPrecioVenta())
        );
    }

    private static void actualizarInventario() {
        listarInventario();
        System.out.print("\nIngrese ID del producto a actualizar: ");
        int id = leerEntero();

        List<Inventario> lista = inventarioController.listarInventario();
        Inventario existente = lista.stream().filter(i -> i.getId() == id).findFirst().orElse(null);
        if (existente == null) {
            System.out.println("❌ Producto no encontrado.");
            return;
        }

        System.out.print("Nuevo nombre del producto (" + existente.getNombreProducto() + "): ");
        String nombre = sc.nextLine();
        if (!nombre.isBlank()) existente.setNombreProducto(nombre);

        System.out.print("Nuevo precio de venta (" + existente.getPrecioVenta() + "): ");
        String precioStr = sc.nextLine();
        if (!precioStr.isBlank()) existente.setPrecioVenta(new BigDecimal(precioStr));

        inventarioController.actualizarInventario(existente);
    }

    private static void eliminarInventario() {
        listarInventario();
        System.out.print("\nIngrese ID del producto a eliminar: ");
        int id = leerEntero();
        inventarioController.eliminarInventario(id);
    }

    // ================== MENÚ DE PRESCRIPCIONES ==================
    private static void menuPrescripciones() {
        while (true) {
            System.out.println("\n--- Gestión de Prescripciones ---");
            System.out.println("1. Registrar prescripción");
            System.out.println("2. Listar prescripciones");
            System.out.println("3. Eliminar prescripción");
            System.out.println("0. Volver");
            System.out.print("Seleccione una opción: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1 -> registrarPrescripcion();
                case 2 -> listarPrescripciones();
                case 3 -> eliminarPrescripcion();
                case 0 -> { return; }
                default -> System.out.println("⚠️ Opción inválida.");
            }
        }
    }

    private static void registrarPrescripcion() {
        System.out.println("\n--- Registrar nueva prescripción ---");

        System.out.print("Consulta ID (opcional, 0 si no aplica): ");
        int consultaId = leerEntero();
        System.out.print("Procedimiento ID (opcional, 0 si no aplica): ");
        int procedimientoId = leerEntero();
        System.out.print("Producto ID: ");
        int productoId = leerEntero();
        System.out.print("Cantidad: ");
        int cantidad = leerEntero();
        System.out.print("Dosis: ");
        String dosis = sc.nextLine();
        System.out.print("Frecuencia: ");
        String frecuencia = sc.nextLine();
        System.out.print("Duración (días, opcional): ");
        String duracionStr = sc.nextLine();
        Integer duracion = duracionStr.isBlank() ? null : Integer.parseInt(duracionStr);
        System.out.print("Instrucciones: ");
        String instrucciones = sc.nextLine();

        Prescripcion p = new Prescripcion(0,
                consultaId == 0 ? null : consultaId,
                procedimientoId == 0 ? null : procedimientoId,
                productoId,
                cantidad,
                dosis,
                frecuencia,
                duracion,
                instrucciones,
                LocalDateTime.now()
        );

        prescripcionController.registrarPrescripcion(p);
    }

    private static void listarPrescripciones() {
        System.out.println("\n--- Lista de Prescripciones ---");
        List<Prescripcion> lista = prescripcionController.listarPrescripciones();
        if (lista.isEmpty()) {
            System.out.println("No hay prescripciones registradas.");
            return;
        }
        lista.forEach(p ->
                System.out.println("ID: " + p.getId() + " | Producto ID: " + p.getProductoId() + " | Dosis: " + p.getDosis() + " | Frecuencia: " + p.getFrecuencia())
        );
    }

    private static void eliminarPrescripcion() {
        listarPrescripciones();
        System.out.print("\nIngrese ID de la prescripción a eliminar: ");
        int id = leerEntero();
        prescripcionController.eliminarPrescripcion(id);
    }

    // ================== MÉTODO AUXILIAR ==================
    private static int leerEntero() {
        while (true) {
            try {
                String input = sc.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("⚠️ Ingrese un número válido: ");
            }
        }
    }
}
