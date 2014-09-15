/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author ideacentre
 */
@Embeddable
public class ClientesatributosPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idCliente")
    private int idCliente;
    @Basic(optional = false)
    @Column(name = "idAtributo")
    private int idAtributo;

    public ClientesatributosPK() {
    }

    public ClientesatributosPK(int idCliente, int idAtributo) {
        this.idCliente = idCliente;
        this.idAtributo = idAtributo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdAtributo() {
        return idAtributo;
    }

    public void setIdAtributo(int idAtributo) {
        this.idAtributo = idAtributo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idCliente;
        hash += (int) idAtributo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClientesatributosPK)) {
            return false;
        }
        ClientesatributosPK other = (ClientesatributosPK) object;
        if (this.idCliente != other.idCliente) {
            return false;
        }
        if (this.idAtributo != other.idAtributo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.adminclientesapi.entity.ClientesatributosPK[ idCliente=" + idCliente + ", idAtributo=" + idAtributo + " ]";
    }
    
}
