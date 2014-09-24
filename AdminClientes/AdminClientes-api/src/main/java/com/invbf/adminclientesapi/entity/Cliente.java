/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.entity;

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
@Table(name = "clientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Clientes.findByIdCliente", query = "SELECT c FROM Cliente c WHERE c.idCliente = :idCliente"),
    @NamedQuery(name = "Clientes.findByNombres", query = "SELECT c FROM Cliente c WHERE c.nombres = :nombres"),
    @NamedQuery(name = "Clientes.findByApellidos", query = "SELECT c FROM Cliente c WHERE c.apellidos = :apellidos"),
    @NamedQuery(name = "Clientes.findByTelefono1", query = "SELECT c FROM Cliente c WHERE c.telefono1 = :telefono1"),
    @NamedQuery(name = "Clientes.findByTelefono2", query = "SELECT c FROM Cliente c WHERE c.telefono2 = :telefono2"),
    @NamedQuery(name = "Clientes.findByIdentificacion", query = "SELECT c FROM Cliente c WHERE c.identificacion = :identificacion"),
    @NamedQuery(name = "Clientes.findByCorreo", query = "SELECT c FROM Cliente c WHERE c.correo = :correo")})
public class Cliente implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Listasclientestareas> listasclientestareasList;
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
    @Column(name = "telefono1")
    private String telefono1;
    @Column(name = "genero")
    private String genero;
    @Column(name = "telefono2")
    private String telefono2;
    @Column(name = "pais")
    private String pais;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "ciudad")
    private String ciudad;
    @Column(name = "bonoFidelizacion")
    private String bonoFidelizacion;
    @Column(name = "cumpleanos")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cumpleanos;
    @Column(name = "correo")
    private String correo;
    @Column(name = "identificacion")
    private String identificacion;
    @JoinTable(name = "clientestiposjuegos", joinColumns = {
        @JoinColumn(name = "idCliente", referencedColumnName = "idCliente")}, inverseJoinColumns = {
        @JoinColumn(name = "idTipoJuego", referencedColumnName = "idTipoJuego")})
    @ManyToMany
    private List<TipoJuego> tiposjuegosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientes")
    private List<Clienteatributo> clientesatributosList;
    @JoinColumn(name = "idCategorias", referencedColumnName = "idCategorias")
    @ManyToOne(optional = false)
    private Categoria idCategorias;
    @JoinColumn(name = "idCasinoPreferencial", referencedColumnName = "idCasino")
    @ManyToOne
    private Casino idCasinoPreferencial;
    @JoinColumn(name = "idTipoDocumento", referencedColumnName = "idTipoDocumento")
    @ManyToOne
    private TipoDocumento idTipoDocumento;

    public Cliente() {
    }

    public Cliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Cliente(Integer idCliente, String nombres, String apellidos) {
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

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    @XmlTransient
    public List<TipoJuego> getTiposjuegosList() {
        return tiposjuegosList;
    }

    public void setTiposjuegosList(List<TipoJuego> tiposjuegosList) {
        this.tiposjuegosList = tiposjuegosList;
    }

    @XmlTransient
    public List<Clienteatributo> getClientesatributosList() {
        return clientesatributosList;
    }

    public void setClientesatributosList(List<Clienteatributo> clientesatributosList) {
        this.clientesatributosList = clientesatributosList;
    }

    public Categoria getIdCategorias() {
        return idCategorias;
    }

    public void setIdCategorias(Categoria idCategorias) {
        this.idCategorias = idCategorias;
    }

    public Casino getIdCasinoPreferencial() {
        return idCasinoPreferencial;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getBonoFidelizacion() {
        return bonoFidelizacion;
    }

    public void setBonoFidelizacion(String bonoFidelizacion) {
        this.bonoFidelizacion = bonoFidelizacion;
    }

    public void setIdCasinoPreferencial(Casino idCasinoPreferencial) {
        this.idCasinoPreferencial = idCasinoPreferencial;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idCliente + " " + nombres + " "+ apellidos;
    }

    @XmlTransient
    public List<Listasclientestareas> getListasclientestareasList() {
        return listasclientestareasList;
    }

    public void setListasclientestareasList(List<Listasclientestareas> listasclientestareasList) {
        this.listasclientestareasList = listasclientestareasList;
    }

    public Date getCumpleanos() {
        return cumpleanos;
    }

    public void setCumpleanos(Date cumpleanos) {
        this.cumpleanos = cumpleanos;
    }

    public TipoDocumento getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(TipoDocumento idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
}
