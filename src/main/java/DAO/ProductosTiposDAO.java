/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Entities.Especies;
import Model.Entities.ProductoTipos;
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

// tablas consulta
public class ProductosTiposDAO {
    
    
    public void agregar(ProductoTipos pt){
    
        String SQL = "INSERT INTO producto_tipos(nombre,descripcion) VALUES (?,?)";
        
        try(Connection con = ConexionDB.conectar();
                PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS) ){
        
            ps.setString(1, pt.getNombre());
            ps.setString(2, pt.getDescripcion());
            
            int filas = ps. executeUpdate();
            
            try(ResultSet rs = ps.getGeneratedKeys()) {
               if(rs.next()){
               int idGenerado = rs.getInt(1);
                   System.out.println("producto insertado con id = " + idGenerado);
               
               }
                
                
            } 
            
            System.out.println("producto agregado filas afectadas: " + filas);
        
        }catch(SQLException ex){
       
            System.out.println("error sql: " + ex.getMessage());
        
        }
    
    }
     
     
    
      public boolean eliminar(int id) {
        String sql = "DELETE FROM productos_tipos WHERE id = ?";
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

     
     
     
     public boolean actualizar(ProductoTipos pt) {
        String sql = "UPDATE productos_tipos SET nombre = ?, descripcion = ? WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pt.getNombre());
            ps.setString(2, pt.getDescripcion());
            ps.setInt(3, pt.getId());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

     
     
     
     
      public ProductoTipos obtenerPorId(int id) {
        String sql = "SELECT * FROM productos_tipos WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new ProductoTipos(
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
    
    
    public List<ProductoTipos> Listar(){
    List<ProductoTipos> lista = new ArrayList<>();
    
    String SQL = " SELECT id, nombre, descripcion FROM productos_tipos";
    
        try(Connection con = ConexionDB.conectar();
         Statement st  = con.createStatement();
         ResultSet rs = st.executeQuery(SQL)
                ) {
            
            while(rs.next()){
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");
            String descripcion = rs.getString("descripcion");
            
            
            ProductoTipos pt = new ProductoTipos(id, nombre, descripcion);
            lista.add(pt);
            
            }
            
            
        } catch (SQLException e) {
            
            System.out.println("Error sql " + e.getMessage());
            e.printStackTrace();
        }
    return lista;
    }
    
    
    
}

