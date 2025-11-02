/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Entities.canjes_beneficios;
import Model.Enums.EstadoCanjees; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import util.ConexionDB;
/**
 *
 * @author camper
 */
public class Canjes_beneficiosDAO {
private canjes_beneficios mapearResultSetACanje(ResultSet rs) throws SQLException {
        
        Integer facturaId = rs.getObject("factura_id") != null ? rs.getInt("factura_id") : null;
        

        Date fechaExpiracion = rs.getDate("fecha_expiracion");
        
        return new canjes_beneficios(
            rs.getInt("id"),
            rs.getInt("club_mascotas_id"),
            rs.getInt("beneficio_id"),
            rs.getTimestamp("fecha_canje"), 
            rs.getInt("puntos_canjeados"),
            EstadoCanjees.valueOf(rs.getString("estado")), 
            fechaExpiracion,
            facturaId
        );
    }
    
    // --- 1. CREATE 
    public void agregar(canjes_beneficios c){
        String SQL = "INSERT INTO canjes_beneficios(club_mascotas_id, beneficio_id, fecha_canje, puntos_canjeados, estado, fecha_expiracion, factura_id) VALUES (?,?,?,?,?,?,?)";
        
        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS) ){
        
            ps.setInt(1, c.getClubMascotasId());
            ps.setInt(2, c.getBeneficioId());
            ps.setTimestamp(3, c.getFechaCanje());
            ps.setInt(4, c.getPuntosCanjeados());
            ps.setString(5, c.getEstado().name()); 
            
            if (c.getFechaExpiracion() != null) {
                ps.setDate(6, new java.sql.Date(c.getFechaExpiracion().getTime())); 
            } else {
                 ps.setNull(6, java.sql.Types.DATE);
            }
            
            if (c.getFacturaId() != null) {
                ps.setInt(7, c.getFacturaId());
            } else {
                ps.setNull(7, java.sql.Types.INTEGER);
            }
            
            int filas = ps.executeUpdate();
            
            try(ResultSet rs = ps.getGeneratedKeys()) {
               if(rs.next()){
                   int idGenerado = rs.getInt(1);
                   c.setId(idGenerado);
                   System.out.println("Canje de Beneficio insertado con ID = " + idGenerado);
               }
            }
            
            System.out.println("Canje de Beneficio agregado, filas afectadas: " + filas);
        
        }catch(SQLException ex){
            System.out.println("Error SQL al agregar canje de beneficio: " + ex.getMessage());
            ex.printStackTrace();
        
        }
    }
    
    // --- 2. READ 
    public List<canjes_beneficios> listarPorClubId(int clubMascotasId){
        List<canjes_beneficios> lista = new ArrayList<>();
        String SQL = "SELECT * FROM canjes_beneficios WHERE club_mascotas_id = ? ORDER BY fecha_canje DESC";
        
        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(SQL)
            ) {
            
            ps.setInt(1, clubMascotasId);
            try (ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    lista.add(mapearResultSetACanje(rs));
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error SQL al listar canjes por Club ID: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // --- 3. READ 
    public canjes_beneficios obtenerPorId(int id) {
        String sql = "SELECT * FROM canjes_beneficios WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearResultSetACanje(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al obtener canje de beneficio por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    // --- 4. UPDATE 
    public boolean actualizarEstado(int id, EstadoCanjees nuevoEstado, Integer facturaId) {
        String sql = "UPDATE canjes_beneficios SET estado = ?, factura_id = ? WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado.name());
            
            if (facturaId != null) {
                ps.setInt(2, facturaId);
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }
            
            ps.setInt(3, id); 
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al actualizar estado del canje: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // --- 5. DELETE 
    public boolean eliminar(int id) {
        String sql = "DELETE FROM canjes_beneficios WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al eliminar canje de beneficio: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
