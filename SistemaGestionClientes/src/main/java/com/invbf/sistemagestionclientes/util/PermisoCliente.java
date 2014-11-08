/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.util;

import com.invbf.sistemagestionclientes.entity.Cliente;
import com.invbf.sistemagestionclientes.entity.Permiso;

/**
 *
 * @author ideacentre
 */
public class PermisoCliente {
    private Permiso permiso;
    private Cliente cliente;

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public PermisoCliente(Permiso permiso, Cliente cliente) {
        this.permiso = permiso;
        this.cliente = cliente;
    }

    public PermisoCliente() {
    }
}
