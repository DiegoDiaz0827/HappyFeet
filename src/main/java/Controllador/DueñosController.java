/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;

import DAO.DueñoDAO;
import Model.Entities.Dueños;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author camper
 */
public class DueñosController {
    
    DueñoDAO dueñodao = new DueñoDAO();

   
    
    public void registrarDueño(Dueños d) throws IllegalArgumentException {
    if (d.getDocumentoIdentidad() == null || d.getDocumentoIdentidad().length() != 10) {
        throw new IllegalArgumentException("El documento debe tener exactamente 10 números.");
    }

    if (!d.getDocumentoIdentidad().chars().allMatch(Character::isDigit)) {
        throw new IllegalArgumentException("El documento solo debe contener números.");
    }

    if (d.getTelefono() == null || d.getTelefono().length() != 10) {
        throw new IllegalArgumentException("El teléfono debe tener exactamente 10 números.");
    }

    if (!d.getTelefono().chars().allMatch(Character::isDigit)) {
        throw new IllegalArgumentException("El teléfono solo debe contener números.");
    }

  
    if (d.getFechaRegistro() == null) {
        d.setFechaRegistro(LocalDateTime.now());
    }

    dueñodao.agregar(d);
    System.out.println(" El dueño " + d.getNombreCompleto() + " ha sido registrado exitosamente.");
}
    
    
    
    public boolean actualizarDueno(Dueños dueno) {
        System.out.println("\n--- ACTUALIZANDO DUEÑO ID: " + dueno.getId() + " ---");
        boolean actualizado = dueñodao.actualizar(dueno);
        if (actualizado) {
            System.out.println(" Datos del dueño ID " + dueno.getId() + " actualizados correctamente.");
        } else {
            System.out.println(" No se pudo actualizar el dueño ID " + dueno.getId() + ".");
        }
        
        
        return actualizado;
    }
    
    public boolean eliminarDueno(int id) {
        System.out.println("\n--- ELIMINANDO DUEÑO ID: " + id + " ---");
        boolean eliminado = dueñodao.eliminar(id);
        if (eliminado) {
            System.out.println(" Dueño ID " + id + " eliminado correctamente.");
        } else {
            System.out.println(" No se pudo eliminar el dueño ID " + id + ".");
        }
        return eliminado;
    }

    public Dueños buscarDuenoPorId(int id) throws IllegalArgumentException {
        System.out.println("\n--- BUSCANDO DUEÑO ID: " + id + " ---");
        Dueños dueno = dueñodao.obtenerPorId(id);
        if (dueno != null) {
            System.out.println(" Encontrado: " + dueno.getNombreCompleto());
        } else {
            throw new IllegalArgumentException("️ Dueño ID " + id + " no encontrado.");
        }
        return dueno;
    }

    public List<Dueños> obtenerTodosLosDuenos() {
        System.out.println("\n--- LISTANDO TODOS LOS DUEÑOS ---");
        List<Dueños> lista = dueñodao.listarTodos();
        System.out.println(" Se encontraron " + lista.size() + " dueños.");
        return lista;
    }
    
    
    }
    
    
    

