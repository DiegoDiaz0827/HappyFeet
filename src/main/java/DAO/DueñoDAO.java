/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Entities.Dueños;
import Model.Entities.citaEstados;
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
public class DueñoDAO {
    
     public void agregar(Dueños d){
    
        String SQL = "INSERT INTO duenos(nombre_completo,documento_identidad,direccion, telefono,email,contacto_emergencia,fecha_registro,activo ) VALUES (?,?,?,?,?,?,?,?)";
        
        try(Connection con = ConexionDB.conectar();
                PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS) ){
        
            ps.setString(1, d.getNombreCompleto());
            ps.setString(2, d.getDocumentoIdentidad());
            ps.setString(3, d.getDireccion());
            ps.setString(4, d.getTelefono());
            ps.setString(5, d.getEmail());
            ps.setString(6, d.getContactoEmergencia());
            ps.setTimestamp(7, Timestamp.valueOf(d.getFechaRegistro()));
            ps.setBoolean(8, d.isActivo());
           
            int filas = ps. executeUpdate();
            
            try(ResultSet rs = ps.getGeneratedKeys()) {
               if(rs.next()){
               int idGenerado = rs.getInt(1);
                   System.out.println("dueño insertado con id = " + idGenerado);
               
               }
                
                
            } 
            
            System.out.println("dueño agregado filas afectadas: " + filas);
        
        }catch(SQLException ex){
       
            System.out.println("error sql: " + ex.getMessage());
        
        }
    
    }
     
     
    
      public boolean eliminar(int id) {
        String sql = "DELETE FROM duenos WHERE id = ?";
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

     
     
     
     public boolean actualizar(Dueños d) {
        String sql = "UPDATE duenos SET nombre_completo = ?,documento_identidad = ?,direccion = ?, telefono = ?,email = ?,"
                + "contacto_emergencia = ?,fecha_registro = ?,activo = ? WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getNombreCompleto());
            ps.setString(2, d.getDocumentoIdentidad());
            ps.setString(3, d.getDireccion());
            ps.setString(4, d.getTelefono());
            ps.setString(5, d.getEmail());
            ps.setString(6, d.getContactoEmergencia());
            ps.setTimestamp(7, Timestamp.valueOf(d.getFechaRegistro()));
            ps.setBoolean(8, d.isActivo());
            ps.setInt(9, d.getId());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

     
     
     
     
      public Dueños obtenerPorId(int id) {
        String sql = "SELECT * FROM duenos WHERE id = ?";
        
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Dueños(
                    rs.getInt("id"),
                    rs.getString("nombre_completo"),
                    rs.getString("documento_identidad"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("contacto_emergencia"),
                    rs.getTimestamp("fecha_registro").toLocalDateTime(),
                    rs.getBoolean("activo")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
       public List<Dueños> listarTodos() {
        List<Dueños> lista = new ArrayList<>();
        String sql = "SELECT * FROM duenos";
        
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Dueños d = new Dueños(
                    rs.getInt("id"),
                    rs.getString("nombre_completo"),
                    rs.getString("documento_identidad"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("contacto_emergencia"),
                    rs.getTimestamp("fecha_registro").toLocalDateTime(),
                    rs.getBoolean("activo")
                );
                lista.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    
    
    
}
