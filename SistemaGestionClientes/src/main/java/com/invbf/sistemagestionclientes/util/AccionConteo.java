/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.invbf.sistemagestionclientes.util;

/**
 *
 * @author Celula4
 */
public class AccionConteo {
    private String nombre;
    private long cantidad;

    public AccionConteo() {
    }

    public AccionConteo(String nombre, long cantidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombreAccion(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the cantidad
     */
    public long getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AccionConteo other = (AccionConteo) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }
    
    
    
}
