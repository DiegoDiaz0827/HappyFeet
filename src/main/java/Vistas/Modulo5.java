/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vistas;
import Controllador.beneficios_clubController;
import Controllador.canjes_beneficiosController;
import Controllador.club_mascotasController;
import Model.Entities.ClubMascotas;
import Model.Entities.beneficios_club;
import Model.Entities.canjes_beneficios;
import Model.Enums.EstadoCanjees;
import Model.Enums.TipoBeneficio;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author USUARIO
 */
public class Modulo5 {
    
// Instancias de los Controladores
    private final club_mascotasController clubController = new club_mascotasController();
    private final beneficios_clubController beneficioController = new beneficios_clubController();
    private final canjes_beneficiosController canjeController = new canjes_beneficiosController();
    
    public static void main(String[] args) {
        Modulo5 modulo = new Modulo5();      
        modulo.iniciar(); 
    }
    
    public void iniciar() {
        System.out.println("=================================================");
        System.out.println("              INICIANDO MÓDULO 5:              ");
        System.out.println("           GESTIÓN DEL CLUB DE MASCOTAS         ");
        System.out.println("=================================================");

        // 1. REGISTRAR MIEMBRO DEL CLUB Y ACUMULAR PUNTOS
        System.out.println("\n--- PASO 1: REGISTRO DE MIEMBRO Y ACUMULACIÓN ---");
        ClubMascotas club1 = new ClubMascotas(0, 101, 0, 0, 0, null, null, null, true); // Dueño ID 101
        clubController.registrarMembresia(club1);
        
        // Simular acumulación inicial de puntos por una compra
        clubController.acumularPuntos(club1.getDuenoId(), 500);
        clubController.acumularPuntos(club1.getDuenoId(), 250);
        
        ClubMascotas clubActualizado = clubController.buscarMembresiaPorDuenoId(club1.getDuenoId());
        
        // 2. CREAR BENEFICIOS PARA EL CATÁLOGO
        System.out.println("\n--- PASO 2: CREACIÓN DEL CATÁLOGO DE BENEFICIOS ---");
        
        beneficios_club b1 = new beneficios_club(0, "Descuento 10% Alimento", "10% OFF en cualquier bulto de alimento.", 
                                                 "Bronce", 500, TipoBeneficio.DESCUENTO, new BigDecimal("10.00"), true);
        beneficios_club b2 = new beneficios_club(0, "Baño Gratis", "Servicio de baño completo para cualquier mascota.", 
                                                 "Plata", 1000, TipoBeneficio.SERVICIOGRATIS, BigDecimal.ZERO, true);
        
        beneficioController.registrarBeneficio(b1);
        beneficioController.registrarBeneficio(b2);

        // Listar beneficios activos
        beneficioController.obtenerBeneficiosActivos();
        
        // 3. SIMULAR PROCESO DE CANJE
        System.out.println("\n--- PASO 3: SIMULACIÓN DE CANJES DE PUNTOS ---");
        
        // Intento de canje 1: Beneficio 1 (Puntos OK)
        simularCanje(clubActualizado, b1);
        
        // Intento de canje 2: Beneficio 2 (Falla por Nivel Requerido)
        simularCanje(clubActualizado, b2); 
        
        // Listar historial de canjes del club
        ClubMascotas clubPostCanje = clubController.buscarMembresiaPorDuenoId(club1.getDuenoId());
        if (clubPostCanje != null) {
            canjeController.obtenerCanjesPorClubId(clubPostCanje.getId());
        }

        System.out.println("\n=================================================");
        System.out.println("        MÓDULO 5 FINALIZADO CORRECTAMENTE.       ");
        System.out.println("=================================================");
    }
    
    /**
     * Lógica que envuelve la verificación, registro y actualización del canje.
     */
    private void simularCanje(ClubMascotas club, beneficios_club beneficio) {
        
        System.out.println("\n*** Intentando canjear: " + beneficio.getNombre() + " ***");
        
        if (beneficioController.verificarCanje(club, beneficio)) {
            
            int puntosRequeridos = beneficio.getPuntosNecesarios();
            int saldoAnterior = club.getPuntosDisponibles();
            
            // Lógica de Negocio: Restar puntos en la entidad ClubMascotas
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
                new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(90)), // Expira en 90 días
                null
            );
            canjeController.registrarCanje(canje);
            
            // 2. Actualizar la Membresía del Club
            if (clubController.actualizarMembresia(club)) {
                System.out.println(" Puntos descontados de la membresía. Nuevo saldo: " + club.getPuntosDisponibles());
                
                // 3. Marcar el Canje como 'CANJEADO' (simulación de uso inmediato)
                canjeController.actualizarEstadoCanje(canje.getId(), EstadoCanjees.APLICADO, null);
            } else {
                System.out.println(" ERROR: No se pudieron descontar los puntos. Proceso de canje fallido.");
                // Debería eliminar el registro de canje si la actualización del club falla (rollback)
                canjeController.eliminarCanje(canje.getId()); 
            }
            
        } else {
            System.out.println("? CANJE RECHAZADO: No se cumplen todos los requisitos.");
        }
    }
}
