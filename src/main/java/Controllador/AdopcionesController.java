/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;
import DAO.AdopcionesDAO;
import DAO.Mascotas_adopcionDAO;
import DAO.DueñoDAO;
import Model.Entities.adopciones;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import Model.Enums.EstadoAdopcion;
/**
 *
 * @author camper
 */

public class AdopcionesController {
// 1. ATRIBUTOS: LOS 4 ELEMENTOS NECESARIOS
    private final AdopcionesDAO adopcionesDAO;
    private final Mascotas_adopcionDAO mascotasAdopcionDAO; 
    private final MascotaController mascotaController; // Para obtener datos de la mascota
    private final DueñoDAO dueñoDAO; // Para obtener datos del dueño

    // 2. CONSTRUCTOR (¡ESTO RESUELVE EL ERROR EN Modulo5.java!)
    public AdopcionesController(
            AdopcionesDAO adopcionesDAO,
            Mascotas_adopcionDAO mascotasAdopcionDAO,
            MascotaController mascotaController,
            DueñoDAO dueñoDAO) {
        this.adopcionesDAO = adopcionesDAO;
        this.mascotasAdopcionDAO = mascotasAdopcionDAO;
        this.mascotaController = mascotaController;
        this.dueñoDAO = dueñoDAO;
        System.out.println("✅ AdopcionesController inicializado con todas las dependencias.");
    }

    // 1️ Registrar una nueva adopción (CREATE)
    public boolean registrarAdopcion(adopciones adopcion) {  
        // 1. Validaciones  de IDs
        // 1. Validaciones básicas de IDs
        if (adopcion.getMascotaAdopcionId() <= 0) {
            System.out.println("️ ID de Mascota Adopción inválido. Debe especificar la mascota.");
            return false;
        }
        if (adopcion.getDuenoId() <= 0) {
            System.out.println(" ID de Dueño inválido. Debe especificar el adoptante.");
            return false;
        }

        if (!mascotasAdopcionDAO.verificarDisponibilidad(adopcion.getMascotaAdopcionId())) {
             System.out.println("❌ Error de lógica: La mascota ID " + adopcion.getMascotaAdopcionId() + " NO está disponible para adopción.");
             return false;
        }
        
        // El resto de tus validaciones de fechas (mantenidas)...
        Date hoy = new Date();
        if (adopcion.getFechaAdopcion() == null || adopcion.getFechaAdopcion().after(hoy)) {
             System.out.println("⚠️ La fecha de adopción es obligatoria y no puede ser futura.");
             return false;
        }
        
        // Lógica de generación de contrato (antes de guardar)
        String contratoGenerado = generarContrato(
            adopcion.getMascotaAdopcionId(), 
            adopcion.getDuenoId(), 
            adopcion.getCondicionesEspeciales());
       adopcion.setContratoTexto(contratoGenerado);


        try {
            
            // 1. Guardar el registro de la adopción
            adopcionesDAO.agregar(adopcion);

            mascotasAdopcionDAO.actualizarEstado(adopcion.getMascotaAdopcionId(), EstadoAdopcion.ADOPTADA);
            
            System.out.println("🎉 Adopción registrada y Mascota ID " + adopcion.getMascotaAdopcionId() + " marcada como ADOPTADA.");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Error al registrar adopción (posiblemente la mascota ya estaba adoptada o error de DB): " + e.getMessage());
            return false;
        }
    }

    // --- 2️ Listar todas las adopciones (READ ALL)
    public List<adopciones> listarAdopciones() {
        return adopcionesDAO.listar();
    }
    
    
    // 6️ Generación del Contrato Simple en Texto 
    public String generarContrato(int mascotaAdopcionId, int duenoId, String condicionesEspeciales) {

        String nombreMascota = "MASCOTA_ID_" + mascotaAdopcionId; 
        String nombreAdoptante = "ADOPTANTE_ID_" + duenoId; 
        String fechaHoy = new Date().toString();
        
        StringBuilder contrato = new StringBuilder();
        contrato.append("===================================================\n");
        contrato.append("           CONTRATO SIMPLE DE ADOPCIÓN\n");
        contrato.append("===================================================\n\n");
        contrato.append("FECHA: ").append(fechaHoy).append("\n\n");
        contrato.append("Yo, el adoptante **").append(nombreAdoptante).append("**, me comprometo a:\n");
        contrato.append("1. Proveer alimentación, refugio y atención veterinaria adecuada a:\n");
        contrato.append("   - **Mascota:** ").append(nombreMascota).append("\n\n");
        contrato.append("2. Cumplir con la esterilización (si aplica) y el esquema de vacunación.\n");
        contrato.append("3. Notificar a la Clínica Veterinaria *VetLife* de cualquier cambio de domicilio.\n");
        
        if (condicionesEspeciales != null && !condicionesEspeciales.isBlank()) {
            contrato.append("\nCONDICIONES ESPECIALES ADICIONALES:\n");
            contrato.append("-> ").append(condicionesEspeciales).append("\n");
        }
        
        contrato.append("\nAmbas partes aceptan los términos y condiciones.\n");
        contrato.append("\n\n_________________________           _________________________\n");
        contrato.append("Firma del Adoptante                 Firma del Representante VetLife\n");
        contrato.append("===================================================\n");
        
        return contrato.toString();
    }
}
