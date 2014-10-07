/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.util;

import com.invbf.sistemagestionclientes.entity.Categoria;

/**
 *
 * @author ideacentre
 */
public class CategoriaBoolean {
    private Categoria categoria;
    private boolean selected;

    public CategoriaBoolean() {
    }

    public CategoriaBoolean(Categoria categoria, boolean isSelected) {
        this.categoria = categoria;
        this.selected = isSelected;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public boolean compareIdCategorias(Integer categoria){
        if ((this.categoria.getIdCategorias() == null || !this.categoria.getIdCategorias().equals(categoria))) {
            return false;
        }
        return true;
    }
    
}
