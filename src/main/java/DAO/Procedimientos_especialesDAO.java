/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Entities.ProcedimientosEspeciales;
import Model.Entities.ProcedimientosEspeciales.EstadoProcedimiento;
import java.sql.Connection;
import java.sql.Date; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp; 
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import util.ConexionDB;
/**
 *
 * @author camper
 */
public class Procedimientos_especialesDAO {
    private final String TABLA = "procedimientos_especiales";

    private final String COLUMNAS = "mascota_id, veterinario_id, tipo_procedimiento, nombre_procedimiento, fecha_hora, duracion_estimada_minutos, informacion_preoperatoria, detalle_procedimiento, complicaciones, seguimiento_postoperatorio, proximo_control, estado, costo_procedimiento";


    public void agregar(ProcedimientosEspeciales p) {

        String SQL = "INSERT INTO " + TABLA + " (" + COLUMNAS + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1; 

            ps.setInt(i++, p.getMascotaId());
            ps.setInt(i++, p.getVeterinarioId());
            ps.setString(i++, p.getTipoProcedimiento());
            ps.setString(i++, p.getNombreProcedimiento());
            ps.setTimestamp(i++, Timestamp.valueOf(p.getFechaHora()));
            
            if (p.getDuracionEstimadaMinutos() != null) {
                ps.setInt(i++, p.getDuracionEstimadaMinutos());
            } else {
                ps.setNull(i++, java.sql.Types.INTEGER);
            }
            
            ps.setString(i++, p.getInformacionPreoperatoria());
            ps.setString(i++, p.getDetalleProcedimiento());
            ps.setString(i++, p.getComplicaciones());
            ps.setString(i++, p.getSeguimientoPostoperatorio());
            
            ps.setDate(i++, Date.valueOf(p.getProximoControl())); 
            
            ps.setString(i++, p.getEstado().name()); 
            
            if (p.getCostoProcedimiento() != null) {
                ps.setDouble(i++, p.getCostoProcedimiento());
            } else {
                ps.setNull(i++, java.sql.Types.DOUBLE);
            }
            
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    p.setId(idGenerado);
                    System.out.println("Procedimiento insertado con ID = " + idGenerado);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error SQL al agregar procedimiento: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private ProcedimientosEspeciales mapearProcedimiento(ResultSet rs) throws SQLException {
        LocalDateTime fechaHora = rs.getTimestamp("fecha_hora").toLocalDateTime();
        LocalDate proximoControl = rs.getDate("proximo_control").toLocalDate();
        EstadoProcedimiento estado = EstadoProcedimiento.valueOf(rs.getString("estado"));
        
        Integer duracion = (Integer) rs.getObject("duracion_estimada_minutos");
        Double costo = (Double) rs.getObject("costo_procedimiento");

        return new ProcedimientosEspeciales(
            rs.getInt("id"),
            rs.getInt("mascota_id"),
            rs.getInt("veterinario_id"),
            rs.getString("tipo_procedimiento"),
            rs.getString("nombre_procedimiento"),
            fechaHora,
            duracion,
            rs.getString("informacion_preoperatoria"),
            rs.getString("detalle_procedimiento"),
            rs.getString("complicaciones"),
            rs.getString("seguimiento_postoperatorio"),
            proximoControl,
            estado,
            costo
        );
    }


    public List<ProcedimientosEspeciales> listar() {
        List<ProcedimientosEspeciales> lista = new ArrayList<>();
        String SQL = "SELECT id, " + COLUMNAS + " FROM " + TABLA;

        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(SQL)) {

            while (rs.next()) {
                lista.add(mapearProcedimiento(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al listar procedimientos: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
    
    public ProcedimientosEspeciales obtenerPorId(int id) {
        String sql = "SELECT id, " + COLUMNAS + " FROM " + TABLA + " WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapearProcedimiento(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al obtener procedimiento por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    public boolean actualizar(ProcedimientosEspeciales p) {
        String sql = "UPDATE " + TABLA + " SET mascota_id = ?, veterinario_id = ?, tipo_procedimiento = ?, nombre_procedimiento = ?, fecha_hora = ?, duracion_estimada_minutos = ?, informacion_preoperatoria = ?, detalle_procedimiento = ?, complicaciones = ?, seguimiento_postoperatorio = ?, proximo_control = ?, estado = ?, costo_procedimiento = ? WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int i = 1;

            ps.setInt(i++, p.getMascotaId());
            ps.setInt(i++, p.getVeterinarioId());
            ps.setString(i++, p.getTipoProcedimiento());
            ps.setString(i++, p.getNombreProcedimiento());
            ps.setTimestamp(i++, Timestamp.valueOf(p.getFechaHora()));

            if (p.getDuracionEstimadaMinutos() != null) {
                ps.setInt(i++, p.getDuracionEstimadaMinutos());
            } else {
                ps.setNull(i++, java.sql.Types.INTEGER);
            }
            
            ps.setString(i++, p.getInformacionPreoperatoria());
            ps.setString(i++, p.getDetalleProcedimiento());
            ps.setString(i++, p.getComplicaciones());
            ps.setString(i++, p.getSeguimientoPostoperatorio());
            
            ps.setDate(i++, Date.valueOf(p.getProximoControl())); 
            
            ps.setString(i++, p.getEstado().name()); 
            
            if (p.getCostoProcedimiento() != null) {
                ps.setDouble(i++, p.getCostoProcedimiento());
            } else {
                ps.setNull(i++, java.sql.Types.DOUBLE);
            }
            
            ps.setInt(i++, p.getId()); 

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al actualizar procedimiento: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM " + TABLA + " WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id); 
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al eliminar procedimiento: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
