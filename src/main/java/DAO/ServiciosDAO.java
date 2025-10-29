/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Entities.Servicios;
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
public class ServiciosDAO {
    public void agregar(Servicios s){
        // Excluye 'id' (AUTO_INCREMENT) y 'activo' (tiene DEFAULT TRUE en DB,
        // pero lo incluimos para mayor control o si el usuario lo setea explícitamente)
        String SQL = "INSERT INTO servicios(nombre, descripcion, categoria, precio_base, duracion_estimada_minutos, activo) VALUES (?,?,?,?,?,?)";
        
        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS) ){
        
            ps.setString(1, s.getNombre());
            ps.setString(2, s.getDescripcion());
            ps.setString(3, s.getCategoria());
            ps.setBigDecimal(4, s.getPrecioBase());
            ps.setInt(5, s.getDuracionEstimadaMinutos());
            ps.setBoolean(6, s.isActivo());
            
            int filas = ps.executeUpdate();
            
            try(ResultSet rs = ps.getGeneratedKeys()) {
               if(rs.next()){
                   int idGenerado = rs.getInt(1);
                   // Asigna el ID generado de vuelta al objeto Servicios
                   s.setId(idGenerado);
                   System.out.println("Servicio insertado con ID = " + idGenerado);
               }
            }
            
            System.out.println("Servicio agregado, filas afectadas: " + filas);
        
        }catch(SQLException ex){
            System.out.println("Error SQL al agregar servicio: " + ex.getMessage());
            ex.printStackTrace();
        
        }
    }
    
    // --- 2. READ (Listar todos) 📋 ---
    public List<Servicios> listar(){
        List<Servicios> lista = new ArrayList<>();
        
        String SQL = "SELECT id, nombre, descripcion, categoria, precio_base, duracion_estimada_minutos, activo FROM servicios";
        
        try(Connection con = ConexionDB.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL)
            ) {
            
            while(rs.next()){
                Servicios s = new Servicios(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getString("categoria"),
                    rs.getBigDecimal("precio_base"),
                    rs.getInt("duracion_estimada_minutos"),
                    rs.getBoolean("activo")
                );
                lista.add(s);
            }
            
        } catch (SQLException e) {
            System.out.println("Error SQL al listar servicios: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // --- 3. READ (Obtener por ID) 🔍 ---
    public Servicios obtenerPorId(int id) {
        String sql = "SELECT id, nombre, descripcion, categoria, precio_base, duracion_estimada_minutos, activo FROM servicios WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Servicios(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getString("categoria"),
                    rs.getBigDecimal("precio_base"),
                    rs.getInt("duracion_estimada_minutos"),
                    rs.getBoolean("activo")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al obtener servicio por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    // --- 4. UPDATE (Actualizar) ✏️ ---
    public boolean actualizar(Servicios servicio) {
        String sql = "UPDATE servicios SET nombre = ?, descripcion = ?, categoria = ?, precio_base = ?, duracion_estimada_minutos = ?, activo = ? WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, servicio.getNombre());
            ps.setString(2, servicio.getDescripcion());
            ps.setString(3, servicio.getCategoria());
            ps.setBigDecimal(4, servicio.getPrecioBase());
            ps.setInt(5, servicio.getDuracionEstimadaMinutos());
            ps.setBoolean(6, servicio.isActivo());
            ps.setInt(7, servicio.getId()); // Condición WHERE
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0; // Retorna true si se actualizó una o más filas

        } catch (SQLException e) {
            System.out.println("Error SQL al actualizar servicio: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // --- 5. DELETE (Eliminar) 🗑️ ---
    public boolean eliminar(int id) {
        String sql = "DELETE FROM servicios WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0; // Retorna true si se eliminó al menos una fila

        } catch (SQLException e) {
            System.out.println("Error SQL al eliminar servicio: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
