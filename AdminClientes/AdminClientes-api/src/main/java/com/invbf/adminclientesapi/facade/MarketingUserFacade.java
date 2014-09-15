/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.facade;

import com.invbf.adminclientesapi.entity.Atributo;
import com.invbf.adminclientesapi.entity.Casino;
import com.invbf.adminclientesapi.entity.Categoria;
import com.invbf.adminclientesapi.entity.Cliente;
import com.invbf.adminclientesapi.entity.Estadocliente;
import com.invbf.adminclientesapi.entity.Evento;
import com.invbf.adminclientesapi.entity.TipoJuego;
import com.invbf.adminclientesapi.entity.Tipoevento;
import java.util.List;

/**
 *
 * @author ideacentre
 */
public interface MarketingUserFacade {

    public List<Cliente> findAllClientes();

    public List<Estadocliente> findAllEstadosClietes();

    public Estadocliente findByIdEstadoCliente(int idEstadoCliente);

    public void deleteEstadoCliente(Estadocliente estadoCliente);

    public boolean guardarEstadoCliente(Estadocliente estadoscliente);

    public List<Categoria> findAllCategorias();

    public void deleteCategorias(Categoria elemento);

    public boolean guardarCategorias(Categoria elemento);

    public List<Atributo> findAllAtributos();

    public void deleteAtributos(Atributo elemento);

    public boolean guardarAtributos(Atributo elemento);

    public void deleteTiposjuegos(TipoJuego elemento);

    public List<TipoJuego> findAllTiposjuegos();

    public boolean guardarTiposjuegos(TipoJuego elemento);

    public void deleteCasinos(Casino elemento);

    public boolean guardarCasinos(Casino elemento);

    public List<Casino> findAllCasinos();

    public List<Evento> findAllEventos();

    public void deleteEventos(Evento elemento);

    public Evento guardarEventos(Evento elemento);

    public void deleteClientes(Cliente elemento);

    public Cliente guardarClientes(Cliente elemento);

    public List<TipoJuego> getTiposJuegosNoClientes(Integer idCliente);

    public Cliente findCliente(Integer integer);

    public Evento findEvento(Integer integer);

    public Estadocliente findByNombreEstadoCliente(String iniciado);

    public void guardarImagen(byte[] contents, Integer idEvento, String fileName);

    public List<Tipoevento> findAllTipoevento();

    public void deleteTipoevento(Tipoevento elemento);

    public boolean guardarTipoevento(Tipoevento elemento);

}
