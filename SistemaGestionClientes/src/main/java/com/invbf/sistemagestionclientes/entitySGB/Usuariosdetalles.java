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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "usuariosdetalles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuariosdetalles.findAll", query = "SELECT u FROM Usuariosdetalles u"),
    @NamedQuery(name = "Usuariosdetalles.findByIdUsuario", query = "SELECT u FROM Usuariosdetalles u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "Usuariosdetalles.findByCorreo", query = "SELECT u FROM Usuariosdetalles u WHERE u.correo = :correo")})
public class Usuariosdetalles implements Serializable {
    @JoinTable(name = "UsuariosDetalles_has_Accesos", joinColumns = {
        @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")}, inverseJoinColumns = {
        @JoinColumn(name = "Accesos_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Accesos> accesosList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idUsuario")
    private Integer idUsuario;
    @Size(max = 45)
    @Column(name = "correo")
    private String correo;
    @JoinColumn(name = "idcargo", referencedColumnName = "idcargo")
    @ManyToOne
    private Cargos idcargo;

    public Usuariosdetalles() {
    }

    public Usuariosdetalles(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Cargos getIdcargo() {
        return idcargo;
    }

    public void setIdcargo(Cargos idcargo) {
        this.idcargo = idcargo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuariosdetalles)) {
            return false;
        }
        Usuariosdetalles other = (Usuariosdetalles) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.sistemagestionbonos.entity.Usuariosdetalles[ idUsuario=" + idUsuario + " ]";
    }

    @XmlTransient
    public List<Accesos> getAccesosList() {
        return accesosList;
    }

    public void setAccesosList(List<Accesos> accesosList) {
        this.accesosList = accesosList;
    }
    
}
