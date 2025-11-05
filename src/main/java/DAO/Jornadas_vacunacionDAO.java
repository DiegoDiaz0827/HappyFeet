/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Entities.jornadas_vacunacion;
import Model.Enums.EstadoVacunacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import util.ConexionDB;
/**
 *
 * @author camper
 */
public class Jornadas_vacunacionDAO {
    private jornadas_vacunacion mapearResultSetAJornada(ResultSet rs) throws SQLException {
        java.util.Date fecha = rs.getDate("fecha"); 
        
        Integer capacidadMaxima = rs.getObject("capacidad_maxima") != null ? rs.getInt("capacidad_maxima") : null;
        
        return new jornadas_vacunacion(
            rs.getInt("id"),
            rs.getString("nombre"),
            fecha,
            rs.getTime("hora_inicio"), 
            rs.getTime("hora_fin"),  
            rs.getString("ubicacion"),
            rs.getString("descripcion"),
            capacidadMaxima,
            EstadoVacunacion.valueOf(rs.getString("estado")) 
        );
    }
    
    public void agregar(jornadas_vacunacion j){
        String SQL = "INSERT INTO jornadas_vacunacion(nombre, fecha, hora_inicio, hora_fin, ubicacion, descripcion, capacidad_maxima, estado) VALUES (?,?,?,?,?,?,?,?)";
        
        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS) ){
        
            ps.setString(1, j.getNombre());
            ps.setDate(2, new java.sql.Date(j.getFecha().getTime())); 
            ps.setTime(3, j.getHoraInicio());
            ps.setTime(4, j.getHoraFin());
            ps.setString(5, j.getUbicacion());
            ps.setString(6, j.getDescripcion());
            
            if (j.getCapacidadMaxima() != null) {
                ps.setInt(7, j.getCapacidadMaxima());
            } else {
                ps.setNull(7, java.sql.Types.INTEGER);
            }
            
            ps.setString(8, j.getEstado().name()); 
            
            int filas = ps.executeUpdate();
            
            try(ResultSet rs = ps.getGeneratedKeys()) {
               if(rs.next()){
                   int idGenerado = rs.getInt(1);
                   j.setId(idGenerado);
                   System.out.println("Jornada de Vacunación insertada con ID = " + idGenerado);
               }
            }
            
            System.out.println("Jornada de Vacunación agregada, filas afectadas: " + filas);
        
        }catch(SQLException ex){
            System.out.println("Error SQL al agregar jornada de vacunación: " + ex.getMessage());
            ex.printStackTrace();
        
        }
    }
    
    // --- 2. READ 
    public List<jornadas_vacunacion> listar(){
        List<jornadas_vacunacion> lista = new ArrayList<>();
        String SQL = "SELECT * FROM jornadas_vacunacion";
        
        try(Connection con = ConexionDB.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL)
            ) {
            
            while(rs.next()){
                lista.add(mapearResultSetAJornada(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Error SQL al listar jornadas de vacunación: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // --- 3. READ 
    public jornadas_vacunacion obtenerPorId(int id) {
        String sql = "SELECT * FROM jornadas_vacunacion WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearResultSetAJornada(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al obtener jornada de vacunación por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    // --- 4. UPDATE
    public boolean actualizar(jornadas_vacunacion j) {
        String sql = "UPDATE jornadas_vacunacion SET nombre = ?, fecha = ?, hora_inicio = ?, hora_fin = ?, ubicacion = ?, descripcion = ?, capacidad_maxima = ?, estado = ? WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, j.getNombre());
            ps.setDate(2, new java.sql.Date(j.getFecha().getTime()));
            ps.setTime(3, j.getHoraInicio());
            ps.setTime(4, j.getHoraFin());
            ps.setString(5, j.getUbicacion());
            ps.setString(6, j.getDescripcion());
            
            if (j.getCapacidadMaxima() != null) {
                ps.setInt(7, j.getCapacidadMaxima());
            } else {
                ps.setNull(7, java.sql.Types.INTEGER);
            }
            
            ps.setString(8, j.getEstado().name());
            ps.setInt(9, j.getId()); 
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al actualizar jornada de vacunación: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // --- 5. DELETE 
    public boolean eliminar(int id) {
        String sql = "DELETE FROM jornadas_vacunacion WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al eliminar jornada de vacunación: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
