/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.sistemagestionclientes.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ideacentre
 */
@Entity
@Table(name = "logs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Logs.findAll", query = "SELECT l FROM Log l"),
    @NamedQuery(name = "Logs.findByIdLog", query = "SELECT l FROM Log l WHERE l.idLog = :idLog"),
    @NamedQuery(name = "Logs.findByFecha", query = "SELECT l FROM Log l WHERE l.fecha = :fecha"),
    @NamedQuery(name = "Logs.findByMensaje", query = "SELECT l FROM Log l WHERE l.mensaje = :mensaje")})
public class Log implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idLog")
    private Integer idLog;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "mensaje")
    private String mensaje;
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;
    @JoinColumn(name = "idFormulario", referencedColumnName = "idFormulario")
    @ManyToOne
    private Formulario idFormulario;

    public Log() {
    }

    public Log(Integer idLog) {
        this.idLog = idLog;
    }

    public Log(Integer idLog, Date fecha) {
        this.idLog = idLog;
        this.fecha = fecha;
    }

    public Integer getIdLog() {
        return idLog;
    }

    public void setIdLog(Integer idLog) {
        this.idLog = idLog;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Formulario getIdFormulario() {
        return idFormulario;
    }

    public void setIdFormulario(Formulario idFormulario) {
        this.idFormulario = idFormulario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLog != null ? idLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Log)) {
            return false;
        }
        Log other = (Log) object;
        if ((this.idLog == null && other.idLog != null) || (this.idLog != null && !this.idLog.equals(other.idLog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.adminclientesapi.entity.Logs[ idLog=" + idLog + " ]";
    }
    
}
