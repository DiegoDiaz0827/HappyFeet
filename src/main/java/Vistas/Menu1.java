/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vistas;

import Controllador.MascotaController;
import DAO.DueñoDAO;
import DAO.MascotasDAO;
import DAO.RazasDAO;
import Model.Entities.Mascotas;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author USUARIO
 */
public class Menu1 {
    
    private static Scanner sc = new Scanner(System.in);
    private static MascotasDAO mascotaDAO = new MascotasDAO();
     private static DueñoDAO duenoDAO = new DueñoDAO();
     private static RazasDAO razaDAO = new RazasDAO();
    private static MascotaController mascotacontroller = new MascotaController(mascotaDAO, duenoDAO, razaDAO);
    
    
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
    
    
    private static void Logica(){
        Mascotas mascota = null;
        System.out.println(" -- CONSULTANDO --");
        int id;
        while(true){
         id = leerEntero("ingrese el id de la mascota: ");
         mascota =   mascotacontroller.verMascota(id);
        if(mascota != null){
            System.out.println("mascota encontrada con id" + mascota.getId());
            break;
        }
        }
        
        List<Mascotas> or = mascotacontroller.PesoMacotas(id);
        
        System.out.println("---HISTORIAL DEL PACIENTE----");
        System.out.println("DATOS DEL PACIENTE");
        
        System.out.println("Nombre: "+ mascota.getNombre());
        System.out.println("Raza: " + mascota.getNombreraza());
        
        for(Mascotas m : or){
            System.out.println(m.getIfechaconsulta()+" || "+ m.getIpesoconsulta()+"KG");
        }
        
       
        
    
    
    
    }
    
    
    public static void Menuprincipal(){
    
        System.out.println("-----CONSULTAR HISTORIAL DE PESO------");
        System.out.println("1.Consultar peso");
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
    
    

