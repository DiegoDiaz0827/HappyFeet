/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entities;

import Model.Enums.MetodoPago;
import Model.Enums.EstadoFacturas;
import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 *
 * @author USUARIO
 */

//modulo 4(completar)
public class Facturas {
private int id;
    private int duenoId;
    private String numeroFactura;
    private LocalDateTime fechaEmision;
    private BigDecimal subtotal;
    private BigDecimal impuesto;
    private BigDecimal descuento;
    private BigDecimal total;
    private MetodoPago metodoPago;
    private EstadoFacturas estado;   
    private String observaciones;
}


