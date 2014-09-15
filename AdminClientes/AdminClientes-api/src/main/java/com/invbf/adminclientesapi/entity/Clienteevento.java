/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ideacentre
 */
@Entity
@Table(name = "listasclientesevento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Listasclientesevento.findAll", query = "SELECT l FROM Clienteevento l"),
    @NamedQuery(name = "Listasclientesevento.findByIdEvento", query = "SELECT l FROM Clienteevento l WHERE l.listasclienteseventoPK.idEvento = :idEvento"),
    @NamedQuery(name = "Listasclientesevento.findByIdCliente", query = "SELECT l FROM Clienteevento l WHERE l.listasclienteseventoPK.idCliente = :idCliente"),
    @NamedQuery(name = "Listasclientesevento.findByObservaciones", query = "SELECT l FROM Clienteevento l WHERE l.observaciones = :observaciones"),
    @NamedQuery(name = "Listasclientesevento.findByFechaAtencion", query = "SELECT l FROM Clienteevento l WHERE l.fechaAtencion = :fechaAtencion")})
public class Clienteevento implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ListasclienteseventoPK listasclienteseventoPK;
    @Column(name = "Observaciones")
    private String observaciones;
    @Column(name = "fechaAtencion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAtencion;
    @JoinColumn(name = "Usuario", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario usuario;
    @JoinColumn(name = "idEstadoCliente", referencedColumnName = "idEstadoCliente")
    @ManyToOne(optional = false)
    private Estadocliente idEstadoCliente;
    @JoinColumn(name = "idCliente", referencedColumnName = "idCliente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cliente clientes;
    @JoinColumn(name = "idEvento", referencedColumnName = "idEvento", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Evento eventos;

    public Clienteevento() {
    }

    public Clienteevento(ListasclienteseventoPK listasclienteseventoPK) {
        this.listasclienteseventoPK = listasclienteseventoPK;
    }

    public Clienteevento(int idEvento, int idCliente) {
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

    public Date getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(Date fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Estadocliente getIdEstadoCliente() {
        return idEstadoCliente;
    }

    public void setIdEstadoCliente(Estadocliente idEstadoCliente) {
        this.idEstadoCliente = idEstadoCliente;
    }

    public Cliente getClientes() {
        return clientes;
    }

    public void setClientes(Cliente clientes) {
        this.clientes = clientes;
    }

    public Evento getEventos() {
        return eventos;
    }

    public void setEventos(Evento eventos) {
        this.eventos = eventos;
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
        if (!(object instanceof Clienteevento)) {
            return false;
        }
        Clienteevento other = (Clienteevento) object;
        if ((this.listasclienteseventoPK == null && other.listasclienteseventoPK != null) || (this.listasclienteseventoPK != null && !this.listasclienteseventoPK.equals(other.listasclienteseventoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.adminclientesapi.entity.Listasclientesevento[ listasclienteseventoPK=" + listasclienteseventoPK + " ]";
    }
    
}
