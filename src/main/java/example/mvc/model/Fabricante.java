/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package example.mvc.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Delcio Amarillo
 */
@Entity
@Table(name = "Fabricantes")
public class Fabricante implements Serializable {
    
    private static final long serialVersionUID = 6787411012408357010L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger idFabricante;
    
    @Basic
    private String nombre;
    
    @Temporal(TemporalType.DATE)
    private Date fechaFundacion;
    
    /**
     * Crea una nueva instancia de la clase {@code Fabricante}.
     * @deprecated Obligatorio para JPA, NO debe utilizarse explícitamente.
     * @see #Fabricante(java.lang.String, java.util.Date) 
     */
    public Fabricante() {
        super();
    }

    public Fabricante(String nombre, Date fechaFundacion) {
        this.nombre = nombre;
        this.fechaFundacion = fechaFundacion;
    }

    public BigInteger getId() {
        return idFabricante;
    }
    
    public void setId(BigInteger id) {
        // Esta propiedad no debe ser modificada explícitamente!
        throw new UnsupportedOperationException("Not supported!");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaFundacion() {
        return fechaFundacion != null ? new Date(fechaFundacion.getTime()) : null;
    }

    public void setFechaFundacion(Date fechaFundacion) {
        this.fechaFundacion = fechaFundacion != null 
                            ? new Date(fechaFundacion.getTime()) : null;
    }
    
    @Override
    public int hashCode() {
        return idFabricante != null ? idFabricante.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Fabricante) {
            Fabricante other = (Fabricante) object;
            return (this.idFabricante != null || other.idFabricante == null) 
                && (this.idFabricante == null || this.idFabricante.equals(other.idFabricante));
        }
        return false;
    }

    @Override
    public String toString() {
        return "example.mvc.model.Fabricante[ id=" + idFabricante + " ]";
    }
}
