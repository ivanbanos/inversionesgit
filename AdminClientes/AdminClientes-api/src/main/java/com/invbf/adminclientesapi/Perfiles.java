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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "perfiles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Perfiles.findAll", query = "SELECT p FROM Perfiles p"),
    @NamedQuery(name = "Perfiles.findByIdPerfil", query = "SELECT p FROM Perfiles p WHERE p.idPerfil = :idPerfil"),
    @NamedQuery(name = "Perfiles.findByNombre", query = "SELECT p FROM Perfiles p WHERE p.nombre = :nombre")})
public class Perfiles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPerfil")
    private Integer idPerfil;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @JoinTable(name = "perfilesformularios", joinColumns = {
        @JoinColumn(name = "idPerfil", referencedColumnName = "idPerfil")}, inverseJoinColumns = {
        @JoinColumn(name = "idFormulario", referencedColumnName = "idFormulario")})
    @ManyToMany
    private List<Formularios> formulariosList;
    @JoinTable(name = "perfilesvistas", joinColumns = {
        @JoinColumn(name = "idPerfil", referencedColumnName = "idPerfil")}, inverseJoinColumns = {
        @JoinColumn(name = "idVista", referencedColumnName = "idVista")})
    @ManyToMany
    private List<Vistas> vistasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPerfil")
    private List<Usuarios> usuariosList;

    public Perfiles() {
    }

    public Perfiles(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Perfiles(Integer idPerfil, String nombre) {
        this.idPerfil = idPerfil;
        this.nombre = nombre;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Formularios> getFormulariosList() {
        return formulariosList;
    }

    public void setFormulariosList(List<Formularios> formulariosList) {
        this.formulariosList = formulariosList;
    }

    @XmlTransient
    public List<Vistas> getVistasList() {
        return vistasList;
    }

    public void setVistasList(List<Vistas> vistasList) {
        this.vistasList = vistasList;
    }

    @XmlTransient
    public List<Usuarios> getUsuariosList() {
        return usuariosList;
    }

    public void setUsuariosList(List<Usuarios> usuariosList) {
        this.usuariosList = usuariosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPerfil != null ? idPerfil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Perfiles)) {
            return false;
        }
        Perfiles other = (Perfiles) object;
        if ((this.idPerfil == null && other.idPerfil != null) || (this.idPerfil != null && !this.idPerfil.equals(other.idPerfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.adminclientesapi.Perfiles[ idPerfil=" + idPerfil + " ]";
    }
    
}
