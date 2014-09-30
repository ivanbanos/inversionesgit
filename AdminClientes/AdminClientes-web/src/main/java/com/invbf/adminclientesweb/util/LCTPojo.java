/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesweb.util;

import com.invbf.adminclientesapi.entity.Accion;
import com.invbf.adminclientesapi.entity.Cliente;
import com.invbf.adminclientesapi.entity.Listasclientestareas;
import com.invbf.adminclientesapi.entity.ListasclientestareasPK;
import com.invbf.adminclientesapi.entity.Tarea;
import com.invbf.adminclientesapi.entity.Usuario;
import java.util.Date;

/**
 *
 * @author ideacentre
 */
public class LCTPojo {

    private ListasclientestareasPK listasclientestareasPK;
    private String observaciones;
    private Integer accion;
    private Cliente cliente;
    private Tarea tareas;

    public LCTPojo() {
    }

    public LCTPojo(Integer cliente, Integer tareas) {
        listasclientestareasPK = new ListasclientestareasPK(tareas, cliente);
    }
    

    public LCTPojo(Listasclientestareas lct) {
        this.listasclientestareasPK = lct.getListasclientestareasPK();
        this.observaciones = lct.getObservaciones();
        this.accion = lct.getIdAccion().getIdAccion();
        this.cliente = lct.getCliente();
        this.tareas = lct.getTareas();
    }

    public Listasclientestareas getListaclientetareas(Usuario usuario, Accion accion) {
        Listasclientestareas lct = new Listasclientestareas();
        lct.setListasclientestareasPK(this.listasclientestareasPK);
        lct.setObservaciones(observaciones);
        lct.setUsuario(usuario);
        lct.setIdAccion(accion);
        lct.setCliente(cliente);
        lct.setTareas(tareas);
        return lct;
    }

    public ListasclientestareasPK getListasclientestareasPK() {
        return listasclientestareasPK;
    }

    public void setListasclientestareasPK(ListasclientestareasPK listasclientestareasPK) {
        this.listasclientestareasPK = listasclientestareasPK;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getAccion() {
        return accion;
    }

    public void setAccion(Integer accion) {
        this.accion = accion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Tarea getTareas() {
        return tareas;
    }

    public void setTareas(Tarea tareas) {
        this.tareas = tareas;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LCTPojo)) {
            return false;
        }
        LCTPojo other = (LCTPojo) object;
        if ((this.listasclientestareasPK == null && other.listasclientestareasPK != null) || (this.listasclientestareasPK != null && !this.listasclientestareasPK.equals(other.listasclientestareasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.listasclientestareasPK != null ? this.listasclientestareasPK.hashCode() : 0);
        return hash;
    }
}
