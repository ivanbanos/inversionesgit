/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invbf.adminclientesapi.util;

import com.invbf.adminclientesapi.entity.Clientes;

/**
 *
 * @author Celula4
 */
public class InfoCorreoCliente {
    private Clientes cliente;
    private String info;

    public InfoCorreoCliente(Clientes cliente, String info) {
        this.cliente = cliente;
        this.info = info;
    }

    public InfoCorreoCliente() {
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    
    
}
