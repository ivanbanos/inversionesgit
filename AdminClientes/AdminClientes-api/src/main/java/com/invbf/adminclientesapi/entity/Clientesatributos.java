/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ideacentre
 */
@Entity
@Table(name = "clientesatributos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientesatributos.findAll", query = "SELECT c FROM Clientesatributos c"),
    @NamedQuery(name = "Clientesatributos.findByIdCliente", query = "SELECT c FROM Clientesatributos c WHERE c.clientesatributosPK.idCliente = :idCliente"),
    @NamedQuery(name = "Clientesatributos.findByIdAtributo", query = "SELECT c FROM Clientesatributos c WHERE c.clientesatributosPK.idAtributo = :idAtributo"),
    @NamedQuery(name = "Clientesatributos.findByValor", query = "SELECT c FROM Clientesatributos c WHERE c.valor = :valor")})
public class Clientesatributos implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ClientesatributosPK clientesatributosPK;
    @Column(name = "valor")
    private String valor;
    @JoinColumn(name = "idAtributo", referencedColumnName = "idAtributo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Atributos atributos;
    @JoinColumn(name = "idCliente", referencedColumnName = "idCliente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Clientes clientes;

    public Clientesatributos() {
    }

    public Clientesatributos(ClientesatributosPK clientesatributosPK) {
        this.clientesatributosPK = clientesatributosPK;
    }

    public Clientesatributos(int idCliente, int idAtributo) {
        this.clientesatributosPK = new ClientesatributosPK(idCliente, idAtributo);
    }

    public ClientesatributosPK getClientesatributosPK() {
        return clientesatributosPK;
    }

    public void setClientesatributosPK(ClientesatributosPK clientesatributosPK) {
        this.clientesatributosPK = clientesatributosPK;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Atributos getAtributos() {
        return atributos;
    }

    public void setAtributos(Atributos atributos) {
        this.atributos = atributos;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clientesatributosPK != null ? clientesatributosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clientesatributos)) {
            return false;
        }
        Clientesatributos other = (Clientesatributos) object;
        if ((this.clientesatributosPK == null && other.clientesatributosPK != null) || (this.clientesatributosPK != null && !this.clientesatributosPK.equals(other.clientesatributosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.adminclientesapi.Clientesatributos[ clientesatributosPK=" + clientesatributosPK + " ]";
    }
    
}
