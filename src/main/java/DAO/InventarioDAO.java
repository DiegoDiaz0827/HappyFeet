/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import util.ConexionDB;
import Model.Entities.Inventario;

/**
 *
 * @author camper 
 */
public class InventarioDAO {

    public boolean crearInventario(Inventario pInventario) {
        String pSQL = "INSERT INTO inventario (nombre_producto, producto_tipo_id, descripcion, fabricante, proveedor_id, "
                    + "lote, cantidad_stock, stock_minimo, unidad_medida, fecha_vencimiento, precio_compra, "
                    + "precio_venta, requiere_receta, activo, fecha_registro) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection Conexion = ConexionDB.conectar();
             PreparedStatement pStmt = Conexion.prepareStatement(pSQL)) {

            pStmt.setString(1, pInventario.getNombreProducto());
            pStmt.setInt(2, pInventario.getProductoTipoId());
            pStmt.setString(3, pInventario.getDescripcion());
            pStmt.setString(4, pInventario.getFabricante());

            if (pInventario.getProveedorId() != null) {
                pStmt.setInt(5, pInventario.getProveedorId());
            } else {
                pStmt.setNull(5, Types.INTEGER);
            }

            pStmt.setString(6, pInventario.getLote());
            pStmt.setInt(7, pInventario.getCantidadStock());
            pStmt.setInt(8, pInventario.getStockMinimo());
            pStmt.setString(9, pInventario.getUnidadMedida());

            if (pInventario.getFechaVencimiento() != null) {
                pStmt.setDate(10, Date.valueOf(pInventario.getFechaVencimiento()));
            } else {
                pStmt.setNull(10, Types.DATE);
            }

            pStmt.setBigDecimal(11, pInventario.getPrecioCompra());
            pStmt.setBigDecimal(12, pInventario.getPrecioVenta());
            pStmt.setBoolean(13, pInventario.isRequiereReceta());
            pStmt.setBoolean(14, pInventario.isActivo());
            pStmt.setTimestamp(15, Timestamp.valueOf(pInventario.getFechaRegistro()));

            return pStmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Inventario> obtenerTodos() {
        List<Inventario> pLista = new ArrayList<>();
        String pSQL = "SELECT * FROM inventario";

        try (Connection pConexion = ConexionDB.conectar();
             Statement pStmt = pConexion.createStatement();
             ResultSet pRs = pStmt.executeQuery(pSQL)) {

            while (pRs.next()) {
                Inventario pInv = new Inventario(
                        pRs.getInt("id"),
                        pRs.getString("nombre_producto"),
                        pRs.getInt("producto_tipo_id"),
                        pRs.getString("descripcion"),
                        pRs.getString("fabricante"),
                        pRs.getInt("proveedor_id"),
                        pRs.getString("lote"),
                        pRs.getInt("cantidad_stock"),
                        pRs.getInt("stock_minimo"),
                        pRs.getString("unidad_medida"),
                        pRs.getDate("fecha_vencimiento") != null ? pRs.getDate("fecha_vencimiento").toLocalDate() : null,
                        pRs.getBigDecimal("precio_compra"),
                        pRs.getBigDecimal("precio_venta"),
                        pRs.getBoolean("requiere_receta"),
                        pRs.getBoolean("activo"),
                        pRs.getTimestamp("fecha_registro").toLocalDateTime()
                );
                pLista.add(pInv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pLista;
    }

    public boolean actualizarInventario(Inventario pInventario) {
        String pSQL = "UPDATE inventario SET nombre_producto=?, producto_tipo_id=?, descripcion=?, fabricante=?, proveedor_id=?, "
                    + "lote=?, cantidad_stock=?, stock_minimo=?, unidad_medida=?, fecha_vencimiento=?, precio_compra=?, "
                    + "precio_venta=?, requiere_receta=?, activo=? WHERE id=?";

        try (Connection pConexion = ConexionDB.conectar();
             PreparedStatement pStmt = pConexion.prepareStatement(pSQL)) {

            pStmt.setString(1, pInventario.getNombreProducto());
            pStmt.setInt(2, pInventario.getProductoTipoId());
            pStmt.setString(3, pInventario.getDescripcion());
            pStmt.setString(4, pInventario.getFabricante());

            if (pInventario.getProveedorId() != null) {
                pStmt.setInt(5, pInventario.getProveedorId());
            } else {
                pStmt.setNull(5, Types.INTEGER);
            }

            pStmt.setString(6, pInventario.getLote());
            pStmt.setInt(7, pInventario.getCantidadStock());
            pStmt.setInt(8, pInventario.getStockMinimo());
            pStmt.setString(9, pInventario.getUnidadMedida());

            if (pInventario.getFechaVencimiento() != null) {
                pStmt.setDate(10, Date.valueOf(pInventario.getFechaVencimiento()));
            } else {
                pStmt.setNull(10, Types.DATE);
            }

            pStmt.setBigDecimal(11, pInventario.getPrecioCompra());
            pStmt.setBigDecimal(12, pInventario.getPrecioVenta());
            pStmt.setBoolean(13, pInventario.isRequiereReceta());
            pStmt.setBoolean(14, pInventario.isActivo());
            pStmt.setInt(15, pInventario.getId());

            return pStmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private Inventario mapearInventario(ResultSet rs) throws SQLException {
    return new Inventario(
        
        rs.getInt("id"),
        rs.getString("nombre_producto"),
        rs.getInt("producto_tipo_id"),
        rs.getString("descripcion"),
        rs.getString("fabricante"),
        rs.getObject("proveedor_id") != null ? rs.getInt("proveedor_id") : null, 
        rs.getString("lote"),
        rs.getInt("cantidad_stock"),
        rs.getInt("stock_minimo"),
        rs.getString("unidad_medida"),
        rs.getDate("fecha_vencimiento") != null ? rs.getDate("fecha_vencimiento").toLocalDate() : null,
        rs.getBigDecimal("precio_compra"),
        rs.getBigDecimal("precio_venta"),
        rs.getBoolean("requiere_receta"),
        rs.getBoolean("activo"),
        rs.getTimestamp("fecha_registro").toLocalDateTime()
    );
}
    
     public Inventario obtenerPorId(int id) {
    String sql = "SELECT * FROM inventario WHERE id = ?";
    Inventario inv = null;

    try (Connection conexion = ConexionDB.conectar();
         PreparedStatement ps = conexion.prepareStatement(sql)) {

        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                // Aquí es donde se llama el método mapearInventario
                inv = mapearInventario(rs); 
            }
        }

        } catch (SQLException e) {
            System.out.println("Error al obtener inventario por ID: " + e.getMessage());
        }
        return inv;
    }

    public boolean eliminarInventario(int pId) {
        String pSQL = "DELETE FROM inventario WHERE id=?";
        try (Connection pConexion = ConexionDB.conectar();
             PreparedStatement pStmt = pConexion.prepareStatement(pSQL)) {

            pStmt.setInt(1, pId);
            return pStmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
