/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Entities.Proveedor;
import util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author camper
 */
public class ProveedorDAO {


    public boolean insertar(Proveedor proveedor) {
        String sql = "INSERT INTO proveedores (nombre_empresa, contacto, telefono, email, direccion, sitio_web, activo, fecha_registro) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, proveedor.getNombreEmpresa());
            ps.setString(2, proveedor.getContacto());
            ps.setString(3, proveedor.getTelefono());
            ps.setString(4, proveedor.getEmail());
            ps.setString(5, proveedor.getDireccion());
            ps.setString(6, proveedor.getSitioWeb());
            ps.setBoolean(7, proveedor.isActivo());
            ps.setTimestamp(8, Timestamp.valueOf(proveedor.getFechaRegistro()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar proveedor: " + e.getMessage());
            return false;
        }
    }

    public List<Proveedor> listar() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedores";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Proveedor p = new Proveedor(
                        rs.getInt("id"),
                        rs.getString("nombre_empresa"),
                        rs.getString("contacto"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("direccion"),
                        rs.getString("sitio_web"),
                        rs.getBoolean("activo"),
                        rs.getTimestamp("fecha_registro").toLocalDateTime()
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar proveedores: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(Proveedor proveedor) {
        String sql = "UPDATE proveedores SET nombre_empresa=?, contacto=?, telefono=?, email=?, direccion=?, sitio_web=?, activo=? WHERE id=?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, proveedor.getNombreEmpresa());
            ps.setString(2, proveedor.getContacto());
            ps.setString(3, proveedor.getTelefono());
            ps.setString(4, proveedor.getEmail());
            ps.setString(5, proveedor.getDireccion());
            ps.setString(6, proveedor.getSitioWeb());
            ps.setBoolean(7, proveedor.isActivo());
            ps.setInt(8, proveedor.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar proveedor: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM proveedores WHERE id=?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar proveedor: " + e.getMessage());
            return false;
        }
    }
}

    

