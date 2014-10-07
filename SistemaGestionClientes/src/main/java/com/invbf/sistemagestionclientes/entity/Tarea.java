/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ideacentre
 */
@Entity
@Table(name = "tareas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tareas.findAll", query = "SELECT t FROM Tarea t"),
    @NamedQuery(name = "Tareas.findByIdTarea", query = "SELECT t FROM Tarea t WHERE t.idTarea = :idTarea"),
    @NamedQuery(name = "Tareas.findByNombre", query = "SELECT t FROM Tarea t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Tareas.findByFechaInicio", query = "SELECT t FROM Tarea t WHERE t.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Tareas.findByFechaFinalizacion", query = "SELECT t FROM Tarea t WHERE t.fechaFinalizacion = :fechaFinalizacion"),
    @NamedQuery(name = "Tareas.findByDescripcion", query = "SELECT t FROM Tarea t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "Tareas.findByEstado", query = "SELECT t FROM Tarea t WHERE t.estado = :estado"),
    @NamedQuery(name = "Tareas.findBySpeech", query = "SELECT t FROM Tarea t WHERE t.speech = :speech"),
    @NamedQuery(name = "Tareas.findByCategorias", query = "SELECT t FROM Tarea t WHERE t.categorias = :categorias"),
    @NamedQuery(name = "Tareas.findByTiposdejuegos", query = "SELECT t FROM Tarea t WHERE t.tiposdejuegos = :tiposdejuegos")})
public class Tarea implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTarea")
    private Integer idTarea;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @Column(name = "fechaFinalizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinalizacion;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "estado")
    private String estado;
    @Column(name = "speech")
    private String speech;
    @Column(name = "categorias")
    private String categorias;
    @Column(name = "tiposdejuegos")
    private String tiposdejuegos;
    @JoinTable(name = "tareausuarios", joinColumns = {
        @JoinColumn(name = "idTarea", referencedColumnName = "idTarea")}, inverseJoinColumns = {
        @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")})
    @ManyToMany
    private List<Usuario> usuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tareas")
    private List<Listasclientestareas> listasclientestareasList;
    @JoinColumn(name = "idEvento", referencedColumnName = "idEvento")
    @ManyToOne(optional = false)
    private Evento idEvento;
    @JoinColumn(name = "tipo", referencedColumnName = "idTipotarea")
    @ManyToOne(optional = false)
    private Tipotarea tipo;

    public Tarea() {
    }

    public Tarea(Integer idTarea) {
        this.idTarea = idTarea;
    }

    public Tarea(Integer idTarea, String nombre) {
        this.idTarea = idTarea;
        this.nombre = nombre;
    }

    public Integer getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Integer idTarea) {
        this.idTarea = idTarea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(Date fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    public String getCategorias() {
        return categorias;
    }

    public void setCategorias(String categorias) {
        this.categorias = categorias;
    }

    public String getTiposdejuegos() {
        return tiposdejuegos;
    }

    public void setTiposdejuegos(String tiposdejuegos) {
        this.tiposdejuegos = tiposdejuegos;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @XmlTransient
    public List<Listasclientestareas> getListasclientestareasList() {
        return listasclientestareasList;
    }

    public void setListasclientestareasList(List<Listasclientestareas> listasclientestareasList) {
        this.listasclientestareasList = listasclientestareasList;
    }

    public Evento getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Evento idEvento) {
        this.idEvento = idEvento;
    }

    public Tipotarea getTipo() {
        return tipo;
    }

    public void setTipo(Tipotarea tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTarea != null ? idTarea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tarea)) {
            return false;
        }
        Tarea other = (Tarea) object;
        if ((this.idTarea == null && other.idTarea != null) || (this.idTarea != null && !this.idTarea.equals(other.idTarea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.adminclientesapi.entity.Tareas[ idTarea=" + idTarea + " ]";
    }
    
}
