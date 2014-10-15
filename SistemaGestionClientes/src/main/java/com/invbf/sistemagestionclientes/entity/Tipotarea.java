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
@Table(name = "TiposTareas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipostareas.findAll", query = "SELECT t FROM Tipotarea t"),
    @NamedQuery(name = "Tipostareas.findByIdTipotarea", query = "SELECT t FROM Tipotarea t WHERE t.idTipotarea = :idTipotarea"),
    @NamedQuery(name = "Tipostareas.findByNombre", query = "SELECT t FROM Tipotarea t WHERE t.nombre = :nombre")})
public class Tipotarea implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipotarea")
    private Integer idTipotarea;
    @Column(name = "nombre")
    private String nombre;
    @ManyToMany(mappedBy = "tipostareasList")
    private List<Accion> accionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipo")
    private List<Tarea> tareasList;

    public Tipotarea() {
    }

    public Tipotarea(Integer idTipotarea) {
        this.idTipotarea = idTipotarea;
    }

    public Integer getIdTipotarea() {
        return idTipotarea;
    }

    public void setIdTipotarea(Integer idTipotarea) {
        this.idTipotarea = idTipotarea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Accion> getAccionList() {
        return accionList;
    }

    public void setAccionList(List<Accion> accionList) {
        this.accionList = accionList;
    }

    @XmlTransient
    public List<Tarea> getTareasList() {
        return tareasList;
    }

    public void setTareasList(List<Tarea> tareasList) {
        this.tareasList = tareasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipotarea != null ? idTipotarea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipotarea)) {
            return false;
        }
        Tipotarea other = (Tipotarea) object;
        if ((this.idTipotarea == null && other.idTipotarea != null) || (this.idTipotarea != null && !this.idTipotarea.equals(other.idTipotarea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.adminclientesapi.entity.Tipostareas[ idTipotarea=" + idTipotarea + " ]";
    }
    
}
