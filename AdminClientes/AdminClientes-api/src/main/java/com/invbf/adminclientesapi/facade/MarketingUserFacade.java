/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.facade;

import com.invbf.adminclientesapi.entity.Atributos;
import com.invbf.adminclientesapi.entity.Casinos;
import com.invbf.adminclientesapi.entity.Categorias;
import com.invbf.adminclientesapi.entity.Clientes;
import com.invbf.adminclientesapi.entity.Estadoscliente;
import com.invbf.adminclientesapi.entity.Eventos;
import com.invbf.adminclientesapi.entity.Perfiles;
import com.invbf.adminclientesapi.entity.Tiposjuegos;
import com.invbf.adminclientesapi.entity.Usuarios;
import java.util.List;

/**
 *
 * @author ideacentre
 */
public interface MarketingUserFacade {

    public List<Clientes> findAllClientes();

    public List<Estadoscliente> findAllEstadosClietes();

    public Estadoscliente findByIdEstadoCliente(int idEstadoCliente);

    public void deleteEstadoCliente(Estadoscliente estadoCliente);

    public void guardarEstadoCliente(Estadoscliente estadoscliente);

    public List<Categorias> findAllCategorias();

    public void deleteCategorias(Categorias elemento);

    public void guardarCategorias(Categorias elemento);

    public List<Atributos> findAllAtributos();

    public void deleteAtributos(Atributos elemento);

    public void guardarAtributos(Atributos elemento);

    public void deleteTiposjuegos(Tiposjuegos elemento);

    public List<Tiposjuegos> findAllTiposjuegos();

    public void guardarTiposjuegos(Tiposjuegos elemento);

    public void deleteCasinos(Casinos elemento);

    public void guardarCasinos(Casinos elemento);

    public List<Casinos> findAllCasinos();

    public List<Eventos> findAllEventos();

    public void deleteEventos(Eventos elemento);

    public void guardarEventos(Eventos elemento);

    public void deleteClientes(Clientes elemento);

    public void guardarClientes(Clientes elemento);

    public List<Tiposjuegos> getTiposJuegosNoClientes(Integer idCliente);

    public Clientes findCliente(Integer integer);

}
