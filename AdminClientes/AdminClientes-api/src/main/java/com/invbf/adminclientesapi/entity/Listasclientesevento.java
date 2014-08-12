/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ideacentre
 */
@Entity
@Table(name = "listasclientesevento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Listasclientesevento.findAll", query = "SELECT l FROM Listasclientesevento l"),
    @NamedQuery(name = "Listasclientesevento.findByIdEvento", query = "SELECT l FROM Listasclientesevento l WHERE l.listasclienteseventoPK.idEvento = :idEvento"),
    @NamedQuery(name = "Listasclientesevento.findByIdCliente", query = "SELECT l FROM Listasclientesevento l WHERE l.listasclienteseventoPK.idCliente = :idCliente"),
    @NamedQuery(name = "Listasclientesevento.findByObservaciones", query = "SELECT l FROM Listasclientesevento l WHERE l.observaciones = :observaciones")})
public class Listasclientesevento implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ListasclienteseventoPK listasclienteseventoPK;
    @Column(name = "Observaciones")
    private String observaciones;
    @Column(name = "count")
    private Integer count;
    @JoinColumn(name = "idEstadoCliente", referencedColumnName = "idEstadoCliente")
    @ManyToOne(optional = false)
    private Estadoscliente idEstadoCliente;
    @JoinColumn(name = "idCliente", referencedColumnName = "idCliente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Clientes clientes;
    @JoinColumn(name = "idEvento", referencedColumnName = "idEvento", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Eventos eventos;

    public Listasclientesevento() {
    }

    public Listasclientesevento(ListasclienteseventoPK listasclienteseventoPK) {
        this.listasclienteseventoPK = listasclienteseventoPK;
    }

    public Listasclientesevento(int idEvento, int idCliente) {
        this.listasclienteseventoPK = new ListasclienteseventoPK(idEvento, idCliente);
    }

    public ListasclienteseventoPK getListasclienteseventoPK() {
        return listasclienteseventoPK;
    }

    public void setListasclienteseventoPK(ListasclienteseventoPK listasclienteseventoPK) {
        this.listasclienteseventoPK = listasclienteseventoPK;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Estadoscliente getIdEstadoCliente() {
        return idEstadoCliente;
    }

    public void setIdEstadoCliente(Estadoscliente idEstadoCliente) {
        this.idEstadoCliente = idEstadoCliente;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    public Eventos getEventos() {
        return eventos;
    }

    public void setEventos(Eventos eventos) {
        this.eventos = eventos;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (listasclienteseventoPK != null ? listasclienteseventoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Listasclientesevento)) {
            return false;
        }
        Listasclientesevento other = (Listasclientesevento) object;
        if ((this.listasclienteseventoPK == null && other.listasclienteseventoPK != null) || (this.listasclienteseventoPK != null && !this.listasclienteseventoPK.equals(other.listasclienteseventoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.adminclientesapi.Listasclientesevento[ listasclienteseventoPK=" + listasclienteseventoPK + " ]";
    }
    
}
