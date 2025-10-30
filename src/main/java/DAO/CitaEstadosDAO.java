/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Entities.Especies;
import Model.Entities.citaEstados;
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
public class CitaEstadosDAO {
    
    public void agregar(citaEstados ce){
    
        String SQL = "INSERT INTO cita_estados(nombre,descripcion) VALUES (?,?)";
        
        try(Connection con = ConexionDB.conectar();
                PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS) ){
        
            ps.setString(1, ce.getNombre());
            ps.setString(2, ce.getDescripcion());
            
            int filas = ps. executeUpdate();
            
            try(ResultSet rs = ps.getGeneratedKeys()) {
               if(rs.next()){
               int idGenerado = rs.getInt(1);
                   System.out.println("especie insertado con id = " + idGenerado);
               
               }
                
                
            } 
            
            System.out.println("especie agregado filas afectadas: " + filas);
        
        }catch(SQLException ex){
       
            System.out.println("error sql: " + ex.getMessage());
        
        }
    
    }
     
     
    
      public boolean eliminar(int id) {
        String sql = "DELETE FROM citas_estados WHERE id = ?";
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

     
     
     
     public boolean actualizar(citaEstados ct) {
        String sql = "UPDATE citas_estados SET nombre = ?, descripcion = ? WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ct.getNombre());
            ps.setString(2, ct.getDescripcion());
            ps.setInt(3, ct.getId());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

     
     
     
     
      public citaEstados obtenerPorId(int id) {
        String sql = "SELECT * FROM citas_estados WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new citaEstados(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public List<citaEstados> Listar(){
    List<citaEstados> lista = new ArrayList<>();
    
    String SQL = " SELECT id, nombre, descripcion FROM cita_estados";
    
        try(Connection con = ConexionDB.conectar();
         Statement st  = con.createStatement();
         ResultSet rs = st.executeQuery(SQL)
                ) {
            
            while(rs.next()){
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");
            String descripcion = rs.getString("descripcion");
            
            
                citaEstados ct = new citaEstados(id, nombre, descripcion);
            lista.add(ct);
            
            }
            
            
        } catch (SQLException e) {
            
            System.out.println("Error sql " + e.getMessage());
            e.printStackTrace();
        }
    return lista;
    }
    
    
    
    
}
