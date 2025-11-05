/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Entities.beneficios_club;
import Model.Enums.TipoBeneficio; 
import java.math.BigDecimal;
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
public class Beneficios_clubDAO {
    // permite devolver los datos en un objeto para usar
    private beneficios_club mapearResultSetABeneficio(ResultSet rs) throws SQLException {
        
        return new beneficios_club(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("descripcion"),
            rs.getString("nivel_requerido"),
            rs.getInt("puntos_necesarios"),
            TipoBeneficio.valueOf(rs.getString("tipo_beneficio")), // Mapeo del Enum
            rs.getBigDecimal("valor_beneficio"), // java.math.BigDecimal
            rs.getBoolean("activo")
        );
    }
    
    // --- 1. CREATE 
    public void agregar(beneficios_club b){
        String SQL = "INSERT INTO beneficios_club(nombre, descripcion, nivel_requerido, puntos_necesarios, tipo_beneficio, valor_beneficio, activo) VALUES (?,?,?,?,?,?,?)";
        
        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS) ){
        
            ps.setString(1, b.getNombre());
            ps.setString(2, b.getDescripcion());
            ps.setString(3, b.getNivelRequerido());
            ps.setInt(4, b.getPuntosNecesarios());
            ps.setString(5, b.getTipoBeneficio().name()); 
            ps.setBigDecimal(6, b.getValorBeneficio());
            ps.setBoolean(7, b.isActivo());
            
            int filas = ps.executeUpdate();
            
            try(ResultSet rs = ps.getGeneratedKeys()) {
               if(rs.next()){
                   int idGenerado = rs.getInt(1);
                   b.setId(idGenerado);
                   System.out.println("Beneficio de Club insertado con ID = " + idGenerado);
               }
            }
            
            System.out.println("Beneficio de Club agregado, filas afectadas: " + filas);
        
        }catch(SQLException ex){
            System.out.println("Error SQL al agregar beneficio de club: " + ex.getMessage());
            ex.printStackTrace();
        
        }
    }
    
    // --- 2. READ 
    // Consulta común para mostrar el catálogo a los usuarios
    public List<beneficios_club> listarActivos(){
        List<beneficios_club> lista = new ArrayList<>();
        String SQL = "SELECT * FROM beneficios_club WHERE activo = TRUE ORDER BY puntos_necesarios ASC";
        
        try(Connection con = ConexionDB.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL)
            ) {
            
            while(rs.next()){
                lista.add(mapearResultSetABeneficio(rs));
            }
            
        } catch (SQLException e) {
            System.out.println("Error SQL al listar beneficios activos: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // --- 3. READ 
    public beneficios_club obtenerPorId(int id) {
        String sql = "SELECT * FROM beneficios_club WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearResultSetABeneficio(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al obtener beneficio de club por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    // --- 4. UPDATE 
    public boolean actualizar(beneficios_club b) {
        String sql = "UPDATE beneficios_club SET nombre = ?, descripcion = ?, nivel_requerido = ?, puntos_necesarios = ?, tipo_beneficio = ?, valor_beneficio = ?, activo = ? WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, b.getNombre());
            ps.setString(2, b.getDescripcion());
            ps.setString(3, b.getNivelRequerido());
            ps.setInt(4, b.getPuntosNecesarios());
            ps.setString(5, b.getTipoBeneficio().name());
            ps.setBigDecimal(6, b.getValorBeneficio());
            ps.setBoolean(7, b.isActivo());
            ps.setInt(8, b.getId()); // Condición WHERE
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al actualizar beneficio de club: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // --- 5. DELETE 
    public boolean eliminar(int id) {
        String sql = "DELETE FROM beneficios_club WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al eliminar beneficio de club: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
