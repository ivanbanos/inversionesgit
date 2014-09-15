/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.util;

import com.invbf.adminclientesapi.entity.Cliente;
import com.invbf.adminclientesapi.entity.Clienteevento;
import com.invbf.adminclientesapi.entity.Estadocliente;
import com.invbf.adminclientesapi.entity.Evento;
import com.invbf.adminclientesapi.entity.ListasclienteseventoPK;
import com.invbf.adminclientesapi.entity.Usuario;
import java.util.Date;


/**
 *
 * @author ideacentre
 */

public class ListasclienteseventoPojo {
    protected ListasclienteseventoPK listasclienteseventoPK;
    private String observaciones;
    private String idEstadoCliente;
    private Cliente clientes;
    private Evento eventos;
    private Usuario usuario;
    private Date fechaAtencion;

    public ListasclienteseventoPojo() {
    }

    public ListasclienteseventoPojo(Clienteevento l) {
        this.listasclienteseventoPK = l.getListasclienteseventoPK();
        this.observaciones = l.getObservaciones();
        this.idEstadoCliente = l.getIdEstadoCliente().getNombre();
        this.clientes = l.getClientes();
        this.eventos = l.getEventos();
        this.usuario = l.getUsuario();
        this.fechaAtencion = l.getFechaAtencion();
    }
    
    public Clienteevento getListasclientesevento(Estadocliente ec, Usuario u){
        Clienteevento l = new Clienteevento();
        l.setClientes(clientes);
        l.setEventos(eventos);
        l.setIdEstadoCliente(ec);
        l.setListasclienteseventoPK(listasclienteseventoPK);
        l.setObservaciones(observaciones);
        l.setUsuario(u);
        l.setFechaAtencion(new Date());
        return l;
    }

    public ListasclienteseventoPojo(int idEvento, int idCliente) {
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

    public String getIdEstadoCliente() {
        return idEstadoCliente;
    }

    public void setIdEstadoCliente(String idEstadoCliente) {
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(Date fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (listasclienteseventoPK != null ? listasclienteseventoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "com.invbf.adminclientesapi.Listasclientesevento[ listasclienteseventoPK=" + listasclienteseventoPK + " ]";
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListasclienteseventoPojo)) {
            return false;
        }
        ListasclienteseventoPojo other = (ListasclienteseventoPojo) object;
        if ((this.listasclienteseventoPK == null && other.getListasclienteseventoPK() != null) || (this.listasclienteseventoPK != null && !this.listasclienteseventoPK.equals(other.getListasclienteseventoPK()))) {
            return false;
        }
        return true;
    }
}
