/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Entities.transacciones_puntos;
import Model.Enums.TipoTransacciones; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import util.ConexionDB;
/**
 *
 * @author camper
 */
public class Transacciones_puntosDAO {
    private transacciones_puntos mapearResultSetATransaccion(ResultSet rs) throws SQLException {
        
        Integer facturaId = rs.getObject("factura_id") != null ? rs.getInt("factura_id") : null;
        
        return new transacciones_puntos(
            rs.getInt("id"),
            rs.getInt("club_mascotas_id"),
            facturaId,
            rs.getInt("puntos"),
            TipoTransacciones.valueOf(rs.getString("tipo")), 
            rs.getTimestamp("fecha"), 
            rs.getString("descripcion"),
            rs.getInt("saldo_anterior"),
            rs.getInt("saldo_nuevo")
        );
    }
    
    // --- 1. CREATE  
    public void agregar(transacciones_puntos t){
        String SQL = "INSERT INTO transacciones_puntos(club_mascotas_id, factura_id, puntos, tipo, fecha, descripcion, saldo_anterior, saldo_nuevo) VALUES (?,?,?,?,?,?,?,?)";
        
        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS) ){
        
            ps.setInt(1, t.getClubMascotasId());
            
            if (t.getFacturaId() != null) {
                ps.setInt(2, t.getFacturaId());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }
            
            ps.setInt(3, t.getPuntos());
            ps.setString(4, t.getTipo().name());
            ps.setTimestamp(5, t.getFecha()); 
            ps.setString(6, t.getDescripcion());
            ps.setInt(7, t.getSaldoAnterior());
            ps.setInt(8, t.getSaldoNuevo());
            
            int filas = ps.executeUpdate();
            
            try(ResultSet rs = ps.getGeneratedKeys()) {
               if(rs.next()){
                   int idGenerado = rs.getInt(1);
                   t.setId(idGenerado);
                   System.out.println("Transacción de Puntos insertada con ID = " + idGenerado);
               }
            }
            
            System.out.println("Transacción de Puntos agregada, filas afectadas: " + filas);
        
        }catch(SQLException ex){
            System.out.println("Error SQL al agregar transacción de puntos: " + ex.getMessage());
            ex.printStackTrace();
        
        }
    }
    
    // --- 2. READ 
    public List<transacciones_puntos> listarPorClub(int clubMascotasId){
        List<transacciones_puntos> lista = new ArrayList<>();
        String SQL = "SELECT * FROM transacciones_puntos WHERE club_mascotas_id = ? ORDER BY fecha DESC";
        
        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(SQL)
            ) {
            
            ps.setInt(1, clubMascotasId);
            try (ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    lista.add(mapearResultSetATransaccion(rs));
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error SQL al listar transacciones por Club ID: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // --- 3. READ 
    public transacciones_puntos obtenerPorId(int id) {
        String sql = "SELECT * FROM transacciones_puntos WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearResultSetATransaccion(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al obtener transacción de puntos por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    // --- 4. UPDATE 
    public boolean actualizar(transacciones_puntos t) {
        String sql = "UPDATE transacciones_puntos SET puntos = ?, tipo = ?, descripcion = ?, saldo_anterior = ?, saldo_nuevo = ? WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, t.getPuntos());
            ps.setString(2, t.getTipo().name());
            ps.setString(3, t.getDescripcion());
            ps.setInt(4, t.getSaldoAnterior());
            ps.setInt(5, t.getSaldoNuevo());
            ps.setInt(6, t.getId()); 
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al actualizar transacción de puntos: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // --- 5. DELETE 
    public boolean eliminar(int id) {
        String sql = "DELETE FROM transacciones_puntos WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al eliminar transacción de puntos: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
