/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.entity;

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
@Table(name = "vistas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vistas.findAll", query = "SELECT v FROM Vista v"),
    @NamedQuery(name = "Vistas.findByIdVista", query = "SELECT v FROM Vista v WHERE v.idVista = :idVista"),
    @NamedQuery(name = "Vistas.findByNombreVista", query = "SELECT v FROM Vista v WHERE v.nombreVista = :nombreVista")})
public class Vista implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idVista")
    private Integer idVista;
    @Basic(optional = false)
    @Column(name = "nombreVista")
    private String nombreVista;
    @ManyToMany(mappedBy = "vistasList")
    private List<Perfil> perfilesList;

    public Vista() {
    }

    public Vista(Integer idVista) {
        this.idVista = idVista;
    }

    public Vista(Integer idVista, String nombreVista) {
        this.idVista = idVista;
        this.nombreVista = nombreVista;
    }

    public Integer getIdVista() {
        return idVista;
    }

    public void setIdVista(Integer idVista) {
        this.idVista = idVista;
    }

    public String getNombreVista() {
        return nombreVista;
    }

    public void setNombreVista(String nombreVista) {
        this.nombreVista = nombreVista;
    }

    @XmlTransient
    public List<Perfil> getPerfilesList() {
        return perfilesList;
    }

    public void setPerfilesList(List<Perfil> perfilesList) {
        this.perfilesList = perfilesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVista != null ? idVista.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vista)) {
            return false;
        }
        Vista other = (Vista) object;
        if ((this.idVista == null && other.idVista != null) || (this.idVista != null && !this.idVista.equals(other.idVista))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idVista + " "+nombreVista;
    }
    
}
