/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;


import Model.Entities.Mascotas;
import Model.Enums.Sexo;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import util.ConexionDB;

/**
 *
 * @author camper
 */
public class MascotasDAO {
  
        public void insertar(Mascotas m) {
        String SQL = "INSERT INTO mascotas(dueno_id, nombre, raza_id, fecha_nacimiento, sexo, peso_actual, microchip, tatuaje, url_foto, alergias, condiciones_preexistentes, fecha_registro, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, m.getDuenoId());
            ps.setString(2, m.getNombre());
            ps.setInt(3, m.getRazaId());
            ps.setDate(4, m.getFechaNacimiento() != null ? Date.valueOf(m.getFechaNacimiento()) : null);
            ps.setString(5, m.getSexo().name());
            ps.setDouble(6, m.getPesoActual());
            ps.setString(7, m.getMicrochip());
            ps.setString(8, m.getTatuaje());
            ps.setString(9, m.getUrlFoto());
            ps.setString(10, m.getAlergias());
            ps.setString(11, m.getCondicionesPreexistentes());
            ps.setTimestamp(12, Timestamp.valueOf(m.getFechaRegistro()));
            ps.setBoolean(13, m.isActivo());

            int filas = ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    System.out.println("Mascota insertada con ID = " + idGenerado);
                }
            }

            System.out.println("Mascota agregada. Filas afectadas: " + filas);

        } catch (SQLException ex) {
            System.out.println("Error SQL al insertar mascota: " + ex.getMessage());
        }
    }
        
        // tranferir mascota
        public boolean transferirmascota(Mascotas m,int id2) {
        String SQL = "UPDATE mascotas SET dueno_id = ? WHERE id = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, id2);
            ps.setInt(2, m.getId());
            

            int filas = ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    System.out.println("Mascota insertada con ID = " + idGenerado);
                }
            }

            System.out.println("Mascota agregada. Filas afectadas: " + filas);

        } catch (SQLException ex) {
            System.out.println("Error SQL al insertar mascota: " + ex.getMessage());
        }
            return false;
        }

    // 🔹 ELIMINAR
    public boolean eliminar(int id) {
        String sql = "DELETE FROM mascotas WHERE id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            System.out.println("Mascota eliminada. Filas afectadas: " + filas);
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar mascota: " + e.getMessage());
            return false;
        }
    }

    // 🔹 ACTUALIZAR
    public boolean actualizar(Mascotas m) {
        String sql = "UPDATE mascotas SET dueno_id = ?, nombre = ?, raza_id = ?, fecha_nacimiento = ?, sexo = ?, peso_actual = ?, microchip = ?, tatuaje = ?, url_foto = ?, alergias = ?, condiciones_preexistentes = ?, fecha_registro = ?, activo = ? WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, m.getDuenoId());
            ps.setString(2, m.getNombre());
            ps.setInt(3, m.getRazaId());
            ps.setDate(4, m.getFechaNacimiento() != null ? Date.valueOf(m.getFechaNacimiento()) : null);
            ps.setString(5, m.getSexo().name());
            ps.setDouble(6, m.getPesoActual() );
            ps.setString(7, m.getMicrochip());
            ps.setString(8, m.getTatuaje());
            ps.setString(9, m.getUrlFoto());
            ps.setString(10, m.getAlergias());
            ps.setString(11, m.getCondicionesPreexistentes());
            ps.setTimestamp(12, Timestamp.valueOf(m.getFechaRegistro()));
            ps.setBoolean(13, m.isActivo());
            ps.setInt(14, m.getId());

            int filas = ps.executeUpdate();
            System.out.println("Mascota actualizada. Filas afectadas: " + filas);
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al actualizar mascota: " + e.getMessage());
            return false;
        }
    }

    
    public Mascotas obtenerPorId(int id) {
        String sql = "SELECT m.*,r.nombre AS Raza, d.nombre_completo AS nombredueño FROM mascotas m JOIN razas r ON m.raza_id = r.id JOIN duenos d ON m.dueno_id = d.id WHERE m.id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Mascotas(
                    rs.getInt("id"),
                    rs.getInt("dueno_id"),
                    rs.getString("nombre"),
                    rs.getString("Raza"),
                    rs.getString("nombredueño"),
                    rs.getInt("raza_id"),
                    rs.getDate("fecha_nacimiento") != null ? rs.getDate("fecha_nacimiento").toLocalDate() : null,
                    Sexo.valueOf(rs.getString("sexo").toUpperCase()), 
                    rs.getDouble("peso_actual"),
                    rs.getString("microchip"),
                    rs.getString("tatuaje"),
                    rs.getString("url_foto"),
                    rs.getString("alergias"),
                    rs.getString("condiciones_preexistentes"),
                    rs.getTimestamp("fecha_registro").toLocalDateTime(),
                    rs.getBoolean("activo")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al obtener mascota: " + e.getMessage());
        }
        return null;
    }

    
    public List<Mascotas> listarTodas() {
        List<Mascotas> lista = new ArrayList<>();
        String sql = "SELECT m.*,r.nombre AS Raza, d.nombre_completo AS nombredueño FROM mascotas m JOIN razas r ON m.raza_id = r.id JOIN duenos d ON m.dueno_id = d.id";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Mascotas m = new Mascotas(
                    rs.getInt("id"),
                    rs.getInt("dueno_id"),
                    rs.getString("nombre"),
                    rs.getString("Raza"), 
                    rs.getString("nombredueño"),    
                    rs.getInt("raza_id"),
                    rs.getDate("fecha_nacimiento") != null ? rs.getDate("fecha_nacimiento").toLocalDate() : null,
                     Sexo.valueOf(rs.getString("sexo").toUpperCase()),
                    rs.getDouble("peso_actual"),
                    rs.getString("microchip"),
                    rs.getString("tatuaje"),
                    rs.getString("url_foto"),
                    rs.getString("alergias"),
                    rs.getString("condiciones_preexistentes"),
                    rs.getTimestamp("fecha_registro").toLocalDateTime(),
                    rs.getBoolean("activo")
                );
                lista.add(m);
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al listar mascotas: " + e.getMessage());
        }
        return lista;
    }
    
    
     public List<Mascotas> obtenerpeso(int id) {
          List<Mascotas> listapesos = new ArrayList<>();
        String sql = "SELECT m.*, cm.peso_registrado AS peso,cm.fecha_hora AS Fecha FROM mascotas m JOIN consultas_medicas cm ON m.id = cm.mascota_id  WHERE cm.mascota_id = ? AND cm.peso_registrado IS NOT NULL";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
               Mascotas m = new Mascotas(
                        rs.getString("nombre"),
                        rs.getInt("peso"),
                        rs.getTimestamp("Fecha").toLocalDateTime());
                    
                    
                     listapesos.add(m);     
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al obtener mascota: " + e.getMessage());
        }
        return listapesos;
    }
    
     public Mascotas obtenerprocedimiento(int id) {
        String sql = "SELECT m.*, p.nombre_procedimiento as nombrepro FROM mascotas m JOIN procedimientos_especiales p ON m.id = p.mascota_id  WHERE p.mascota_id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Mascotas(
                   
                   
                    rs.getString("nombre"),
                    rs.getString("nombrepro"),
                    rs.getDouble("peso_actual"),
                    rs.getString("alergias"),
                    rs.getString("condiciones_preexistentes"));
                    
                    
                          
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al obtener mascota: " + e.getMessage());
        }
        return null;
    }
     
     
     
     
    
    
     
    
    
    
    
}
       
