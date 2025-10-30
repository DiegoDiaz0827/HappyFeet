/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Entities.MovimientoInventario;
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
public class MovimientoInventarioDAO {


    public boolean insertar(MovimientoInventario m) {
        String sql = "INSERT INTO movimientos_inventario (producto_id, tipo_movimiento, cantidad, stock_anterior, stock_nuevo, motivo, referencia_consulta_id, referencia_procedimiento_id, usuario, fecha_movimiento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, m.getProductoId());
            ps.setString(2, m.getTipoMovimiento());
            ps.setInt(3, m.getCantidad());
            ps.setInt(4, m.getStockAnterior());
            ps.setInt(5, m.getStockNuevo());
            ps.setString(6, m.getMotivo());
            ps.setObject(7, m.getReferenciaConsultaId());
            ps.setObject(8, m.getReferenciaProcedimientoId());
            ps.setString(9, m.getUsuario());
            ps.setTimestamp(10, Timestamp.valueOf(m.getFechaMovimiento()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar movimiento inventario: " + e.getMessage());
            return false;
        }
    }

    public List<MovimientoInventario> listar() {
        List<MovimientoInventario> lista = new ArrayList<>();
        String sql = "SELECT * FROM movimientos_inventario";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MovimientoInventario m = new MovimientoInventario(
                        rs.getInt("id"),
                        rs.getInt("producto_id"),
                        rs.getString("tipo_movimiento"),
                        rs.getInt("cantidad"),
                        rs.getInt("stock_anterior"),
                        rs.getInt("stock_nuevo"),
                        rs.getString("motivo"),
                        (Integer) rs.getObject("referencia_consulta_id"),
                        (Integer) rs.getObject("referencia_procedimiento_id"),
                        rs.getString("usuario"),
                        rs.getTimestamp("fecha_movimiento").toLocalDateTime()
                );
                lista.add(m);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar movimientos inventario: " + e.getMessage());
        }
        return lista;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM movimientos_inventario WHERE id=?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar movimiento inventario: " + e.getMessage());
            return false;
        }
    }
}

    

