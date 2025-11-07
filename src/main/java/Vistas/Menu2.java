/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vistas;

import Controllador.facturasController;
import DAO.DueñoDAO;
import DAO.FacturasDAO;
import Model.Entities.Facturas;
import static Vistas.Menu1.Menuprincipal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author USUARIO
 */
public class Menu2 {
    
        private static FacturasDAO facturadao = new FacturasDAO();
        private static DueñoDAO duenodao = new DueñoDAO();
    
        private static facturasController Facturascontroller = new facturasController(facturadao,duenodao);
           private static Scanner sc = new Scanner(System.in);
        
        public static void main(String[] args) {
        boolean entrada = true;
        while(entrada){
        Menuprincipal();
        int opcion = leerOpcion("Seleccione una opcion:");
        
        switch (opcion) {
            case 1:Logica();
            break;
            case 0 : entrada = false;
            break;
            default : System.out.println("no puedes ingreasar eso bro");
                
        }
    
        }
        
        
        
    }
        
        
        
        public static void Logica(){
            System.out.println("Consultar prodcutos por fecha");
            LocalDateTime fechainicio = leerFechaHora("fecha inicio: ");
            LocalDateTime fechafin = leerFechaHora("fecha fin: ");
        
            try {
                List<Facturas> facturas = Facturascontroller.obtenerproductos(fechainicio, fechafin);
                System.out.println("REPORTE PRODUCTOS MAS VENDIDOS ->");
                System.out.println("periodo - >" +fechainicio+"-"+fechafin);
                for(Facturas f: facturas){
                    System.out.println(f.getnombreinventario()+"-"+f.getcantidadtotal()+"-"+ f.getsubtotal());
                
                }
            } catch (IllegalArgumentException e) {
                System.out.println("error bro" + e.getMessage());
            }
        
        
        }
        
        
        
         public static void Menuprincipal(){
    
        System.out.println("-----CONSULTAR PRODUCTOS------");
        System.out.println("1.Consultar productos");
        System.out.println("0.salir");
        
    
    }
         
          private static int leerOpcion(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            System.out.print("Entrada inválida. Ingrese solo un número: ");
            sc.next(); 
        }
        int opcion = sc.nextInt();
        sc.nextLine(); 
        return opcion;
    }
         
       public static LocalDateTime leerFechaHora(String mensaje) {
    while (true) {
        try {
            System.out.println(mensaje);

            // Leer fecha
            String fechaStr;
            do {
                System.out.print("Fecha (YYYY-MM-DD): ");
                fechaStr = sc.nextLine();
                if (fechaStr.isEmpty()) {
                    System.out.println("⚠️ Debes ingresar un valor. Intenta de nuevo.");
                }
            } while (fechaStr.isEmpty());

            // Leer hora
            String horaStr;
            do {
                System.out.print("Hora (HH:MM): ");
                horaStr = sc.nextLine();
                if (horaStr.isEmpty()) {
                    System.out.println("⚠️ Debes ingresar un valor. Intenta de nuevo.");
                }
            } while (horaStr.isEmpty());

            // Intentar parsear
            LocalDate fecha = LocalDate.parse(fechaStr);
            LocalTime hora = LocalTime.parse(horaStr);

            // Si todo sale bien, retornar el LocalDateTime
            return LocalDateTime.of(fecha, hora);

        } catch (DateTimeParseException
                e) {
            System.out.println("⚠️ Formato inválido. Usa el formato correcto:");
            System.out.println("   Fecha → YYYY-MM-DD  (ej: 2025-11-06)");
            System.out.println("   Hora  → HH:MM  (ej: 14:30, entre 00 y 23 horas)\n");
        }
    }
}
        
    
}
