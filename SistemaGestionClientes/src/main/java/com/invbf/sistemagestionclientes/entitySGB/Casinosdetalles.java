/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.entitySGB;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "casinosdetalles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Casinosdetalles.findAll", query = "SELECT c FROM Casinosdetalles c"),
    @NamedQuery(name = "Casinosdetalles.findByIdCasino", query = "SELECT c FROM Casinosdetalles c WHERE c.idCasino = :idCasino"),
    @NamedQuery(name = "Casinosdetalles.findByAbreviacion", query = "SELECT c FROM Casinosdetalles c WHERE c.abreviacion = :abreviacion"),
    @NamedQuery(name = "Casinosdetalles.findByCiudad", query = "SELECT c FROM Casinosdetalles c WHERE c.ciudad = :ciudad"),
    @NamedQuery(name = "Casinosdetalles.findByImagen", query = "SELECT c FROM Casinosdetalles c WHERE c.imagen = :imagen"),
    @NamedQuery(name = "Casinosdetalles.findByAbreCiudad", query = "SELECT c FROM Casinosdetalles c WHERE c.abreCiudad = :abreCiudad")})
public class Casinosdetalles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idCasino")
    private Integer idCasino;
    @Size(max = 45)
    @Column(name = "abreviacion")
    private String abreviacion;
    @Size(max = 45)
    @Column(name = "ciudad")
    private String ciudad;
    @Size(max = 45)
    @Column(name = "imagen")
    private String imagen;
    @Size(max = 45)
    @Column(name = "abreCiudad")
    private String abreCiudad;

    public Casinosdetalles() {
    }

    public Casinosdetalles(Integer idCasino) {
        this.idCasino = idCasino;
    }

    public Integer getIdCasino() {
        return idCasino;
    }

    public void setIdCasino(Integer idCasino) {
        this.idCasino = idCasino;
    }

    public String getAbreviacion() {
        return abreviacion;
    }

    public void setAbreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getAbreCiudad() {
        return abreCiudad;
    }

    public void setAbreCiudad(String abreCiudad) {
        this.abreCiudad = abreCiudad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCasino != null ? idCasino.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Casinosdetalles)) {
            return false;
        }
        Casinosdetalles other = (Casinosdetalles) object;
        if ((this.idCasino == null && other.idCasino != null) || (this.idCasino != null && !this.idCasino.equals(other.idCasino))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionbonos.entity.Casinosdetalles[ idCasino=" + idCasino + " ]";
    }
    
}
