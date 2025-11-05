/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;
import DAO.Jornadas_vacunacionDAO;
import DAO.Registro_jornada_vacunacionDAO;
import Model.Entities.Mascotas;
import Model.Entities.jornadas_vacunacion;
import Model.Entities.registro_jornada_vacunacion;
import Model.Enums.EstadoVacunacion;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
/**
 *
 * @author camper
 */
public class jornadas_vacunacionController {
private final Jornadas_vacunacionDAO jornadasVacunacionDAO;
private final Registro_jornada_vacunacionDAO registroJornadaDAO;
private final MascotaController mascotaController;

    // Inyección de dependencias
    public jornadas_vacunacionController(Jornadas_vacunacionDAO jornadasVacunacionDAO, 
    Registro_jornada_vacunacionDAO registroJornadaDAO, MascotaController mascotaController) {
        this.jornadasVacunacionDAO = jornadasVacunacionDAO;
        this.registroJornadaDAO = registroJornadaDAO;
        this.mascotaController = mascotaController;
    }

   
   
    public boolean registrarJornada(jornadas_vacunacion jornada) {
        if (jornada.getNombre() == null || jornada.getNombre().isBlank()) {
            System.out.println("️ El nombre de la jornada es obligatorio.");
            return false;
        }
        if (jornada.getFecha() == null) {
            System.out.println("️ La fecha de la jornada es obligatoria.");
            return false;
        }
        if (jornada.getUbicacion() == null || jornada.getUbicacion().isBlank()) {
            System.out.println("️ La ubicación de la jornada es obligatoria.");
            return false;
        }
        
       
        if (jornada.getHoraInicio() == null || jornada.getHoraFin() == null) {
            System.out.println(" La hora de inicio y fin son obligatorias.");
            return false;
        }
        if (jornada.getHoraInicio().after(jornada.getHoraFin()) || jornada.getHoraInicio().equals(jornada.getHoraFin())) {
            System.out.println("️ La hora de inicio debe ser estrictamente anterior a la hora de fin.");
            return false;
        }

       
        if (jornada.getCapacidadMaxima() != null && jornada.getCapacidadMaxima() <= 0) {
            System.out.println("️ La capacidad máxima debe ser un valor positivo si se especifica.");
            return false;
        }
        
        if (jornada.getEstado() == null) {
            jornada.setEstado(EstadoVacunacion.PLANIFICADA);
            System.out.println("ℹ️ Estado establecido a PLANIFICADA por defecto.");
        }

        try {
            jornadasVacunacionDAO.agregar(jornada);
            System.out.println(" Jornada de Vacunación registrada correctamente con ID: " + jornada.getId());
            return true;
        } catch (Exception e) {
            System.out.println(" Error al registrar jornada de vacunación: " + e.getMessage());
            return false;
        }
    }

   
    public List<jornadas_vacunacion> listarJornadas() {
        return jornadasVacunacionDAO.listar();
    }

     
    
    public jornadas_vacunacion obtenerJornadaPorId(int id) {
        if (id <= 0) {
            System.out.println("️ ID inválido para la búsqueda.");
            return null;
        }
        jornadas_vacunacion jornada = jornadasVacunacionDAO.obtenerPorId(id);
        if (jornada == null) {
            System.out.println("ℹ️ Jornada con ID " + id + " no encontrada.");
        }
        return jornada;
    }

   
  
    public boolean actualizarJornada(jornadas_vacunacion jornada) {
        if (jornada.getId() <= 0) {
            System.out.println("️ La jornada debe tener un ID válido para ser actualizada.");
            return false;
        }
        if (!validarCoherenciaJornada(jornada)) {
            return false;
        }

        boolean exito = jornadasVacunacionDAO.actualizar(jornada);
        System.out.println(exito ? " Jornada actualizada correctamente." : " No se pudo actualizar la jornada (ID no encontrado o error en DAO).");
        return exito;
    }

   

    public boolean eliminarJornada(int id) {
        if (id <= 0) {
            System.out.println("️ ID de jornada inválido.");
            return false;
        }
        
        jornadas_vacunacion jornadaExistente = obtenerJornadaPorId(id);
        if (jornadaExistente != null && (jornadaExistente.getEstado() == EstadoVacunacion.FINALIZADA || jornadaExistente.getEstado() == EstadoVacunacion.ENCURSO)) {
            System.out.println(" No se puede eliminar una jornada que está EN CURSO o FINALIZADA.");
            return false;
        }

        boolean exito = jornadasVacunacionDAO.eliminar(id);
        System.out.println(exito ? "?️ Jornada eliminada correctamente." : " No se encontró la jornada con ese ID para eliminar.");
        return exito;
    }

   
    private boolean validarCoherenciaJornada(jornadas_vacunacion jornada) {
        if (jornada.getNombre() == null || jornada.getNombre().isBlank()) return false;
        if (jornada.getFecha() == null) return false;
        if (jornada.getUbicacion() == null || jornada.getUbicacion().isBlank()) return false;
        if (jornada.getHoraInicio() == null || jornada.getHoraFin() == null) return false;
        

        if (jornada.getHoraInicio().after(jornada.getHoraFin()) || jornada.getHoraInicio().equals(jornada.getHoraFin())) {
            System.out.println("️ La hora de inicio debe ser estrictamente anterior a la hora de fin.");
            return false;
        }

        if (jornada.getCapacidadMaxima() != null && jornada.getCapacidadMaxima() <= 0) {
            System.out.println("️ La capacidad máxima debe ser un valor positivo si se especifica.");
            return false;
        }
        
        return true;
    }
    
    public boolean registrarAtencionRapida(int jornadaId, int mascotaId, int vacunaId, Integer veterinarioId, String loteVacuna) {
        
        jornadas_vacunacion jornada = obtenerJornadaPorId(jornadaId);
        
      
        if (jornada == null || jornada.getEstado() != EstadoVacunacion.PLANIFICADA) {
            System.out.println("❌ Jornada no encontrada o no está en estado PLANIFICADA para recibir registros.");
            return false;
        }

       
        Mascotas mascota = mascotaController.verMascota(mascotaId);
        if (mascota == null) {
            System.out.println("❌ Mascota con ID " + mascotaId + " no existe.");
            return false;
        }
        
       
        if (jornada.getCapacidadMaxima() != null) {
            int atendidas = this.registroJornadaDAO.obtenerConteoPorJornada(jornadaId);
            if (atendidas >= jornada.getCapacidadMaxima()) {
                System.out.println("⚠️ La jornada ha alcanzado su capacidad máxima.");
                return false;
            }
        }
        
        //  Crear el Registro 
        registro_jornada_vacunacion registro = new registro_jornada_vacunacion(
            0,
            jornadaId,
            mascotaId,
            mascota.getDuenoId(), 
            vacunaId,
            veterinarioId,
            new Timestamp(System.currentTimeMillis()),
            loteVacuna,
            null, 
            "Vacunación en jornada " + jornada.getNombre()
        );
        
        // E. Guardar y Concluir
        try {
            registroJornadaDAO.agregar(registro);
            System.out.println(" Mascota " + mascotaId + " registrada exitosamente en la jornada.");
            return true;
        } catch (Exception e) {
            System.out.println(" Error al guardar registro en la jornada: " + e.getMessage());
            return false;
        }
    }
}
