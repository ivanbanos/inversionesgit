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
public class ListasclienteseventoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "idEvento")
    private int idEvento;
    @Basic(optional = false)
    @Column(name = "idCliente")
    private int idCliente;

    public ListasclienteseventoPK() {
    }

    public ListasclienteseventoPK(int idEvento, int idCliente) {
        this.idEvento = idEvento;
        this.idCliente = idCliente;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idEvento;
        hash += (int) idCliente;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListasclienteseventoPK)) {
            return false;
        }
        ListasclienteseventoPK other = (ListasclienteseventoPK) object;
        if (this.idEvento != other.idEvento) {
            return false;
        }
        if (this.idCliente != other.idCliente) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.adminclientesapi.entity.ListasclienteseventoPK[ idEvento=" + idEvento + ", idCliente=" + idCliente + " ]";
    }
    
}
