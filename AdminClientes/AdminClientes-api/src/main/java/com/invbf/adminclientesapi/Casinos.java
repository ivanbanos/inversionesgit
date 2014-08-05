/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "casinos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Casinos.findAll", query = "SELECT c FROM Casinos c"),
    @NamedQuery(name = "Casinos.findByIdCasino", query = "SELECT c FROM Casinos c WHERE c.idCasino = :idCasino"),
    @NamedQuery(name = "Casinos.findByNombre", query = "SELECT c FROM Casinos c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Casinos.findByDireccion", query = "SELECT c FROM Casinos c WHERE c.direccion = :direccion")})
public class Casinos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCasino")
    private Integer idCasino;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "direccion")
    private String direccion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCasino")
    private List<Eventos> eventosList;
    @OneToMany(mappedBy = "idCasinoPreferencial")
    private List<Clientes> clientesList;

    public Casinos() {
    }

    public Casinos(Integer idCasino) {
        this.idCasino = idCasino;
    }

    public Casinos(Integer idCasino, String nombre) {
        this.idCasino = idCasino;
        this.nombre = nombre;
    }

    public Integer getIdCasino() {
        return idCasino;
    }

    public void setIdCasino(Integer idCasino) {
        this.idCasino = idCasino;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @XmlTransient
    public List<Eventos> getEventosList() {
        return eventosList;
    }

    public void setEventosList(List<Eventos> eventosList) {
        this.eventosList = eventosList;
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
        hash += (idCasino != null ? idCasino.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Casinos)) {
            return false;
        }
        Casinos other = (Casinos) object;
        if ((this.idCasino == null && other.idCasino != null) || (this.idCasino != null && !this.idCasino.equals(other.idCasino))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.adminclientesapi.Casinos[ idCasino=" + idCasino + " ]";
    }
    
}
