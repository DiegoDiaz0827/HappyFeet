/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;

import DAO.PrescripcionDAO;
import Model.Entities.Prescripcion;

import java.time.LocalDateTime;
import java.util.List;
/**
 *
 * @author camper
 */
public class PrescripcionController {

    private PrescripcionDAO prescripcionDAO;

    public PrescripcionController(PrescripcionDAO prescripcionDAO) {
        this.prescripcionDAO = prescripcionDAO;
    }

    
    public boolean registrarPrescripcion(Prescripcion prescripcion) {
        if (prescripcion.getProductoId() <= 0) {
            System.out.println(" El producto es obligatorio.");
            return false;
        }
        if (prescripcion.getCantidad() <= 0) {
            System.out.println("La cantidad debe ser mayor que 0.");
            return false;
        }
        if (prescripcion.getDosis() == null || prescripcion.getDosis().isBlank()) {
            System.out.println("La dosis es obligatoria.");
            return false;
        }
        if (prescripcion.getFrecuencia() == null || prescripcion.getFrecuencia().isBlank()) {
            System.out.println("La frecuencia es obligatoria.");
            return false;
        }
        if (prescripcion.getDuracionDias() != null && prescripcion.getDuracionDias() <= 0) {
            System.out.println("La duración debe ser mayor que 0 días.");
            return false;
        }

        prescripcion.setFechaPrescripcion(LocalDateTime.now());

        try {
            boolean exito = prescripcionDAO.insertar(prescripcion);
            System.out.println(exito ? "Prescripción registrada correctamente." : " No se pudo registrar la prescripción.");
            return exito;
        } catch (Exception e) {
            System.out.println("Error al registrar prescripción: " + e.getMessage());
            return false;
        }
    }

   
    public List<Prescripcion> listarPrescripciones() {
        return prescripcionDAO.listar();
    }

   
    public Prescripcion obtenerPrescripcionPorId(int id) {
        if (id <= 0) {
            System.out.println("ID inválido.");
            return null;
        }

        List<Prescripcion> lista = prescripcionDAO.listar(); 
        for (Prescripcion p : lista) {
            if (p.getId() == id) {
                return p;
            }
        }
        System.out.println("No se encontró la prescripción con ID: " + id);
        return null;
    }

  
    public boolean actualizarPrescripcion(Prescripcion prescripcion) {
        if (prescripcion.getId() <= 0) {
            System.out.println("️ La prescripción debe tener un ID válido.");
            return false;
        }
        if (prescripcion.getCantidad() <= 0) {
            System.out.println("️ La cantidad debe ser mayor que 0.");
            return false;
        }

        boolean exito = prescripcionDAO.actualizar(prescripcion);
        System.out.println(exito ? " Prescripción actualizada correctamente." : " No se pudo actualizar la prescripción.");
        return exito;
    }

    
    public boolean eliminarPrescripcion(int id) {
        if (id <= 0) {
            System.out.println(" ID inválido.");
            return false;
        }

        boolean exito = prescripcionDAO.eliminar(id);
        System.out.println(exito ? "️ Prescripción eliminada correctamente." : " No se encontró la prescripción para eliminar.");
        return exito;
    }
}
    

