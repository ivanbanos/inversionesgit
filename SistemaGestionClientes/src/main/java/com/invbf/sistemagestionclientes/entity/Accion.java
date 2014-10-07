/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "acciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Acciones.findAll", query = "SELECT a FROM Accion a"),
    @NamedQuery(name = "Acciones.findByNombre", query = "SELECT a FROM Accion a WHERE a.nombre = :nombre")})
public class Accion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAccion")
    private Integer idAccion;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @JoinTable(name = "acciones_has_tipostareas", joinColumns = {
        @JoinColumn(name = "idAccion", referencedColumnName = "idAccion")}, inverseJoinColumns = {
        @JoinColumn(name = "idTipoTarea", referencedColumnName = "idTipotarea")})
    @ManyToMany
    private List<Tipotarea> tipostareasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAccion")
    private List<Listasclientestareas> listasclientestareasList;

    public Accion() {
    }

    public Accion(Integer idAccion, String nombre) {
        this.idAccion = idAccion;
        this.nombre = nombre;
    }

    public Integer getIdAccion() {
        return idAccion;
    }

    public void setIdAccion(Integer idAccion) {
        this.idAccion = idAccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Tipotarea> getTipostareasList() {
        return tipostareasList;
    }

    public void setTipostareasList(List<Tipotarea> tipostareasList) {
        this.tipostareasList = tipostareasList;
    }

    @XmlTransient
    public List<Listasclientestareas> getListasclientestareasList() {
        return listasclientestareasList;
    }

    public void setListasclientestareasList(List<Listasclientestareas> listasclientestareasList) {
        this.listasclientestareasList = listasclientestareasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccion != null ? idAccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accion)) {
            return false;
        }
        Accion other = (Accion) object;
        if ((this.idAccion == null && other.idAccion != null) || (this.idAccion != null && !this.idAccion.equals(other.idAccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  idAccion + " " + nombre;
    }
    
}
