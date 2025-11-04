/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;

import DAO.DueñoDAO;
import DAO.MascotasDAO;
import DAO.RazasDAO;
import Model.Entities.Mascotas;
import java.util.List;

/**
 *
 * @author camper
 */
public class MascotaController {
    
    private MascotasDAO mascotaDAO;
    private DueñoDAO duenoDAO;   
    private RazasDAO razaDAO;     

    public MascotaController(MascotasDAO mascotaDAO, DueñoDAO duenoDAO, RazasDAO razaDAO) {
        this.mascotaDAO = mascotaDAO;
        this.duenoDAO = duenoDAO;
        this.razaDAO = razaDAO;
    }

    public boolean registrarMascota(Mascotas m) throws IllegalArgumentException{
       
        if (m.getDuenoId() <= 0 || duenoDAO.obtenerPorId(m.getDuenoId()) == null) {
            throw new IllegalArgumentException ("Error: Dueño no encontrado.");
           
        }
        if (m.getRazaId() <= 0 || razaDAO.obtenerPorId(m.getRazaId()) == null) {
           throw new IllegalArgumentException("Error: Raza no encontrada.");
            
        }
        
        mascotaDAO.insertar(m);
        return true;
    }

    public boolean actualizarMascota(Mascotas m) {
        if (m.getId() <= 0 || mascotaDAO.obtenerPorId(m.getId()) == null) {
            System.out.println("Error: Mascota no encontrada.");
            return false;
        }
        if (m.getDuenoId() <= 0 || duenoDAO.obtenerPorId(m.getDuenoId()) == null) {
            System.out.println("Error: Dueño no encontrado.");
            return false;
        }
        if (m.getRazaId() <= 0 || razaDAO.obtenerPorId(m.getRazaId()) == null) {
            System.out.println("Error: Raza no encontrada.");
            return false;
        }
        return mascotaDAO.actualizar(m);
    }

    // 🔹 Transferir mascota a otro dueño 
    public boolean transferirMascota(int idMascota, int idNuevoDueno) throws IllegalArgumentException {
        if (idMascota <= 0 || mascotaDAO.obtenerPorId(idMascota) == null) {
            throw new IllegalArgumentException("Error: Mascota no encontrada.");
            
        }
        if (idNuevoDueno <= 0 || duenoDAO.obtenerPorId(idNuevoDueno) == null) {
            throw new IllegalArgumentException("Error: Nuevo dueño no encontrado.");
            
        }
        Mascotas m = mascotaDAO.obtenerPorId(idMascota);
        m.setDuenoId(idNuevoDueno);
        return mascotaDAO.transferirmascota(m, idNuevoDueno);
    }

    public boolean eliminarMascota(int id) {
        if (id <= 0 || mascotaDAO.obtenerPorId(id) == null) {
            System.out.println("Error: Mascota no encontrada.");
            return false;
        }
        return mascotaDAO.eliminar(id);
    }

    public Mascotas verMascota(int id) throws IllegalArgumentException {
       
        
        
        Mascotas m = mascotaDAO.obtenerPorId(id);
        if(m == null){
        throw new IllegalArgumentException("no se encontro la mascota");
        }
        return mascotaDAO.obtenerPorId(id);
    }

    public List<Mascotas> listarMascotas() {
        return mascotaDAO.listarTodas();
    }
    
    
     public Mascotas verhistorial(int id)throws IllegalArgumentException {
        Mascotas m = mascotaDAO.obtenerprocedimiento(id);
        if(m == null){
        throw new IllegalArgumentException("no se encontro la mascota");
        }
        return mascotaDAO.obtenerprocedimiento(id);
    }
}
