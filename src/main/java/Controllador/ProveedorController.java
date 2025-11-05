/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;

import DAO.ProveedorDAO;
import Model.Entities.Proveedor;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author camper
 */
public class ProveedorController {

    private ProveedorDAO proveedorDAO;

    public ProveedorController(ProveedorDAO proveedorDAO) {
        this.proveedorDAO = proveedorDAO;
    }

 
    public boolean registrarProveedor(Proveedor proveedor) {
        if (proveedor.getNombreEmpresa() == null || proveedor.getNombreEmpresa().isBlank()) {
            System.out.println("️ El nombre de la empresa es obligatorio.");
            return false;
        }
        if (proveedor.getEmail() != null && !proveedor.getEmail().isBlank() && !proveedor.getEmail().contains("@")) {
            System.out.println("️ El correo electrónico no es válido.");
            return false;
        }
        if (proveedor.getTelefono() != null && proveedor.getTelefono().length() < 7) {
            System.out.println("️ El número de teléfono parece inválido.");
            return false;
        }

        proveedor.setFechaRegistro(LocalDateTime.now());
        proveedor.setActivo(true);

        try {
            boolean exito = proveedorDAO.insertar(proveedor);
            System.out.println(exito ? " Proveedor registrado correctamente." : " No se pudo registrar el proveedor.");
            return exito;
        } catch (Exception e) {
            System.out.println(" Error al registrar proveedor: " + e.getMessage());
            return false;
        }
    }

   
    public List<Proveedor> listarProveedores() {
        return proveedorDAO.listar();
    }

   
    public Proveedor obtenerProveedorPorId(int id) {
        if (id <= 0) {
            System.out.println("️ ID inválido.");
            return null;
        }

        
        List<Proveedor> lista = proveedorDAO.listar();
        for (Proveedor p : lista) {
            if (p.getId() == id) {
                return p;
            }
        }
        System.out.println(" No se encontró el proveedor con ID: " + id);
        return null;
    }

   
    public boolean actualizarProveedor(Proveedor proveedor) {
        if (proveedor.getId() <= 0) {
            System.out.println(" El proveedor debe tener un ID válido.");
            return false;
        }
        if (proveedor.getNombreEmpresa() == null || proveedor.getNombreEmpresa().isBlank()) {
            System.out.println("️ El nombre de la empresa es obligatorio.");
            return false;
        }

        boolean exito = proveedorDAO.actualizar(proveedor);
        System.out.println(exito ? " Proveedor actualizado correctamente." : " No se pudo actualizar el proveedor.");
        return exito;
    }

   
    public boolean eliminarProveedor(int id) {
        if (id <= 0) {
            System.out.println("️ ID inválido.");
            return false;
        }

        boolean exito = proveedorDAO.eliminar(id);
        System.out.println(exito ? "🗑️ Proveedor eliminado correctamente." : " No se encontró el proveedor para eliminar.");
        return exito;
    }
}
    

