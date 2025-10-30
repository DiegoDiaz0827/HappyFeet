/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Entities.InsumoProcedimiento;
import util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author camper
 */
public class InsumoProcedimientoDAO {
    

    public boolean insertar(InsumoProcedimiento insumo) {
        String sql = "INSERT INTO insumos_procedimientos (procedimiento_id, producto_id, cantidad_usada, observaciones) VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, insumo.getProcedimientoId());
            ps.setInt(2, insumo.getProductoId());
            ps.setInt(3, insumo.getCantidadUsada());
            ps.setString(4, insumo.getObservaciones());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar insumo_procedimiento: " + e.getMessage());
            return false;
        }
    }

    public List<InsumoProcedimiento> listar() {
        List<InsumoProcedimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM insumos_procedimientos";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                InsumoProcedimiento insumo = new InsumoProcedimiento(
                        rs.getInt("id"),
                        rs.getInt("procedimiento_id"),
                        rs.getInt("producto_id"),
                        rs.getInt("cantidad_usada"),
                        rs.getString("observaciones")
                );
                lista.add(insumo);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar insumos_procedimientos: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(InsumoProcedimiento insumo) {
        String sql = "UPDATE insumos_procedimientos SET procedimiento_id=?, producto_id=?, cantidad_usada=?, observaciones=? WHERE id=?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, insumo.getProcedimientoId());
            ps.setInt(2, insumo.getProductoId());
            ps.setInt(3, insumo.getCantidadUsada());
            ps.setString(4, insumo.getObservaciones());
            ps.setInt(5, insumo.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar insumo_procedimiento: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM insumos_procedimientos WHERE id=?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar insumo_procedimiento: " + e.getMessage());
            return false;
        }
    }
}
