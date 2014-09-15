/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "acciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Acciones.findAll", query = "SELECT a FROM Accion a"),
    @NamedQuery(name = "Acciones.findByIdAcciones", query = "SELECT a FROM Accion a WHERE a.idAcciones = :idAcciones"),
    @NamedQuery(name = "Acciones.findByTipo", query = "SELECT a FROM Accion a WHERE a.tipo = :tipo"),
    @NamedQuery(name = "Acciones.findById", query = "SELECT a FROM Accion a WHERE a.id = :id"),
    @NamedQuery(name = "Acciones.findByTabla", query = "SELECT a FROM Accion a WHERE a.tabla = :tabla"),
    @NamedQuery(name = "Acciones.findByCampo", query = "SELECT a FROM Accion a WHERE a.campo = :campo"),
    @NamedQuery(name = "Acciones.findByNuevoValor", query = "SELECT a FROM Accion a WHERE a.nuevoValor = :nuevoValor")})
public class Accion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
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
    @ManyToOne(optional = false)
    private Perfil perfil;

    public Accion() {
    }

    public Accion(Integer idAcciones) {
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
        if (!(object instanceof Accion)) {
            return false;
        }
        Accion other = (Accion) object;
        if ((this.idAcciones == null && other.idAcciones != null) || (this.idAcciones != null && !this.idAcciones.equals(other.idAcciones))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.adminclientesapi.entity.Acciones[ idAcciones=" + idAcciones + " ]";
    }
    
}
