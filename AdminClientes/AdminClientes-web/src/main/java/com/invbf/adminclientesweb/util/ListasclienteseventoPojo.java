/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.util;

import com.invbf.adminclientesapi.entity.Clientes;
import com.invbf.adminclientesapi.entity.Estadoscliente;
import com.invbf.adminclientesapi.entity.Eventos;
import com.invbf.adminclientesapi.entity.Listasclientesevento;
import com.invbf.adminclientesapi.entity.ListasclienteseventoPK;

/**
 *
 * @author ideacentre
 */

public class ListasclienteseventoPojo {
    protected ListasclienteseventoPK listasclienteseventoPK;
    private String observaciones;
    private Integer count;
    private String idEstadoCliente;
    private Clientes clientes;
    private Eventos eventos;

    public ListasclienteseventoPojo() {
    }

    public ListasclienteseventoPojo(Listasclientesevento l) {
        this.listasclienteseventoPK = l.getListasclienteseventoPK();
        this.observaciones = l.getObservaciones();
        this.count = l.getCount();
        this.idEstadoCliente = l.getIdEstadoCliente().getNombre();
        this.clientes = l.getClientes();
        this.eventos = l.getEventos();
    }
    
    public Listasclientesevento getListasclientesevento(Estadoscliente ec){
        Listasclientesevento l = new Listasclientesevento();
        l.setClientes(clientes);
        l.setCount(count);
        l.setEventos(eventos);
        l.setIdEstadoCliente(ec);
        l.setListasclienteseventoPK(listasclienteseventoPK);
        l.setObservaciones(observaciones);
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
    public String toString() {
        return "com.invbf.adminclientesapi.Listasclientesevento[ listasclienteseventoPK=" + listasclienteseventoPK + " ]";
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Listasclientesevento)) {
            return false;
        }
        Listasclientesevento other = (Listasclientesevento) object;
        if ((this.listasclienteseventoPK == null && other.getListasclienteseventoPK() != null) || (this.listasclienteseventoPK != null && !this.listasclienteseventoPK.equals(other.getListasclienteseventoPK()))) {
            return false;
        }
        return true;
    }
}
