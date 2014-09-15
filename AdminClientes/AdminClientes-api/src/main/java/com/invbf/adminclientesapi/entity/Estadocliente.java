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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
    @NamedQuery(name = "Estadoscliente.findAll", query = "SELECT e FROM Estadocliente e"),
    @NamedQuery(name = "Estadoscliente.findByIdEstadoCliente", query = "SELECT e FROM Estadocliente e WHERE e.idEstadoCliente = :idEstadoCliente"),
    @NamedQuery(name = "Estadoscliente.findByNombre", query = "SELECT e FROM Estadocliente e WHERE e.nombre = :nombre")})
public class Estadocliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEstadoCliente")
    private Integer idEstadoCliente;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @JoinTable(name = "estadoscliente_has_tiposeventos", joinColumns = {
        @JoinColumn(name = "EstadosCliente_idEstadoCliente", referencedColumnName = "idEstadoCliente")}, inverseJoinColumns = {
        @JoinColumn(name = "Tiposeventos_idTiposeventos", referencedColumnName = "idTiposeventos")})
    @ManyToMany
    private List<Tipoevento> tiposeventosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstadoCliente")
    private List<Clienteevento> listasclienteseventoList;

    public Estadocliente() {
    }

    public Estadocliente(Integer idEstadoCliente) {
        this.idEstadoCliente = idEstadoCliente;
    }

    public Estadocliente(Integer idEstadoCliente, String nombre) {
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
    public List<Tipoevento> getTiposeventosList() {
        return tiposeventosList;
    }

    public void setTiposeventosList(List<Tipoevento> tiposeventosList) {
        this.tiposeventosList = tiposeventosList;
    }

    @XmlTransient
    public List<Clienteevento> getListasclienteseventoList() {
        return listasclienteseventoList;
    }

    public void setListasclienteseventoList(List<Clienteevento> listasclienteseventoList) {
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
        if (!(object instanceof Estadocliente)) {
            return false;
        }
        Estadocliente other = (Estadocliente) object;
        if ((this.idEstadoCliente == null && other.idEstadoCliente != null) || (this.idEstadoCliente != null && !this.idEstadoCliente.equals(other.idEstadoCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  idEstadoCliente + " " + nombre;
    }
    
}
