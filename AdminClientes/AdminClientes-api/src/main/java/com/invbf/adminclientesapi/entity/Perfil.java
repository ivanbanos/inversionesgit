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
@Table(name = "perfiles", schema="inversiones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Perfiles.findAll", query = "SELECT p FROM Perfil p"),
    @NamedQuery(name = "Perfiles.findByIdPerfil", query = "SELECT p FROM Perfil p WHERE p.idPerfil = :idPerfil"),
    @NamedQuery(name = "Perfiles.findByNombre", query = "SELECT p FROM Perfil p WHERE p.nombre = :nombre")})
public class Perfil implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfil")
    private List<Permiso> permisosList;
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
    private List<Formulario> formulariosList;
    @JoinTable(name = "perfilesvistas", joinColumns = {
        @JoinColumn(name = "idPerfil", referencedColumnName = "idPerfil")}, inverseJoinColumns = {
        @JoinColumn(name = "idVista", referencedColumnName = "idVista")})
    @ManyToMany
    private List<Vista> vistasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfil")
    private List<Permiso> permisoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPerfil")
    private List<Usuario> usuariosList;

    public Perfil() {
    }

    public Perfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Perfil(Integer idPerfil, String nombre) {
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
    public List<Formulario> getFormulariosList() {
        return formulariosList;
    }

    public void setFormulariosList(List<Formulario> formulariosList) {
        this.formulariosList = formulariosList;
    }

    @XmlTransient
    public List<Vista> getVistasList() {
        return vistasList;
    }

    public void setVistasList(List<Vista> vistasList) {
        this.vistasList = vistasList;
    }

    @XmlTransient
    public List<Permiso> getPermisosList() {
        return permisosList;
    }

    public void setPermisosList(List<Permiso> permisosList) {
        this.permisosList = permisosList;
    }

    @XmlTransient
    public List<Usuario> getUsuariosList() {
        return usuariosList;
    }

    public void setUsuariosList(List<Usuario> usuariosList) {
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
        if (!(object instanceof Perfil)) {
            return false;
        }
        Perfil other = (Perfil) object;
        if ((this.idPerfil == null && other.idPerfil != null) || (this.idPerfil != null && !this.idPerfil.equals(other.idPerfil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.adminclientesapi.entity.Perfiles[ idPerfil=" + idPerfil + " ]";
    }

    @XmlTransient
    public List<Permiso> getPermisoList() {
        return permisosList;
    }

    public void setPermisoList(List<Permiso> permisosList) {
        this.permisosList = permisosList;
    }
    
}
