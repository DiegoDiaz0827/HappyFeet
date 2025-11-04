 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.happyfeet;

import Vistas.Modulo1;
import Vistas.Modulo2;
import Vistas.Modulo3;
import Vistas.Modulo4;
import Vistas.Modulo5; 
import java.util.Scanner;
/**
 *
 * @author camper
 */
public class HappyFeet {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("     BIENVENIDO AL SISTEMA HAPPY FEET   ");
        System.out.println("==========================================");
        
        while (true) {
            mostrarMenuPrincipal();
            int opcion = leerOpcion("Seleccione un módulo para ingresar (0 para salir): ");

            try {
                switch (opcion) {
                    case 1:
                        System.out.println("\n--- INICIANDO MÓDULO 1: GESTIÓN DE PACIENTES ---");
                        Modulo1.main(new String[]{});
                        break;
                    case 2:
                        System.out.println("\n--- INICIANDO MÓDULO 2: SERVICIOS MÉDICOS Y PERSONAL ---");
                        Modulo2.main(new String[]{});
                        break;
                    case 3:
                        System.out.println("\n--- INICIANDO MÓDULO 3: INVENTARIO Y FARMACIA ---");
                        Modulo3.main(new String[]{});
                        break;
                    case 4:
                        System.out.println("\n--- INICIANDO MÓDULO 4: FACTURACIÓN Y REPORTES ---");
                        Modulo4.main(new String[]{});
                        break;
                    case 5:
                        System.out.println("\n--- INICIANDO MÓDULO 5: ACTIVIDADES ESPECIALES ---");
                        Modulo5.main(new String[]{});
                        break;
                    case 0:
                        System.out.println("\n? GRACIAS POR USAR HAPPY FEET. ¡HASTA PRONTO! ?");
                        return; 
                    default:
                        System.out.println(" Opción inválida. Por favor, ingrese un número del 0 al 5.");
                }
            } catch (Exception e) {
                System.err.println("¡Ocurrió un error al ejecutar el módulo! " + e.getMessage());
            }
        }
    }
    
    // -------------------------------------------------------------
    
    private static void mostrarMenuPrincipal() {
        System.out.println("\n==================== MENU PRINCIPAL ====================");
        System.out.println("1. GESTION DE DUEÑOS Y MASCOTAS (MÓDULO 1)");
        System.out.println("2. SERVICIOS MÉDICOS Y PERSONAL (MÓDULO 2)");
        System.out.println("3. INVENTARIO Y FARMACIA (MÓDULO 3)");
        System.out.println("4. FACTURACIÓN Y REPORTES (MÓDULO 4)");
        System.out.println("5. ACTIVIDADES ESPECIALES (MÓDULO 5)");
        System.out.println("0. Salir");
        System.out.println("--------------------------------------------------------");
    }

    private static int leerOpcion(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.print("Entrada inválida. Ingrese solo un número: ");
            scanner.next(); 
        }
        int opcion = scanner.nextInt();
        scanner.nextLine(); 
        return opcion;
    }
}
