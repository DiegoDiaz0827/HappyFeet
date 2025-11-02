/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vistas;

import Controllador.DueñosController;
import Controllador.MascotaController;
import DAO.MascotasDAO;
import DAO.DueñoDAO;
import DAO.RazasDAO;
import Model.Entities.Dueños;
import Model.Entities.Mascotas;
import Model.Enums.Sexo;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author camper
 */
public class Modulo1 {
    
      private static MascotaController mascotaController;
    private static DueñosController duenoController;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        
          MascotasDAO mascotaDAO = new MascotasDAO();
        DueñoDAO duenoDAO = new DueñoDAO();
        RazasDAO razaDAO = new RazasDAO();

        // === Inicialización de Controladores ===
        mascotaController = new MascotaController(mascotaDAO, duenoDAO, razaDAO);
        duenoController = new DueñosController();

       
        while (true) {
            mostrarMenuPrincipal();
            int opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1 -> registrarDueno();
                case 2 -> registrarMascota();
                case 3 -> listarDuenos();
                case 4 -> listarMascotas();
                case 5 -> verMascota();
                case 6 -> transferirMascota();
                case 7 -> actualizarMascota();
                case 8 -> actualizarDueño();
                case 9 -> eliminarDueño();
                case 10 -> eliminarMascota();
                case 0 -> {
                    System.out.println("Saliendo del sistema...");
                    return;
                }
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n=== Sistema de Gestión de Pacientes ===");
        System.out.println("1. Registrar dueño");
        System.out.println("2. Registrar mascota");
        System.out.println("3. Listar dueños");
        System.out.println("4. Listar mascotas");
        System.out.println("5. Ver mascota por ID");
        System.out.println("6. Transferir mascota a otro dueño");
        System.out.println("7. Actualizar mascota");
        System.out.println("8. Actualizar Dueño");
        System.out.println("9. Eliminar dueño");
        System.out.println("10. Eliminar mascota");
        System.out.println("0. Salir");
    }

    // -------------------- DUEÑO --------------------
    private static void registrarDueno() {
        System.out.println("\n--- Registrar Dueño ---");
        String nombre = leerTexto("Nombre completo: ");
        String documento = leerTexto("Documento de identidad: ");
        String direccion = leerTexto("Dirección: ");
        String telefono = leerTexto("Teléfono: ");
        String correo = leerTexto("Correo electrónico: ");
        String contactoEmergencia = leerTextoOpcional("Contacto de emergencia (opcional): ");
        boolean activo = true;
        
        Dueños dueno = new Dueños(nombre,documento,direccion,telefono,correo,contactoEmergencia,activo);
        

        duenoController.registrarDueño(dueno);
        System.out.println("Dueño registrado correctamente.");
    }

    private static void listarDuenos() {
        System.out.println("\n--- Lista de Dueños ---");
        List<Dueños> duenios = duenoController.obtenerTodosLosDuenos();
        if (duenios.isEmpty()) {
            System.out.println("No hay dueños registrados.");
            return;
        }
        for (Dueños d : duenios) {
            System.out.println("ID: " + d.getId() + " | Nombre: " + d.getNombreCompleto() + " | Correo: " + d.getEmail() );
        }
    }

    // -------------------- MASCOTA --------------------
    private static void registrarMascota() {
        System.out.println("\n--- Registrar Mascota ---");
        listarDuenos();
        int duenoId = leerEntero("Ingrese ID del dueño: ");

        String nombre = leerTexto("Nombre de la mascota: ");
        int razaId = leerEntero("ID de la raza: "); // Para simplificar, suponemos que conoces las razas
        LocalDate fechaNacimiento = leerFecha("Fecha de nacimiento (AAAA-MM-DD): ");
        String sexo = leerTexto("Sexo (M/F): ");
        double peso = leerDouble("Peso actual: ");
        String microchip = leerTextoOpcional("Número de microchip (opcional): ");
        String tatuaje = leerTextoOpcional("Tatuaje (opcional): ");
        String urlFoto = leerTextoOpcional("URL de la foto (opcional): ");
        String alergias = leerTextoOpcional("Alergias (separadas por coma, opcional): ");
        String condiciones = leerTextoOpcional("Condiciones preexistentes (opcional): ");

        Mascotas m = new Mascotas();
        m.setDuenoId(duenoId);
        m.setNombre(nombre);
        m.setRazaId(razaId);
        m.setFechaNacimiento(fechaNacimiento);
        m.setSexo(Sexo.valueOf(sexo.toUpperCase()));
        m.setPesoActual(peso);
        m.setMicrochip(microchip);
        m.setTatuaje(tatuaje);
        m.setUrlFoto(urlFoto);
        m.setAlergias(alergias);
        m.setCondicionesPreexistentes(condiciones);
        m.setActivo(true);
        m.setFechaRegistro(LocalDate.now().atStartOfDay());

        mascotaController.registrarMascota(m);
    }

    private static void listarMascotas() {
        System.out.println("\n--- Lista de Mascotas ---");
        List<Mascotas> mascotas = mascotaController.listarMascotas();
        if (mascotas.isEmpty()) {
            System.out.println("No hay mascotas registradas.");
            return;
        }
        for (Mascotas m : mascotas) {
            System.out.println("ID: " + m.getId() + " | Nombre: " + m.getNombre() + " | Dueño ID: " + m.getDuenoId()+ "| Raza: " + m.getNombreraza());
        }
    }

    private static void verMascota() {
        int id = leerEntero("Ingrese ID de la mascota: ");
        Mascotas m = mascotaController.verMascota(id);
        if (m == null) {
            System.out.println("Mascota no encontrada.");
            return;
        }
        System.out.println("ID: " + m.getId() + "\nNombre: " + m.getNombre() + "\nDueño ID: " + m.getDuenoId() +
                "\nRaza ID: " + m.getRazaId() + "\nPeso: " + m.getPesoActual() + "\nSexo: " + m.getSexo() +
                "\nMicrochip: " + m.getMicrochip() + "\nAlergias: " + m.getAlergias());
    }

    private static void transferirMascota() {
        int idMascota = leerEntero("Ingrese ID de la mascota a transferir: ");
        listarDuenos();
        int idNuevoDueno = leerEntero("Ingrese ID del nuevo dueño: ");
        mascotaController.transferirMascota(idMascota, idNuevoDueno);
    }

    private static void actualizarMascota() {
        int id = leerEntero("Ingrese ID de la mascota a actualizar: ");
        Mascotas m = mascotaController.verMascota(id);
        if (m == null) {
            System.out.println("Mascota no encontrada.");
            return;
        }
        String nombre = leerTextoOpcional("Nombre (" + m.getNombre() + "): ");
        if (!nombre.isEmpty()) m.setNombre(nombre);

        double peso = leerDoubleOpcional("Peso (" + m.getPesoActual() + "): ");
        if (peso > 0) m.setPesoActual(peso);
        
        int raza = leerEntero("raza: ("+ m.getRazaId()+"):");
        m.setRazaId(raza);
        
        String microchip = leerTexto("microchip: ("+ m.getMicrochip()+"):");
        m.setMicrochip(microchip);
        
        String tatuaje = leerTexto("tatuaje:  ("+ m.getTatuaje()+"):");
        m.setTatuaje(tatuaje);
        
        String alergias = leerTexto("alergias: ("+ m.getAlergias()+"):");
        m.setAlergias(alergias);
        // Aquí puedes agregar más campos opcionales
        mascotaController.actualizarMascota(m);
    }
    
    private static void actualizarDueño() {
        int id = leerEntero("Ingrese ID de el dueño a actualizar: ");
        Dueños d = duenoController.buscarDuenoPorId(id);
        if (d == null) {
            System.out.println("Mascota no encontrada.");
            return;
        }
        String nombre = leerTextoOpcional("Nombre (" + d.getNombreCompleto()+ "): ");
        if (!nombre.isEmpty()) d.setNombreCompleto(nombre);

        String direccion = leerTexto("direccion (" + d.getDireccion()+ "): ");
         d.setDireccion(direccion);
        
        String email = leerTexto("email: ("+ d.getEmail()+"):");
        d.setEmail(email);
        
        String numero = leerTexto("Telefono: ("+ d.getTelefono()+"):");
        d.setTelefono(numero);
        
        String contacto = leerTexto("contacto  ("+ d.getContactoEmergencia()+"):");
        d.setContactoEmergencia(contacto);
        
        
        // Aquí puedes agregar más campos opcionales
        duenoController.actualizarDueno(d);
    }
    
    
    
    
    

    private static void eliminarMascota() {
        int id = leerEntero("Ingrese ID de la mascota a eliminar: ");
        mascotaController.eliminarMascota(id);
    }
    
    private static void eliminarDueño(){
    int id = leerEntero("ingrese el id del dueño a eliminar");
    duenoController.eliminarDueno(id);
    
    }

    // -------------------- MÉTODOS AUXILIARES --------------------
    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine();
    }

    private static String leerTextoOpcional(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            System.out.print("Debe ingresar un número: ");
            sc.next();
        }
        int valor = sc.nextInt();
        sc.nextLine(); // limpiar buffer
        return valor;
    }

    private static LocalDate leerFecha(String mensaje) {
        System.out.print(mensaje);
        while (true) {
            try {
                return LocalDate.parse(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Formato incorrecto, use AAAA-MM-DD: ");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextDouble()) {
            System.out.print("Debe ingresar un número válido: ");
            sc.next();
        }
        double valor = sc.nextDouble();
        sc.nextLine();
        return valor;
    }

    private static double leerDoubleOpcional(String mensaje) {
        System.out.print(mensaje);
        String linea = sc.nextLine().trim();
        if (linea.isEmpty()) return -1;
        try {
            return Double.parseDouble(linea);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
    

