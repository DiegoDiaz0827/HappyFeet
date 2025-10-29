/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entities;

import Model.Enums.EstadoCanjees;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author camper
 */

//modulo 5
public class canjes_beneficios {
    private int id;
    private int clubMascotasId;     
    private int beneficioId;         
    private Timestamp fechaCanje;    
    private int puntosCanjeados;     
    private EstadoCanjees estado;    
    private Date fechaExpiracion;    
    private Integer facturaId;       

    // CONSTRUCTORES
    public canjes_beneficios(int pId, int pClubMascotasId, int pBeneficioId, Timestamp pFechaCanje, 
                             int pPuntosCanjeados, EstadoCanjees pEstado, Date pFechaExpiracion, 
                             Integer pFacturaId) {
        this.id = pId;
        this.clubMascotasId = pClubMascotasId;
        this.beneficioId = pBeneficioId;
        this.fechaCanje = pFechaCanje;
        this.puntosCanjeados = pPuntosCanjeados;
        this.estado = pEstado;
        this.fechaExpiracion = pFechaExpiracion;
        this.facturaId = pFacturaId;
    }
    
    private canjes_beneficios(int pClubMascotasId, int pBeneficioId, Timestamp pFechaCanje, 
                              int pPuntosCanjeados, EstadoCanjees pEstado, Date pFechaExpiracion, 
                              Integer pFacturaId) {
        this.clubMascotasId = pClubMascotasId;
        this.beneficioId = pBeneficioId;
        this.fechaCanje = pFechaCanje;
        this.puntosCanjeados = pPuntosCanjeados;
        this.estado = pEstado;
        this.fechaExpiracion = pFechaExpiracion;
        this.facturaId = pFacturaId;
    }

    public int getId() { return id; }
    public int getClubMascotasId() { return clubMascotasId; }
    public int getBeneficioId() { return beneficioId; }
    public Timestamp getFechaCanje() { return fechaCanje; }
    public int getPuntosCanjeados() { return puntosCanjeados; }
    public EstadoCanjees getEstado() { return estado; }
    public Date getFechaExpiracion() { return fechaExpiracion; }
    public Integer getFacturaId() { return facturaId; }

    public void setId(int id) { this.id = id; }
    public void setClubMascotasId(int clubMascotasId) { this.clubMascotasId = clubMascotasId; }
    public void setBeneficioId(int beneficioId) { this.beneficioId = beneficioId; }
    public void setFechaCanje(Timestamp fechaCanje) { this.fechaCanje = fechaCanje; }
    public void setPuntosCanjeados(int puntosCanjeados) { this.puntosCanjeados = puntosCanjeados; }
    public void setEstado(EstadoCanjees estado) { this.estado = estado; }
    public void setFechaExpiracion(Date fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }
    public void setFacturaId(Integer facturaId) { this.facturaId = facturaId; }

    @Override
    public String toString() {
        return "canjes_beneficios{" +
                "id=" + id +
                ", clubMascotasId=" + clubMascotasId +
                ", beneficioId=" + beneficioId +
                ", fechaCanje=" + fechaCanje +
                ", puntosCanjeados=" + puntosCanjeados +
                ", estado=" + estado +
                ", fechaExpiracion=" + fechaExpiracion +
                ", facturaId=" + facturaId +
                '}';
    }

}
