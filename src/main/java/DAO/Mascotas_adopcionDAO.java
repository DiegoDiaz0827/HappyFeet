/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Entities.mascotas_adopcion;
import Model.Enums.EstadoAdopcion;
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
public class Mascotas_adopcionDAO {
    

    private mascotas_adopcion mapearResultSetAMascotaAdopcion(ResultSet rs) throws SQLException {
        Date fechaIngreso = rs.getDate("fecha_ingreso"); 
        
        return new mascotas_adopcion(
            rs.getInt("id"),
            rs.getInt("mascota_id"),
            fechaIngreso,
            rs.getString("motivo_ingreso"),
            EstadoAdopcion.valueOf(rs.getString("estado")), 
            rs.getString("historia"),
            rs.getString("temperamento"),
            rs.getString("necesidades_especiales"),
            rs.getString("foto_adicional_url")
        );
    }
    
    // --- 1. CREATE 
    public void agregar(mascotas_adopcion m){
        String SQL = "INSERT INTO mascotas_adopcion(mascota_id, fecha_ingreso, motivo_ingreso, estado, historia, temperamento, necesidades_especiales, foto_adicional_url) VALUES (?,?,?,?,?,?,?,?)";
        
        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS) ){
        
            ps.setInt(1, m.getMascotaId());
            ps.setDate(2, new java.sql.Date(m.getFechaIngreso().getTime())); 
            ps.setString(3, m.getMotivoIngreso());
            ps.setString(4, m.getEstado().name()); 
            ps.setString(5, m.getHistoria());
            ps.setString(6, m.getTemperamento());
            ps.setString(7, m.getNecesidadesEspeciales());
            ps.setString(8, m.getFotoAdicionalUrl());
            
            int filas = ps.executeUpdate();
            
            try(ResultSet rs = ps.getGeneratedKeys()) {
               if(rs.next()){
                   int idGenerado = rs.getInt(1);
                   m.setId(idGenerado);
                   System.out.println("Registro de Adopción insertado con ID = " + idGenerado);
               }
            }
            
            System.out.println("Registro de Adopción agregado, filas afectadas: " + filas);
        
        }catch(SQLException ex){
            System.out.println("Error SQL al agregar registro de adopción: " + ex.getMessage());
            ex.printStackTrace();
        
        }
    }
    
    // --- 2. READ 
    public List<mascotas_adopcion> listar(){
        List<mascotas_adopcion> lista = new ArrayList<>();
        String SQL = "SELECT * FROM mascotas_adopcion";
        
        try(Connection con = ConexionDB.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL)
            ) {
            
            while(rs.next()){
                lista.add(mapearResultSetAMascotaAdopcion(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Error SQL al listar registros de adopción: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // --- 3. READ 
    public mascotas_adopcion obtenerPorId(int id) {
        String sql = "SELECT * FROM mascotas_adopcion WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearResultSetAMascotaAdopcion(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al obtener registro de adopción por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    // --- 4. UPDATE 
    public boolean actualizar(mascotas_adopcion m) {
        String sql = "UPDATE mascotas_adopcion SET mascota_id = ?, fecha_ingreso = ?, motivo_ingreso = ?, estado = ?, historia = ?, temperamento = ?, necesidades_especiales = ?, foto_adicional_url = ? WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, m.getMascotaId());
            ps.setDate(2, new java.sql.Date(m.getFechaIngreso().getTime())); 
            ps.setString(3, m.getMotivoIngreso());
            ps.setString(4, m.getEstado().name());
            ps.setString(5, m.getHistoria());
            ps.setString(6, m.getTemperamento());
            ps.setString(7, m.getNecesidadesEspeciales());
            ps.setString(8, m.getFotoAdicionalUrl());
            ps.setInt(9, m.getId()); 
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al actualizar registro de adopción: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // --- 5. DELETE 
    public boolean eliminar(int id) {
        String sql = "DELETE FROM mascotas_adopcion WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al eliminar registro de adopción: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    // --- 6. MÉTODO DE LÓGICA DE NEGOCIO
    public boolean actualizarEstado(int mascotaAdopcionId, EstadoAdopcion nuevoEstado) {
        
        String SQL = "UPDATE mascotas_adopcion SET estado = ? WHERE id = ?";
        
        try (Connection con = ConexionDB.conectar(); 
             PreparedStatement ps = con.prepareStatement(SQL)) {
            
            // 1. Convertir el Enum a String (esto es lo que la columna 'estado' almacena)
            ps.setString(1, nuevoEstado.name()); 
            
            // 2. ID del registro a actualizar
            ps.setInt(2, mascotaAdopcionId);
            
            int filasAfectadas = ps.executeUpdate();
            
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error SQL al actualizar el estado de adopción: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean verificarDisponibilidad(int mascotaAdopcionId) {
        mascotas_adopcion m = obtenerPorId(mascotaAdopcionId);
        
        return m != null && m.getEstado() == EstadoAdopcion.DISPONIBLE;
    }
}
