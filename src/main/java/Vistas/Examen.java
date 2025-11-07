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
import static Vistas.Menu2.leerFechaHora;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author camper
 */
public class Examen {
    
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
     String documento = leerTexto("ingrese el documento");
        
     try {
                List<Facturas> facturas = Facturascontroller.obtenerfactura(documento);
                System.out.println("INFORME FACTURACION POR CLIENTE ->");
                for(Facturas f: facturas){
                  System.out.println("Datos del cliente" +"documento: "+documento+"- ID:"+f.getidcliente());  
                    System.out.println("id factura: "+f.getId());
                    System.out.println("fecha emision: "+f.getFechaEmision());
                    System.out.println("totales: "+f.gettotal2());
                    System.out.println("total por todo: " + f.gettotalfacturado());


                
                }
                System.out.println("Datos del cliente" +"documento: "+documento+"- Nombre:"+facturas);
                
            } catch (IllegalArgumentException e) {
                System.out.println("error bro" + e.getMessage());
            }
    
    
    }
    
    private static String leerTexto(String mensaje) {
    String texto;
    do {
        System.out.print(mensaje);
        texto = sc.nextLine().trim(); 
        if (texto.isEmpty()) {
            System.out.println(" Debes ingresar un valor. Intenta de nuevo.");
        }
    } while (texto.isEmpty());
    return texto;
}
    
    
    
    
        public static void Menuprincipal(){
    
        System.out.println("-----CONSULTAR FACTURACION------");
        System.out.println("1.Consultar facturacion por documento");
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
     
     private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            System.out.print("Debe ingresar un número: ");
            sc.next();
        }
        int valor = sc.nextInt();
        sc.nextLine(); 
        return valor;
    }
     
}
    
        
        
        
    
    
    
    
}
