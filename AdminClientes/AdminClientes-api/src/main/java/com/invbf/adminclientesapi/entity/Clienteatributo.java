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
    @NamedQuery(name = "Clientesatributos.findAll", query = "SELECT c FROM Clienteatributo c"),
    @NamedQuery(name = "Clientesatributos.findByIdCliente", query = "SELECT c FROM Clienteatributo c WHERE c.clientesatributosPK.idCliente = :idCliente"),
    @NamedQuery(name = "Clientesatributos.findByIdAtributo", query = "SELECT c FROM Clienteatributo c WHERE c.clientesatributosPK.idAtributo = :idAtributo"),
    @NamedQuery(name = "Clientesatributos.findByValor", query = "SELECT c FROM Clienteatributo c WHERE c.valor = :valor")})
public class Clienteatributo implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ClientesatributosPK clientesatributosPK;
    @Column(name = "valor")
    private String valor;
    @JoinColumn(name = "idAtributo", referencedColumnName = "idAtributo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Atributo atributos;
    @JoinColumn(name = "idCliente", referencedColumnName = "idCliente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cliente clientes;

    public Clienteatributo() {
    }

    public Clienteatributo(ClientesatributosPK clientesatributosPK) {
        this.clientesatributosPK = clientesatributosPK;
    }

    public Clienteatributo(int idCliente, int idAtributo) {
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

    public Atributo getAtributos() {
        return atributos;
    }

    public void setAtributos(Atributo atributos) {
        this.atributos = atributos;
    }

    public Cliente getClientes() {
        return clientes;
    }

    public void setClientes(Cliente clientes) {
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
        if (!(object instanceof Clienteatributo)) {
            return false;
        }
        Clienteatributo other = (Clienteatributo) object;
        if ((this.clientesatributosPK == null && other.clientesatributosPK != null) || (this.clientesatributosPK != null && !this.clientesatributosPK.equals(other.clientesatributosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.adminclientesapi.entity.Clientesatributos[ clientesatributosPK=" + clientesatributosPK + " ]";
    }
    
}
