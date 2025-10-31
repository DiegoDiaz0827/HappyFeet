/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;


import Model.Entities.Razas;
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
//tablas consulta
public class RazasDAO {
    
    public void agregar(Razas rz){
    
        String SQL = "INSERT INTO razas(especie_id,nombre,caracteristicas) VALUES (?,?,?)";
        
        try(Connection con = ConexionDB.conectar();
                PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS) ){
        
            ps.setInt(1, rz.getEspecieId());
            ps.setString(2, rz.getNombre());
            ps.setString(3, rz.getCaracteristicas());
            int filas = ps. executeUpdate();
            
            try(ResultSet rs = ps.getGeneratedKeys()) {
               if(rs.next()){
               int idGenerado = rs.getInt(1);
                   System.out.println("raza insertado con id = " + idGenerado);
               
               }
                
                
            } 
            
            System.out.println("raza  agregado filas afectadas: " + filas);
        
        }catch(SQLException ex){
       
            System.out.println("error sql: " + ex.getMessage());
        
        }
    
    }
     
     
    
      public boolean eliminar(int id) {
        String sql = "DELETE FROM razas WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

     
     
     
     public boolean actualizar(Razas rz) {
        String sql = "UPDATE razas SET especie_id = ?, nombre = ?,caracteristicas = ? WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt (1, rz.getEspecieId());
            ps.setString(2, rz.getNombre());
            ps.setString(3, rz.getCaracteristicas());
            ps.setInt(4, rz.getId());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

     
     
     
     
      public Razas obtenerPorId(int id) {
        String sql = "SELECT * FROM razas WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Razas(
                    rs.getInt("especie_id"),
                    rs.getString("nombre"),
                    rs.getString("caracteristicas")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public List<Razas> Listar(){
    List<Razas> lista = new ArrayList<>();
    
    String SQL = " SELECT id,especie_id,nombre,caracteristicas FROM razas";
    
        try(Connection con = ConexionDB.conectar();
         Statement st  = con.createStatement();
         ResultSet rs = st.executeQuery(SQL)
                ) {
            
            while(rs.next()){
            int id = rs.getInt("id");
            int especieid = rs.getInt("especie_id");
            String nombre = rs.getString("nombre");
            String caracteristicas = rs.getString("caracteristicas");
            
            
            Razas rz = new Razas(id,especieid,nombre,caracteristicas);
            lista.add(rz);
            
            }
            
            
        } catch (SQLException e) {
            
            System.out.println("Error sql " + e.getMessage());
            e.printStackTrace();
        }
    return lista;
    }
    
    
    
    
    
}
