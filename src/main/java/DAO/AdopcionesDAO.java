/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Entities.adopciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import util.ConexionDB;

/**
 *
 * @author camper
 */
public class AdopcionesDAO {
    private adopciones mapearResultSetAAdopcion(ResultSet rs) throws SQLException {
        Date fechaAdopcion = rs.getDate("fecha_adopcion");
        Date fechaPrimerSeguimiento = rs.getDate("fecha_primer_seguimiento");
        
        return new adopciones(
            rs.getInt("id"),
            rs.getInt("mascota_adopcion_id"),
            rs.getInt("dueno_id"),
            fechaAdopcion,
            rs.getString("contrato_texto"),
            rs.getString("condiciones_especiales"),
            rs.getBoolean("seguimiento_requerido"),
            fechaPrimerSeguimiento
        );
    }
    
    // --- 1. CREATE 
    public void agregar(adopciones a){
        String SQL = "INSERT INTO adopciones(mascota_adopcion_id, dueno_id, fecha_adopcion, contrato_texto, condiciones_especiales, seguimiento_requerido, fecha_primer_seguimiento) VALUES (?,?,?,?,?,?,?)";
        
        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS) ){
        
            ps.setInt(1, a.getMascotaAdopcionId());
            ps.setInt(2, a.getDuenoId());
            ps.setDate(3, new java.sql.Date(a.getFechaAdopcion().getTime())); 
            ps.setString(4, a.getContratoTexto());
            ps.setString(5, a.getCondicionesEspeciales());
            ps.setBoolean(6, a.isSeguimientoRequerido());
            
            if (a.getFechaPrimerSeguimiento() != null) {
                ps.setDate(7, new java.sql.Date(a.getFechaPrimerSeguimiento().getTime())); 
            } else {
                ps.setNull(7, java.sql.Types.DATE);
            }
            
            int filas = ps.executeUpdate();
            
            try(ResultSet rs = ps.getGeneratedKeys()) {
               if(rs.next()){
                   int idGenerado = rs.getInt(1);
                   a.setId(idGenerado);
                   System.out.println("Adopción registrada con ID = " + idGenerado);
               }
            }
            
            System.out.println("Adopción agregada, filas afectadas: " + filas);
        
        }catch(SQLException ex){
            System.out.println("Error SQL al registrar adopción: " + ex.getMessage());
            ex.printStackTrace();
        
        }
    }
    
    // --- 2. READ 
    public List<adopciones> listar(){
        List<adopciones> lista = new ArrayList<>();
        String SQL = "SELECT * FROM adopciones";
        
        try(Connection con = ConexionDB.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL)
            ) {
            
            while(rs.next()){
                lista.add(mapearResultSetAAdopcion(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Error SQL al listar adopciones: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // --- 3. READ 
    public adopciones obtenerPorId(int id) {
        String sql = "SELECT * FROM adopciones WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearResultSetAAdopcion(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al obtener adopción por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    // --- 4. UPDATE 
    public boolean actualizar(adopciones a) {
        String sql = "UPDATE adopciones SET mascota_adopcion_id = ?, dueno_id = ?, fecha_adopcion = ?, contrato_texto = ?, condiciones_especiales = ?, seguimiento_requerido = ?, fecha_primer_seguimiento = ? WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, a.getMascotaAdopcionId());
            ps.setInt(2, a.getDuenoId());
            ps.setDate(3, new java.sql.Date(a.getFechaAdopcion().getTime())); 
            ps.setString(4, a.getContratoTexto());
            ps.setString(5, a.getCondicionesEspeciales());
            ps.setBoolean(6, a.isSeguimientoRequerido());
            
            if (a.getFechaPrimerSeguimiento() != null) {
                ps.setDate(7, new java.sql.Date(a.getFechaPrimerSeguimiento().getTime())); 
            } else {
                ps.setNull(7, java.sql.Types.DATE);
            }
            
            ps.setInt(8, a.getId()); 
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al actualizar adopción: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // --- 5. DELETE ️ 
    public boolean eliminar(int id) {
        String sql = "DELETE FROM adopciones WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al eliminar adopción: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
