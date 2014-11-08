/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ideacentre
 */
@Entity
@Table(name = "Permisos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Permisos.findAll", query = "SELECT p FROM Permiso p"),
    @NamedQuery(name = "Permisos.findByIdAcciones", query = "SELECT p FROM Permiso p WHERE p.idAcciones = :idAcciones"),
    @NamedQuery(name = "Permisos.findByTipo", query = "SELECT p FROM Permiso p WHERE p.tipo = :tipo"),
    @NamedQuery(name = "Permisos.findById", query = "SELECT p FROM Permiso p WHERE p.id = :id"),
    @NamedQuery(name = "Permisos.findByTabla", query = "SELECT p FROM Permiso p WHERE p.tabla = :tabla"),
    @NamedQuery(name = "Permisos.findByCampo", query = "SELECT p FROM Permiso p WHERE p.campo = :campo"),
    @NamedQuery(name = "Permisos.findByNuevoValor", query = "SELECT p FROM Permiso p WHERE p.nuevoValor = :nuevoValor")})
public class Permiso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAcciones")
    private Integer idAcciones;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "id")
    private String id;
    @Column(name = "tabla")
    private String tabla;
    @Column(name = "campo")
    private String campo;
    @Column(name = "nuevoValor")
    private String nuevoValor;
    @JoinColumn(name = "Perfil", referencedColumnName = "idPerfil")
    @ManyToOne
    private Perfil perfil;

    public Permiso() {
    }

    public Permiso(String tipo, String id, String tabla, String campo, String nuevoValor) {
        this.tipo = tipo;
        this.id = id;
        this.tabla = tabla;
        this.campo = campo;
        this.nuevoValor = nuevoValor;
    }

    public Permiso(Integer idAcciones) {
        this.idAcciones = idAcciones;
    }

    public Integer getIdAcciones() {
        return idAcciones;
    }

    public void setIdAcciones(Integer idAcciones) {
        this.idAcciones = idAcciones;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getNuevoValor() {
        return nuevoValor;
    }

    public void setNuevoValor(String nuevoValor) {
        this.nuevoValor = nuevoValor;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAcciones != null ? idAcciones.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permiso)) {
            return false;
        }
        Permiso other = (Permiso) object;
        if ((this.idAcciones == null && other.idAcciones != null) || (this.idAcciones != null && !this.idAcciones.equals(other.idAcciones))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.adminclientesapi.entity.Permisos[ idAcciones=" + idAcciones + " ]";
    }
    
}
