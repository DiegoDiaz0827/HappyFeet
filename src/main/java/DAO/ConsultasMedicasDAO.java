/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Entities.ConsultasMedicas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import util.ConexionDB;

/**
 *
 * @author camper
 */
public class ConsultasMedicasDAO {
    
    public void insertar(ConsultasMedicas c) {
        String SQL = "INSERT INTO consultas_medicas(mascota_id, veterinario_id, cita_id, fecha_hora, motivo, sintomas, diagnostico, recomendaciones, observaciones, peso_registrado, temperatura) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, c.getMascotaId());
            ps.setInt(2, c.getVeterinarioId());
            if (c.getCitaId() != null) ps.setInt(3, c.getCitaId());
            else ps.setNull(3, Types.INTEGER);

            ps.setTimestamp(4, Timestamp.valueOf(c.getFechaHora()));
            ps.setString(5, c.getMotivo());
            ps.setString(6, c.getSintomas());
            ps.setString(7, c.getDiagnostico());
            ps.setString(8, c.getRecomendaciones());
            ps.setString(9, c.getObservaciones());
            ps.setObject(10, c.getPesoRegistrado());
            ps.setObject(11, c.getTemperatura());

            int filas = ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    System.out.println("Consulta insertada con ID = " + idGenerado);
                }
            }

            System.out.println("Consulta agregada. Filas afectadas: " + filas);

        } catch (SQLException ex) {
            System.out.println("Error SQL al insertar consulta: " + ex.getMessage());
        }
    }

    // 🔹 ACTUALIZAR
    public boolean actualizar(ConsultasMedicas c) {
        String sql = "UPDATE consultas_medicas SET mascota_id = ?, veterinario_id = ?, cita_id = ?, fecha_hora = ?, motivo = ?, sintomas = ?, diagnostico = ?, recomendaciones = ?, observaciones = ?, peso_registrado = ?, temperatura = ? "
                   + "WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, c.getMascotaId());
            ps.setInt(2, c.getVeterinarioId());
            if (c.getCitaId() != null) ps.setInt(3, c.getCitaId());
            else ps.setNull(3, Types.INTEGER);

            ps.setTimestamp(4, Timestamp.valueOf(c.getFechaHora()));
            ps.setString(5, c.getMotivo());
            ps.setString(6, c.getSintomas());
            ps.setString(7, c.getDiagnostico());
            ps.setString(8, c.getRecomendaciones());
            ps.setString(9, c.getObservaciones());
            ps.setObject(10, c.getPesoRegistrado());
            ps.setObject(11, c.getTemperatura());
            ps.setInt(12, c.getId());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar consulta: " + e.getMessage());
            return false;
        }
    }

    // 🔹 ELIMINAR
    public boolean eliminar(int id) {
        String sql = "DELETE FROM consultas_medicas WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar consulta: " + e.getMessage());
            return false;
        }
    }

    // 🔹 OBTENER POR ID
    public ConsultasMedicas obtenerPorId(int id) {
        String sql = "SELECT * FROM consultas_medicas WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new ConsultasMedicas(
                    rs.getInt("id"),
                    rs.getInt("mascota_id"),
                    rs.getInt("veterinario_id"),
                    rs.getObject("cita_id") != null ? rs.getInt("cita_id") : null,
                    rs.getTimestamp("fecha_hora").toLocalDateTime(),
                    rs.getString("motivo"),
                    rs.getString("sintomas"),
                    rs.getString("diagnostico"),
                    rs.getString("recomendaciones"),
                    rs.getString("observaciones"),
                    rs.getObject("peso_registrado") != null ? rs.getDouble("peso_registrado") : null,
                    rs.getObject("temperatura") != null ? rs.getDouble("temperatura") : null
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener consulta: " + e.getMessage());
        }
        return null;
    }

    // 🔹 LISTAR TODOS
    public List<ConsultasMedicas> listarTodos() {
        List<ConsultasMedicas> lista = new ArrayList<>();
        String sql = "SELECT * FROM consultas_medicas";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ConsultasMedicas c = new ConsultasMedicas(
                    rs.getInt("id"),
                    rs.getInt("mascota_id"),
                    rs.getInt("veterinario_id"),
                    rs.getObject("cita_id") != null ? rs.getInt("cita_id") : null,
                    rs.getTimestamp("fecha_hora").toLocalDateTime(),
                    rs.getString("motivo"),
                    rs.getString("sintomas"),
                    rs.getString("diagnostico"),
                    rs.getString("recomendaciones"),
                    rs.getString("observaciones"),
                    rs.getObject("peso_registrado") != null ? rs.getDouble("peso_registrado") : null,
                    rs.getObject("temperatura") != null ? rs.getDouble("temperatura") : null
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar consultas: " + e.getMessage());
        }
        return lista;
    }
    
    
}
