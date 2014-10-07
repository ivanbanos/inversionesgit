/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ideacentre
 */
@Entity
@Table(name = "TiposDocumento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Casinos.findAll", query = "SELECT c FROM Casino c"),
    @NamedQuery(name = "Casinos.findByIdCasino", query = "SELECT c FROM Casino c WHERE c.idCasino = :idCasino"),
    @NamedQuery(name = "Casinos.findByNombre", query = "SELECT c FROM Casino c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Casinos.findByDireccion", query = "SELECT c FROM Casino c WHERE c.direccion = :direccion")})
public class TipoDocumento implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipoDocumento")
    private Integer idTipoDocumento;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "idTipoDocumento")
    private List<Cliente> clientesList;

    public TipoDocumento() {
    }

    public TipoDocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public TipoDocumento(Integer idTipoDocumento, String nombre) {
        this.idTipoDocumento = idTipoDocumento;
        this.nombre = nombre;
    }

    public Integer getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Cliente> getClientesList() {
        return clientesList;
    }

    public void setClientesList(List<Cliente> clientesList) {
        this.clientesList = clientesList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.idTipoDocumento != null ? this.idTipoDocumento.hashCode() : 0);
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
        final TipoDocumento other = (TipoDocumento) obj;
        if (this.idTipoDocumento != other.idTipoDocumento && (this.idTipoDocumento == null || !this.idTipoDocumento.equals(other.idTipoDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TipoDocumento{" + "idTipoDocumento=" + idTipoDocumento + '}';
    }
    
}
