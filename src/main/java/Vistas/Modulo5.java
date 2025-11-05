/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vistas;


import Controllador.AdopcionesController;
import Controllador.MascotaController;
import Controllador.beneficios_clubController;
import Controllador.canjes_beneficiosController;
import Controllador.club_mascotasController;
import Controllador.jornadas_vacunacionController;
import DAO.AdopcionesDAO;
import DAO.Beneficios_clubDAO;
import DAO.Canjes_beneficiosDAO;
import DAO.Club_mascotasDAO;
import DAO.DueñoDAO;
import DAO.Jornadas_vacunacionDAO;
import DAO.MascotasDAO;
import DAO.Mascotas_adopcionDAO;
import DAO.RazasDAO;
import DAO.Registro_jornada_vacunacionDAO;
import Model.Entities.ClubMascotas;
import Model.Entities.adopciones;
import Model.Entities.beneficios_club;
import Model.Entities.canjes_beneficios;
import Model.Entities.jornadas_vacunacion;
import Model.Enums.EstadoCanjees;
import Model.Enums.EstadoVacunacion;
import Model.Enums.TipoBeneficio;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import util.ConexionDB;
/**
 *
 * @author USUARIO
 */
public class Modulo5 {
    
   // 1. DECLARACIÓN DE CONTROLADORES COMO ATRIBUTOS FINALES
    private final club_mascotasController clubController;
    private final beneficios_clubController beneficioController;
    private final canjes_beneficiosController canjeController;
    
    // MÓDULO 2: Jornadas de Vacunación
    private final jornadas_vacunacionController jornadaController; 

    // MÓDULO 3: Días de Adopción
    private final AdopcionesController adopcionesController;
    private final MascotaController mascotaController; 

    public Modulo5() {
        // --- 2.1 INICIALIZACIÓN DE DAOS ---
        DueñoDAO duenoDAO = new DueñoDAO();
        MascotasDAO mascotaDAO = new MascotasDAO();
        RazasDAO razaDAO = new RazasDAO();
        Jornadas_vacunacionDAO jornadasVacunacionDAO = new Jornadas_vacunacionDAO();
        Registro_jornada_vacunacionDAO registroJornadaDAO = new Registro_jornada_vacunacionDAO();
        AdopcionesDAO adopcionesDAO = new AdopcionesDAO();
        Mascotas_adopcionDAO mascotasAdopcionDAO = new Mascotas_adopcionDAO();
        Club_mascotasDAO clubMascotasDAO = new Club_mascotasDAO();
        Beneficios_clubDAO beneficioDAO = new Beneficios_clubDAO();
        Canjes_beneficiosDAO canjesDAO = new Canjes_beneficiosDAO();

        // --- 2 INICIALIZACIÓN DE CONTROLLERS 
        
        // Controllers base:
        this.mascotaController = new MascotaController(mascotaDAO, duenoDAO, razaDAO);
        
        // Controllers de Club:
        this.clubController = new club_mascotasController(clubMascotasDAO, duenoDAO);
        this.beneficioController = new beneficios_clubController(beneficioDAO);
        this.canjeController = new canjes_beneficiosController(canjesDAO);

        this.jornadaController = new jornadas_vacunacionController(jornadasVacunacionDAO, registroJornadaDAO, this.mascotaController);
        this.adopcionesController = new AdopcionesController(adopcionesDAO, mascotasAdopcionDAO, this.mascotaController, duenoDAO);
    }
    
    // 3. MÉTODO MAIN
    public static void main(String[] args) {
        Modulo5 modulo = new Modulo5();      
        modulo.iniciar();
    }
    
    // 4. MÉTODO INICIAR (
    public void iniciar() {
        System.out.println("=================================================");
        System.out.println("             INICIANDO MÓDULO 5:                ");
        System.out.println("  CLUB, JORNADAS DE VACUNACIÓN Y ADOPCIONES      ");
        System.out.println("=================================================");

        // 1. Simulación Club de Mascotas
        simularClubMascotas(); 

        // 2. Simulación Jornada de Vacunación
        simularJornadaVacunacion();

        // 3. Simulación Días de Adopción
        simularAdopcion(); 

        System.out.println("\n=================================================");
        System.out.println("        MÓDULO 5 FINALIZADO CORRECTAMENTE.        ");
        System.out.println("=================================================");
    }
    
    // 5. MÉTODOS DE SIMULACIÓN (Nuevos o Ajustados)

    // A. SIMULACIÓN: CLUB DE MASCOTAS (Código original, ahora funcional)
    private void simularClubMascotas() {
        System.out.println("\n\n#################################################");
        System.out.println("### 1. SIMULACIÓN: CLUB DE MASCOTAS FRECUENTES ###");
        System.out.println("#################################################");
        
        int duenoClubId = 101; 
        
        System.out.println("\n--- PASO 1: REGISTRO DE MIEMBRO Y ACUMULACIÓN ---");
        ClubMascotas club1 = new ClubMascotas(0, duenoClubId, 0, 0, 0, null, null, null, true);  
        clubController.registrarMembresia(club1);
        
        clubController.acumularPuntos(duenoClubId, 500);
        clubController.acumularPuntos(duenoClubId, 250); 
        
        ClubMascotas clubActualizado = clubController.buscarMembresiaPorDuenoId(duenoClubId);
        
        System.out.println("\n--- PASO 2: CREACIÓN DEL CATÁLOGO DE BENEFICIOS ---");
        
        beneficios_club b1 = new beneficios_club(0, "Descuento 10% Alimento", "10% OFF en cualquier bulto de alimento.", 
                                                 "Bronce", 500, TipoBeneficio.DESCUENTO, new BigDecimal("10.00"), true);
        beneficios_club b2 = new beneficios_club(0, "Baño Gratis", "Servicio de baño completo para cualquier mascota.", 
                                                 "Plata", 1000, TipoBeneficio.SERVICIOGRATIS, BigDecimal.ZERO, true); 
        
        beneficioController.registrarBeneficio(b1);
        beneficioController.registrarBeneficio(b2);

        System.out.println("\n--- PASO 3: SIMULACIÓN DE CANJES DE PUNTOS ---");
        if (clubActualizado != null) {
            simularCanje(clubActualizado, b1); 
            clubActualizado = clubController.buscarMembresiaPorDuenoId(duenoClubId); 
            simularCanje(clubActualizado, b2);  

            canjeController.obtenerCanjesPorClubId(clubActualizado.getId());
        }
    }
    
    // B. SIMULACIÓN: JORNADA DE VACUNACIÓN (Nuevo)
    private void simularJornadaVacunacion() {
        System.out.println("\n\n#################################################");
        System.out.println("### 2. SIMULACIÓN: JORNADAS DE VACUNACIÓN RÁPIDA ###");
        System.out.println("#################################################");

        int jornadaId = 1; 
        int mascotaJornadaId = 201; 
        int vacunaId = 1; 
        
        System.out.println("\n--- PASO 1: REGISTRO DE LA JORNADA ---");
jornadas_vacunacion jornada = new jornadas_vacunacion(
    1,
    "Jornada Rápida 2025",
    new Date(),
    new Time(System.currentTimeMillis()),
    new Time(System.currentTimeMillis()),
    "Parque Central",
    "Vacunación gratuita de emergencia para el sector central.", 
    1,
    EstadoVacunacion.PLANIFICADA
);

        System.out.println("\n--- PASO 2: REGISTRO DE ATENCIÓN RÁPIDA (Mascota: " + mascotaJornadaId + ") ---");
        jornadaController.registrarAtencionRapida(jornadaId, mascotaJornadaId, vacunaId, 1, "LOTE-XYZ123");
        
        System.out.println("\n--- PASO 3: INTENTO DE REGISTRO FALLIDO (Capacidad Máxima 1) ---");
        jornadaController.registrarAtencionRapida(jornadaId, 2, vacunaId, 1, "LOTE-ABC456"); 
    }
    
    // C. SIMULACIÓN: DÍAS DE ADOPCIÓN (Nuevo)
    private void simularAdopcion() {
        System.out.println("\n\n#################################################");
        System.out.println("### 3. SIMULACIÓN: DÍAS DE ADOPCIÓN Y CONTRATO ###");
        System.out.println("#################################################");

        int mascotaAdopcionDisponibleId = 1; 
        int nuevoAdoptanteId = 1; 
        
        // 1. Caso de éxito:
        System.out.println("\n--- PASO 1: REGISTRAR ADOPCIÓN EXITOSA) ---");
        Date fechaAdopcion = new Date();
        Date fechaSeguimiento = new Date(fechaAdopcion.getTime() + TimeUnit.DAYS.toMillis(15)); 

        adopciones adopcionExitosa = new adopciones(
            0,
            mascotaAdopcionDisponibleId,
            nuevoAdoptanteId,
            fechaAdopcion, 
            null, 
            "El adoptante se compromete a enviar 3 fotos mensuales.",
            true, 
            fechaSeguimiento
        );
        adopcionesController.registrarAdopcion(adopcionExitosa);
        System.out.println("\n--- PASO 2: INTENTO DE ADOPCIÓN FALLIDA (Mascota ya adoptada) ---");
        adopciones adopcionFallida = new adopciones(
            0,
            mascotaAdopcionDisponibleId, 
            1,
            new Date(), 
            null, 
            "Intentando adoptar de nuevo.",
            false, 
            null
        );
        adopcionesController.registrarAdopcion(adopcionFallida);
    }


    private void simularCanje(ClubMascotas club, beneficios_club beneficio) {
        
        System.out.println("\n*** Intentando canjear: " + beneficio.getNombre() + " ***");
        
        if (beneficioController.verificarCanje(club, beneficio)) {
            
            int puntosRequeridos = beneficio.getPuntosNecesarios();
            int saldoAnterior = club.getPuntosDisponibles();
            
            club.setPuntosDisponibles(saldoAnterior - puntosRequeridos);
            club.setPuntosCanjeados(club.getPuntosCanjeados() + puntosRequeridos);
            
            // 1. Registrar el Canje
            canjes_beneficios canje = new canjes_beneficios(
                0, 
                club.getId(), 
                beneficio.getId(), 
                new Timestamp(System.currentTimeMillis()), 
                puntosRequeridos, 
                EstadoCanjees.PENDIENTE, 
                new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(90)), 
                null
            );
            canjeController.registrarCanje(canje);
            
            // 2. Actualizar la Membresía del Club
            if (clubController.actualizarMembresia(club)) {
                System.out.println("Puntos descontados de la membresía. Nuevo saldo: " + club.getPuntosDisponibles());
                
                // 3. Marcar el Canje como 'APLICADO' 
                canjeController.actualizarEstadoCanje(canje.getId(), EstadoCanjees.APLICADO, null);
            } else {
                System.out.println("ERROR: No se pudieron descontar los puntos. Proceso de canje fallido.");
                
                canjeController.eliminarCanje(canje.getId());  
            }
            
        } else {
            System.out.println(" CANJE RECHAZADO: No se cumplen todos los requisitos.");
        }
    }
    
    
    
}