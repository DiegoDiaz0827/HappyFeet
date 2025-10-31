/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vistas;

import Controllador.PrescripcionController;
import Controllador.ProveedorController;
import Controllador.InventarioController;
import DAO.PrescripcionDAO;
import DAO.ProveedorDAO;
import DAO.InventarioDAO;
import Model.Entities.Prescripcion;
import Model.Entities.Proveedor;
import Model.Entities.Inventario;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        int productoId = leerEntero("ID del producto: ");
        int cantidad = leerEntero("Cantidad: ");
        String dosis = leerTexto("Dosis: ");
        String frecuencia = leerTexto("Frecuencia: ");
        Integer duracion = leerEnteroOpcional("Duración en días (opcional): ");

        Prescripcion p = new Prescripcion();
        p.setProductoId(productoId);
        p.setCantidad(cantidad);
        p.setDosis(dosis);
        p.setFrecuencia(frecuencia);
        p.setDuracionDias(duracion);

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
        String email = leerTextoOpcional("Correo electrónico (opcional): ");
        String telefono = leerTextoOpcional("Teléfono (opcional): ");

        Proveedor p = new Proveedor();
        p.setNombreEmpresa(nombre);
        p.setEmail(email);
        p.setTelefono(telefono);

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
        String nombre = leerTexto("Nombre del producto: ");
        BigDecimal precioCompra = leerDecimal("Precio de compra: ");
        BigDecimal precioVenta = leerDecimal("Precio de venta: ");
        int cantidad = leerEntero("Cantidad en stock: ");
        LocalDate fechaVencimiento = leerFechaOpcional("Fecha de vencimiento (AAAA-MM-DD, opcional): ");

        Inventario inv = new Inventario();
        inv.setNombreProducto(nombre);
        inv.setPrecioCompra(precioCompra);
        inv.setPrecioVenta(precioVenta);
        inv.setCantidadStock(cantidad);
        inv.setFechaVencimiento(fechaVencimiento);

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
            return null;
        }
    }

    private static BigDecimal leerDecimal(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextBigDecimal()) {
            System.out.print("Debe ingresar un número válido: ");
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
            return null;
        }
    }
}
