/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.entitySGB;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ivan
 */
@Entity
@Table(name = "Accesos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accesos.findAll", query = "SELECT a FROM Accesos a"),
    @NamedQuery(name = "Accesos.findById", query = "SELECT a FROM Accesos a WHERE a.id = :id"),
    @NamedQuery(name = "Accesos.findByNombre", query = "SELECT a FROM Accesos a WHERE a.nombre = :nombre")})
public class Accesos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre")
    private String nombre;
    
    
    @ManyToMany(mappedBy = "accesosList")
    private List<Usuariosdetalles> usuariosdetallesList;

    public Accesos() {
    }

    public Accesos(Integer id) {
        this.id = id;
    }

    public Accesos(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Usuariosdetalles> getUsuariosdetallesList() {
        return usuariosdetallesList;
    }

    public void setUsuariosdetallesList(List<Usuariosdetalles> usuariosdetallesList) {
        this.usuariosdetallesList = usuariosdetallesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accesos)) {
            return false;
        }
        Accesos other = (Accesos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionclientes.entitySGB.Accesos[ id=" + id + " ]";
    }
    
}
