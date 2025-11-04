/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Entities.Prescripcion;
import util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author camper
 */
public class PrescripcionDAO {
    private Prescripcion mapearPrescripcion(ResultSet rs) throws SQLException {
    return new Prescripcion(
        rs.getInt("id"),
        rs.getObject("consulta_id") != null ? rs.getInt("consulta_id") : null,
        rs.getObject("procedimiento_id") != null ? rs.getInt("procedimiento_id") : null,
        rs.getInt("producto_id"),
        rs.getInt("cantidad"),
        rs.getString("dosis"),
        rs.getString("frecuencia"),
        rs.getObject("duracion_dias") != null ? rs.getInt("duracion_dias") : null,
        rs.getString("instrucciones"),
        rs.getTimestamp("fecha_prescripcion").toLocalDateTime()
    );
}


    public boolean insertar(Prescripcion p) {
        String sql = "INSERT INTO prescripciones (consulta_id, procedimiento_id, producto_id, cantidad, dosis, frecuencia, duracion_dias, instrucciones, fecha_prescripcion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setObject(1, p.getConsultaId());
            ps.setObject(2, p.getProcedimientoId());
            ps.setInt(3, p.getProductoId());
            ps.setInt(4, p.getCantidad());
            ps.setString(5, p.getDosis());
            ps.setString(6, p.getFrecuencia());
            ps.setObject(7, p.getDuracionDias());
            ps.setString(8, p.getInstrucciones());
            ps.setTimestamp(9, Timestamp.valueOf(p.getFechaPrescripcion()));

            return ps.executeUpdate() > 0; 

        } catch (SQLException e) {
            System.out.println("Error al insertar prescripción: " + e.getMessage());
            return false;
        }
    }

    public List<Prescripcion> listar() {
        List<Prescripcion> lista = new ArrayList<>();
        String sql = "SELECT * FROM prescripciones";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Prescripcion p = new Prescripcion(
                        rs.getInt("id"),
                        (Integer) rs.getObject("consulta_id"),
                        (Integer) rs.getObject("procedimiento_id"),
                        rs.getInt("producto_id"),
                        rs.getInt("cantidad"),
                        rs.getString("dosis"),
                        rs.getString("frecuencia"),
                        (Integer) rs.getObject("duracion_dias"),
                        rs.getString("instrucciones"),
                        rs.getTimestamp("fecha_prescripcion").toLocalDateTime()
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar prescripciones: " + e.getMessage());
        }
        return lista;
    }
    
    public Prescripcion obtenerPorId(int id) {
        String sql = "SELECT * FROM prescripciones WHERE id = ?";
        Prescripcion p = null;

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = mapearPrescripcion(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener prescripción por ID: " + e.getMessage());
        }
        return p;
    }

    public boolean actualizar(Prescripcion p) {
        String sql = "UPDATE prescripciones SET consulta_id=?, procedimiento_id=?, producto_id=?, cantidad=?, dosis=?, frecuencia=?, duracion_dias=?, instrucciones=?, fecha_prescripcion=? WHERE id=?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setObject(1, p.getConsultaId());
            ps.setObject(2, p.getProcedimientoId());
            ps.setInt(3, p.getProductoId());
            ps.setInt(4, p.getCantidad());
            ps.setString(5, p.getDosis());
            ps.setString(6, p.getFrecuencia());
            ps.setObject(7, p.getDuracionDias());
            ps.setString(8, p.getInstrucciones());
            ps.setTimestamp(9, Timestamp.valueOf(p.getFechaPrescripcion()));
            ps.setInt(10, p.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar prescripción: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM prescripciones WHERE id=?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar prescripción: " + e.getMessage());
            return false;
        }
    }
    
}

