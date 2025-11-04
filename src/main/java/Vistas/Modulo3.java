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
import java.time.LocalDateTime; 
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
               case 3 -> buscarPrescripcionesPorId(); 
                case 4 -> registrarProveedor();
                case 5 -> listarProveedores();
                case 6 -> buscarProveedoresPorId(); 
                case 7 -> registrarInventario();
                case 8 -> listarInventario();
                case 9 -> buscarInventarioPorId(); 
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
        System.out.println("3. Buscar prescripciones por ID");
        System.out.println("4. Registrar proveedor");
        System.out.println("5. Listar proveedores");
        System.out.println("6. Buscar proveedores por ID");
        System.out.println("7. Registrar inventario");
        System.out.println("8. Listar inventario");
        System.out.println("9. Buscar inventario por ID");
        System.out.println("0. Salir");
    }

    // -------------------- PRESCRIPCIÓN --------------------
    private static void registrarPrescripcion() {
        System.out.println("\n--- Registrar Prescripción ---");
        Integer consultaId = leerEnteroOpcional("ID de la consulta: ");
        Integer procedimientoId = leerEnteroOpcional("ID del procedimiento: ");
        int productoId = leerEntero("ID del producto: ");
        int cantidad = leerEntero("Cantidad: ");
        String dosis = leerTexto("Dosis: ");
        String frecuencia = leerTexto("Frecuencia: ");
        Integer duracion = leerEnteroOpcional("Duración en días (opcional): ");
        String instrucciones = leerTexto("Instrucciones: "); 

        Prescripcion p = new Prescripcion();
        p.setConsultaId(consultaId);
        p.setProcedimientoId(procedimientoId);
        p.setProductoId(productoId);
        p.setCantidad(cantidad);
        p.setDosis(dosis);
        p.setFrecuencia(frecuencia);
        p.setDuracionDias(duracion);
        p.setInstrucciones(instrucciones);
        
        prescripcionController.registrarPrescripcion(p);
    }
    
      
    private static void buscarPrescripcionesPorId() {
     System.out.println("\n--- Buscar Prescripción por ID ---");
        int id = leerEntero("Ingrese el ID de la prescripción a buscar: ");
        
        Prescripcion p = prescripcionController.obtenerPrescripcionPorId(id);
        
        if (p != null) {
            System.out.println("\n*** Prescripción Encontrada ***");
            System.out.println("ID: " + p.getId());
            System.out.println("Consulta ID: " + (p.getConsultaId() != null ? p.getConsultaId() : "N/A"));
            System.out.println("Procedimiento ID: " + (p.getProcedimientoId() != null ? p.getProcedimientoId() : "N/A"));
            System.out.println("Producto ID: " + p.getProductoId());
            System.out.println("Cantidad: " + p.getCantidad());
            System.out.println("Dosis: " + p.getDosis());
            System.out.println("Frecuencia: " + p.getFrecuencia());
            System.out.println("Duración (días): " + (p.getDuracionDias() != null ? p.getDuracionDias() : "N/A"));
            System.out.println("Instrucciones: " + p.getInstrucciones());
        } else {
            System.out.println("No se encontró una prescripción con ID: " + id);
        }
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
        p.setActivo(esActivo); 

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
    
    // buscar
    private static void buscarProveedoresPorId(){
    System.out.println("\n--- Buscar Proveedor por ID ---");
        
        int id = leerEntero("Ingrese el ID del proveedor a buscar: ");
        
        Proveedor p = proveedorController.obtenerProveedorPorId(id);
        
        if (p != null) {
            imprimirDetallesProveedor(p); 
        } else {
            System.out.println("Búsqueda finalizada.");
        }
    }
     // metodo imprimir detalles
    private static void imprimirDetallesProveedor(Proveedor p) {
        System.out.println("\n*** 🔍 Proveedor Encontrado ***");
        System.out.println("ID: " + p.getId());
        System.out.println("Nombre Empresa: " + p.getNombreEmpresa());
        System.out.println("Contacto: " + p.getContacto());
        System.out.println("Teléfono: " + (p.getTelefono() != null && !p.getTelefono().isEmpty() ? p.getTelefono() : "N/A"));
        System.out.println("Email: " + (p.getEmail() != null && !p.getEmail().isEmpty() ? p.getEmail() : "N/A"));
        System.out.println("Dirección: " + (p.getDireccion() != null && !p.getDireccion().isEmpty() ? p.getDireccion() : "N/A"));
        System.out.println("Sitio Web: " + (p.getSitioWeb() != null && !p.getSitioWeb().isEmpty() ? p.getSitioWeb() : "N/A"));
        System.out.println("Activo: " + (p.isActivo() ? "Sí" : "No"));
        System.out.println("Fecha Registro: " + p.getFechaRegistro());
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
    
    //inventario
    private static void buscarInventarioPorId(){
        
    System.out.println("\n--- Buscar Producto en Inventario por ID ---");
        int id = leerEntero("Ingrese el ID del producto a buscar: ");
        
        // Asumiendo que el controlador tiene un método llamado obtenerInventarioPorId(id)
        Inventario i = inventarioController.obtenerInventarioPorId(id); 
        
        if (i != null) {
            System.out.println("\n*** 🔍 Producto de Inventario Encontrado ***");
            System.out.println("ID: " + i.getId());
            System.out.println("Nombre: " + i.getNombreProducto());
            System.out.println("Proveedor ID: " + i.getProveedorId());
            System.out.println("Stock: " + i.getCantidadStock() + " " + i.getUnidadMedida());
            System.out.println("Precio Venta: " + i.getPrecioVenta());
            System.out.println("Lote: " + i.getLote());
            System.out.println("Vencimiento: " + (i.getFechaVencimiento() != null ? i.getFechaVencimiento() : "N/A"));
        } else {
            System.out.println("No se encontró un producto en inventario con ID: " + id);
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