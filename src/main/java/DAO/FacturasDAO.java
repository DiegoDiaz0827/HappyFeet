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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.ConexionDB;
/**
 *
 * @author camper
 */
public class FacturasDAO {
    private Facturas mapearResultSetAFactura(ResultSet rs) throws SQLException {
        LocalDateTime fechaEmision = rs.getTimestamp("fecha_emision") != null ? 
                                     rs.getTimestamp("fecha_emision").toLocalDateTime() : null;
        
        String metodoTexto = rs.getString("metodo_pago");
        MetodoPago metodoPago = null;
        if (metodoTexto != null) {
            try {
                metodoPago = MetodoPago.valueOf(metodoTexto.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("️ Método de pago desconocido en BD: " + metodoTexto);
            }
        }

        String estadoTexto = rs.getString("estado");
        EstadoFacturas estadoFactura = null;
        if (estadoTexto != null) {
            try {
                estadoFactura = EstadoFacturas.valueOf(estadoTexto.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("️ Estado de factura desconocido en BD: " + estadoTexto);
            }
        }

        return new Facturas(
            rs.getInt("id"),
            rs.getInt("dueno_id"),
            rs.getString("numero_factura"),
            fechaEmision,
            rs.getBigDecimal("subtotal"),
            rs.getBigDecimal("impuesto"),
            rs.getBigDecimal("descuento"),
            rs.getBigDecimal("total"),
            metodoPago,
            estadoFactura,
            rs.getString("observaciones")
        );
    }
    
    // 1. CREATE 
    public boolean agregar(Facturas f){
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
            ps.setString(8, f.getMetodoPago() != null ? f.getMetodoPago().name() : null); 
            ps.setString(9, f.getEstado() != null ? f.getEstado().name() : null);     
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
            return filas > 0; 
        
        }catch(SQLException ex){
            System.out.println(" Error SQL al agregar factura: " + ex.getMessage());
            ex.printStackTrace();
            return false; 
        }
    }
    
    // 2. READ (Listar todos)
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
            System.out.println(" Error SQL al listar facturas: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // 3. READ (Obtener por ID)
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
            System.out.println(" Error SQL al obtener factura por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    // 4. UPDATE 
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
            ps.setString(8, f.getMetodoPago() != null ? f.getMetodoPago().name() : null);
            ps.setString(9, f.getEstado() != null ? f.getEstado().name() : null);
            ps.setString(10, f.getObservaciones());
            ps.setInt(11, f.getId()); 
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println(" Error SQL al actualizar factura: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // 5. DELETE 
    public boolean eliminar(int id) {
        String sql = "DELETE FROM facturas WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println(" Error SQL al eliminar factura: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // 6. REPORTE: Análisis de Facturación por Período
    public List<Map<String, Object>> getReporteFacturacionPorPeriodo() {
        List<Map<String, Object>> reporte = new ArrayList<>();
        String SQL = "SELECT " +
                     "    STRFTIME('%Y-%m', fecha_emision) AS periodo, " + 
                     "    SUM(total) AS total_facturado, " +
                     "    COUNT(id) AS num_facturas " +
                     "FROM facturas " +
                     "WHERE estado = 'PAGADA' " + 
                     "GROUP BY periodo " +
                     "ORDER BY periodo DESC";

        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(SQL)) {

            while (rs.next()) {
                Map<String, Object> itemReporte = new HashMap<>(); 
                itemReporte.put("periodo", rs.getString("periodo"));
                itemReporte.put("total_facturado", rs.getBigDecimal("total_facturado"));
                itemReporte.put("num_facturas", rs.getLong("num_facturas")); 
                reporte.add(itemReporte);
            }

        } catch (SQLException e) {
            System.out.println(" Error SQL al generar reporte de facturación por período: " + e.getMessage());
            e.printStackTrace();
        }
        return reporte;
    }
    
    public List<Facturas> obtenerproducto(LocalDateTime fechainicio,LocalDateTime fechafin) {
          List<Facturas> facturas = new ArrayList<>();
        String sql = "SELECT f.*,SUM(itf.cantidad) AS cantidadtotal ,SUM(itf.subtotal) AS subtotal, i.nombre_producto AS nombre "
                + "FROM facturas f "
                + "JOIN items_factura itf ON f.id = itf.factura_id "
                + "JOIN inventario i ON itf.producto_id = i.id "
                + "WHERE itf.tipo_item = 'Producto' AND f.fecha_emision BETWEEN ? AND ? "
                + "GROUP BY f.id, i.nombre_producto "
                + "ORDER BY subtotal DESC ";
                
                
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(fechainicio));
            ps.setTimestamp(2, Timestamp.valueOf(fechafin));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
              Facturas f = new Facturas(
                      rs.getString("nombre"),
                      rs.getInt("subtotal"),
                      rs.getInt("cantidadtotal")
              );
                    
                     facturas.add(f);     
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al obtener producto: " + e.getMessage());
        }
        return facturas;
    }
    
   
     
}
