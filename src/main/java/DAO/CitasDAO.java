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

        String SQL = "INSERT INTO citas(mascotaId, veterinarioId, fechaHora, motivo, estadoId, observaciones, fechaCreacion) VALUES (?, ?, ?, ?, ?, ?, ?)";

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

        String SQL = "SELECT id, mascotaId, veterinarioId, fechaHora, motivo, estadoId, observaciones, fechaCreacion FROM citas";

        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(SQL)) {

            while (rs.next()) {
                LocalDateTime fechaHora = rs.getTimestamp("fechaHora").toLocalDateTime();
                LocalDateTime fechaCreacion = rs.getTimestamp("fechaCreacion").toLocalDateTime();
                
                Integer veterinarioId = (Integer) rs.getObject("veterinarioId");

                Citas cita = new Citas(
                    rs.getInt("id"),
                    rs.getInt("mascotaId"),
                    veterinarioId,
                    fechaHora,
                    rs.getString("motivo"),
                    rs.getInt("estadoId"),
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
        String sql = "SELECT id, mascotaId, veterinarioId, fechaHora, motivo, estadoId, observaciones, fechaCreacion FROM citas WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    LocalDateTime fechaHora = rs.getTimestamp("fechaHora").toLocalDateTime();
                    LocalDateTime fechaCreacion = rs.getTimestamp("fechaCreacion").toLocalDateTime();
                    Integer veterinarioId = (Integer) rs.getObject("veterinarioId");
                    
                    return new Citas(
                        rs.getInt("id"),
                        rs.getInt("mascotaId"),
                        veterinarioId,
                        fechaHora,
                        rs.getString("motivo"),
                        rs.getInt("estadoId"),
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
        String sql = "UPDATE citas SET mascotaId = ?, veterinarioId = ?, fechaHora = ?, motivo = ?, estadoId = ?, observaciones = ? WHERE id = ?";

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

