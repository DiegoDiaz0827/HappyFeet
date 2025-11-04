/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Entities.registro_jornada_vacunacion;
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
public class Registro_jornada_vacunacionDAO {
    private registro_jornada_vacunacion mapearResultSetARegistro(ResultSet rs) throws SQLException {
      
        Integer veterinarioId = rs.getObject("veterinario_id") != null ? rs.getInt("veterinario_id") : null;
        
        return new registro_jornada_vacunacion(
            rs.getInt("id"),
            rs.getInt("jornada_id"),
            rs.getInt("mascota_id"),
            rs.getInt("dueno_id"),
            rs.getInt("vacuna_id"),
            veterinarioId,
            rs.getTimestamp("fecha_hora"),
            rs.getString("lote_vacuna"),
            rs.getDate("proxima_dosis"),   
            rs.getString("observaciones")
        );
    }
    
    // --- 1. CREATE 
    public void agregar(registro_jornada_vacunacion r){
        String SQL = "INSERT INTO registro_jornada_vacunacion(jornada_id, mascota_id, dueno_id, vacuna_id, veterinario_id, fecha_hora, lote_vacuna, proxima_dosis, observaciones) VALUES (?,?,?,?,?,?,?,?,?)";
        
        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS) ){
        
            ps.setInt(1, r.getJornadaId());
            ps.setInt(2, r.getMascotaId());
            ps.setInt(3, r.getDuenoId());
            ps.setInt(4, r.getVacunaId());
            
            if (r.getVeterinarioId() != null) {
                ps.setInt(5, r.getVeterinarioId());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            
            ps.setTimestamp(6, r.getFechaHora()); 
            ps.setString(7, r.getLoteVacuna());
            
            if (r.getProximaDosis() != null) {
                ps.setDate(8, new java.sql.Date(r.getProximaDosis().getTime())); 
            } else {
                 ps.setNull(8, java.sql.Types.DATE);
            }
            
            ps.setString(9, r.getObservaciones());
            
            int filas = ps.executeUpdate();
            
            try(ResultSet rs = ps.getGeneratedKeys()) {
               if(rs.next()){
                   int idGenerado = rs.getInt(1);
                   r.setId(idGenerado);
                   System.out.println("Registro de Vacunación insertado con ID = " + idGenerado);
               }
            }
            
            System.out.println("Registro de Vacunación agregado, filas afectadas: " + filas);
        
        }catch(SQLException ex){
            System.out.println("Error SQL al agregar registro de vacunación: " + ex.getMessage());
            ex.printStackTrace();
        
        }
    }
    
    // --- 2. READ 
    public List<registro_jornada_vacunacion> listar(){
        List<registro_jornada_vacunacion> lista = new ArrayList<>();
        String SQL = "SELECT * FROM registro_jornada_vacunacion";
        
        try(Connection con = ConexionDB.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL)
            ) {
            
            while(rs.next()){
                lista.add(mapearResultSetARegistro(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Error SQL al listar registros de vacunación: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // --- 3. READ 
    public registro_jornada_vacunacion obtenerPorId(int id) {
        String sql = "SELECT * FROM registro_jornada_vacunacion WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearResultSetARegistro(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al obtener registro de vacunación por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    // --- 4. UPDATE 
    public boolean actualizar(registro_jornada_vacunacion r) {
        String sql = "UPDATE registro_jornada_vacunacion SET jornada_id = ?, mascota_id = ?, dueno_id = ?, vacuna_id = ?, veterinario_id = ?, fecha_hora = ?, lote_vacuna = ?, proxima_dosis = ?, observaciones = ? WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, r.getJornadaId());
            ps.setInt(2, r.getMascotaId());
            ps.setInt(3, r.getDuenoId());
            ps.setInt(4, r.getVacunaId());
            
            if (r.getVeterinarioId() != null) {
                ps.setInt(5, r.getVeterinarioId());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            
            ps.setTimestamp(6, r.getFechaHora());
            ps.setString(7, r.getLoteVacuna());
            
            if (r.getProximaDosis() != null) {
                ps.setDate(8, new java.sql.Date(r.getProximaDosis().getTime())); 
            } else {
                 ps.setNull(8, java.sql.Types.DATE);
            }
            
            ps.setString(9, r.getObservaciones());
            ps.setInt(10, r.getId()); 
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al actualizar registro de vacunación: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // --- 5. DELETE 
    public boolean eliminar(int id) {
        String sql = "DELETE FROM registro_jornada_vacunacion WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al eliminar registro de vacunación: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public int obtenerConteoPorJornada(int jornadaId) {
    String sql = "SELECT COUNT(id) FROM registro_jornada_vacunacion WHERE jornada_id = ?";
    int conteo = 0;
    try (Connection conn = ConexionDB.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, jornadaId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                conteo = rs.getInt(1);
            }
        }
    } catch (SQLException e) {
        System.out.println("Error SQL al contar registros de vacunación: " + e.getMessage());
        e.printStackTrace();
    }
    return conteo;
}
}
