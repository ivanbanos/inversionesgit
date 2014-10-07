/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.util;

import com.invbf.sistemagestionclientes.entity.Casino;

/**
 *
 * @author ideacentre
 */
public class CasinoBoolean {
    private Casino casino;
    private boolean selected;

    public CasinoBoolean() {
    }

    public CasinoBoolean(Casino casino, boolean isSelected) {
        this.casino = casino;
        this.selected = isSelected;
    }

    public Casino getCasino() {
        return casino;
    }

    public void setCasino(Casino casino) {
        this.casino = casino;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public boolean compareIdCategorias(Integer casino){
        if ((this.casino.getIdCasino() == null || !this.casino.getIdCasino().equals(casino))) {
            return false;
        }
        return true;
    }
}
