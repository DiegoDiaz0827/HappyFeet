/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllador;



import DAO.DueñoDAO;
import DAO.FacturasDAO;
import Model.Entities.Facturas;
import Model.Enums.EstadoFacturas;
import java.math.BigDecimal;
import java.util.Map;
import java.util.List;
import java.util.Collections;

import DAO.DueñoDAO;
import java.time.LocalDateTime;

/**
 *
 * @author camper
 */
public class facturasController {
   
    private FacturasDAO facturasDAO;
     private DueñoDAO dueñodao;
    
     
    public facturasController(FacturasDAO facturasDAO, DueñoDAO dueñodao) {
        this.dueñodao = dueñodao;

        this.facturasDAO = facturasDAO;
    }


   

    public boolean registrarFactura(Facturas factura) throws IllegalArgumentException {
        if (factura.getDuenoId() <= 0 || dueñodao.obtenerPorId(factura.getDuenoId()) == null) {
            throw new IllegalArgumentException("️ ID de dueño inválido.");
            

        }
        if (factura.getTotal() == null || factura.getTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("️ El total de la factura no puede ser nulo o negativo.");
            
        }

        if (factura.getNumeroFactura() == null || factura.getNumeroFactura().isBlank()) {
            System.out.println("️ El número de factura es obligatorio.");
            return false;}

        if (factura.getNumeroFactura() == null || factura.getNumeroFactura().isBlank() ) {
             throw new IllegalArgumentException("️ El número de factura es obligatorio.");
           
        }
        
        if (factura.getMetodoPago() == null) {
        System.out.println("⚠️ Método de pago inválido. No se puede registrar la factura.");
        return false;

        }

        if (factura.getEstado() == null) {
            factura.setEstado(EstadoFacturas.PENDIENTE);
            
        }
        
        
try {
    boolean exito = facturasDAO.agregar(factura); 
    if (exito) {
        System.out.println("Factura registrada correctamente.");
    } else {
        System.out.println("Error: El DAO reportó un fallo al registrar la factura.");
    }
    return exito;
} catch (Exception e) {
    System.out.println("Error al registrar factura: " + e.getMessage());
    return false;
}
    }
   

    public List<Facturas> listarFacturas() {
        return facturasDAO.listar();
    }


    
   

   
    public Facturas obtenerFacturaPorId(int id) throws IllegalArgumentException {

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


   
    public boolean eliminarFactura(int id) {
        if (id <= 0) {
            System.out.println("️ ID de factura inválido.");
            return false;
        }

        boolean exito = facturasDAO.eliminar(id);
        System.out.println(exito ? "Factura eliminada correctamente." : " No se encontró la factura con ese ID para eliminar.");
        return exito;
    }
    

   
    public List<Map<String, Object>> getReporteFacturacionPorPeriodo() {
        System.out.println("Generando reporte de Facturación por Período...");
        
        try {
            return facturasDAO.getReporteFacturacionPorPeriodo(); 
        } catch (Exception e) {
            System.out.println(" Error en el Controller al obtener el reporte de facturación: " + e.getMessage());
   
            return Collections.emptyList(); 
        }
    }


    public int obtenerUltimoIdRegistrado() {
        return 0; 
    }
    
    
    
    
  
     public List<Facturas> obtenerfactura(String documento){
    
    List<Facturas> listafacturas = facturasDAO.obtenerFactura(documento);
    
    if(listafacturas == null){
    throw new IllegalArgumentException("no hay productos en estas fechas");
    }else{
    return listafacturas;
    }
    
    
    }
}
