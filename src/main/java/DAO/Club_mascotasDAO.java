/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Entities.ClubMascotas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import util.ConexionDB;
/**
 *
 * @author camper
 */
public class Club_mascotasDAO {
    private ClubMascotas mapearResultSetAClubMascotas(ResultSet rs) throws SQLException {
        
        LocalDate fechaInscripcion = rs.getDate("fecha_inscripcion") != null ? 
                                     rs.getDate("fecha_inscripcion").toLocalDate() : null;
        
        LocalDateTime fechaUltimaActualizacion = rs.getTimestamp("fecha_ultima_actualizacion") != null ? 
                                                 rs.getTimestamp("fecha_ultima_actualizacion").toLocalDateTime() : null;
        
        return new ClubMascotas(
            rs.getInt("id"),
            rs.getInt("dueno_id"),
            rs.getInt("puntos_acumulados"),
            rs.getInt("puntos_canjeados"),
            rs.getInt("puntos_disponibles"),
            rs.getString("nivel"),
            fechaInscripcion,
            fechaUltimaActualizacion,
            rs.getBoolean("activo")
        );
    }
    
    // --- 1. CREATE 
    public void agregar(ClubMascotas club){
        String SQL = "INSERT INTO club_mascotas(dueno_id, puntos_acumulados, puntos_canjeados, puntos_disponibles, nivel, fecha_inscripcion, fecha_ultima_actualizacion, activo) VALUES (?,?,?,?,?,?,?,?)";
        
        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS) ){
        
            ps.setInt(1, club.getDuenoId());
            ps.setInt(2, club.getPuntosAcumulados());
            ps.setInt(3, club.getPuntosCanjeados());
            ps.setInt(4, club.getPuntosDisponibles());
            ps.setString(5, club.getNivel());
            
            ps.setDate(6, club.getFechaInscripcion() != null ? java.sql.Date.valueOf(club.getFechaInscripcion()) : null);
            
            ps.setTimestamp(7, club.getFechaUltimaActualizacion() != null ? Timestamp.valueOf(club.getFechaUltimaActualizacion()) : null);
            
            ps.setBoolean(8, club.isActivo());
            
            int filas = ps.executeUpdate();
            
            try(ResultSet rs = ps.getGeneratedKeys()) {
               if(rs.next()){
                   int idGenerado = rs.getInt(1);
                   club.setId(idGenerado);
                   System.out.println("Registro de ClubMascotas insertado con ID = " + idGenerado);
               }
            }
            
            System.out.println("Registro de ClubMascotas agregado, filas afectadas: " + filas);
        
        }catch(SQLException ex){
            System.out.println("Error SQL al agregar ClubMascotas: " + ex.getMessage());
            ex.printStackTrace();
        
        }
    }
    
    // --- 2. READ
    public List<ClubMascotas> listar(){
        List<ClubMascotas> lista = new ArrayList<>();
        String SQL = "SELECT * FROM club_mascotas";
        
        try(Connection con = ConexionDB.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL)
            ) {
            
            while(rs.next()){
                lista.add(mapearResultSetAClubMascotas(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Error SQL al listar ClubMascotas: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // --- 3. READ 
    public ClubMascotas obtenerPorDuenoId(int duenoId) {
        String sql = "SELECT * FROM club_mascotas WHERE dueno_id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, duenoId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearResultSetAClubMascotas(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al obtener ClubMascotas por Dueno ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    // --- 4. UPDATE 
    public boolean actualizar(ClubMascotas club) {
        String sql = "UPDATE club_mascotas SET puntos_acumulados = ?, puntos_canjeados = ?, puntos_disponibles = ?, nivel = ?, fecha_ultima_actualizacion = ?, activo = ? WHERE dueno_id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, club.getPuntosAcumulados());
            ps.setInt(2, club.getPuntosCanjeados());
            ps.setInt(3, club.getPuntosDisponibles());
            ps.setString(4, club.getNivel());
            
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            
            ps.setBoolean(6, club.isActivo());
            ps.setInt(7, club.getDuenoId()); 
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al actualizar ClubMascotas: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // --- 5. DELETE 
    public boolean eliminar(int duenoId) {
        String sql = "DELETE FROM club_mascotas WHERE dueno_id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, duenoId);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al eliminar ClubMascotas: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
