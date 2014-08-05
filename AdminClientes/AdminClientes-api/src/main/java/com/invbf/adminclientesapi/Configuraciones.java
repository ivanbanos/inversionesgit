/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ideacentre
 */
@Entity
@Table(name = "configuraciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Configuraciones.findAll", query = "SELECT c FROM Configuraciones c"),
    @NamedQuery(name = "Configuraciones.findByIdConfiguraciones", query = "SELECT c FROM Configuraciones c WHERE c.idConfiguraciones = :idConfiguraciones"),
    @NamedQuery(name = "Configuraciones.findByNombre", query = "SELECT c FROM Configuraciones c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Configuraciones.findByValor", query = "SELECT c FROM Configuraciones c WHERE c.valor = :valor")})
public class Configuraciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idConfiguraciones")
    private Integer idConfiguraciones;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "valor")
    private String valor;

    public Configuraciones() {
    }

    public Configuraciones(Integer idConfiguraciones) {
        this.idConfiguraciones = idConfiguraciones;
    }

    public Configuraciones(Integer idConfiguraciones, String nombre) {
        this.idConfiguraciones = idConfiguraciones;
        this.nombre = nombre;
    }

    public Integer getIdConfiguraciones() {
        return idConfiguraciones;
    }

    public void setIdConfiguraciones(Integer idConfiguraciones) {
        this.idConfiguraciones = idConfiguraciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConfiguraciones != null ? idConfiguraciones.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Configuraciones)) {
            return false;
        }
        Configuraciones other = (Configuraciones) object;
        if ((this.idConfiguraciones == null && other.idConfiguraciones != null) || (this.idConfiguraciones != null && !this.idConfiguraciones.equals(other.idConfiguraciones))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.adminclientesapi.Configuraciones[ idConfiguraciones=" + idConfiguraciones + " ]";
    }
    
}
