/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.util;

import com.invbf.sistemagestionclientes.entity.TipoJuego;

/**
 *
 * @author ideacentre
 */
public class TipoJuegoBoolean {
    private TipoJuego tipoJuego;
    private boolean selected;

    public TipoJuegoBoolean() {
    }

    public TipoJuegoBoolean(TipoJuego tipoJuego, boolean isSelected) {
        this.tipoJuego = tipoJuego;
        this.selected = isSelected;
    }

    public TipoJuego getTipoJuego() {
        return tipoJuego;
    }

    public void setTipoJuego(TipoJuego tipoJuego) {
        this.tipoJuego = tipoJuego;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public boolean compareIdTipoJuego(Integer categoria){
        if ((this.tipoJuego.getIdTipoJuego() == null || !this.tipoJuego.getIdTipoJuego().equals(categoria))) {
            return false;
        }
        return true;
    }
    
}
