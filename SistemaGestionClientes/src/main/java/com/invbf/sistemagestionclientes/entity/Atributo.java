/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ideacentre
 */
@Entity
@Table(name = "Atributos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Atributos.findAll", query = "SELECT a FROM Atributo a"),
    @NamedQuery(name = "Atributos.findByIdAtributo", query = "SELECT a FROM Atributo a WHERE a.idAtributo = :idAtributo"),
    @NamedQuery(name = "Atributos.findByNombre", query = "SELECT a FROM Atributo a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Atributos.findByTipoDato", query = "SELECT a FROM Atributo a WHERE a.tipoDato = :tipoDato")})
public class Atributo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAtributo")
    private Integer idAtributo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "tipoDato")
    private String tipoDato;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "atributos")
    private List<Clienteatributo> clientesatributosList;

    public Atributo() {
    }

    public Atributo(Integer idAtributo) {
        this.idAtributo = idAtributo;
    }

    public Atributo(Integer idAtributo, String nombre, String tipoDato) {
        this.idAtributo = idAtributo;
        this.nombre = nombre;
        this.tipoDato = tipoDato;
    }

    public Integer getIdAtributo() {
        return idAtributo;
    }

    public void setIdAtributo(Integer idAtributo) {
        this.idAtributo = idAtributo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    @XmlTransient
    public List<Clienteatributo> getClientesatributosList() {
        return clientesatributosList;
    }

    public void setClientesatributosList(List<Clienteatributo> clientesatributosList) {
        this.clientesatributosList = clientesatributosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAtributo != null ? idAtributo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Atributo)) {
            return false;
        }
        Atributo other = (Atributo) object;
        if ((this.idAtributo == null && other.idAtributo != null) || (this.idAtributo != null && !this.idAtributo.equals(other.idAtributo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.adminclientesapi.entity.Atributos[ idAtributo=" + idAtributo + " ]";
    }
    
}
