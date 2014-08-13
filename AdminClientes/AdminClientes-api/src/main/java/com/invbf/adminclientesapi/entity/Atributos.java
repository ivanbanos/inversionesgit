/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.entity;

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
@Table(name = "atributos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Atributos.findAll", query = "SELECT a FROM Atributos a"),
    @NamedQuery(name = "Atributos.findByIdAtributo", query = "SELECT a FROM Atributos a WHERE a.idAtributo = :idAtributo"),
    @NamedQuery(name = "Atributos.findByNombre", query = "SELECT a FROM Atributos a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Atributos.findByTipoDato", query = "SELECT a FROM Atributos a WHERE a.tipoDato = :tipoDato")})
public class Atributos implements Serializable {
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
    private List<Clientesatributos> clientesatributosList;

    public Atributos() {
    }

    public Atributos(Integer idAtributo) {
        this.idAtributo = idAtributo;
    }

    public Atributos(Integer idAtributo, String nombre, String tipoDato) {
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
    public List<Clientesatributos> getClientesatributosList() {
        return clientesatributosList;
    }

    public void setClientesatributosList(List<Clientesatributos> clientesatributosList) {
        this.clientesatributosList = clientesatributosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAtributo != null ? idAtributo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Atributos other = (Atributos) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "com.invbf.adminclientesapi.Atributos[ idAtributo=" + idAtributo + " ]";
    }
    
}
