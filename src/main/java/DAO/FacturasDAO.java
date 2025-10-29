/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Entities.Facturas;
import Model.Enums.MetodoPago;
import Model.Enums.EstadoFacturas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import util.ConexionDB;
/**
 *
 * @author camper
 */
public class FacturasDAO {
    private Facturas mapearResultSetAFactura(ResultSet rs) throws SQLException {
        LocalDateTime fechaEmision = rs.getTimestamp("fecha_emision") != null ? 
                                     rs.getTimestamp("fecha_emision").toLocalDateTime() : null;

        return new Facturas(
            rs.getInt("id"),
            rs.getInt("dueno_id"),
            rs.getString("numero_factura"),
            fechaEmision,
            rs.getBigDecimal("subtotal"),
            rs.getBigDecimal("impuesto"),
            rs.getBigDecimal("descuento"),
            rs.getBigDecimal("total"),
            MetodoPago.valueOf(rs.getString("metodo_pago")),
            EstadoFacturas.valueOf(rs.getString("estado")),
            rs.getString("observaciones")
        );
    }
    
    // --- 1. CREATE 
    public void agregar(Facturas f){
        String SQL = "INSERT INTO facturas(dueno_id, numero_factura, fecha_emision, subtotal, impuesto, descuento, total, metodo_pago, estado, observaciones) VALUES (?,?,?,?,?,?,?,?,?,?)";
        
        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS) ){
        
            ps.setInt(1, f.getDuenoId());
            ps.setString(2, f.getNumeroFactura());
            ps.setTimestamp(3, Timestamp.valueOf(f.getFechaEmision() != null ? f.getFechaEmision() : LocalDateTime.now())); 
            ps.setBigDecimal(4, f.getSubtotal());
            ps.setBigDecimal(5, f.getImpuesto());
            ps.setBigDecimal(6, f.getDescuento());
            ps.setBigDecimal(7, f.getTotal());
            ps.setString(8, f.getMetodoPago().name()); 
            ps.setString(9, f.getEstado().name());     
            ps.setString(10, f.getObservaciones());
            
            int filas = ps.executeUpdate();
            
            try(ResultSet rs = ps.getGeneratedKeys()) {
               if(rs.next()){
                   int idGenerado = rs.getInt(1);
                   f.setId(idGenerado);
                   if (f.getFechaEmision() == null) {
                       f.setFechaEmision(LocalDateTime.now()); 
                   }
                   System.out.println("Factura insertada con ID = " + idGenerado);
               }
            }
            
            System.out.println("Factura agregada, filas afectadas: " + filas);
        
        }catch(SQLException ex){
            System.out.println("Error SQL al agregar factura: " + ex.getMessage());
            ex.printStackTrace();
        
        }
    }
    
    // --- 2. READ 
    public List<Facturas> listar(){
        List<Facturas> lista = new ArrayList<>();
        String SQL = "SELECT * FROM facturas";
        
        try(Connection con = ConexionDB.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL)
            ) {
            
            while(rs.next()){
                lista.add(mapearResultSetAFactura(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Error SQL al listar facturas: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // --- 3. READ 
    public Facturas obtenerPorId(int id) {
        String sql = "SELECT * FROM facturas WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearResultSetAFactura(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al obtener factura por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    // --- 4. UPDATE 
    public boolean actualizar(Facturas f) {
  
        String sql = "UPDATE facturas SET dueno_id = ?, numero_factura = ?, fecha_emision = ?, subtotal = ?, impuesto = ?, descuento = ?, total = ?, metodo_pago = ?, estado = ?, observaciones = ? WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, f.getDuenoId());
            ps.setString(2, f.getNumeroFactura());
            ps.setTimestamp(3, f.getFechaEmision() != null ? Timestamp.valueOf(f.getFechaEmision()) : null);
            ps.setBigDecimal(4, f.getSubtotal());
            ps.setBigDecimal(5, f.getImpuesto());
            ps.setBigDecimal(6, f.getDescuento());
            ps.setBigDecimal(7, f.getTotal());
            ps.setString(8, f.getMetodoPago().name());
            ps.setString(9, f.getEstado().name());
            ps.setString(10, f.getObservaciones());
            ps.setInt(11, f.getId()); 
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al actualizar factura: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // --- 5. DELETE 
    public boolean eliminar(int id) {
        String sql = "DELETE FROM facturas WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al eliminar factura: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
