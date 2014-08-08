/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.invbf.adminclientesapi.facade;

import com.invbf.adminclientesapi.Atributos;
import com.invbf.adminclientesapi.Casinos;
import com.invbf.adminclientesapi.Categorias;
import com.invbf.adminclientesapi.Clientes;
import com.invbf.adminclientesapi.Estadoscliente;
import com.invbf.adminclientesapi.Eventos;
import com.invbf.adminclientesapi.Perfiles;
import com.invbf.adminclientesapi.Tiposjuegos;
import com.invbf.adminclientesapi.Usuarios;
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



}
