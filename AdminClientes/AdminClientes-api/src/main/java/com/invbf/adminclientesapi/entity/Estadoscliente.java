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
@Table(name = "estadoscliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estadoscliente.findAll", query = "SELECT e FROM Estadoscliente e"),
    @NamedQuery(name = "Estadoscliente.findByIdEstadoCliente", query = "SELECT e FROM Estadoscliente e WHERE e.idEstadoCliente = :idEstadoCliente"),
    @NamedQuery(name = "Estadoscliente.findByNombre", query = "SELECT e FROM Estadoscliente e WHERE e.nombre = :nombre")})
public class Estadoscliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEstadoCliente")
    private Integer idEstadoCliente;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstadoCliente")
    private List<Listasclientesevento> listasclienteseventoList;

    public Estadoscliente() {
    }

    public Estadoscliente(Integer idEstadoCliente) {
        this.idEstadoCliente = idEstadoCliente;
    }

    public Estadoscliente(Integer idEstadoCliente, String nombre) {
        this.idEstadoCliente = idEstadoCliente;
        this.nombre = nombre;
    }

    public Integer getIdEstadoCliente() {
        return idEstadoCliente;
    }

    public void setIdEstadoCliente(Integer idEstadoCliente) {
        this.idEstadoCliente = idEstadoCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Listasclientesevento> getListasclienteseventoList() {
        return listasclienteseventoList;
    }

    public void setListasclienteseventoList(List<Listasclientesevento> listasclienteseventoList) {
        this.listasclienteseventoList = listasclienteseventoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadoCliente != null ? idEstadoCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estadoscliente)) {
            return false;
        }
        Estadoscliente other = (Estadoscliente) object;
        if ((this.idEstadoCliente == null && other.idEstadoCliente != null) || (this.idEstadoCliente != null && !this.idEstadoCliente.equals(other.idEstadoCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.adminclientesapi.Estadoscliente[ idEstadoCliente=" + idEstadoCliente + " ]";
    }
    
}
