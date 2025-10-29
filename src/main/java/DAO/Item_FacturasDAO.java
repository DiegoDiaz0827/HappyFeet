/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Entities.Items_factura;
import Model.Enums.itemsFactura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.ConexionDB;
/**
 *
 * @author camper
 */
public class Item_FacturasDAO {
private Items_factura mapearResultSetAItem(ResultSet rs) throws SQLException {
        return new Items_factura(
            rs.getInt("id"),
            rs.getInt("factura_id"),
            itemsFactura.valueOf(rs.getString("tipo_item")),
            rs.getObject("producto_id") != null ? rs.getInt("producto_id") : null,
            rs.getObject("servicio_id") != null ? rs.getInt("servicio_id") : null,
            rs.getString("servicio_descripcion"),
            rs.getInt("cantidad"),
            rs.getBigDecimal("precio_unitario"),
            rs.getBigDecimal("subtotal")
        );
    }
    
    // --- 1. CREATE 
    public void agregar(Items_factura item){
        String SQL = "INSERT INTO items_factura(factura_id, tipo_item, producto_id, servicio_id, servicio_descripcion, cantidad, precio_unitario, subtotal) VALUES (?,?,?,?,?,?,?,?)";
        
        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS) ){
        
            ps.setInt(1, item.getFacturaId());
            ps.setString(2, item.getTipoItem().name()); 
            
            if (item.getProductoId() != null) {
                ps.setInt(3, item.getProductoId());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }
            
            if (item.getServicioId() != null) {
                ps.setInt(4, item.getServicioId());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            
            ps.setString(5, item.getServicioDescripcion());
            ps.setInt(6, item.getCantidad());
            ps.setBigDecimal(7, item.getPrecioUnitario());
            ps.setBigDecimal(8, item.getSubtotal());
            
            int filas = ps.executeUpdate();
            
            try(ResultSet rs = ps.getGeneratedKeys()) {
               if(rs.next()){
                   int idGenerado = rs.getInt(1);
                   item.setId(idGenerado);
                   System.out.println("Ítem de Factura insertado con ID = " + idGenerado);
               }
            }
            
            System.out.println("Ítem de Factura agregado, filas afectadas: " + filas);
        
        }catch(SQLException ex){
            System.out.println("Error SQL al agregar ítem de factura: " + ex.getMessage());
            ex.printStackTrace();
        
        }
    }
    
    // --- 2. Listar todos los ítems de UNA factura-

    public List<Items_factura> listarPorFactura(int facturaId){
        List<Items_factura> lista = new ArrayList<>();
        String SQL = "SELECT * FROM items_factura WHERE factura_id = ?";
        
        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(SQL)
            ) {
            
            ps.setInt(1, facturaId);
            try (ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    lista.add(mapearResultSetAItem(rs));
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error SQL al listar ítems por factura: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // --- 3. Obtener por ID de Ítem
    public Items_factura obtenerPorId(int id) {
        String sql = "SELECT * FROM items_factura WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearResultSetAItem(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al obtener ítem de factura por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    // --- 4. UPDATE 
    public boolean actualizar(Items_factura item) {
        String sql = "UPDATE items_factura SET factura_id = ?, tipo_item = ?, producto_id = ?, servicio_id = ?, servicio_descripcion = ?, cantidad = ?, precio_unitario = ?, subtotal = ? WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, item.getFacturaId());
            ps.setString(2, item.getTipoItem().name()); 
            
            if (item.getProductoId() != null) {
                ps.setInt(3, item.getProductoId());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }
            
            if (item.getServicioId() != null) {
                ps.setInt(4, item.getServicioId());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            
            ps.setString(5, item.getServicioDescripcion());
            ps.setInt(6, item.getCantidad());
            ps.setBigDecimal(7, item.getPrecioUnitario());
            ps.setBigDecimal(8, item.getSubtotal());
            ps.setInt(9, item.getId()); // Condición WHERE
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al actualizar ítem de factura: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // --- 5. DELETE 
    public boolean eliminar(int id) {
        String sql = "DELETE FROM items_factura WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al eliminar ítem de factura: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
