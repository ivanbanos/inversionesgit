/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Table(name = "tiposeventos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tiposeventos.findAll", query = "SELECT t FROM Tipoevento t"),
    @NamedQuery(name = "Tiposeventos.findByIdTiposeventos", query = "SELECT t FROM Tipoevento t WHERE t.idTiposeventos = :idTiposeventos"),
    @NamedQuery(name = "Tiposeventos.findByNombre", query = "SELECT t FROM Tipoevento t WHERE t.nombre = :nombre")})
public class Tipoevento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idTiposeventos")
    private Integer idTiposeventos;
    @Column(name = "nombre")
    private String nombre;
    @ManyToMany(mappedBy = "tiposeventosList")
    private List<Estadocliente> estadosclienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipo")
    private List<Evento> eventosList;

    public Tipoevento() {
    }

    public Tipoevento(Integer idTiposeventos) {
        this.idTiposeventos = idTiposeventos;
    }

    public Integer getIdTiposeventos() {
        return idTiposeventos;
    }

    public void setIdTiposeventos(Integer idTiposeventos) {
        this.idTiposeventos = idTiposeventos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Estadocliente> getEstadosclienteList() {
        return estadosclienteList;
    }

    public void setEstadosclienteList(List<Estadocliente> estadosclienteList) {
        this.estadosclienteList = estadosclienteList;
    }

    @XmlTransient
    public List<Evento> getEventosList() {
        return eventosList;
    }

    public void setEventosList(List<Evento> eventosList) {
        this.eventosList = eventosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTiposeventos != null ? idTiposeventos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipoevento)) {
            return false;
        }
        Tipoevento other = (Tipoevento) object;
        if ((this.idTiposeventos == null && other.idTiposeventos != null) || (this.idTiposeventos != null && !this.idTiposeventos.equals(other.idTiposeventos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.adminclientesapi.entity.Tiposeventos[ idTiposeventos=" + idTiposeventos + " ]";
    }
    
}
