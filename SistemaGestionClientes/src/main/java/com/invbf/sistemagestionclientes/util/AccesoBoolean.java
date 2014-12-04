/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.util;

import com.invbf.sistemagestionclientes.entitySGB.Accesos;

/**
 *
 * @author ivan
 */
public class AccesoBoolean {
    Accesos acceso;
    Boolean selected;

    public AccesoBoolean() {
    }

    public AccesoBoolean(Accesos acceso, Boolean selected) {
        this.acceso = acceso;
        this.selected = selected;
    }

    public Accesos getAcceso() {
        return acceso;
    }

    public void setAcceso(Accesos acceso) {
        this.acceso = acceso;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
    
}
