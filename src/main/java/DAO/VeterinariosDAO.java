/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Entities.Veterinarios;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import util.ConexionDB;
/**
 *
 * @author camper
 */
public class VeterinariosDAO {
    
     public void insertar(Veterinarios v) {
        String SQL = "INSERT INTO veterinarios(nombre_completo, documento_identidad, licencia_profesional, especialidad, telefono, email, fecha_contratacion, activo) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, v.getNombreCompleto());
            ps.setString(2, v.getDocumentoIdentidad());
            ps.setString(3, v.getlicencia());
            ps.setString(4, v.getEspecialidad());
            ps.setString(5, v.getTelefono());
            ps.setString(6, v.getEmail());
            if (v.getFechaRegistro()!= null) {
    ps.setDate(7, Date.valueOf(v.getFechaRegistro()));
} else {
    ps.setNull(7, Types.DATE);
}
            ps.setBoolean(8, v.isActivo());

            int filas = ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    System.out.println("Veterinario insertado con ID = " + idGenerado);
                }
            }

            System.out.println("Veterinario agregado, filas afectadas: " + filas);

        } catch (SQLException ex) {
            System.out.println("Error SQL al insertar veterinario: " + ex.getMessage());
        }
    }

    // 🔹 ACTUALIZAR
    public boolean actualizar(Veterinarios v) {
        String sql = "UPDATE veterinarios SET nombre_completo = ?, documento_identidad = ?, licencia_profesional = ?, "
                   + "especialidad = ?, telefono = ?, email = ?, fecha_contratacion = ?, activo = ? WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getNombreCompleto());
            ps.setString(2, v.getDocumentoIdentidad());
            ps.setString(3, v.getlicencia());
            ps.setString(4, v.getEspecialidad());
            ps.setString(5, v.getTelefono());
            ps.setString(6, v.getEmail());
            ps.setDate(7, (v.getFechaRegistro()!= null) ? Date.valueOf(v.getFechaRegistro()) : null);
            ps.setBoolean(8, v.isActivo());
            ps.setInt(9, v.getId());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔹 ELIMINAR
    public boolean eliminar(int id) {
        String sql = "DELETE FROM veterinarios WHERE id = ?";

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

    // 🔹 OBTENER POR ID
    public Veterinarios obtenerPorId(int id) {
        String sql = "SELECT * FROM veterinarios WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Veterinarios(
                    rs.getInt("id"),
                    rs.getString("nombre_completo"),
                    rs.getString("documento_identidad"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("licencia_profesional"),
                     
                    rs.getString("especialidad"),
                    (rs.getDate("fecha_contratacion") != null)
                        ? rs.getDate("fecha_contratacion").toLocalDate()
                        : null,
                    rs.getBoolean("activo")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔹 LISTAR TODOS
    public List<Veterinarios> listarTodos() {
        List<Veterinarios> lista = new ArrayList<>();
        String sql = "SELECT * FROM veterinarios";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
               
                
                Veterinarios v = new Veterinarios(
                    rs.getInt("id"),
                    rs.getString("nombre_completo"),
                    rs.getString("documento_identidad"),
                    rs.getString("licencia_profesional"),
                    rs.getString("especialidad"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    (rs.getDate("fecha_contratacion") != null)
            ? rs.getDate("fecha_contratacion").toLocalDate()
            : null,
                    rs.getBoolean("activo")
                );
                lista.add(v);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    
}
