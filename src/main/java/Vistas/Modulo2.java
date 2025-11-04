/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vistas;

import Controllador.CitasController;
import Controllador.ConsultacitasController;
import Controllador.ProcedimientosEspecialesController;

import Controllador.VeterinariosController;
import DAO.CitasDAO;
import DAO.ConsultasMedicasDAO;
import DAO.MascotasDAO;
import DAO.Procedimientos_especialesDAO;
import DAO.VeterinariosDAO;
import Model.Entities.Citas;
import Model.Entities.ConsultasMedicas;
import Model.Entities.ProcedimientosEspeciales;
import Model.Entities.ProcedimientosEspeciales.EstadoProcedimiento;
import Model.Entities.Veterinarios;
import Model.Enums.EstadoProcedimientos;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author USUARIO
 */


public class Modulo2 {

    private static VeterinariosController veterinarioController;
    private static CitasController citaController;
    private static ConsultacitasController consultaController;
    private static ProcedimientosEspecialesController procedimientoController;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // === Inicialización de DAOs ===
        VeterinariosDAO veterinarioDAO = new VeterinariosDAO();
        MascotasDAO mascotasdao = new MascotasDAO();
        CitasDAO citaDAO = new CitasDAO();
        ConsultasMedicasDAO consultaDAO = new ConsultasMedicasDAO();
        Procedimientos_especialesDAO procedimientoDAO = new Procedimientos_especialesDAO();

        // === Inicialización de Controladores ===
        veterinarioController = new VeterinariosController();
        citaController = new CitasController(citaDAO);
        consultaController = new ConsultacitasController(consultaDAO, mascotasdao, veterinarioDAO, citaDAO);
        procedimientoController = new ProcedimientosEspecialesController();

        while (true) {
            mostrarMenuPrincipal();
            int opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1 -> menuVeterinarios();
                case 2 -> menuCitas();
                case 3 -> menuConsultas();
                case 4 -> menuProcedimientos();
                case 0 -> {
                    System.out.println("Saliendo del módulo de servicios médicos...");
                    return;
                }
                default -> System.out.println("❌ Opción inválida. Intente de nuevo.");
            }
        }
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n=== MÓDULO 2 - SERVICIOS MÉDICOS ===");
        System.out.println("1. Gestión de Veterinarios");
        System.out.println("2. Gestión de Citas");
        System.out.println("3. Gestión de Consultas Médicas");
        System.out.println("4. Gestión de Procedimientos Especiales");
        System.out.println("0. Salir");
    }

    // ========================= VETERINARIOS =========================
    private static void menuVeterinarios() {
        while (true) {
            System.out.println("\n--- Gestión de Veterinarios ---");
            System.out.println("1. Registrar veterinario");
            System.out.println("2. Listar veterinarios");
            System.out.println("3. Actualizar veterinario");
            System.out.println("4. Eliminar veterinario");
            System.out.println("5. ver veterinario");
            System.out.println("0. Volver");
            int op = leerEntero("Seleccione una opción: ");
            switch (op) {
                case 1 -> registrarVeterinario();
                case 2 -> listarVeterinarios();
                case 3 -> actualizarVeterinario();
                case 4 -> eliminarVeterinario();
                case 5 -> verVeterinario();
                case 0 -> { return; }
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private static void registrarVeterinario() {
        while(true){System.out.println("\n--- Registrar Veterinario ---");
        String nombre = leerTexto("Nombre completo: ");
        String documento = leerTexto("documento: ");
        String licencia = leerTexto("licencia profesional:  ");
        String especialidad = leerTexto("Especialidad: ");
        String telefono = leerTexto("Teléfono: ");
        String correo = leerTexto("Correo: ");
        Veterinarios v = new Veterinarios(nombre, documento, licencia, telefono, correo,especialidad, true);
        
            try {
               veterinarioController.registrarVeterinario(v);
        System.out.println("✅ Veterinario registrado correctamente."); 
        break;
            } catch (Exception e) {
                 System.out.println("❌ Error al actualizar el dueño: " + e.getMessage());
            }
     
    }
    }
    
    private static void verVeterinario() {
        int id = leerEntero("Id del veterinario: ");
        Veterinarios v = veterinarioController.verVeterinario(id);
        
        System.out.println("\n--- Datos del Veterinario ---");
        System.out.println("ID: " + v.getId());
        System.out.println("Nombre: " + v.getNombreCompleto());
        System.out.println("Documento: " + v.getDocumentoIdentidad());
        System.out.println("Teléfono: " + v.getTelefono());
        System.out.println("Email: " + v.getEmail());
        System.out.println("Licencia Profesional: " + v.getlicencia());
        System.out.println("Especialidad: " + v.getEspecialidad());
        System.out.println("Fecha de Contratación: " + v.getFechaRegistro());
        System.out.println("Activo: " + (v.isActivo() ? "Sí" : "No"));
        
        
    }
    
    
    
    

    private static void listarVeterinarios() {
        System.out.println("\n--- Lista de Veterinarios ---");
        List<Veterinarios> lista = veterinarioController.listarVeterinarios();
        if (lista.isEmpty()) {
            System.out.println("No hay veterinarios registrados.");
            return;
        }
        for (Veterinarios v : lista) {
            System.out.println("ID: " + v.getId() + " | Nombre: " + v.getNombreCompleto()+
                               " | Especialidad: " + v.getEspecialidad()+
                               " | Teléfono: " + v.getTelefono());
        }
    }

    private static void actualizarVeterinario() {
        int id = leerEntero("ID del veterinario a actualizar: ");
        Veterinarios v = veterinarioController.verVeterinario(id);
        if (v == null) {
            System.out.println("No se encontró el veterinario.");
            return;
        }
        String nuevoNombre = leerTextoOpcional("Nuevo nombre (" + v.getNombreCompleto()+ "): ");
        if (!nuevoNombre.isEmpty()) v.setNombreCompleto(nuevoNombre);

        
        String especialidad = leerTextoOpcional("Especialidad (" + v.getEspecialidad() + "): ");
        if (!especialidad.isEmpty()) v.setEspecialidad(especialidad);
        
        String Telefono = leerTextoOpcional("telefono (" + v.getTelefono()+ "): ");
        if (!Telefono.isEmpty()) v.setTelefono(Telefono);
        
        String email = leerTextoOpcional("email (" + v.getEmail()+ "): ");
        if (!email.isEmpty()) v.setEmail(email);
        boolean activo1 = true;
        int activo = leerEntero("esta asctivo el veterinario: 1.Activo 2.Inactivo");
        if(activo == 1){
        v.setActivo(activo1);
        }else if(activo == 2){
        activo1 = false;
        v.setActivo(activo1);
        }else{
            System.out.println("debes escoger entre 1 y 2");
        }
        

        veterinarioController.actualizarVeterinario(v);
        System.out.println("✅ Veterinario actualizado correctamente.");
    }

    private static void eliminarVeterinario() {
        int id = leerEntero("ID del veterinario a eliminar: ");
        veterinarioController.eliminarVeterinario(id);
        System.out.println("✅ Veterinario eliminado correctamente.");
    }

    // ========================= CITAS =========================
    private static void menuCitas() {
        while (true) {
            System.out.println("\n--- Gestión de Citas ---");
            System.out.println("1. Programar cita");
            System.out.println("2. Listar citas");
            System.out.println("3. Actualizar cita");
            System.out.println("4. Eliminar cita");
            System.out.println("5. ver cita");
            System.out.println("0. Volver");
            int op = leerEntero("Seleccione una opción: ");
            switch (op) {
                case 1 -> programarCita();
                case 2 -> listarCitas();
                case 3 -> actualizarCita();
                case 4 -> eliminarCita();
                case 5 -> vercita();
                case 0 -> { return; }
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private static void programarCita() {
        while(true){ System.out.println("\n--- Programar Cita ---");
        int mascotaId = leerEntero("ID de la mascota: ");
        int veterinarioId = leerEntero("ID del veterinario: ");
        LocalDateTime fecha = leerFechaHora("Fecha y hora ->");
        int Estadocita = leerEntero("id estado cita: ");
        String observaciones = leerTexto("observaciones: ");
        String motivo = leerTexto("Motivo de la cita: ");

        
        Citas cita = new Citas(mascotaId, veterinarioId,fecha, motivo,Estadocita,observaciones);
            try {
                citaController.registrarCita(cita);
                System.out.println("Cita registrada con exito");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Error al registrar cita: " + e.getMessage());
            }
        
        }
    }
    
    private static void vercita(){
     Citas c = null;

    while (true) {
        int id = leerEntero("Ingrese el ID de la cita: ");
        

        try {
             c = citaController.obtenerCitaPorId(id);
             break;
        } catch (IllegalArgumentException e) {
             System.out.println("❌ Error al ver cita: " + e.getMessage());
        }
    }

    // ✅ Si llegó aquí, c no es null, así que se puede imprimir sin error
    System.out.println("\n--- Detalle de la Cita ---");
    System.out.println("ID: " + c.getId());
    System.out.println("Mascota ID: " + c.getMascotaId());
    System.out.println("Veterinario ID: " + c.getVeterinarioId());
    System.out.println("Fecha y hora: " + c.getFechaHora());
    System.out.println("Motivo: " + c.getMotivo());
    System.out.println("Estado: " + c.getEstado());
    System.out.println("Observaciones: " + c.getObservaciones());
    System.out.println("Fecha de creación: " + c.getFechaCreacion());
    
    }

    private static void listarCitas() {
        System.out.println("\n--- Lista de Citas ---");
        List<Citas> citas = citaController.listarCitas();
        if (citas.isEmpty()) {
            System.out.println("No hay citas registradas.");
            return;
        }
        for (Citas c : citas) {
            System.out.println("ID: " + c.getId() + " | Mascota: " + c.getMascotaId() +
                    " | Veterinario: " + c.getVeterinarioId() +
                    " | Fecha: " + c.getFechaHora() + " | Estado: " + c.getEstado());
        }
    }

    private static void actualizarCita() {
        Citas c = null;
        while(true){int id = leerEntero("ID de la cita a actualizar: ");
            try {
                  c = citaController.obtenerCitaPorId(id);
                 break;
            } catch (IllegalArgumentException e) {
                 System.out.println("❌ Error al econtrar id: " + e.getMessage());
            }
      
        }
        while(true){
          int originalmascota = c.getMascotaId();
        int mascotaid = leerEntero("Id mascota"+"("+c.getMascotaId()+")"+": "  );
       c.setMascotaId(mascotaid);
       
       int originalvet = c.getVeterinarioId();
       int Veterinarioid = leerEntero("Id veterinario"+"("+c.getVeterinarioId()+")"+": "  );
       c.setVeterinarioId(Veterinarioid);
       LocalDateTime originalhora = c.getFechaHora();
       LocalDateTime Fechahora = leerFechaHora("Fecha hora:"+"("+c.getFechaHora()+")"+": "  );
       c.setFechaHora(Fechahora);
       
       String originalmot = c. getMotivo();
       String motivo = leerTexto("motivo"+"("+c.getMotivo()+")"+": ");
       c.setMotivo(motivo);
       
       String originales = c.getEstado();
        int estadoid = leerEntero("estado id"+"("+c.getEstado()+")"+": ");
       c.setEstadoId(estadoid);
       
       String originalob = c.getObservaciones();
        String observaciones = leerTexto("observaciones"+"("+c.getObservaciones()+")"+": ");
       c.setObservaciones(observaciones);
       
            try {
                citaController.actualizarCita(c);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Error al ver cita: " + e.getMessage());
            }
            
            c.setMascotaId(originalmascota);
            c.setVeterinarioId(originalvet);
            c.setFechaHora(originalhora);
            c.setMotivo(originalmot);
            c.setestado(originales);
            c.setObservaciones(originalob);
            
       ;
    }
    }

    private static void eliminarCita() {
        int id = leerEntero("ID de la cita a eliminar: ");
        citaController.eliminarCita(id);
        System.out.println("✅ Cita eliminada.");
    }

    // ========================= CONSULTAS =========================
    private static void menuConsultas() {
        while (true) {
            System.out.println("\n--- Gestión de Consultas Médicas ---");
            System.out.println("1. Registrar consulta");
            System.out.println("2. Listar consultas");
            System.out.println("3. Actualizar consulta");
            System.out.println("4. Eliminar consulta");
            System.out.println("5. ver consulta");
            System.out.println("0. Volver");
            int op = leerEntero("Seleccione una opción: ");
            switch (op) {
                case 1 -> registrarConsulta();
                case 2 -> listarConsultas();
                case 3 -> actualizarConsulta();
                case 4 -> eliminarConsulta();
                 case 5 -> verporid();
                case 0 -> { return; }
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private static void registrarConsulta() {
        while(true){System.out.println("\n--- Registrar Consulta ---");
        int mascotaId = leerEntero("ID mascota: ");
        int veterinarioid = leerEntero("ID veterinario: ");
        int citaid = leerEntero("cita id: ");
        LocalDateTime fechahora = leerFechaHora("fecha y hora:");
        String motivo = leerTexto("Motivo: ");
        String sintomas = leerTexto("sintomas: ");
        String diagnostico = leerTexto("Diagnóstico: ");
        String recomendaciones = leerTexto("recomendaciones: ");
        String observaciones = leerTexto("observaciones:  ");
        double peso = leerEntero("peso registrado: ");
        double temperatura = leerEntero("temperatura(c°): ");
        ConsultasMedicas c = new ConsultasMedicas(mascotaId, veterinarioid, citaid, fechahora, 
         motivo,sintomas,diagnostico,recomendaciones,observaciones,peso,temperatura );
        
            try {
                consultaController.registrarConsulta(c);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Error al registrar cita: " + e.getMessage());
            }
        
        }
    }
    
    private static void verporid(){
    ConsultasMedicas ce = null;
    
    while(true){
    
    int id = leerEntero("ingrse el id de la consulta a buscar: ");
        try {
           ce = consultaController.verConsulta(id);
           break;
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error al ver cita: " + e.getMessage());
        }
    }
    
       System.out.println("ID: " + ce.getId() + " | Mascota: " + ce.getMascotaId() + " | Diagnóstico: " + ce.getDiagnostico());
    
    }
    

    private static void listarConsultas() {
        System.out.println("\n--- Consultas Médicas ---");
        List<ConsultasMedicas> consultas = consultaController.listarConsultas();
        if (consultas.isEmpty()) {
            System.out.println("No hay consultas registradas.");
            return;
        }
        for (ConsultasMedicas c : consultas) {
            System.out.println("ID: " + c.getId() + " | Mascota: " + c.getMascotaId() + " | Diagnóstico: " + c.getDiagnostico());
        }
    }

    private static void actualizarConsulta() {
        ConsultasMedicas  c = null;
        do{int id = leerEntero("ID consulta: ");
          c = consultaController.verConsulta(id);
        if (c == null) {
            System.out.println("No encontrada.");
            
        }
        }while(c==null);
        
        while(true){
          String origidag = c.getDiagnostico();
        String nuevoDiag = leerTextoOpcional("Nuevo diagnóstico (" + c.getDiagnostico() + "): ");
        c.setDiagnostico(nuevoDiag);
        
        LocalDateTime orifecha = c.getFechaHora();
        LocalDateTime fechahora = leerFechaHora("Nuevo fecha (" + c.getFechaHora()+ "): ");
        c.setFechaHora(fechahora);
       
        int orivet = c.getVeterinarioId();
        int veterinarioid = leerEntero("Nuevo veterinario(" + c.getVeterinarioId()+ "): ");
        c.setVeterinarioId(veterinarioid);
       String orisin = c.getSintomas();
        String sintomas = leerTextoOpcional("Nuevos sintomas (" + c.getSintomas()+ "): ");
        c.setSintomas(sintomas);
        
            try {
                consultaController.actualizarConsulta(c);
                System.out.println("actualizada exitosamente");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Error al actualizar cita: " + e.getMessage());
            }
        
           c.setDiagnostico(origidag);
           c.setFechaHora(orifecha);
           c.setVeterinarioId(orivet);
           c.setSintomas(orisin);
    
        }
        }

    private static void eliminarConsulta() {
        int id = leerEntero("ID consulta a eliminar: ");
        consultaController.eliminarConsulta(id);
        System.out.println("✅ Consulta eliminada.");
    }

    // ========================= PROCEDIMIENTOS =========================
    private static void menuProcedimientos() {
        while (true) {
            System.out.println("\n--- Gestión de Procedimientos Especiales ---");
            System.out.println("1. Registrar procedimiento");
            System.out.println("2. Listar procedimientos");
            System.out.println("3. Actualizar procedimiento");
            System.out.println("4. Eliminar procedimiento");
            System.out.println("5. ver procedimiento");
            System.out.println("0. Volver");
            int op = leerEntero("Seleccione una opción: ");
            switch (op) {
                case 1 -> registrarProcedimiento();
                case 2 -> listarProcedimientos();
                case 3 -> actualizarProcedimiento();
                case 4 -> eliminarProcedimiento();
                case 5 -> verprocidiemiento();
                case 0 -> { return; }
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private static void registrarProcedimiento() {
        while(true){System.out.println("\n--- Registrar Procedimiento ---");
        int mascotaId = leerEntero("ID mascota: ");
        int vetrinarioid = leerEntero("ID Veterinario: ");
        String Tipoprocedimiento = leerTexto("Tipo de procedimiento:  ");
        String nombre = leerTexto("Nombre: ");
        LocalDateTime fecha = leerFechaHora("Fecha: ");
        int duracionminutos = leerEntero("duracion en minutos: ");
        String informacion = leerTexto("Informacion preoperatoria: ");
        String detalles = leerTexto("detalles: ");
       
        String compliciones = leerTexto("complicaciones:");
        String seguimiento = leerTexto("seguimiento postoperatorio: ");
        LocalDate control = leerFecha("proximo control: ");
        double costo = leerEntero("costo procedimeinto:");
       String estadoTexto = leerTexto("Estado (PROGRAMADO, EN_PROCESO, FINALIZADO, CANCELADO): ").toUpperCase();
       
       EstadoProcedimiento estado = null;
       try {
    estado = ProcedimientosEspeciales.EstadoProcedimiento.valueOf(nombre).valueOf(estadoTexto);
} catch (IllegalArgumentException e) {
    System.out.println("⚠️ Estado no válido. Se usará PROGRAMADO por defecto.");
    estado = EstadoProcedimiento.PROGRAMADO;
}
        
        
        
        ProcedimientosEspeciales p = new ProcedimientosEspeciales(mascotaId, vetrinarioid, Tipoprocedimiento, 
       nombre, fecha,duracionminutos, informacion, detalles, 
       compliciones, seguimiento, control, estado,costo);
            try {
                 procedimientoController.crearProcedimiento(p);
                 break;
            } catch (IllegalArgumentException e) {
                 System.out.println("❌ Error al registrar procedimiento: " + e.getMessage());
            }
        }
    }

    private static void listarProcedimientos() {
        System.out.println("\n--- Lista de Procedimientos ---");
        List<ProcedimientosEspeciales> procedimientos = procedimientoController.listarProcedimientos();
        if (procedimientos.isEmpty()) {
            System.out.println("No hay procedimientos registrados.");
            return;
        }
        for (ProcedimientosEspeciales p : procedimientos) {
            System.out.println("ID: " + p.getId() + " | mascota ID: " + p.getMascotaId()+ " | Tipo: " + p.getTipoProcedimiento());
        }
    }
    
    
    
    private static void verprocidiemiento(){
     ProcedimientosEspeciales pe = null;
    
    while(true){
    
    int id = leerEntero("ingrse el id del procedimiento a buscar: ");
        try {
           pe = procedimientoController.obtenerPorId(id);
           break;
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Error al ver procedimiento: " + e.getMessage());
        }
    }
    
       System.out.println("ID: " + pe.getId() + " | mascota ID: " + pe.getMascotaId()+ " | Tipo: " + pe.getTipoProcedimiento());
        
    }

    private static void actualizarProcedimiento() {
        ProcedimientosEspeciales p = null;
        do{int id = leerEntero("ID del procedimiento: ");
         p = procedimientoController.obtenerPorId(id);
        if (p == null) {
            System.out.println("No encontrado, intenta otra vez.");
            
        }
        }while( p== null);
        
       while(true){ 
          int originalvet = p.getVeterinarioId();
        int Veterinarioid = leerEntero("Veterinario id (" + p.getVeterinarioId()+ "): ");
        p.setVeterinarioId(Veterinarioid);
        
        String originalTipo = p.getTipoProcedimiento();
        String tipoprocedimiento = leerTexto("tipo procedimiento (" + p.getTipoProcedimiento()+ "): ");
        p.setTipoProcedimiento(tipoprocedimiento);
        
        String originalNombre = p.getNombreProcedimiento();
        String nombreProcedimiento = leerTexto("Nombre del procedimiento (" + p.getNombreProcedimiento() + "): ");
        p.setNombreProcedimiento(nombreProcedimiento);

        LocalDateTime originalFecha = p.getFechaHora();
        LocalDateTime fechaHora = leerFechaHora("Fecha y hora (" + p.getFechaHora() + "): ");
        p.setFechaHora(fechaHora);
        
        int originalDuracion = p.getDuracionEstimadaMinutos();
        int duracion = leerEntero("Duración estimada (minutos) (" + p.getDuracionEstimadaMinutos() + "): ");
        p.setDuracionEstimadaMinutos(duracion);

        String originalInfoPreop = p.getInformacionPreoperatoria();
       String infoPreop = leerTextoOpcional("Información preoperatoria (" + p.getInformacionPreoperatoria() + "): ");
       p.setInformacionPreoperatoria(infoPreop);

       String originalDetalle = p.getDetalleProcedimiento();
      String detalle = leerTextoOpcional("Detalle del procedimiento (" + p.getDetalleProcedimiento() + "): ");
       p.setDetalleProcedimiento(detalle);
       
      String originalComplicaciones = p.getComplicaciones();
     String complicaciones = leerTextoOpcional("Complicaciones (" + p.getComplicaciones() + "): ");
     p.setComplicaciones(complicaciones);
     
    String originalSeguimiento = p.getSeguimientoPostoperatorio();
    String seguimiento = leerTextoOpcional("Seguimiento postoperatorio (" + p.getSeguimientoPostoperatorio() + "): ");
    p.setSeguimientoPostoperatorio(seguimiento);

     LocalDate originalProximoControl = p.getProximoControl();
    LocalDate proximoControl = leerFecha("Próximo control (" + p.getProximoControl() + "): ");
     p.setProximoControl(proximoControl);

     EstadoProcedimiento originalEstado = p.getEstado();
       String estadoTexto = leerTexto("Estado ("+ p.getEstado()+"): ");
     if (!estadoTexto.isEmpty()) {
    
        // Limpia texto: quita espacios, reemplaza espacios por guiones bajos
        String estadoNormalizado = estadoTexto.trim().toUpperCase().replace(" ", "_");
        p.setEstado(EstadoProcedimiento.valueOf(estadoNormalizado));}

     double originalCosto = p.getCostoProcedimiento();
     double costo = leerEntero("Costo del procedimiento (" + p.getCostoProcedimiento() + "): ");
     p.setCostoProcedimiento(costo);
        
           try {
               procedimientoController.actualizarProcedimiento(p);
        System.out.println("✅ Procedimiento actualizado.");
        break;
           } catch (IllegalArgumentException e) {
               System.out.println("❌ Error al actualizar procedimiento: " + e.getMessage());
           }
        
        p.setVeterinarioId(originalvet);
        p.setTipoProcedimiento(originalTipo);
        p.setNombreProcedimiento(originalNombre);
         p.setFechaHora(originalFecha);
       p.setDuracionEstimadaMinutos(originalDuracion);
        p.setInformacionPreoperatoria(originalInfoPreop);
        p.setDetalleProcedimiento(originalDetalle);
        p.setComplicaciones(originalComplicaciones);
        p.setSeguimientoPostoperatorio(originalSeguimiento);
        p.setProximoControl(originalProximoControl);
        p.setEstado(originalEstado);
        p.setCostoProcedimiento(originalCosto);
           
       }
    }
    
    

    private static void eliminarProcedimiento() {
        int id = leerEntero("ID del procedimiento: ");
        procedimientoController.eliminarProcedimiento(id);
       
    }

    // ========================= AUXILIARES =========================
     private static String leerTexto(String mensaje) {
    String texto;
    do {
        System.out.print(mensaje);
        texto = sc.nextLine().trim(); // trim() elimina espacios al inicio y final
        if (texto.isEmpty()) {
            System.out.println("⚠️ Debes ingresar un valor. Intenta de nuevo.");
        }
    } while (texto.isEmpty());
    return texto;
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
        sc.nextLine();
        return valor;
    }

    private static LocalDate leerFecha(String mensaje) {
        System.out.print(mensaje);
        while (true) {
            try {
                return LocalDate.parse(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Formato incorrecto (AAAA-MM-DD): ");
            }
        }
    }
    
     public static LocalDateTime leerFechaHora(String mensaje) {
         String fechaStr;
         String horaStr;
        do{System.out.println(mensaje);
        System.out.print("Fecha (YYYY-MM-DD): ");
        fechaStr = sc.nextLine();
         if (fechaStr.isEmpty()) {
            System.out.println("⚠️ Debes ingresar un valor. Intenta de nuevo.");
        }
        }while(fechaStr.isEmpty());
        
        do{System.out.print("Hora (HH:MM): ");
        horaStr = sc.nextLine();
         if (horaStr.isEmpty()) {
            System.out.println("⚠️ Debes ingresar un valor. Intenta de nuevo.");
        }}while( horaStr.isEmpty());

        LocalDate fecha = LocalDate.parse(fechaStr);
        LocalTime hora = LocalTime.parse(horaStr);

        return LocalDateTime.of(fecha, hora);
        
    }
}
