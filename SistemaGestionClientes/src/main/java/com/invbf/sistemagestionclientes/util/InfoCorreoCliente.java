/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invbf.sistemagestionclientes.util;

import com.invbf.sistemagestionclientes.entity.Cliente;

/**
 *
 * @author Celula4
 */
public class InfoCorreoCliente {
    private Cliente cliente;
    private String info;

    public InfoCorreoCliente(Cliente cliente, String info) {
        this.cliente = cliente;
        this.info = info;
    }

    public InfoCorreoCliente() {
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    
    
}
