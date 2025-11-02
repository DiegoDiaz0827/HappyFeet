/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vistas;

import Controllador.ProcedimientosEspecialesController;
import Model.Enums.EstadoProcedimientos;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

/**
 *
 * @author USUARIO
 */
package app;

import controllers.*;
import models.*;
import dao.*;
import java.time.*;
import java.util.*;

public class Modulo2 {

    private static VeterinarioController veterinarioController;
    private static CitaController citaController;
    private static ConsultaController consultaController;
    private static ProcedimientosEspecialesController procedimientoController;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // === Inicialización de DAOs ===
        VeterinarioDAO veterinarioDAO = new VeterinarioDAO();
        CitaDAO citaDAO = new CitaDAO();
        ConsultaDAO consultaDAO = new ConsultaDAO();
        ProcedimientoDAO procedimientoDAO = new ProcedimientoDAO();

        // === Inicialización de Controladores ===
        veterinarioController = new VeterinarioController(veterinarioDAO);
        citaController = new CitaController(citaDAO);
        consultaController = new ConsultaController(consultaDAO);
        procedimientoController = new ProcedimientoController(procedimientoDAO);

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
            System.out.println("0. Volver");
            int op = leerEntero("Seleccione una opción: ");
            switch (op) {
                case 1 -> registrarVeterinario();
                case 2 -> listarVeterinarios();
                case 3 -> actualizarVeterinario();
                case 4 -> eliminarVeterinario();
                case 0 -> { return; }
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private static void registrarVeterinario() {
        System.out.println("\n--- Registrar Veterinario ---");
        String nombre = leerTexto("Nombre completo: ");
        String especialidad = leerTexto("Especialidad: ");
        String telefono = leerTexto("Teléfono: ");
        String correo = leerTexto("Correo: ");
        Veterinario v = new Veterinario(nombre, especialidad, telefono, correo, true);
        veterinarioController.registrarVeterinario(v);
        System.out.println("✅ Veterinario registrado correctamente.");
    }

    private static void listarVeterinarios() {
        System.out.println("\n--- Lista de Veterinarios ---");
        List<Veterinario> lista = veterinarioController.listarVeterinarios();
        if (lista.isEmpty()) {
            System.out.println("No hay veterinarios registrados.");
            return;
        }
        for (Veterinario v : lista) {
            System.out.println("ID: " + v.getId() + " | Nombre: " + v.getNombre() +
                               " | Especialidad: " + v.getEspecialidad() +
                               " | Teléfono: " + v.getTelefono());
        }
    }

    private static void actualizarVeterinario() {
        int id = leerEntero("ID del veterinario a actualizar: ");
        Veterinario v = veterinarioController.buscarVeterinario(id);
        if (v == null) {
            System.out.println("No se encontró el veterinario.");
            return;
        }
        String nuevoNombre = leerTextoOpcional("Nuevo nombre (" + v.getNombre() + "): ");
        if (!nuevoNombre.isEmpty()) v.setNombre(nuevoNombre);

        String especialidad = leerTextoOpcional("Especialidad (" + v.getEspecialidad() + "): ");
        if (!especialidad.isEmpty()) v.setEspecialidad(especialidad);

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
            System.out.println("0. Volver");
            int op = leerEntero("Seleccione una opción: ");
            switch (op) {
                case 1 -> programarCita();
                case 2 -> listarCitas();
                case 3 -> actualizarCita();
                case 4 -> eliminarCita();
                case 0 -> { return; }
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private static void programarCita() {
        System.out.println("\n--- Programar Cita ---");
        int mascotaId = leerEntero("ID de la mascota: ");
        int veterinarioId = leerEntero("ID del veterinario: ");
        LocalDate fecha = leerFecha("Fecha de la cita (AAAA-MM-DD): ");
        String hora = leerTexto("Hora (HH:MM): ");
        String motivo = leerTexto("Motivo de la cita: ");

        Cita cita = new Cita(mascotaId, veterinarioId, fecha.atTime(LocalTime.parse(hora)), motivo, "Programada");
        citaController.programarCita(cita);
        System.out.println("✅ Cita registrada correctamente.");
    }

    private static void listarCitas() {
        System.out.println("\n--- Lista de Citas ---");
        List<Cita> citas = citaController.listarCitas();
        if (citas.isEmpty()) {
            System.out.println("No hay citas registradas.");
            return;
        }
        for (Cita c : citas) {
            System.out.println("ID: " + c.getId() + " | Mascota: " + c.getMascotaId() +
                    " | Veterinario: " + c.getVeterinarioId() +
                    " | Fecha: " + c.getFechaHora() + " | Estado: " + c.getEstado());
        }
    }

    private static void actualizarCita() {
        int id = leerEntero("ID de la cita a actualizar: ");
        Cita c = citaController.buscarCita(id);
        if (c == null) {
            System.out.println("Cita no encontrada.");
            return;
        }
        String nuevoEstado = leerTextoOpcional("Nuevo estado (Programada/Finalizada/Cancelada): ");
        if (!nuevoEstado.isEmpty()) c.setEstado(nuevoEstado);
        citaController.actualizarCita(c);
        System.out.println("✅ Cita actualizada correctamente.");
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
            System.out.println("0. Volver");
            int op = leerEntero("Seleccione una opción: ");
            switch (op) {
                case 1 -> registrarConsulta();
                case 2 -> listarConsultas();
                case 3 -> actualizarConsulta();
                case 4 -> eliminarConsulta();
                case 0 -> { return; }
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private static void registrarConsulta() {
        System.out.println("\n--- Registrar Consulta ---");
        int mascotaId = leerEntero("ID mascota: ");
        int veterinarioId = leerEntero("ID veterinario: ");
        String motivo = leerTexto("Motivo: ");
        String diagnostico = leerTexto("Diagnóstico: ");
        ConsultaMedica c = new ConsultaMedica(mascotaId, veterinarioId, null, LocalDateTime.now(), motivo, "", diagnostico, "");
        consultaController.registrarConsulta(c);
        System.out.println("✅ Consulta registrada.");
    }

    private static void listarConsultas() {
        System.out.println("\n--- Consultas Médicas ---");
        List<ConsultaMedica> consultas = consultaController.listarConsultas();
        if (consultas.isEmpty()) {
            System.out.println("No hay consultas registradas.");
            return;
        }
        for (ConsultaMedica c : consultas) {
            System.out.println("ID: " + c.getId() + " | Mascota: " + c.getMascotaId() + " | Diagnóstico: " + c.getDiagnostico());
        }
    }

    private static void actualizarConsulta() {
        int id = leerEntero("ID consulta: ");
        ConsultaMedica c = consultaController.buscarConsulta(id);
        if (c == null) {
            System.out.println("No encontrada.");
            return;
        }
        String nuevoDiag = leerTextoOpcional("Nuevo diagnóstico (" + c.getDiagnostico() + "): ");
        if (!nuevoDiag.isEmpty()) c.setDiagnostico(nuevoDiag);
        consultaController.actualizarConsulta(c);
        System.out.println("✅ Consulta actualizada.");
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
            System.out.println("0. Volver");
            int op = leerEntero("Seleccione una opción: ");
            switch (op) {
                case 1 -> registrarProcedimiento();
                case 2 -> listarProcedimientos();
                case 3 -> actualizarProcedimiento();
                case 4 -> eliminarProcedimiento();
                case 0 -> { return; }
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private static void registrarProcedimiento() {
        System.out.println("\n--- Registrar Procedimiento ---");
        int mascotaId = leerEntero("ID mascota: ");
        String Tipoprocedimiento = leerTexto("Tipo de procedimiento:  ");
        String nombre = leerTexto("Nombre: ");
        LocalDate fecha = leerFecha("Fecha: ");
        String duracionminutos = leerTexto("duracion en minutos: ");
        String informacion = leerTexto("Informacion preoperatoria: ");
        String detalles = leerTexto("detalles: ");
        String compliciones = leerTexto("complicaciones:");
        String seguimiento = leerTexto("seguimiento postoperatorio: ");
        LocalDate control = leerFecha("proximo control: ");
       String estadoTexto = leerTexto("Estado (PROGRAMADO, EN_PROCESO, FINALIZADO, CANCELADO): ").toUpperCase();
       
       EstadoProcedimiento estado = null;
       try {
    estado = ProcedimientosEspeciales.EstadoProcedimiento.valueOf(nombre).valueOf(estadoTexto);
} catch (IllegalArgumentException e) {
    System.out.println("⚠️ Estado no válido. Se usará PROGRAMADO por defecto.");
    estado = EstadoProcedimiento.PROGRAMADO;
}
        
        
        
        Procedimiento p = new Procedimiento(consultaId, tipo, detalle, LocalDateTime.now());
        procedimientoController.registrarProcedimiento(p);
        System.out.println("✅ Procedimiento registrado.");
    }

    private static void listarProcedimientos() {
        System.out.println("\n--- Lista de Procedimientos ---");
        List<Procedimiento> procedimientos = procedimientoController.listarProcedimientos();
        if (procedimientos.isEmpty()) {
            System.out.println("No hay procedimientos registrados.");
            return;
        }
        for (Procedimiento p : procedimientos) {
            System.out.println("ID: " + p.getId() + " | Consulta ID: " + p.getConsultaId() + " | Tipo: " + p.getTipo());
        }
    }

    private static void actualizarProcedimiento() {
        int id = leerEntero("ID del procedimiento: ");
        Procedimiento p = procedimientoController.buscarProcedimiento(id);
        if (p == null) {
            System.out.println("No encontrado.");
            return;
        }
        String nuevoDetalle = leerTextoOpcional("Nuevo detalle (" + p.getDetalle() + "): ");
        if (!nuevoDetalle.isEmpty()) p.setDetalle(nuevoDetalle);
        procedimientoController.actualizarProcedimiento(p);
        System.out.println("✅ Procedimiento actualizado.");
    }

    private static void eliminarProcedimiento() {
        int id = leerEntero("ID del procedimiento: ");
        procedimientoController.eliminarProcedimiento(id);
        System.out.println("✅ Procedimiento eliminado.");
    }

    // ========================= AUXILIARES =========================
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
}
