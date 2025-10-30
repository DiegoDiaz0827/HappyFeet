/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Model.Entities.HistorialMedico;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import util.ConexionDB;

/**
 *
 * @author camper
 */
public class historial_medicoDAO {
    private final String TABLA = "historial_medico";
    private final String COLUMNAS = "mascota_id, fecha_evento, evento_tipo_id, descripcion, diagnostico, tratamiento_recomendado, veterinario_id, consulta_id, procedimiento_id";

    public void agregar(HistorialMedico h) {

        String SQL = "INSERT INTO " + TABLA + " (" + COLUMNAS + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

            int i = 1; 

            ps.setInt(i++, h.getMascotaId());
            ps.setDate(i++, Date.valueOf(h.getFechaEvento())); 
            ps.setInt(i++, h.getEventoTipoId());
            ps.setString(i++, h.getDescripcion());
            ps.setString(i++, h.getDiagnostico());
            ps.setString(i++, h.getTratamientoRecomendado());
            
            setNullableInt(ps, i++, h.getVeterinarioId());
            setNullableInt(ps, i++, h.getConsultaId());
            setNullableInt(ps, i++, h.getProcedimientoId());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    h.setId(idGenerado);
                    System.out.println("Registro de historial insertado con ID = " + idGenerado);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error SQL al agregar historial: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    

    private void setNullableInt(PreparedStatement ps, int index, Integer value) throws SQLException {
        if (value != null) {
            ps.setInt(index, value);
        } else {
            ps.setNull(index, java.sql.Types.INTEGER);
        }
    }

    private HistorialMedico mapearHistorial(ResultSet rs) throws SQLException {
        
        LocalDate fechaEvento = rs.getDate("fecha_evento").toLocalDate();
        
        Integer veterinarioId = (Integer) rs.getObject("veterinario_id");
        Integer consultaId = (Integer) rs.getObject("consulta_id");
        Integer procedimientoId = (Integer) rs.getObject("procedimiento_id");

        return new HistorialMedico(
            rs.getInt("id"),
            rs.getInt("mascota_id"),
            fechaEvento,
            rs.getInt("evento_tipo_id"),
            rs.getString("descripcion"),
            rs.getString("diagnostico"),
            rs.getString("tratamiento_recomendado"),
            veterinarioId,
            consultaId,
            procedimientoId
        );
    }

    public List<HistorialMedico> listar() {
        List<HistorialMedico> lista = new ArrayList<>();
        String SQL = "SELECT id, " + COLUMNAS + " FROM " + TABLA;

        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(SQL)) {

            while (rs.next()) {
                lista.add(mapearHistorial(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al listar historial: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
    
    public HistorialMedico obtenerPorId(int id) {
        String sql = "SELECT id, " + COLUMNAS + " FROM " + TABLA + " WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapearHistorial(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al obtener historial por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizar(HistorialMedico h) {
        String sql = "UPDATE " + TABLA + " SET mascota_id = ?, fecha_evento = ?, evento_tipo_id = ?, descripcion = ?, diagnostico = ?, tratamiento_recomendado = ?, veterinario_id = ?, consulta_id = ?, procedimiento_id = ? WHERE id = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int i = 1;

            ps.setInt(i++, h.getMascotaId());
            ps.setDate(i++, Date.valueOf(h.getFechaEvento()));
            ps.setInt(i++, h.getEventoTipoId());
            ps.setString(i++, h.getDescripcion());
            ps.setString(i++, h.getDiagnostico());
            ps.setString(i++, h.getTratamientoRecomendado());
            
            setNullableInt(ps, i++, h.getVeterinarioId());
            setNullableInt(ps, i++, h.getConsultaId());
            setNullableInt(ps, i++, h.getProcedimientoId());
 
            ps.setInt(i++, h.getId()); 

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al actualizar historial: " + e.getMessage());
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
            System.out.println("Error SQL al eliminar historial: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
