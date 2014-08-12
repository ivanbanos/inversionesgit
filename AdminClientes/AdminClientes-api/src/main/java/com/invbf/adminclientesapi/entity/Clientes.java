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
import javax.persistence.ManyToOne;
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
@Table(name = "clientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c"),
    @NamedQuery(name = "Clientes.findByIdCliente", query = "SELECT c FROM Clientes c WHERE c.idCliente = :idCliente"),
    @NamedQuery(name = "Clientes.findByNombres", query = "SELECT c FROM Clientes c WHERE c.nombres = :nombres"),
    @NamedQuery(name = "Clientes.findByApellidos", query = "SELECT c FROM Clientes c WHERE c.apellidos = :apellidos")})
public class Clientes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCliente")
    private Integer idCliente;
    @Basic(optional = false)
    @Column(name = "nombres")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    @JoinTable(name = "clientestiposjuegos", joinColumns = {
        @JoinColumn(name = "idCliente", referencedColumnName = "idCliente")}, inverseJoinColumns = {
        @JoinColumn(name = "idTipoJuego", referencedColumnName = "idTipoJuego")})
    @ManyToMany
    private List<Tiposjuegos> tiposjuegosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientes")
    private List<Clientesatributos> clientesatributosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientes")
    private List<Listasclientesevento> listasclienteseventoList;
    @JoinColumn(name = "idCategorias", referencedColumnName = "idCategorias")
    @ManyToOne(optional = false)
    private Categorias idCategorias;
    @JoinColumn(name = "idCasinoPreferencial", referencedColumnName = "idCasino")
    @ManyToOne
    private Casinos idCasinoPreferencial;

    public Clientes() {
        idCategorias = new Categorias();
        idCasinoPreferencial = new Casinos();
               
    }

    public Clientes(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Clientes(Integer idCliente, String nombres, String apellidos) {
        this.idCliente = idCliente;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @XmlTransient
    public List<Tiposjuegos> getTiposjuegosList() {
        return tiposjuegosList;
    }

    public void setTiposjuegosList(List<Tiposjuegos> tiposjuegosList) {
        this.tiposjuegosList = tiposjuegosList;
    }

    @XmlTransient
    public List<Clientesatributos> getClientesatributosList() {
        return clientesatributosList;
    }

    public void setClientesatributosList(List<Clientesatributos> clientesatributosList) {
        this.clientesatributosList = clientesatributosList;
    }

    @XmlTransient
    public List<Listasclientesevento> getListasclienteseventoList() {
        return listasclienteseventoList;
    }

    public void setListasclienteseventoList(List<Listasclientesevento> listasclienteseventoList) {
        this.listasclienteseventoList = listasclienteseventoList;
    }

    public Categorias getIdCategorias() {
        return idCategorias;
    }

    public void setIdCategorias(Categorias idCategorias) {
        this.idCategorias = idCategorias;
    }

    public Casinos getIdCasinoPreferencial() {
        return idCasinoPreferencial;
    }

    public void setIdCasinoPreferencial(Casinos idCasinoPreferencial) {
        this.idCasinoPreferencial = idCasinoPreferencial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clientes)) {
            return false;
        }
        Clientes other = (Clientes) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.invbf.adminclientesapi.Clientes[ idCliente=" + idCliente + " ]";
    }
    
}
