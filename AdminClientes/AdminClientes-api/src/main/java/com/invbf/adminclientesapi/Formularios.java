/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ideacentre
 */
@Entity
@Table(name = "formularios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formularios.findAll", query = "SELECT f FROM Formularios f"),
    @NamedQuery(name = "Formularios.findByIdFormulario", query = "SELECT f FROM Formularios f WHERE f.idFormulario = :idFormulario"),
    @NamedQuery(name = "Formularios.findByTabla", query = "SELECT f FROM Formularios f WHERE f.tabla = :tabla"),
    @NamedQuery(name = "Formularios.findByAccion", query = "SELECT f FROM Formularios f WHERE f.accion = :accion")})
public class Formularios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFormulario")
    private Integer idFormulario;
    @Basic(optional = false)
    @Column(name = "tabla")
    private String tabla;
    @Basic(optional = false)
    @Column(name = "accion")
    private String accion;
    @ManyToMany(mappedBy = "formulariosList")
    private List<Perfiles> perfilesList;
    @OneToMany(mappedBy = "idFormulario")
    private List<Logs> logsList;

    public Formularios() {
    }

    public Formularios(Integer idFormulario) {
        this.idFormulario = idFormulario;
    }

    public Formularios(Integer idFormulario, String tabla, String accion) {
        this.idFormulario = idFormulario;
        this.tabla = tabla;
        this.accion = accion;
    }

    public Integer getIdFormulario() {
        return idFormulario;
    }

    public void setIdFormulario(Integer idFormulario) {
        this.idFormulario = idFormulario;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    @XmlTransient
    public List<Perfiles> getPerfilesList() {
        return perfilesList;
    }

    public void setPerfilesList(List<Perfiles> perfilesList) {
        this.perfilesList = perfilesList;
    }

    @XmlTransient
    public List<Logs> getLogsList() {
        return logsList;
    }

    public void setLogsList(List<Logs> logsList) {
        this.logsList = logsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFormulario != null ? idFormulario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Formularios)) {
            return false;
        }
        Formularios other = (Formularios) object;
        if ((this.idFormulario == null && other.idFormulario != null) || (this.idFormulario != null && !this.idFormulario.equals(other.idFormulario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.adminclientesapi.Formularios[ idFormulario=" + idFormulario + " ]";
    }
    
}
