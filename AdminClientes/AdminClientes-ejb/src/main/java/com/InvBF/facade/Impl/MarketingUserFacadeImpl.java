/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.InvBF.facade.Impl;

import com.InvBF.EntityFacade.AtributosFacadeLocal;
import com.InvBF.EntityFacade.CasinosFacadeLocal;
import com.InvBF.EntityFacade.CategoriasFacadeLocal;
import com.InvBF.EntityFacade.ClientesFacadeLocal;
import com.InvBF.EntityFacade.EstadosclienteFacadeLocal;
import com.InvBF.EntityFacade.EventosFacadeLocal;
import com.InvBF.EntityFacade.TiposjuegosFacadeLocal;
import com.invbf.adminclientesapi.entity.Atributos;
import com.invbf.adminclientesapi.entity.Casinos;
import com.invbf.adminclientesapi.entity.Categorias;
import com.invbf.adminclientesapi.entity.Clientes;
import com.invbf.adminclientesapi.entity.Estadoscliente;
import com.invbf.adminclientesapi.entity.Eventos;
import com.invbf.adminclientesapi.entity.Tiposjuegos;
import com.invbf.adminclientesapi.facade.MarketingUserFacade;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ideacentre
 */
@Stateless
public class MarketingUserFacadeImpl implements MarketingUserFacade {

    @EJB
    ClientesFacadeLocal clientesFacadeLocal;
    @EJB
    EstadosclienteFacadeLocal estadosclienteFacadeLocal;
    @EJB
    CategoriasFacadeLocal categoriasFacadeLocal;
    @EJB
    AtributosFacadeLocal atributosFacadeLocal;
    @EJB
    TiposjuegosFacadeLocal tiposjuegosFacadeLocal;
    @EJB
    CasinosFacadeLocal casinosFacadeLocal;
    @EJB
    EventosFacadeLocal eventosFacadeLocal;

    @Override
    public List<Clientes> findAllClientes() {
        return clientesFacadeLocal.findAll();
    }

    @Override
    public List<Estadoscliente> findAllEstadosClietes() {
        return estadosclienteFacadeLocal.findAll();
    }

    @Override
    public Estadoscliente findByIdEstadoCliente(int idEstadoCliente) {
        return estadosclienteFacadeLocal.find(new Estadoscliente(idEstadoCliente));
    }

    @Override
    public void deleteEstadoCliente(Estadoscliente estadoCliente) {
        estadosclienteFacadeLocal.remove(estadoCliente);
    }

    @Override
    public boolean guardarEstadoCliente(Estadoscliente elemento) {
        if (elemento.getIdEstadoCliente() == null) {
            estadosclienteFacadeLocal.create(elemento);
            return false;
        } else {
            estadosclienteFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public List<Categorias> findAllCategorias() {
        return categoriasFacadeLocal.findAll();
    }

    @Override
    public void deleteCategorias(Categorias elemento) {
        categoriasFacadeLocal.remove(elemento);
    }

    @Override
    public boolean guardarCategorias(Categorias elemento) {
        if (elemento.getIdCategorias() == null) {
            categoriasFacadeLocal.create(elemento);
            return false;
        } else {
            categoriasFacadeLocal.edit(elemento);
            return true;
        }
        
    }

    @Override
    public List<Atributos> findAllAtributos() {
        return atributosFacadeLocal.findAll();
    }

    @Override
    public void deleteAtributos(Atributos elemento) {
        atributosFacadeLocal.remove(elemento);
    }

    @Override
    public boolean guardarAtributos(Atributos elemento) {
        if (elemento.getIdAtributo() == null) {
            atributosFacadeLocal.create(elemento);
            return false;
        } else {
            atributosFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public void deleteTiposjuegos(Tiposjuegos elemento) {
        tiposjuegosFacadeLocal.remove(elemento);
    }

    @Override
    public List<Tiposjuegos> findAllTiposjuegos() {
        return tiposjuegosFacadeLocal.findAll();
    }

    @Override
    public boolean guardarTiposjuegos(Tiposjuegos elemento) {
        if (elemento.getIdTipoJuego() == null) {
            tiposjuegosFacadeLocal.create(elemento);
            return true;
        } else {
            tiposjuegosFacadeLocal.edit(elemento);
            return true;
        }
        
    }

    @Override
    public void deleteCasinos(Casinos elemento) {
        casinosFacadeLocal.remove(elemento);
    }

    @Override
    public boolean guardarCasinos(Casinos elemento) {
        if (elemento.getIdCasino() == null) {
            casinosFacadeLocal.create(elemento);
            return false;
        } else {
            casinosFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public List<Casinos> findAllCasinos() {
        return casinosFacadeLocal.findAll();
    }

    @Override
    public List<Eventos> findAllEventos() {
        return eventosFacadeLocal.findAll();
    }

    @Override
    public void deleteEventos(Eventos elemento) {
        eventosFacadeLocal.remove(elemento);
    }

    @Override
    public Eventos guardarEventos(Eventos elemento) {
        if (elemento.getIdEvento() == null) {
            eventosFacadeLocal.create(elemento);
            return elemento;
        } else {
            eventosFacadeLocal.edit(elemento);
            return elemento;
        }
    }

    @Override
    public void deleteClientes(Clientes elemento) {
        clientesFacadeLocal.remove(elemento);
    }

    @Override
    public boolean guardarClientes(Clientes elemento) {
        if (elemento.getIdCliente() == null) {
            clientesFacadeLocal.create(elemento);
            return false;
        } else {
            clientesFacadeLocal.edit(elemento);
            return true;
        }
    }

    @Override
    public List<Tiposjuegos> getTiposJuegosNoClientes(Integer idCliente) {
        Clientes cliente = clientesFacadeLocal.find(idCliente);
        List<Tiposjuegos> tiposjuego = tiposjuegosFacadeLocal.findAll();
        Iterator<Tiposjuegos> iter = tiposjuego.iterator();
        while (iter.hasNext()) {
            if (iter.next().getClientesList().contains(cliente)) {
                iter.remove();
            }
        }
        return tiposjuego;
    }

    @Override
    public Clientes findCliente(Integer integer) {
        return clientesFacadeLocal.find(integer);
    }

    @Override
    public Eventos findEvento(Integer integer) {
        return eventosFacadeLocal.find(integer);
    }

    @Override
    public Estadoscliente findByNombreEstadoCliente(String iniciado) {
        return estadosclienteFacadeLocal.findByNombreEstadoCliente(iniciado);
    }
   
}
