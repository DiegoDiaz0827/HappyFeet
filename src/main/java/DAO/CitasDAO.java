/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Entities.Citas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp; 
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import util.ConexionDB;

/**
 *
 * @author camper
 */
public class CitasDAO {
    public void agregar(Citas cita) {

        String SQL = "INSERT INTO citas(mascota_id, veterinario_id, fecha_hora, motivo, estado_id, observaciones, fecha_creacion) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, cita.getMascotaId());
            if (cita.getVeterinarioId() != null) {
                ps.setInt(2, cita.getVeterinarioId());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }
            ps.setTimestamp(3, Timestamp.valueOf(cita.getFechaHora())); 
            ps.setString(4, cita.getMotivo());
            ps.setInt(5, cita.getEstadoId());
            ps.setString(6, cita.getObservaciones());
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now())); 

            int filas = ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    cita.setId(idGenerado); // Asignamos el ID al objeto.
                    System.out.println("Cita insertada con ID = " + idGenerado);
                }
            }
            System.out.println("Cita agregada, filas afectadas: " + filas);

        } catch (SQLException ex) {
            System.out.println("Error SQL al agregar cita: " + ex.getMessage());
            ex.printStackTrace();
        }
    }



    public List<Citas> listar() {
        List<Citas> lista = new ArrayList<>();

        String SQL = "SELECT *,ct.nombre AS nombreestado FROM citas JOIN cita_estados ct ON citas.estado_id = ct.id";

        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(SQL)) {

            while (rs.next()) {
                LocalDateTime fechaHora = rs.getTimestamp("fecha_hora").toLocalDateTime();
                LocalDateTime fechaCreacion = rs.getTimestamp("fecha_creacion").toLocalDateTime();
                
                Integer veterinarioId = (Integer) rs.getObject("veterinario_id");

                Citas cita = new Citas(
                    rs.getInt("id"),
                    rs.getInt("mascota_id"),
                    rs.getString("nombreestado"),
                    veterinarioId,
                    fechaHora,
                    rs.getString("motivo"),
                    rs.getInt("estado_id"),
                    rs.getString("observaciones"),
                    fechaCreacion
                );
                lista.add(cita);
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al listar citas: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }


    public Citas obtenerPorId(int id) {
        String sql = "SELECT *,ct.nombre AS nombreestado FROM citas c JOIN cita_estados ct ON c.estado_id = ct.id WHERE c.id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    LocalDateTime fechaHora = rs.getTimestamp("fecha_hora").toLocalDateTime();
                    LocalDateTime fechaCreacion = rs.getTimestamp("fecha_creacion").toLocalDateTime();
                    Integer veterinarioId = (Integer) rs.getObject("veterinario_id");
                    
                    return new Citas(
                        rs.getInt("id"),
                        rs.getInt("mascota_id"),
                        rs.getString("nombreestado"),
                        veterinarioId,
                        fechaHora,
                        rs.getString("motivo"),
                        rs.getInt("estado_id"),
                        rs.getString("observaciones"),
                        fechaCreacion
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al obtener cita por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    

    public boolean actualizar(Citas cita) {
        String sql = "UPDATE citas SET mascota_id = ?, veterinario_id = ?, fecha_hora = ?, motivo = ?, estado_id = ?, observaciones = ? WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cita.getMascotaId());
            if (cita.getVeterinarioId() != null) {
                ps.setInt(2, cita.getVeterinarioId());
            } else {
                ps.setNull(2, java.sql.Types.INTEGER);
            }
            ps.setTimestamp(3, Timestamp.valueOf(cita.getFechaHora()));
            ps.setString(4, cita.getMotivo());
            ps.setInt(5, cita.getEstadoId());
            ps.setString(6, cita.getObservaciones());
            
            ps.setInt(7, cita.getId()); 

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al actualizar cita: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public boolean eliminar(int id) {
        String sql = "DELETE FROM citas WHERE id = ?";
        
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id); 
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0; 

        } catch (SQLException e) {
            System.out.println("Error SQL al eliminar cita: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}

