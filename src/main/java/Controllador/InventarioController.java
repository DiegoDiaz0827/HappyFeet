/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;

import DAO.InventarioDAO;
import Model.Entities.Inventario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
/**
 *
 * @author camper
 */
public class InventarioController {

    private InventarioDAO inventarioDAO;

    public InventarioController(InventarioDAO inventarioDAO) {
        this.inventarioDAO = inventarioDAO;
    }

    // 1️ Crear nuevo registro de inventario
    public boolean registrarInventario(Inventario inventario) {
        if (inventario.getNombreProducto() == null || inventario.getNombreProducto().isBlank()) {
            System.out.println("️ El nombre del producto es obligatorio.");
            return false;
        }
        if (inventario.getPrecioCompra() == null || inventario.getPrecioCompra().compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("️ El precio de compra debe ser mayor que 0.");
            return false;
        }
        if (inventario.getPrecioVenta() == null || inventario.getPrecioVenta().compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("️ El precio de venta debe ser mayor que 0.");
            return false;
        }
        if (inventario.getCantidadStock() < 0) {
            System.out.println("️ La cantidad en stock no puede ser negativa.");
            return false;
        }
        if (inventario.getFechaVencimiento() != null && inventario.getFechaVencimiento().isBefore(LocalDate.now())) {
            System.out.println("️ La fecha de vencimiento no puede ser anterior a la fecha actual.");
            return false;
        }

        inventario.setFechaRegistro(LocalDateTime.now());

        try {
            boolean exito = inventarioDAO.crearInventario(inventario);
            System.out.println(exito ? " Inventario registrado correctamente." : " No se pudo registrar el inventario.");
            return exito;
        } catch (Exception e) {
            System.out.println(" Error al registrar inventario: " + e.getMessage());
            return false;
        }
    }

    // 2️ Listar todos los registros del inventario
    public List<Inventario> listarInventario() {
        return inventarioDAO.obtenerTodos();
    }
    
    // 3️ OBTENER INVENTARIO POR ID 
    public Inventario obtenerInventarioPorId(int id) {
        if (id <= 0) {
            System.out.println("️ ID inválido.");
            return null;
        }

        for (Inventario i : listarInventario()) {
            if (i.getId() == id) {
                return i;
            }
        }
        System.out.println(" No se encontró el producto en inventario con ID: " + id);
        return null;
    }
    
    // 4️ Actualizar un registro existente
    public boolean actualizarInventario(Inventario inventario) {
        if (inventario.getId() <= 0) {
            System.out.println("️ El inventario debe tener un ID válido.");
            return false;
        }
        if (inventario.getNombreProducto() == null || inventario.getNombreProducto().isBlank()) {
            System.out.println("️ El nombre del producto es obligatorio.");
            return false;
        }
        if (inventario.getPrecioVenta() == null || inventario.getPrecioVenta().compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println(" El precio de venta debe ser mayor que 0.");
            return false;
        }
        if (inventario.getFechaVencimiento() != null && inventario.getFechaVencimiento().isBefore(LocalDate.now())) {
            System.out.println("️ La fecha de vencimiento no puede ser anterior a la actual.");
            return false;
        }

        boolean exito = inventarioDAO.actualizarInventario(inventario);
        System.out.println(exito ? " Inventario actualizado correctamente." : " No se pudo actualizar el inventario.");
        return exito;
    }

    // 5️ Eliminar un registro de inventario
    public boolean eliminarInventario(int id) {
        if (id <= 0) {
            System.out.println("️ ID inválido.");
            return false;
        }

        boolean exito = inventarioDAO.eliminarInventario(id);
        System.out.println(exito ? "️ Inventario eliminado correctamente." : " No se encontró el registro para eliminar.");
        return exito;
    }
}
    

