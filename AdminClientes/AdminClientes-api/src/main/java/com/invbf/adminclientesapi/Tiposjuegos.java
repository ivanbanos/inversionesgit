/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ideacentre
 */
@Entity
@Table(name = "tiposjuegos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tiposjuegos.findAll", query = "SELECT t FROM Tiposjuegos t"),
    @NamedQuery(name = "Tiposjuegos.findByIdTipoJuego", query = "SELECT t FROM Tiposjuegos t WHERE t.idTipoJuego = :idTipoJuego"),
    @NamedQuery(name = "Tiposjuegos.findByNombre", query = "SELECT t FROM Tiposjuegos t WHERE t.nombre = :nombre")})
public class Tiposjuegos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipoJuego")
    private Integer idTipoJuego;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @ManyToMany(mappedBy = "tiposjuegosList")
    private List<Clientes> clientesList;

    public Tiposjuegos() {
    }

    public Tiposjuegos(Integer idTipoJuego) {
        this.idTipoJuego = idTipoJuego;
    }

    public Tiposjuegos(Integer idTipoJuego, String nombre) {
        this.idTipoJuego = idTipoJuego;
        this.nombre = nombre;
    }

    public Integer getIdTipoJuego() {
        return idTipoJuego;
    }

    public void setIdTipoJuego(Integer idTipoJuego) {
        this.idTipoJuego = idTipoJuego;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Clientes> getClientesList() {
        return clientesList;
    }

    public void setClientesList(List<Clientes> clientesList) {
        this.clientesList = clientesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoJuego != null ? idTipoJuego.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tiposjuegos)) {
            return false;
        }
        Tiposjuegos other = (Tiposjuegos) object;
        if ((this.idTipoJuego == null && other.idTipoJuego != null) || (this.idTipoJuego != null && !this.idTipoJuego.equals(other.idTipoJuego))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.adminclientesapi.Tiposjuegos[ idTipoJuego=" + idTipoJuego + " ]";
    }
    
}
