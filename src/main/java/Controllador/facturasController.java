/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;
<<<<<<< HEAD

=======
import DAO.DueñoDAO;
>>>>>>> 1407fbf (diego)
import DAO.FacturasDAO;
import Model.Entities.Facturas;
import Model.Enums.EstadoFacturas;
import java.math.BigDecimal;
import java.util.Map;
import java.util.List;
import java.util.Collections;
/**
 *
 * @author camper
 */
public class facturasController {
   // El DAO de Facturas
    private FacturasDAO facturasDAO;
<<<<<<< HEAD
    
    // --------------------------------------------------------------------------
    // CONSTRUCTOR
    // --------------------------------------------------------------------------
    public facturasController(FacturasDAO facturasDAO) {
=======
    private DueñoDAO dueñodao;
    public facturasController(FacturasDAO facturasDAO, DueñoDAO dueñodao) {
        this.dueñodao = dueñodao;
>>>>>>> 1407fbf (diego)
        this.facturasDAO = facturasDAO;
    }
    // 1. Registrar una nueva factura 

<<<<<<< HEAD
    public boolean registrarFactura(Facturas factura) {
        if (factura.getDuenoId() <= 0) {
            System.out.println("️ ID de dueño inválido. No se puede registrar la factura sin dueño.");
            return false;
=======
    public boolean registrarFactura(Facturas factura) throws IllegalArgumentException {
        if (factura.getDuenoId() <= 0 || dueñodao.obtenerPorId(factura.getDuenoId()) == null) {
            throw new IllegalArgumentException("️ ID de dueño inválido.");
            
>>>>>>> 1407fbf (diego)
        }
        if (factura.getTotal() == null || factura.getTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("️ El total de la factura no puede ser nulo o negativo.");
            
        }
<<<<<<< HEAD
        if (factura.getNumeroFactura() == null || factura.getNumeroFactura().isBlank()) {
            System.out.println("️ El número de factura es obligatorio.");
            return false;
=======
        if (factura.getNumeroFactura() == null || factura.getNumeroFactura().isBlank() ) {
             throw new IllegalArgumentException("️ El número de factura es obligatorio.");
           
        }
        
        if (factura.getMetodoPago() == null) {
        System.out.println("⚠️ Método de pago inválido. No se puede registrar la factura.");
        return false;
>>>>>>> 1407fbf (diego)
        }

        if (factura.getEstado() == null) {
            factura.setEstado(EstadoFacturas.PENDIENTE);
            
        }
        
        

        try {
<<<<<<< HEAD
            boolean exito = facturasDAO.agregar(factura); 
            
            if (exito) {
                System.out.println(" Factura registrada correctamente.");
            } else {
                System.out.println(" Error: El DAO reportó un fallo al registrar la factura.");
            }
            return exito;
        } catch (Exception e) {
            System.out.println(" Error al registrar factura en la base de datos: " + e.getMessage());
=======
            facturasDAO.agregar(factura);
            System.out.println(" Factura registrada correctamente con ID: " + factura.getId());
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(" Error al registrar factura: " + e.getMessage());
>>>>>>> 1407fbf (diego)
            return false;
        }
    }
    // 2. Listar todas las facturas

    public List<Facturas> listarFacturas() {
        return facturasDAO.listar();
    }

<<<<<<< HEAD
    // 3. Buscar factura por ID 
    public Facturas obtenerFacturaPorId(int id) {
=======
    // --- 3️ Buscar factura por ID
    public Facturas obtenerFacturaPorId(int id) throws IllegalArgumentException {
>>>>>>> 1407fbf (diego)
        if (id <= 0) {
            System.out.println("⚠️ ID inválido.");
            return null;
        }
        
        Facturas f = facturasDAO.obtenerPorId(id);
        if(f == null){
        throw new IllegalArgumentException("el id no existe");
        }
        return facturasDAO.obtenerPorId(id);
    }


    // 4. Actualizar factura existente 

    public boolean actualizarFactura(Facturas factura) {
        if (factura.getId() <= 0) {
            System.out.println("️ La factura debe tener un ID válido para ser actualizada.");
            return false;
        }
        if (factura.getTotal() == null || factura.getTotal().compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("️ El total actualizado no puede ser nulo o negativo.");
            return false;
        }

        boolean exito = facturasDAO.actualizar(factura);
        System.out.println(exito ? " Factura actualizada correctamente." : " No se pudo actualizar la factura (ID no encontrado o error en DAO).");
        return exito;
    }


    // 5. Eliminar factura 
    public boolean eliminarFactura(int id) {
        if (id <= 0) {
            System.out.println("️ ID de factura inválido.");
            return false;
        }

        boolean exito = facturasDAO.eliminar(id);
        System.out.println(exito ? "Factura eliminada correctamente." : " No se encontró la factura con ese ID para eliminar.");
        return exito;
    }
    
 
    // 6. Reporte de Facturación por Período

   
    public List<Map<String, Object>> getReporteFacturacionPorPeriodo() {
        System.out.println("Generando reporte de Facturación por Período...");
        
        try {
            return facturasDAO.getReporteFacturacionPorPeriodo(); 
        } catch (Exception e) {
            System.out.println("❌ Error en el Controller al obtener el reporte de facturación: " + e.getMessage());
   
            return Collections.emptyList(); 
        }
    }


    // 7. Método Auxiliar 


    public int obtenerUltimoIdRegistrado() {
        return 0; 
    }
}
