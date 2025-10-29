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
public class canjes_beneficios {
    private int id;
    private int clubMascotasId;      // Mapea club_mascotas_id
    private int beneficioId;         // Mapea beneficio_id
    private Timestamp fechaCanje;    // Mapea DATETIME (fecha_canje)
    private int puntosCanjeados;     // Mapea puntos_canjeados
    private EstadoCanjees estado;    // Mapea el ENUM 'estado'
    private Date fechaExpiracion;    // Mapea DATE (fecha_expiracion)
    private Integer facturaId;       // Mapea factura_id (ON DELETE SET NULL, por eso Integer)

    // CONSTRUCTOR COMPLETO (Con ID)
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
    
    // CONSTRUCTOR BASE PRIVADO (Inicializa todos los campos sin ID - Para inserción/llamadas internas)
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

    // Getters
    public int getId() { return id; }
    public int getClubMascotasId() { return clubMascotasId; }
    public int getBeneficioId() { return beneficioId; }
    public Timestamp getFechaCanje() { return fechaCanje; }
    public int getPuntosCanjeados() { return puntosCanjeados; }
    public EstadoCanjees getEstado() { return estado; }
    public Date getFechaExpiracion() { return fechaExpiracion; }
    public Integer getFacturaId() { return facturaId; }

    // Setters
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
